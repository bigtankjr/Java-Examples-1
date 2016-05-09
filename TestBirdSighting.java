
public class TestBirdSighting {
	
	public static void main(String args[])
	{
		// declare variables
		BirdSighting bird = new BirdSighting();
		//display object
		System.out.println("Species: " + bird.getSpecies());
		System.out.println("Number sighted: " + bird.getNumber());
		System.out.println("Day of sighting: " + bird.getDay());
		
		BirdSighting bird2 = new BirdSighting();
		//set values
		bird2.setSpecies("cardinal");
		bird2.setNumber(3);
		bird2.setDay(20);
		//display object
		System.out.println("Species: " + bird2.getSpecies());
		System.out.println("Number sighted: " + bird2.getNumber());
		System.out.println("Day of sighting: " + bird2.getDay());
		
		//set values
		BirdSighting bird3 = new BirdSighting("nuthatcher", 3, 20);
		
		
		
		//display object
		System.out.println("Species: " + bird3.getSpecies());
		System.out.println("Number sighted: " + bird3.getNumber());
		System.out.println("Day of sighting: " + bird3.getDay());
	}

}
