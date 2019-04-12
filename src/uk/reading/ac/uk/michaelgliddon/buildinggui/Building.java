package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Michael-Lee Class for a building which have rooms
 */
public class Building implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The building in which there are various rooms and objects Its size is defined
	 * by xSize,ySize Variables are used for actual rooms
	 * 
	 */
	private int xSize = 10; // x size of building
	private int ySize = 10; // y size of building
	private ArrayList<Room> allRooms; // array of rooms
	private Random ranGen; // for generating random numbers
	private ArrayList<Things> allThings; // array of things inside the building
	private ArrayList<Things> allPeople;
	public int PeopleInRoom = 0;

	/**
	 * Construct a building
	 * 
	 * @param bs
	 */
	public Building(String bs) {
		allRooms = new ArrayList<Room>(); // create space for rooms
		allThings = new ArrayList<Things>(); // create space for Things inside the building
		allPeople = new ArrayList<Things>();
		ranGen = new Random(); // create object for generating random numbers
		setBuilding(bs); // now set building using string bs
	}

	/**
	 * x size of the building
	 * 
	 * @return xSize
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * y size of the building
	 * 
	 * @return size in y direction of building
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * set up the building, as defined in string
	 * 
	 * @param bS of form xS,yS;x1 y1 x2 y2 xd yd ds; etc xS,yS define size, and for
	 *           each room have locations of opposite corners, door and door size
	 */
	public void setBuilding(String bS) {
		allRooms.clear();
		StringSplitter bSME = new StringSplitter(bS, ";"); // split strings by ;
		StringSplitter bSz = new StringSplitter(bSME.getNth(0, "5 5"), " "); // split first by space
		xSize = bSz.getNthInt(0, 5); // get first of the first string, being xsize
		ySize = bSz.getNthInt(1, 5);
		for (int ct = 1; ct < bSME.numElement(); ct++) // remaining strings define rooms
			allRooms.add(new Room(bSME.getNth(ct, ""))); // add each in turn
		allThings.add(new Light(allRooms.get(0).LightPos()));
		allThings.add(new Alarm(allRooms.get(2).AlarmPos()));
		allPeople.add(new Person(allRooms.get(0).PersonPos()));
	}

	/**
	 * set new destination for person and path to it In this version puts person in
	 * room 1 and they navigate to a random room
	 * 
	 * @param occupant
	 */
	void setNewRoom(Things occupant) {
		// at this stage all this does is allow an occupant to navigate to a door then
		// outside, then to a random room
		int cRoom = whichRoom(occupant.getXY());
		int dRoom = cRoom;
		while (dRoom == cRoom)
			dRoom = ranGen.nextInt(allRooms.size()); // navigate to another random room
		occupant.clearPath();
		occupant.setPath(allRooms.get(cRoom).getByDoor(+15)); // move outside of door of the current room
		occupant.setPath(allRooms.get(cRoom).getByDoor(-15)); // move inside door
		occupant.setPath(allRooms.get(cRoom).getByDoor(0)); // move inside doorway
		occupant.setPath(allRooms.get(dRoom).getByDoor(+15)); // move outside of next room door
		occupant.setPath(allRooms.get(dRoom).getByDoor(-15)); // move inside next room door
		occupant.setPath(allRooms.get(dRoom).getByDoor(0)); // move inside the doorway
		occupant.setStopped(false); // say person can move
	}

	/**
	 * calculate a random room number
	 * 
	 * @return number in range 0.. number of rooms
	 */
	public int randRoom() {
		return ranGen.nextInt(allRooms.size());
	}

	/**
	 * create new person and set path for it to follow and starting position
	 */
	public void addPerson() {
		allPeople.add(new Person(allRooms.get(0).PersonPos())); // adding a new person to the room
	}

	/**
	 * Remove a person from the array
	 */
	public void RemovePerson() {
		allPeople.remove(0);
	}

	/**
	 * Counts the number of people in a room
	 * 
	 * @param Room r
	 * @return PeopleInRoom
	 */
	int PersonCount(Room r) {
		PeopleInRoom = 0;
		for (int i = 0; i < allPeople.size(); i++) {
			Point a = allPeople.get(i).getXY();
			if (r.isInRoom(a) == true) {
				PeopleInRoom++;
			}
		}
		return PeopleInRoom;
	}

	/**
	 * Method for turning the light on
	 */
	void On() {
		for (int i = 0; i < allThings.size(); i++) {
			if (allThings.get(i).thingsID == 100000) {
				allThings.get(i).col = 'y';
			}
		}
	}

	/**
	 * Method for turning the light off
	 */
	void Off() {
		for (int i = 0; i < allThings.size(); i++) {
			if (allThings.get(i).thingsID == 100000) { // searches the allThings arraylist and identifies the Light's
														// thingsID
				allThings.get(i).col = 'k'; // Once found
			}
		}
	}

	/**
	 * function for changing the color of alarm
	 */
	void AlarmOn() {
		for (int i = 0; i < allThings.size(); i++) {
			if (allThings.get(i).thingsID == 90) {
				allThings.get(i).col = 'r';
				allThings.get(i).size = 8;
			}
		}
	}

	/**
	 * function for changing the color of alarm
	 */
	void AlarmOff() {
		for (int i = 0; i < allThings.size(); i++) {
			if (allThings.get(i).thingsID == 90) {
				allThings.get(i).col = 'b';
				allThings.get(i).size = 4;
			}
		}
	}

	/**
	 * to check if person is in room 2
	 */
	public void CheckRoom() {
		if (PersonCount(allRooms.get(2)) > 0) {
			AlarmOn();
		} else {
			AlarmOff();
		}
	}

	/**
	 * show all the building's rooms and person in the interface
	 * 
	 * @param bi the interface
	 */
	public void showBuilding(BuildingGUI bi) {
		for (Room r : allRooms)
			r.showRoom(bi);
		{
			for (Things t : allPeople)
				t.showThings(bi);
			for (Things l : allThings)
				l.showThings(bi);
			CheckRoom();
		}
		// loop through array of all rooms, displaying each
		// occupant.showPerson(bi);
	}

	/**
	 * method to update the building Here it just deals with the occupant
	 */
	public void update() {
		// for (Things t : allThings)
		for (Things p : allPeople)
			if (p.getStopped())
				setNewRoom(p);
			else {
				p.update();
			}
	}

	/**
	 * method to determine which room position x,y is in
	 * 
	 * @param xy
	 * @return n, the number of the room or -1 if in corridor
	 */
	public int whichRoom(Point xy) {
		int ans = -1;
		for (int ct = 0; ct < allRooms.size(); ct++)
			if (allRooms.get(ct).isInRoom(xy))
				ans = ct;
		return ans;
	}

	private double temp(Room t) {
		int PersonCount = PersonCount(t);
		double temperature = 18 + (PersonCount * 2);
		return temperature;
	}

	/**
	 * method to return information bout the building as a string
	 */
	public String toString() {
		String s = "Building size " + getXSize() + "," + getYSize() + "\n\n";
		for (Room r : allRooms)
			s = s + r.toString() + "\nNumber of people in the room: " + PersonCount(r) + "\nTemperature of room: "
					+ temp(r) + "\n\n";
		{

			for (Things t : allThings)
				s = s + t.toString() + "\n";
			{
				for (Things p : allPeople)
					s = s + p.toString() + "\n";
				return s;

			}
		}
	}

	public static void main(String[] args) {
		Building b = new Building("500 600;0 0 250 250 100 250 20;250 0 450 450 320 450 30;0 300 450 450 300 300 15"); // create
																														// building
		System.out.println(b.toString()); // and print it
	}

}
