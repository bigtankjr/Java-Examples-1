import java.util.*;
public class Percentages2 
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Input the first number you wish to compare");
		double a = Double.parseDouble(scan.nextLine());
		System.out.println("Input the second number you wish to compare");
		double b = Double.parseDouble(scan.nextLine());
		System.out.println(a + " is " + computePercent(a,b) + " percent" + " of " + b);
		System.out.println(b + " is " + computePercent(b,a) + " percent" + " of " + a);
		
		
	}
	public static double computePercent(double a, double b)
	{ double c;
		c = Math.floor((a / b) * 100);
		return c;
	}
	
}
