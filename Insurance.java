import java.util.*;


public class Insurance
{	private static int multnum = 20;
	private static int addnum  = 15;
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		
		System.out.println("What is year is it? ");
		int curryear = Integer.parseInt(input.nextLine());
		
		System.out.println("What is your birth year? ");
		int birthyear = Integer.parseInt(input.nextLine());
		
		calcPremium(curryear, birthyear);
		input.close();
	}
	/*------------------------------- calcPremium() ----------------------------------*/
	private static void calcPremium(int curryear, int birthyear)
	{	int premium = 0;
		int divnum  = 10;
		int decade  = 0;
		int age = curryear - birthyear;
		
		decade = age / divnum;
		premium = (decade + addnum) * multnum;
		
		System.out.println("Your Insurance premium is: $" + premium);
		
			
	}
}
