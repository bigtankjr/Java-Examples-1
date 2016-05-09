import java.util.*;

public class MetricConversion
{
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input the number to be converted? ");
		double num = Double.parseDouble(scan.nextLine());
		convertToCent(num);
		convertToLiter(num);
	}
	public static void convertToCent(double cent)
	{
		
		double convertcent = 2.54;
		double value = cent * convertcent;
		System.out.println("You have converted " + cent + " inches to " + value + " centimeters");
	}
	public static void convertToLiter(double liter)
	{
		double convertliter = 3.7854;
		double value = liter * convertliter;
		System.out.println("You have converted " + liter + " liters to " + value + " gallons");
	}
}
