import javax.swing.*;
public class TwoCharacters
{
	@SuppressWarnings("static-access")
	public static void main( String args[] )
	{
		JOptionPane jop = new JOptionPane();
		
		MyCharacter buggy = new MyCharacter();
		MyCharacter ziggy = new MyCharacter();
		
		buggy.setSkinColor( jop.showInputDialog( null,"What color is Buggy's skin? " ) );
		jop.showMessageDialog( null,"Buggy's skin color is: " + buggy.getSkinColor() );
		
		buggy.setNumEyes( Integer.parseInt( jop.showInputDialog( null,"How many eyes does Buggy have? " ) ) );
		jop.showMessageDialog( null,"Buggy has: " + buggy.getNumEyes() + " eyes." );
		
		buggy.setNumLegs( Integer.parseInt( jop.showInputDialog( null,"How many legs does Buggy have? ") ) );
		jop.showMessageDialog( null,"Buggy has: " + buggy.getNumLegs() + " legs." );
		
		ziggy.setSkinColor( jop.showInputDialog( null,"What color is Ziggy's skin? " ) );
		jop.showMessageDialog( null,"Ziggy's skin color is: " + ziggy.getSkinColor() );
		
		ziggy.setNumEyes( Integer.parseInt( jop.showInputDialog( null,"How many eyes does Ziggy have? " ) ) );
		jop.showMessageDialog(null,"Ziggy has: " + ziggy.getNumEyes() + " eyes.");
		
		ziggy.setNumLegs( Integer.parseInt( jop.showInputDialog( null,"How many legs does Ziggy have? " ) ) );
		jop.showMessageDialog(null,"Ziggy has: " + ziggy.getNumLegs() + " legs.");
	
	}//main()
	
}//TwoCharacters.java
