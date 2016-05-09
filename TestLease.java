import java.util.*;

public class TestLease
{	@SuppressWarnings("static-access")
	public static void main( String args[] )
	{  
		// created new Scanner Object
		Scanner input = new Scanner(System.in);
		
		// new lease Objects
		Lease lease   = new Lease();
		Lease apt     = new Lease();

		
        
		// getData methods
		apt.getAptNum();
		apt. getLeaseTerm();
		apt.getName();

		// 
		System.out.println("What is the Lesser's name? ");
		lease.setName(input.nextLine());
		
		System.out.println("What is the Apartment number? ");
		lease.setAptNum(Integer.parseInt(input.nextLine()));
		
		System.out.println("What is the rental amount? ");
		lease.setRentAmt(Double.parseDouble(input.nextLine()));
		
		System.out.println("What is the term of the lease? ");
		lease.setLeaseTerm(Integer.parseInt(input.nextLine()));
		showValues(lease.getRentAmt());
		
		
		showValues(apt.getRentAmt());		
		
		Lease someone = new Lease();
		
		
		showValues(someone.getRentAmt());
		someone.addPetFee();
		showValues(someone.getRentAmt());
		
		Lease renter  = new Lease();
		
		showValues(lease.getRentAmt());

		showValues(renter.getRentAmt());
		input.close();
	}
	
	public static void showValues( Object e )
	{
		System.out.println(e);
		
	}
}
