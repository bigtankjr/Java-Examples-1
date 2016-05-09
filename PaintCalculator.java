import java.util.*;

public class PaintCalculator
{	private static int length;
	private static int width;
	private static int height;
	private static int area;
	private static int paintprice = 32;
	private static int gallons;
	private static int price;
	public static void main(String args[])
	{ 
	  
	  Scanner input = new Scanner(System.in);
	  
	  System.out.println("Enter the length of the wall");
	  length = Integer.parseInt(input.nextLine());
	  
	  System.out.println("Enter the width of the wall");
	  width = Integer.parseInt(input.nextLine());
	  
	  System.out.println("Enter the height of the wall");
	  height = Integer.parseInt(input.nextLine());
	  
	  PaintCalculator calc = new PaintCalculator(length, width, height);
	  
	  System.out.println("The price to paint the wall is: $" + price);	
	  
					
	}
	public PaintCalculator(int l, int w, int h)
	{ 
		area = ((l * h) * 2 + (w * h) * 2);
		CalcGallonsNeeded(area);
		price = gallons * paintprice;
		
	}
	public static int CalcGallonsNeeded(int a)
	{
		gallons = (int) Math.ceil(a / 350.0);
		return gallons;
	}
	
}
