import javax.swing.*;

public class ShouldIDoIt
{	
	private static String gender;
	private static int yes    = 0;
	private static int no     = 0;
	private static int cancel = 0;
	private static int friends;
	@SuppressWarnings("static-access")
	public static void main( String args[] )
	{	int code = 0;
		int counter = 0;
	
		JOptionPane jop = new JOptionPane();
		
		//set the gender of the questioner and convert to string used in event question
		gender = jop.showInputDialog(null,"Are you a male or female?");
		
		
		//set the number of people you wish to survey by adding value to friends variable.
		friends = Integer.parseInt( jop.showInputDialog( null, "How many people do you want to survey? " ));
		String event = jop.showInputDialog( null,"What do you want to do? " );
		
		//go through list of friends and count responses to total result.
		setGender();
		while ( counter < friends )
		{ 	

		jop.showMessageDialog( null,"Your friend wants to " + event );
		code = jop.showConfirmDialog( null,"Should " + gender + " do it? " );
		
				if ( code == 0 )
				{	
					yes++;
					
				}if ( code == 1 ) {
					
					no++;
				}if (code == 2) { 
					
					cancel++; 
					}
				++counter;
		}
		
		jop.showMessageDialog( null, yes + " Thinks you should do it.  " + no + " Thinks you shouldn't do it.  " + cancel + " Declined to respond." + "\n"   
										 + " END OF SURVEY. Thank You.\n"
																															  + "\n"
																															  + "Hit Ok" );
		if ( yes > no && yes > cancel ) {
			jop.showMessageDialog(null,"You should definitely do it.");
			
		}if ( cancel > yes && cancel > no ){
			jop.showMessageDialog(null,"Little interest in the subject.");
			
		}if ( no > yes && no > cancel ){
			jop.showMessageDialog(null,"You shouldn't do it.");
		}
		jop.showMessageDialog(null,"*************************\n"
								 + "    \n"
								 + "THANKS FOR PARTICIPATING\n"
								 + "			    \n"
								 + "*************************");
	}
	public static String setGender()
	{	String h = "he";
		String f = "she";
		if ( gender == "male" )
		{
			gender = h;
			
		}if ( gender == "female" )
		{
			gender = f;
		}
		return gender;
	}
}
