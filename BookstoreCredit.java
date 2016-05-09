import java.util.*;

public class BookstoreCredit
{
	public static void main(String args[])
	{
		
		Scanner scan = new Scanner(System.in);
		System.out.println("What is your full name? ");
		String name = scan.nextLine();
		
		System.out.println("What is your grade point average? ");
		double grade = Double.parseDouble(scan.nextLine());
		calcCreditAmount(name, grade);
		

	}
	public static void calcCreditAmount(String name, double a)
	{	double credit;
		double rate = 10;
		
		credit = a * 10;
		System.out.println("Your name is " + name + "." + " Your grade point average is " + a + "," + " and your credit earned is: $" + credit);
		
	}
}
