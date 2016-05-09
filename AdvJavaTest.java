import java.io.*;
import javax.swing.*;
import java.util.*;


public class AdvJavaTest {
	public static  final JOptionPane jop = new JOptionPane();
	public static final char cr = '\n';
	public static void main(String args[])
	{
		

		String address1 = jop.showInputDialog("Input your address please? ");
		displayAddress(address1);
		confirmAddress(address1);
	}
	
	/*----------------------------------- displayAdress Method() --------------------------------------------------*/
	
	public static void displayAddress(String address)
	{
		

		
		jop.showMessageDialog(null, "Your address is: " + cr + cr + address);
		
		
		
	}
	/*----------------------------------- confirmAdress Method() --------------------------------------------------*/
	
	public static void confirmAddress(String address)
	{   
		

		int code = jop.showConfirmDialog(null,"Is this your address? " + cr + address);
		while (code < 3)
		{
			switch (code)
			{
			case 0:
				jop.showMessageDialog(null,"We will forward the package to your current address. Thank You.");
				code = 3;
				break;
			case 1:
				String address2 = jop.showInputDialog("Input the correct address? ");
				code = jop.showConfirmDialog(null,"Is this your address? Please confirm?: " + cr + address2);				
			
			break;
					
			case 2:
				jop.showMessageDialog(null,"No worries. Restart the process when you are ready to continue. Thanks.");
				code = 4;
				break;
				
		
			}//switch
		}//while
		
		
		
		if (code == 3)
		{
			
			jop.showMessageDialog(null,"Your package has been sent. Enjoy");
		}
		else if (code == 4)
		{
			
			jop.showMessageDialog(null,"Goodbye. Hope things are swell.");
		}
	}
}
