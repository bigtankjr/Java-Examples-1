import java.util.*;

public class RandomGuessMatch {

	public static void main(String args[]){
	Scanner input = new Scanner(System.in);
	//declare variables
	String guessString;
	int random;
	int guess;
	final int MIN = 10;
	final int MAX = 100;
	boolean isMatch;
	
	//generate random number
	random = MIN + (int)(Math.random() * MAX);
	//accept user input
	System.out.print("Enter a number between " + MIN + " and " + MAX + ":");
	guessString = input.nextLine();
	guess = Integer.parseInt(guessString);
	//compare user input with random number
	isMatch = (random == guess); //
	//display results
	System.out.println("Your guess was " + guess +
			" which is " + (random - guess) + " away from " + MAX);
	System.out.println("The random number was " + random +
			". The result is " + isMatch);
	}
}
