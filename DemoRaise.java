import java.io.*;
import java.util.*;
public class DemoRaise {
	public static void main( String [] args )
	{
		Scanner input = new Scanner( System.in );
		System.out.print( "Enter George's dollar amount: " );
		double george = input.nextDouble();
		System.out.print( "Enter Salary's dollar amount: " );
		double salary = input.nextDouble();
		System.out.print( "Enter the starting wage dollar amount: " );
		double startingWage = input.nextDouble();
		System.out.println( "Demonstrating some raises" );
		predictRaise( 400.00, 1.20 );
		predictRaise( startingWage, 1.10 );
		predictRaise( salary, 1.10 );
		
	}
	public static void predictRaise( double salary, double rate)
	{
		double newSalary;
		//final double RAISE_RATE = 1.10;
		newSalary = salary * rate;
		System.out.println( "Current salary: " + 
		    salary + "    After raise: " + 
			newSalary );
	}
}
