
public class ShowStudent 
{	@SuppressWarnings("static-access")
	public static void main( String args[] )
	{
		
		Student st = new Student();
		
		st.setGpa( 20,4 );
		System.out.println( st.getGpa() );
		
		st.setPtsEarned(20);
		st.setCreditsEarned(7);		
		System.out.println( st.getGpa() );
		
		st.setCreditsEarned(4);
		System.out.println( st.getPtsEarned() );
		
		
	}
	
}
