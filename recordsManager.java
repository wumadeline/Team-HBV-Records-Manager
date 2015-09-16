import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class recordsManager {

	public static void main(String[] args) {
		recordsManager state = new recordsManager();
		File existingState = new File("myState.ser");
		
		// load in previous .ser file
		// following if statement referenced from http://avajava.com/tutorials/lessons/how-do-i-write-an-object-to-a-file-and-read-it-back.html
		if (existingState.exists()) {
			try {
				FileInputStream inFile = new FileInputStream("myState.ser");
				ObjectInputStream inObject = new ObjectInputStream(inFile);
				state = (recordsManager) inObject.readObject();
				inObject.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		// load out finished .ser file
		// following code referenced from http://avajava.com/tutorials/lessons/how-do-i-write-an-object-to-a-file-and-read-it-back.html
		try {
			FileOutputStream outFile = new FileOutputStream("myState.ser");
			ObjectOutputStream outObject = new ObjectOutputStream(outFile);
			outObject.writeObject(state);
			outObject.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}