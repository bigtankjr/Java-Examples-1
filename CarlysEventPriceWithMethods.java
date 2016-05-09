import java.util.*;

public class CarlysEventPriceWithMethods
{
	
	private static double rate = 35.00;
	private static int guests;
	private static String eventnum;
	@SuppressWarnings("unused")
	private static double price;
	public static void main( String args[] )
	{
		Scanner input = new Scanner(System.in);
		
		System.out.println( "How many guest are attending? " );
		setNumGuests( Integer.parseInt( input.nextLine() ) );
		System.out.println( "The number of guests attending are: " + guests );
		setEventPrice(guests);
		companyMotto();
		
		input.close();
	}
	
	public static int setNumGuests( int g )
	{ 
		guests = g;
		
		return guests; }
	
	public static void companyMotto()
	{
		System.out.println("                                              \n"
						 + "**********************************************\n"
						 + "*                                            *\n" 
						 + "*                                            *\n"
						 + "*              FOOD FOR THOUGHT              *\n"
						 + "*                                            *\n"
						 + "*                                            *\n"
						 + "**********************************************");
		
	}
	
	public static void setEventPrice(int g)
	{
		
		price = g * rate;
		
		if ( guests > 100)
		{
			System.out.println("This is a large event.");
		}else {
			System.out.println("This is not a large event.");
			
		}
		
		
	}
	public String getEventNumber()
	{
		
		return eventnum;
	}
}
