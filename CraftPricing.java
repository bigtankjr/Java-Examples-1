import java.util.*;

public class CraftPricing 
{
	public static void main(String args[])
	{
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("What is the name of the product? ");
		String product = input.nextLine();
		
		System.out.println("What is the cost of the material? ");
		double cost = Double.parseDouble(input.nextLine());
		
		System.out.println("How many hours of work are required? ");
		double hours = Double.parseDouble(input.nextLine());
		System.out.println(product + " will cost $" + compCraftPricing(cost,hours) + " to make");		
		input.close();
	}
	private static double compCraftPricing(double cost, double hours)
	{    		
		double price = (cost + (12.00 * hours)) + 7.00;
	
		return price;
	}
}
