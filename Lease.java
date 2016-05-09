
public class Lease
{   private static String name;
	private static int    aptnum;
	private static double rentamt;
	private static int    leaseterm;
	
	public static void main( String args[] )
	{
		
	}
	public Lease()
	{
		name = "XXX";
		aptnum = 0;
		rentamt = 1000;
		leaseterm = 12;
	}
	public static void setName(String n)
	{
		name = n;
	}
	public String getName()
								{ return name; }
	
	public static void setAptNum(int a)
	{
		aptnum = a;
	}
	public int getAptNum()
								{ return aptnum; }
	
	public static void setRentAmt(double r)
	{
		rentamt = r;
	}
	public double getRentAmt()
								{ return rentamt; }
	
	public static void setLeaseTerm(int l)
	{
		leaseterm = l;
	}
	public int getLeaseTerm()
								{ return leaseterm; }
	
	public void addPetFee()
	{
		
		double petfee = 10.00;
		rentamt = rentamt + petfee;
		explainPetPolicy();
		
	}	
	public static void explainPetPolicy()
	{
		
		System.out.println("The $10 fee helps cover any expenses from wear and tear on the apartment due to pet ownership.");
		
	}
}
