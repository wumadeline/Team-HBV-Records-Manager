import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class recordsManager {
	HashMap<String, Person> members;
	HashMap<String, Event> events;
	
	public recordsManager() {
		members = new HashMap<String, Person>();
		events = new HashMap<String, Event>();
	}
	
	public void eventEntry(String name, ArrayList<String> people) {
		if (! events.containsKey(name)) {
			System.out.println("Event " + name + " does not exist");
		} 
		Event e = events.get(name);
		for (String s: people) {
			Person member = members.get(s);
			e.addPerson(member);
			member.addEventAttendance(e);
			System.out.println(member.myName + "attended " + name);
		}
	}
	
	public class Person {
		private String myName;
		private int myPoints;
		private ArrayList<Event> myEvents;
		
		public Person(String name) {
			myName = name;
			myPoints = 0;
			myEvents = new ArrayList<Event>();
			members.put(myName, this);
			System.out.println("Added new member: " + myName + "!");
		}
		
		public void addEventAttendance(Event e) {
			myEvents.add(e);
			// THIS CHECK NEEDS TO BE FIXED
			if ((! e.myType.equals("Other Social")) && (! e.myType.equals("General Meeting"))) {
				this.myPoints++;
			}
			this.myPoints++;
		}
		
		public ArrayList getEvents() {
			return myEvents;
		}
	}
	
	public class Event {
		private String myName; 
		private ArrayList<Person> myPeople;
		private String myType;
		
		public Event(String name, String type) {
			myName = name;
			myPeople = new ArrayList<Person>(); 
			myType = type;
			events.put(myName, this);
			System.out.println("Created event: " + myName + "!");
		}
		
		public void addPerson(Person p) {
			if (! myPeople.contains(p)) {
				myPeople.add(p);
			} else {
				System.out.println(p.myName + " is already associated with event: " + myName);
			}
		}
	}

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