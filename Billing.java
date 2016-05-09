/*
 * Billing.java
 * written by Ben Levels
 * This application demonstrates the use of overloaded methods 
 * using a photo book store,
 * computing the bill based on orders received
 */

public class Billing {
	//declare TAX to be used in any method within the class
	//declare outside of any method
	final static double TAX = 0.08;
	
	public static void main( String args[] )
	{
		//declare variables
		double price = 14.99;
		int quantity = 3;
		double coupon = 5.00;
		System.out.println("The cost of the photo book with tax is $" +
				computeBill(price));
		System.out.println("The cost of the photo book with tax: " 
				+ "quantity: " + quantity + " is $" 
				+ computeBill(price, quantity));
		System.out.println("The cost of the photo book with tax: " 
				+ "quantity: " + quantity + 
				" and coupon: " + coupon + " is $" 
				+ computeBill(price, quantity, coupon));
	}
	//overloaded methods
	public static double computeBill( double p )
	//Calculates bill for photo book
	//Precondition: receive double value for price
	//	TAX has been defined somewhere else
	//Postcondition: return double value for totalPrice
	{	//declare variables
		double computedTax = p * TAX;
		double totalPrice = computedTax + p;
		//calculate value
		//return double value
		return totalPrice;
		
	}
	public static double computeBill(double p, int q)
	{
		//Calculates bill for photo book
		//Precondition: receive double value for price and integer value for quantity
		//	TAX has been defined somewhere else
		//Postcondition: return double value for totalPrice
		//compute and return value at the same time.
		return ((p * q) * (1 + TAX));
		
	}
	public static double computeBill(double p, int q, double c)
	{
		//Calculates bill for photo book
		//Precondition: receive double value for price, integer value for quantity, and double for coupon
		//	TAX has been defined somewhere else
		//Postcondition: return double value for totalPrice
		//declare variables
		double calcPrice;
		double calcTax;
		double totalPrice;
		calcPrice = p * q - c;
		calcTax = calcPrice * TAX;
		totalPrice = calcPrice + calcTax;
		
		//compute and return value.
		return totalPrice;
	}
}
