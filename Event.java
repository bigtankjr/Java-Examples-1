
public class Event
{
	public final static double priceperguest = 35.00;
	public final static int cuttoff = 50;
	private static String eventnum;
	private static int guests;
	private static double price;
	public static void main( String args[] )
	{
		
	}
	public static void setEventNum(String e)
	{
		eventnum = e;
	}
	public String getEventNum()
											{ return eventnum; }
	
	public static void setGuests( int g )
	{
		guests = g;	
		price = guests * priceperguest;
	}	
	public int getGuests()
											{ return guests; }
	
	public double getPrice()
											{ return price; }
	
}
