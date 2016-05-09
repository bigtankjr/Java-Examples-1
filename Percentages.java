
public class Percentages 
{
	public static void main(String args[])
	{
		double a = 2.0;
		double b = 5.0;
		System.out.println(a + " is " + computePercent(a,b) + " percent" + " of " + b);
		System.out.println(b + " is " + computePercent(b,a) + " percent" + " of " + a);
		
		
	}
	public static double computePercent(double a, double b)
	{ double c;
		c = (a / b) * 100;
		return c;
	}
}
