import java.io.*;
import java.util.*;

import org.jdom.*;
import org.jdom.xpath.*;

import com.bdi.Shared.Util.*;
import com.bdi.SPS.Db.*;
import com.bdi.SPS.JobStep.Common.*;
import com.bdi.SPS.Misc.*;
import com.bdi.SPS.XML.*;
import com.bdi.SPS.XML.JobTypeHandlers.JobTypeHandler;

public class FastForwardReportFilter {

	private JobStepBase jobStep = null;
	private Jobs 		jobs = null;

	// keep track of a list of image filenames
	// that's to make it easier to identify different messages
	private HashMap acctHolderMap = null;
	private BufferedWriter logFile = null;
	
	private static String location = "CustomJava/FastForwardReportFilter";
	
	private static final int DEFAULT_INITIAL_SIZE = 10000;

	private static final int UNKNOWN = 0;
	private static final int ACCTHOLDER_NAME = 1;
	private static final int ACCTHOLDER_ADDRESS = 2;

	public FastForwardReportFilter() {
	}

	public FastForwardReportFilter( JobStepBase step ) {
		jobStep = step;
	}

	public boolean filterReport( String inputFilename, String reportFilename ) throws InterruptedException {
		String filteredReport = null, exceptionReport = null; 
		BufferedReader reportReader = null;
		BufferedWriter filteredWriter = null, exceptionWriter = null;
		
		CommonLog.getLog().write( location, "inputFilename: " + inputFilename );
		CommonLog.getLog().write( location, "reportFilename: " + reportFilename );
		
		// from the input filename, get job id
		inputFilename = new File( inputFilename ).getName();
		String custId = inputFilename.substring( 0, inputFilename.indexOf( '.' ) );
		String jobId = inputFilename.substring( inputFilename.indexOf( '.' ) + 1 ).trim();
		if( jobId.indexOf( '.' ) >= 0 ) {
			jobId = jobId.substring( 0, jobId.indexOf( '.' ) );
		}
		int spsJobId = Integer.parseInt( jobId );
		
		// basename
		String path = new File( reportFilename ).getParent();
		String basename = new File( reportFilename ).getName();
		basename = new File( path, basename.substring( 0, basename.lastIndexOf( "." ) ) ).getPath();
		basename += "_" + jobId;
		String extension = reportFilename.substring( reportFilename.lastIndexOf( "." ) );
		
		// log filename
		String logFilename = basename + ".log";

		// get XML/XMLi files from the output of the parser step
		CommonLog.getLog().write( location, "Loading account holders for job id: " + spsJobId );
		if( !loadAccountHolders( spsJobId ) ) {
			CommonLog.getLog().write( location, "Error loading account holders for job id: " + spsJobId );
			return false;
		}
		CommonLog.getLog().write( location, "Loading complete" );

		// use nested try/catch blocks to close files gracefully and cleanly
		// while logging all messages properly
		try {
			try {
				// load input input
				reportReader = new BufferedReader( new FileReader( reportFilename ) );
				// create filtered report file
				filteredReport = basename + "_filtered" + extension;
				filteredWriter = new BufferedWriter( new FileWriter( filteredReport ) );
				// create exception report file
				exceptionReport = basename + "_exception" + extension;
				exceptionWriter = new BufferedWriter( new FileWriter( exceptionReport ) );
				// create log file
				logFile = new BufferedWriter( new FileWriter( logFilename ) );
				
				// write header to both output reports
				String line = reportReader.readLine();
				if( line == null ) {
					throw new KnownException( "Report file is empty" );
				}
				filteredWriter.write( line );
				filteredWriter.newLine();
				exceptionWriter.write( line );
				exceptionWriter.newLine();
				
				// for each line in the input file: get name and old address lines
				int inputCount = 0, inputDupCount = 0, filteredReportLines = 0, filteredInputCount = 0, exceptionReportLines = 0;
				int lastHash = 0, curHash = 0;
				String lastLine = null;
				while( ( line = reportReader.readLine() ) != null ) {
					// if line doesn't have tabs, save on both files (footer)
					if( line.indexOf( "\t" ) < 0 ) {
						// if reporting number of records, adjust # of records for filtered report
						if( line.indexOf( "# of Records:" ) >= 0 ) {
							line = "# of Records: " + filteredReportLines;
						}
						// add line to the filtered report
						filteredWriter.write( line );
						filteredWriter.newLine();
						
						// if reporting number of records, adjust # of records for exception report
						if( line.indexOf( "# of Records:" ) >= 0 ) {
							line = "# of Records: " + exceptionReportLines;
						}
						// and to the exception report
						exceptionWriter.write( line );
						exceptionWriter.newLine();
						
						continue;
					}
					
					// increment input count;
					inputCount++;
					
					// skip repeated lines
					curHash = line.hashCode();
					if( curHash == lastHash ) {
						if( line.equals( lastLine ) ) {
							// increment duplicate count
							inputDupCount++;
							continue;
						}
					}
						
					// check whether address lines match
					AccountHolder[] acctholders = getMatchingAccountHolders( line );
					if( acctholders != null ? acctholders.length > 0 : false ) {
						// increment input count
						filteredInputCount++;
						
						// add one line per accountholder found
						for( int j=0; j<acctholders.length; j++ ) {
							// add line to the filtered report
							filteredWriter.write( line );
							// add account id
							filteredWriter.write( "\t" + acctholders[j].getAcctId() );
							
							// add names to the end of the line
							String[] names = acctholders[j].getNames();
							for( int j2=0; j2<3; j2++ ) {
								filteredWriter.write( "\t" );
								if( j2 < names.length ) {
									filteredWriter.write( names[j2] );
								}
							}
							// add address lines to the end of the line
							String[] addrLines = acctholders[j].getAddresses();
							for( int j2=0; j2<addrLines.length; j2++ ) {
								filteredWriter.write( "\t" + addrLines[j2] );
							}
	
							filteredWriter.newLine();
							
							// reset match on accountholder
							acctholders[j].resetMatch();
	
							// increment line counter for the filtered report
							filteredReportLines++;
						}
					}
					// otherwise
					else {
						// add line to the exception report
						exceptionWriter.write( line );
						exceptionWriter.newLine();
						// increment line counter for the exception report
						exceptionReportLines++;
					}
					
					// store current line and hash as last
					lastHash = curHash;
					lastLine = line;
				}
				try {
					logFile.write( "Total records in input file: " + inputCount + " - Duplicate: " + inputDupCount + " - Unique: " + (inputCount-inputDupCount) );
					logFile.newLine();
					logFile.write( "Records found in job (filtered): " + filteredInputCount + " - Matching accounts on filtered report: " + filteredReportLines );
					logFile.newLine();
					logFile.write( "Records not found in job (exception): " + exceptionReportLines );
					logFile.newLine();
				}
				catch( Exception e ) {
					// just log, don't error out
					CommonLog.getLog().write( location, "Error writing to log file: ", e );
				}
	
				// look for empty filtered report
				if( filteredReportLines == 0 ) {
					throw new KnownException( "No records on filtered report.  The job selected on upload is probably incorrect." );
				}
			}
			// log the message then rethrow, 
			// so that files are closed properly
			catch( KnownException e ) {
				// log the message
				CommonLog.getLog().write( location, "Error: " + e.getMessage() );
				throw e;
			}
			// log the message and exception trace, then rethrow 
			// so that files are closed properly
			catch( Exception e ) {
				CommonLog.getLog().write( location, "Error on filterReport(): " + e.getMessage(), e );
				throw e;
			}
			finally {
				// close input report
				try { reportReader.close(); } catch( IOException e ) {}
				
				// close filtered report file
				try { filteredWriter.close(); } catch( IOException e ) {}
				
				// close exception report file
				try { exceptionWriter.close(); } catch( IOException e ) {}
	
				// close log file
				try { logFile.close(); } catch( IOException e ) {}
			}
		}
		catch( Exception e ) {
			return false;
		}

		if( jobStep != null ) {
			// create a fileset and add it to the output fileset
			FileSet fs = new FileSet( "FileList" );
			fs.addFile( extension, filteredReport, "filtered" );
			fs.addFile( extension, exceptionReport, "exception" );
			fs.addFile( "log", logFilename, "log" );
			
			jobStep.addFileset( fs );
		}
		
		return true;
	}

	private boolean loadAccountHolders( int spsJobId ) throws InterruptedException {
		String inputFilename = null;
		Element root = null;
		ArrayListHandler jt = null;
		Buckets buckets = null;
		try {
			jobs = new Jobs();
			buckets = new Buckets();
		}
		catch( Exception e ) {
			CommonLog.getLog().write( location, "Unable to create Jobs instance" );
			return false;
		}
		
		// get the total count for the job 
		// then create the account holder hashmap for best performance
		int initialSize = buckets.getTotalCount( spsJobId );
		// if total job count is greater than zero,
		// set size to total count divided by .75 (load factor) 
		// for good performance and prevent rehashing 	
		if( initialSize > 0 ) {
			initialSize /= .75;
		}
		else {	// otherwise, use default size
			initialSize = DEFAULT_INITIAL_SIZE;
		}
		acctHolderMap = new HashMap( initialSize );
		
		// this method returns all threads by creation date in descending order
		jt = jobs.getExecutedJobThreads( spsJobId );
		if( jt.size() > 0 ) {
			// get the first / most recent thread
			String stepXML = jt.getString( "job_xml" );
			XMLDocument xmldoc = new XMLDocument( stepXML );
			root = xmldoc.getRootElement();
			// get the element then retrieve filename from element
	        try {
	        	Element inputFileEl = (Element)XPath.selectSingleNode( root, "Step[@Type='Dialogue'][not(contains( @DisplayName, 'Archive' )) and not(contains( @DisplayName, 'Audit' )) and not(contains( @DisplayName, 'Sample' )) and not(contains( @DisplayName, 'Test' ))]/Input/PrimaryInputFile" );
	        	if( inputFileEl != null ) {
	        		inputFilename = inputFileEl.getValue();
	        		JobTypeHandler jth = new FieldTypeHandler().getJobTypeHandler( "InputFileSet" );
	        		jth.setJobXML( root );
	        		jth.setRuntimeField( inputFileEl );
	        		FileSet fs = (FileSet) jth.resolve();
	        		
	        		if( fs.getChildFileCount() > 0 ) {
	        			String xmlFilename = fs.getFile( "xml" );
	        			String xmliFilename = fs.getFile( "xmli" );
	        			CommonLog.getLog().write( location, "Loading account holders from XML / XMLi <" + fs.getName() + ">: " + xmlFilename + " / " + xmliFilename );
	        			if( ! loadAccountHoldersInFile( xmlFilename, xmliFilename ) ) {
	        				throw new KnownException( "Error loading account holders" );
	        			}
	        		}
	        		
	        		if( fs.getChildCount() > 0 ) {
		        		FileSet[] fileSets = fs.getChildren();
		        		for( int i=0; i<fileSets.length; i++ ) {
		        			String xmlFilename = fileSets[i].getFile( "xml" );
		        			String xmliFilename = fileSets[i].getFile( "xmli" );
		        			CommonLog.getLog().write( location, "Loading account holders from XML / XMLi <" + fileSets[i].getName() + ">: " + xmlFilename + " / " + xmliFilename );
		        			if( ! loadAccountHoldersInFile( xmlFilename, xmliFilename ) ) {
		        				throw new KnownException( "Error loading account holders" );
		        			}
		        		}
	        		}
	        	}
	        }
	        catch( InterruptedException e ) {
	        	throw e;
	        }
	        // log only message for known exceptions
	        catch( KnownException e ) {
	        	CommonLog.getLog().write( location, "Error on loadAccountHolders(): " + e.getMessage() );
	        	return false;
	        }
	        // log exception trace for unknown exceptions
	        catch( Exception e ) {
	        	CommonLog.getLog().write( location, "Error on loadAccountHolders(): " + e.getMessage(), e );
	        	return false;
	        }
		}

        return true;
	}
	
	private boolean loadAccountHoldersInFile( String xmlFilename, String xmliFilename ) throws InterruptedException {
		boolean ret = true;
		int sequence = 0;
		BufferedReader xmlReader = null;
		XMLiParser xmliParser = null;
		try {
			// open xml file
			xmlReader = new BufferedReader( new FileReader( xmlFilename ) );
			// open xmli file
			xmliParser = new XMLiParser( xmliFilename ); 
			
			// parse each top level index entry
			List list = null;
			Element entry = null;
			long offset = 0, inputXmlOffset = 0;
			int length = 0, count = 0;
			char[] xmlBuffer = new char[10000];	// start with 10K chars
			// while there are entries in the xmli file
			while( ( list = xmliParser.getNext() ) != null ) {
				// now loop through the items in the list
				for( int i=0; i<list.size(); i++ ) {
					entry = (Element) list.get( i );
					// check value of offset tag
					String tmp = entry.getChildText( "Offset" );
					// if offset doesn't exist, skip
					if( tmp == null ? true : tmp.length() == 0 ) {
						continue;
					}

					// get offset and length of document in the xml
					offset = Long.parseLong( tmp );
					tmp = entry.getChildText( "Length" );
					length = Integer.parseInt( tmp );

					// get sequence number
					tmp = entry.getChildText( "Sequence" );
					sequence = Integer.parseInt( tmp );
					
					// increase xml buffer size as needed
					if( length > xmlBuffer.length ) {
						if( length > xmlBuffer.length * 2 + 2 ) {
							xmlBuffer = new char[ length ];
						}
						else {
							xmlBuffer = new char[xmlBuffer.length * 2 + 2];
						}
					}
					
					// if this is the first entry
					if( inputXmlOffset == 0 ) {
						// read the offset number of characters from the top of input xml file 
						xmlReader.read( xmlBuffer, 0, (int)offset );
						// update input offset
						inputXmlOffset += offset;
					}
				
					// load data from xml input
					xmlReader.skip( offset - inputXmlOffset );
					xmlReader.read( xmlBuffer, 0, length );
					// update input offset
					inputXmlOffset += length;
	
					// now create the xml structure for it
					XMLDocument doc = new XMLDocument( new String( xmlBuffer, 0, length ) );
					Element root = doc.getRootElement();
					if( root == null ) continue;
					
					
					Element acctIdEl = (Element) XPath.selectSingleNode( root, "AccountHolder/ID[@Type='AcctID']" );
					List addresses = XPath.selectNodes( root, "AccountHolder/ContactInfo/PostalAddr/AddrLine[string-length(.) > 0]" );
					Element cityEl = (Element) XPath.selectSingleNode( root, "AccountHolder/ContactInfo/PostalAddr/City" );
					Element stateEl = (Element) XPath.selectSingleNode( root, "AccountHolder/ContactInfo/PostalAddr/State" );
					Element zipEl = (Element) XPath.selectSingleNode( root, "AccountHolder/ContactInfo/PostalAddr/PostalCode" );
					
					// sometimes city state zip is not in the AddrLine tag
					StringBuilder csz = new StringBuilder();
					if( cityEl != null ) {
						csz.append( cityEl.getText() );
					}
					if( stateEl != null ) {
						if( csz.length() > 0 ) csz.append( " " );
						csz.append( stateEl.getText() );
					}
					if( zipEl != null ) {
						if( csz.length() > 0 ) csz.append( " " );
						csz.append( zipEl.getText() );
					}
					String cszLine = csz.toString().trim();

					// load account id
					String acctId = "";
					if( acctIdEl != null ) {
						acctId = acctIdEl.getText();
					}
					
					// load one entry for each name in the address lines
					for( int j=0; j<(addresses.size()-1); j++ ) { 
						// load name
						String name = ((Element)addresses.get( j )).getText();
						// if name looks like address line, break out of loop
						if( Character.isDigit( name.charAt(0) ) ) break;
						if( name.matches( "P.*O.* BOX .*" ) ) break;
						
						// create an array list and store the address tokens in it
						ArrayList addressList = new ArrayList();
						for( int i2=0; i2<addresses.size(); i2++ ) {
							addressList.add( ((Element)addresses.get(i2)).getText() );
						}
						if( cszLine.length() > 0 ) {
							addressList.add( cszLine );
						}
						
						// create an accountholder
						AccountHolder ah = new AccountHolder( sequence, name, acctId, addressList );
						
						// create a key from the name: use getName() to get standardized name from ah
						String[] keys = getKeys( ah.getName() );
						for( int k=0; k<keys.length; k++ ) {
							if( keys[k] == null ) continue;
							// get the entry in the hash map from the key
							ArrayList entryList = (ArrayList) acctHolderMap.get( keys[k] );
							// if entry list doesn't exist, create one and put it in the hash map
							if( entryList == null ) {
								entryList = new ArrayList();
								acctHolderMap.put( keys[k], entryList );
							}
							// add account holder to the entry list 
							entryList.add( ah );
						}
					}
					
					// log progress
					if( ++count % 15000 == 0 ) {
						CommonLog.getLog().write( location, "Loaded " + count + " account holders..." );
					}

					// check for thread aborted
					if( count % 500 == 0 && jobStep != null ) {
						if ( jobStep.isThreadAborted() ) {
							throw new InterruptedException( "Thread aborted on loadAccountHolders()" );
						}
					}
				}
			}
			
			// log progress
			CommonLog.getLog().write( location, "Total account holders loaded for this file: " + count );

		}
		catch( InterruptedException e ) {
			throw e;
		}
		catch( Throwable e ) {
        	CommonLog.getLog().write( location, "Error on loadAccountHoldersInFile() after sequence " + sequence + ": " + e.getMessage() );
        	ret = false;
		}
		finally {
			try { xmlReader.close(); } catch( Exception e ) {}
			try { xmliParser.close(); } catch( Exception e ) {}
		}
		
		return ret;
	}
	
	private AccountHolder[] getMatchingAccountHolders( String line ) {
		// exclude lines with empty new address
		if( line.endsWith( "\t\t\t\t\t\t\t\t\t\t\t\t" ) ) {
			try {
				logFile.write( "Empty new address on name: " + line.substring( 16, 36 ) );
				logFile.newLine();
			}
			catch( Exception e ) {}
			return null;
		}
		
		if( line.charAt( line.length()-1 ) == '\t' ) line += " ";
		// get name and address from the report file
		String[] lineFields = line.split( "\t" );
		String[] nameRpt = { 
				lineFields[2] /* prefix */, 
				lineFields[3] /* first name */, 
				lineFields[4] /* last name */, 
				lineFields[5] /* suffix */ 
				           };
		String urbanization = lineFields[7];		// Old Primary Nbr
		String primAddrNbr = lineFields[8];		// Old Primary Nbr
		//	IGNORED: 	lineFields[9], 		// Old Pre Direct
		String[] addrRpt = lineFields[10].split( " " ); 		// Old Primary Name
		//	IGNORED: 	lineFields[11], 	// Old Suffix
		//	IGNORED: 	lineFields[12], 	// Old Post Direct
		//	IGNORED: 	lineFields[13], 	// Old Unit Designtr
		String secAddrNbr = lineFields[14];		// Old Sec Nbr 

		//String[] addressRpt = { lineFields[19], lineFields[20], lineFields[21], lineFields[22], lineFields[23], lineFields[24], lineFields[25], lineFields[26], lineFields[27], lineFields[28], lineFields[29] };
		String[] city =	lineFields[15].split( " " );		// Old City 

		//	IGNORED: 	lineFields[16]	 	// Old State
		String zip = lineFields[17]; 	// Old Zip
		//	IGNORED: 	lineFields[18] 		// Old Zip+4
		String moveType = lineFields[32];

		// store account numbers here
		TreeSet ahList = new TreeSet();
		
		// look up the member name and address in the address list
		boolean nameFound = false;
		boolean ahFound = false;
		int index = 4;
		// key should be last name or suffix (fields 4 and 5)
		while( index < 6 ) {
			String key = lineFields[ index++ ];
			// skip empty key text
			if( key.length() == 0 )	continue;
			// there may be spaces in the name, so tokenize it and search for each token
			String[] keyTokens = key.split( " " );
			for( int n=0; n<keyTokens.length; n++ ) {
				// get list of accountholders for the key
				ArrayList list = (ArrayList) acctHolderMap.get( keyTokens[n] );
				if( list == null ) {
					// no items found with this key, try another one
					continue;
				}
				// for each item in the address line for this key
				for( int i=0; i<list.size(); i++ ) {
					boolean found = false;
					// get account holder for the index
					AccountHolder ah = (AccountHolder) list.get(i);
					// the first name has to match
					String name = ah.getName();
					if( name.indexOf( lineFields[3] ) < 0 ) {
						if( moveType.equals( "I" ) ) {
							continue;
						}
					}
					else {
						// first name matches!
						nameFound = true;
					}
	
					// now check the address
					
					// primary number must be found
					if( primAddrNbr.length() > 0 ) {
						// primary numbers may have dash
						// split and make sure all tokens exist
						String[] tokens = primAddrNbr.split( "-" );
						if( ! ah.search( tokens, ACCTHOLDER_ADDRESS ) ) {
							continue;
						}
					}
					
					// and secondary numbers too
					if( secAddrNbr.length() > 0 ) {
						// secondary numbers may have dash
						// split and make sure all tokens exist
						String[] tokens = secAddrNbr.split( "-" );
						if( ! ah.search( tokens, ACCTHOLDER_ADDRESS ) ) {
							continue;
						}
					}
					
					// at least one token of the primary name must exist in the address from the xml
					for( int j=0; j<addrRpt.length; j++ ) {
						String token = addrRpt[j];
						// if token found, look further
						if( ah.search( token, ACCTHOLDER_ADDRESS ) ) {
							// now check for zip or city
							// if zip is found, it matches
							if( ah.search( zip, ACCTHOLDER_ADDRESS ) ) {
								found = true;
								break;
							}
							// otherwise, search for city: all tokens must match
							if( ah.search( city, ACCTHOLDER_ADDRESS ) ) {
								found = true;
								break;
							}
						}
					}
					// if found, store account number
					if( found ) {
						ahFound = true;
						ahList.add( ah );
					}
				}
			}
		}
		
		// if name wasn't found, log it
		if( ! nameFound || ! ahFound ) {
			String name = "";
			for( int i=0; i<nameRpt.length; i++ ) {
				if( nameRpt[i].length() > 0 ) {
					name += nameRpt[i] + " ";
				}
			}
			try {
				if( ahFound )
					logFile.write( "Filtered - Address found without first name match (F or B) for: " + name );
				else if( nameFound )
					logFile.write( "Exception - Name found without address match for: " + name );
				else {
					logFile.write( "Exception - Name not found: " + name );
				}
				logFile.newLine();
			}
			catch( Exception e ) {}
		}
		
		// now create array from the list
		AccountHolder[] accountholders = new AccountHolder[ahList.size()];
		accountholders = (AccountHolder[]) ahList.toArray( accountholders );
		
		return accountholders;
	}
	
	// the keys are the possible last token of the name
	private String[] getKeys( String name ) {
		// create list where possible keys will be stored
		ArrayList<String> keys = new ArrayList<String>();
		// break name into tokens
		String[] tokens = name.replaceAll( "[\\.-]", "" ).trim().split( " " );
		int index = tokens.length - 1;
		// ignore suffixes JR, SR, II, III, or zero/one character tokens
		while( index > 0 ) {
			if( tokens[index].length() < 2 ) index--;
			else if( tokens[index].length() == 2 && tokens[index].matches( "[JS]R") ) index--;
			else if( tokens[index].length() == 2 && tokens[index].matches( "II") ) index--;
			else if( tokens[index].length() == 3 && tokens[index].matches( "III") ) index--;
			else {
				keys.add( tokens[index--] );
			}
		}
		
		return keys.toArray( new String[0] );
	}
	
	// account holder class
	private class AccountHolder implements Comparable {
		String name = null, acctId = null;
		String[] addrLines = null;
		String[][] addrLinesTokens = null;
		int[] matchType = null;
		
		boolean initialized = false;
		int sequence = 0;
		
		public AccountHolder( int sequence, String name, String acctId, ArrayList addrList ) {
			this.sequence = sequence;
			this.name = name.toUpperCase().replaceAll( "'", "" );	// normalize names like O'CONNOR, O'NEIL
			this.acctId = acctId;
			// store address lines
			addrLines = new String[addrList.size()];
			for( int i=0; i<addrList.size(); i++ ) {
				addrLines[i] = (String) addrList.get(i);
			}
		}
		
		public String getName() { return name; }
		public String getAcctId() { return acctId; }
		public String[] getAddrLines() { return addrLines; }

		public String[] getNames() {
			return Arrays.copyOfRange( addrLines, 0, getFirstAddressIndex() ); 
		}

		public String[] getAddresses() {
			return Arrays.copyOfRange( addrLines, getFirstAddressIndex(), addrLines.length ); 
		}

		private int getFirstAddressIndex() {
			int firstAddressIndex = 0;
			for( int i=0; i<addrLines.length; i++ ) {
				if( matchType[i] == ACCTHOLDER_ADDRESS ) {
					firstAddressIndex = i;
					break;
				}
			}
			return firstAddressIndex;
		}
		
		public void resetMatch() {
			// reset all address matches 
			for( int i=0; i<matchType.length; i++ ) {
				if( matchType[i] == ACCTHOLDER_ADDRESS ) {
					matchType[i] = UNKNOWN;
				}
			}
		}

		// search a single token in any address line 
		public boolean search( String searchToken, int type ) {
			return search( new String[] { searchToken }, type );
		}

		// search multiple tokens in any single address line 
		// returns true only if all tokens are found
		public boolean search( String[] lookupTokens, int type ) {
			boolean found = false;
			
			// initialize, if not done yet
			if( !initialized ) {
				// create an array to identify the type of line, if found
				if( matchType == null ) {
					matchType = new int[addrLines.length];
					Arrays.fill( matchType, UNKNOWN );
				}
				
				// flag the address line that contains the name
				for( int i=0; i<addrLines.length; i++ ) {
					if( addrLines[i].equals( name ) ) {
						matchType[i] = ACCTHOLDER_NAME;
						break;
					}
				}
				
				// tokenize address lines and add to sorted token set (tree set)
				addrLinesTokens = new String[addrLines.length][];
				TreeSet<String> addressTokenSet = new TreeSet();
				for( int i=0; i<addrLines.length; i++ ) {
					String tmp = addrLines[i].toUpperCase();
					// standardize street address with numbers as street names
					tmp = tmp.replaceAll( "\\.", " " );
					tmp = tmp.replaceAll( " +", " " );
					tmp = tmp.replaceFirst( "^([0-9\\-]+(?: | *[NSEW] ?[EW]? | NORTH | SOUTH | EAST | WEST )(?:1|[0-9]*[0-9&&[^1]]1))((?:[ A-Z&&[^S]]|S(?!T )).*)", "$1ST $2" );
					tmp = tmp.replaceFirst( "^([0-9\\-]+(?: | *[NSEW] ?[EW]? | NORTH | SOUTH | EAST | WEST )(?:2|[0-9]*[0-9&&[^1]]2))((?:[ A-Z&&[^N]]|N(?!D )).*)", "$1ND $2" );
					tmp = tmp.replaceFirst( "^([0-9\\-]+(?: | *[NSEW] ?[EW]? | NORTH | SOUTH | EAST | WEST )(?:3|[0-9]*[0-9&&[^1]]3))((?:[ A-Z&&[^R]]|R(?!D )).*)", "$1RD $2" );
					tmp = tmp.replaceFirst( "^([0-9\\-]+(?: | *[NSEW] ?[EW]? | NORTH | SOUTH | EAST | WEST )(?:[0-9]*[0456789]|[0-9]*1[0-9]))((?:[ A-Z&&[^T]]|T(?!H )).*)", "$1TH $2" );
					
					// remove dash, pound, and multiple spaces
					tmp = tmp.replaceAll( "-|#", " " );
					tmp = tmp.replaceAll( " +", " " );
					
					addressTokenSet.addAll( Arrays.asList( tmp.split( " " ) ) );
					String[] addrTokens = new String[addressTokenSet.size()];
					addrLinesTokens[i] = addressTokenSet.toArray( addrTokens );
					addressTokenSet.clear();
				}
				initialized = true;
			}
			
			// next, search backwards through each line and determine if token was found
			for( int i=addrLinesTokens.length-1; i>=0; i-- ) {
				// do not allow name to be overwritten by anything else
				if( matchType[i] == ACCTHOLDER_NAME ) {
					found = false;
					continue;
				}
				// initialize found as true
				found = true;
				// if all lookup tokens are not found in the address line tokens for this address line
				// it is NOT a match
				for( int j=0; j<lookupTokens.length; j++ ) {
					if( Arrays.binarySearch( addrLinesTokens[i], lookupTokens[j].toUpperCase() ) < 0 ) {
						found = false;
						break;
					}
				}
				if( found ) {
					// identify line as type
					matchType[i] = type;
					break;
				}
			}
			
			return found; 
		}
		
		public int compareTo( Object anotherAccountHolder ) {
			return new Integer( sequence ).compareTo( new Integer( ((AccountHolder) anotherAccountHolder).sequence ) );
		}
	}

	/**
	 * KnownException - defines a known exception
	 * @author fsantos
	 *
	 */
	private class KnownException extends Exception {
		/**
		 * Constructor
		 * @param message - Exception message
		 */
		public KnownException( String message ) {
			super( message );
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( Calendar.getInstance().getTime() );
		// set test mode
		FastForwardReportFilter ff = null;
		// create a FastForwardReportFilter object
		try {
			ff = new FastForwardReportFilter();
			if( ff.filterReport( args[0], args[1] ) ) {
				System.out.println( "Filtered report files created" );
			}
			else {
				System.out.println( "Error creating filtered report files" );
			}
		}
		catch(Exception e) {
			System.out.println( "Exception caught while creating filtered report files" );
		}
		System.out.println( Calendar.getInstance().getTime() );
	}

}
