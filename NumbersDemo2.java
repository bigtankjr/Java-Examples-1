import java.util.*;
public class NumbersDemo2 
{
	public static void main(String args[])
	{	
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the first number ");
		int a = Integer.parseInt(scan.nextLine());
		System.out.println("Please enter the second number ");
		int b = Integer.parseInt(scan.nextLine());
		System.out.println("The twice the result is: " + displayTwiceTheNumber(a,b));
		
		System.out.println("Please enter the first number ");
		a = Integer.parseInt(scan.nextLine());
		System.out.println("Please enter the second number ");
		b = Integer.parseInt(scan.nextLine());
		System.out.println("The result plus five is: " + displayNumberPlusFive(a,b));
		
		System.out.println("Please enter the first number ");
		a = Integer.parseInt(scan.nextLine());
		System.out.println("Please enter the second number");
		b = Integer.parseInt(scan.nextLine());
		System.out.println("The result squared is: " + displayNumberSquared(a,b));
		
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
