/*
 * TicketNumber.java
 * written by Benjamin Levels
 * 
 * 
 */

import javax.swing.*;


public class TicketNumber {

	public static void main(String argsp[]){ 
		
		//declare variables
		int ticketNumber;
		int lastDigit;
		int remainder;
		boolean result;
		//allow user input
		JOptionPane jop= new JOptionPane();
		ticketNumber = 
				Integer.parseInt(jop.showInputDialog(null,
						"Enter ticket number: "));
		lastDigit = ticketNumber % 10; //get last digit
		remainder = (ticketNumber / 10) % 7;
		result = lastDigit == remainder;
		jop.showMessageDialog(null,
				 lastDigit == remainder);
		//validate ticket number and display results
		
		
	}
}
