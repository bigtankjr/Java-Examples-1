
public class NumbersDemo 
{	 
	public static void main(String args[])
	{
		int a = 2;
		int b = 3;
		System.out.println(displayTwiceTheNumber(a,b));
		System.out.println(displayNumberPlusFive(a,b));
		System.out.println(displayNumberSquared(a,b));
		
	}
	public static int displayTwiceTheNumber( int a, int b)
	{
		int c;
		c = 2 * (a + b);
		return c;
	}
	public static int displayNumberPlusFive(int a, int b)
	{
		int c;
		c = 5 + (a +b);
		return c;
	}
	public static int displayNumberSquared(int a, int b)
	{
		int c;
		c = (a+b) * (a+b);
		return c;
		
	}
}
