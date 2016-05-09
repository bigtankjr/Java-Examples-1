
public class Student
{
	
	private static int idnum;
	private static double creditsearned;
	private static double ptsearned;
	final static double scale = 4;
	private static double gpa;
	
	public static void main( String args[] )
	{		
		
	}
	public Student()
	{
	
		idnum         = 9999;
		ptsearned     = 12;
		creditsearned = 3;
	}
	public static void setID( int id )
	{
		idnum = id;
	}
	public int getID()
	{
		return idnum;
	}
	public static void setCreditsEarned( double hrs )
	{
		creditsearned = hrs;
	}
	public double getCreditsEarned()
	{
		
		return creditsearned;
	}
	public static void setPtsEarned( double pts )
	{
		ptsearned = pts;
	}
	public double getPtsEarned()
	{
		ptsearned = creditsearned * scale;
		return ptsearned;
	}
	public static void setGpa( double pts, double credits )
	{
		creditsearned = credits;
		ptsearned     = pts;
		
	}
	
	
	public double getGpa()
	{   gpa = ptsearned / creditsearned;
		return gpa;
	}
}
