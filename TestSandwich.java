
public class TestSandwich 
{
	public static void main(String args[])
	{
		Sandwich san = new Sandwich();
		
		System.out.println("               SANDWHICH PRICES              ");
		System.out.println("---------------------------------------------");
		
		san.setMain("Ham");
		System.out.println("The main ingredient in the sandwich is: " + san.getMain());
		
		san.setBread("Whole Wheat");
		System.out.println("The bread used in this sandwich is: " + san.getBread());
		san.setPrice(3.59);
		System.out.println("The price for this sandwich is: " + san.getPrice());
		System.out.println("");
		
		san.setMain("Salami");
		System.out.println("The main ingredient in the sandwich is: " + san.getMain());
		
		san.setBread("Italian");
		System.out.println("The bread used in this sandwich is: " + san.getBread());
		san.setPrice(4.25);
		System.out.println("The price for this sandwich is: " + san.getPrice());
		System.out.println("");
		
		san.setMain("Turkey");
		System.out.println("The main ingredient in the sandwich is: " + san.getMain());
		
		san.setBread("Potato Bread");
		System.out.println("The bread used in this sandwich is: " + san.getBread());
		san.setPrice(3.59);
		System.out.println("The price for this sandwich is: " + san.getPrice());
		System.out.println("");
		
		san.setMain("Chicken");
		System.out.println("The main ingredient in the sandwich is: " + san.getMain());
		
		san.setBread("White Honey Oat");
		System.out.println("The bread used in this sandwich is: " + san.getBread());
		san.setPrice(5.25);
		System.out.println("The price for this sandwich is: " + san.getPrice());
		
		
		
		
	}
}
