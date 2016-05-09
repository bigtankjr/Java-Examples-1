
public class RemoveExtensionTest {
	
	public static String removeExtension(String s) {

	    String separator = System.getProperty("file.separator");
	    String filename;

	    // Remove the path upto the filename.
	    int lastSeparatorIndex = s.lastIndexOf(separator);
	    if (lastSeparatorIndex == -1) {
	        filename = s;
	    } else {
	        filename = s.substring(lastSeparatorIndex + 1);
	    }

	    // Remove the extension.
	    int extensionIndex = filename.lastIndexOf(".");
	    if (extensionIndex == -1)
	        return filename;

	    return filename.substring(0, extensionIndex);
	}
	public static void main(String args[])
	{
		
		
		System.out.println(removeExtension("a.jpg.jpg"));
		System.out.println(removeExtension("a.jpg"));
		System.out.println(removeExtension("a.txt"));
		System.out.println(removeExtension("amended.jpg.jpg"));
		System.out.println(removeExtension("amended.jpg"));
		System.out.println(removeExtension("c.xlsx.xlsx"));
		System.out.println(removeExtension("C:/Users/You Know who/Something really crazy like.csv"));
		
	}

}
