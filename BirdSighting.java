
public class BirdSighting {
	//declare member variables
			String species;
			int number;
			int day;
		
		
	//constructors
	public BirdSighting()
	{
		//set inital values to member variables
		//species = "robin";
		//number = 1;
		//day = 1;
		this( "robin", 1, 1 );
	}
	public BirdSighting(String s, int n, int d)
	{
		//assign new values from parameters to member variables
		species = s;
		number = n;
		day = d;
	}
	
	
	//mutator methods(set)
	public void setSpecies( String s )
	{
		//Mutator method to set value to name of species sighted
		//Precondition: species is declared as a member variable
		// receive a string value
		//Postcondition: none
		species = s;
	}
	
	public void setNumber( int n )
	{
		//Mutator method to set value of the number of species sighted
		//Precondition: number is declared as a member variable
		// receive a integer value
		//Postcondition: none
		number = n;
	}
	
	public void setDay( int d )
	{
		//Mutator method to set value to day species was sighted
		//Precondition: day is declared as a member variable
		// receive a integer value
		//Postcondition: none
		day = d;
	}
	
	
	//accessor methods (get)
	public String getSpecies()
	{
		//Accessor method to get name of species sighted
		//Precondition: species is declared as member variable
		//Postcondition: return string value
		return species; }
	
	public int getNumber()
	{
		//Accessor method to get number of species sighted
		//Precondition: number is declared as member variable
		//Postcondition: return integer value
		
		
		return number; }
	
	public int getDay()
	{
		//Accessor method to get day species sighted
		//Precondition: day is declared as member variable
		//Postcondition: return integer value
		return day; }
	
	//other methods

}
