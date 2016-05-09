
public class BloodData {

	String bloodType;
	String rhFactor;
	
	//constructors
	public BloodData()
	{
		//assign default values to member variables
		bloodType = "O";
		rhFactor = "+";
		
	}
	//mutator methods(set)
	public void setBloodType(String b)
	{
		//Mutator method to set value of blood type
		//Precondition bloodType declared as member variable
		// reveives a string value
		//Postcondition:
		bloodType = b;
		
	}
	public void setRhFactor(String r)
	{
		//Mutator method to set value of rhFactor
		//Precondition rhFactor declared as member variable
		// reveives a string value
		//Postcondition:
		rhFactor = r;	
		
		
	}
	//accessor methods(get)
	public String getBloodType()
	{
		//Accessor method to get value of bloodType
		//Precondition: bloodType is declared as a member variable
		//Postcondition: return string value
		return bloodType;
	}
	public String getRhFactor()
	{
		//Accessor method to get value of rhFactor
		//Precondition: rhFactor is declared as a member variable
		//Postcondition:return string value
		return rhFactor;
		
		
	}
	//other methods
}
