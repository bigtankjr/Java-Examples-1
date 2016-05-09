
public class RecursiveProgram1 {
  public static void main( String args[] )
  {
	  recurseValue(5);  
	  
  }

	public static void recurseValue(int a)
	{
		
		if (a == 24) {
			return;
			
		}else{
			
			System.out.println("Hello " + a);
			recurseValue(++a);
		}
		
	}
}