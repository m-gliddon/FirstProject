package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.awt.Point;
import java.io.Serializable;
import java.util.Random;

/**
 * @author Michael-Lee Defines a room in a building
 */
public class Room implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x1, y1, x2, y2, xd, yd, ds; // x1,y1 and x2,y2 are opp corners of room,
											// xd,yd are door location ds is door size

	/**
	 * construct room, dummy parameters
	 */

	public Room() {
		this("0 0 5 5 0 2");
	}

	/**
	 * construct room
	 * 
	 * @param rStr has xcoords of opposite corners and door as numbers separated by
	 *             spaces
	 */
	public Room(String rStr) {
		StringSplitter m = new StringSplitter(rStr, " ");
		x1 = m.getNthInt(0, 0);
		y1 = m.getNthInt(1, 0);
		x2 = m.getNthInt(2, 5);
		y2 = m.getNthInt(3, 5);
		xd = m.getNthInt(4, 0);
		yd = m.getNthInt(5, 2);
		ds = m.getNthInt(6, 1);
	}

	/**
	 * return size of door
	 * 
	 * @return door size
	 */
	public int getDoorSize() {
		return ds;
	}

	/**
	 * position of the light
	 * 
	 * @return position of the light
	 */
	public Point LightPos() {
		int z = (20);
		int m = (20);
		Point v = new Point(z, m);
		return v;
	}

	public Point PersonPos() {
		Point m = new Point(x2 / 2, y2 / 2);
		return m;
	}

	/**
	 * position of the alarm
	 * 
	 * @return position of the alarm
	 */
	public Point AlarmPos() {
		int z = (335);
		int m = (35);
		Point f = new Point(z, m);
		return f;
	}

	/**
	 * doWall draw wall, but check if door there
	 * 
	 * @param bi interface into which it is shown
	 * @param xa start x coord of wall
	 * @param ya and y
	 * @param xb end x coord of wall
	 * @param yb and y
	 */
	private void doWall(BuildingGUI bi, int xa, int ya, int xb, int yb) {
		if ((xa == xb) && (xd == xa)) { // if vert wall, check if door in it
			bi.showWall(xa, ya, xb, yd - 1);
			bi.showWall(xa, yd + ds, xb, yb);
		} else if ((ya == yb) && (yd == ya)) { // similar for horiz wall
			bi.showWall(xa, ya, xd - 1, yd);
			bi.showWall(xd + ds, yd, xb, yb);
		} else
			bi.showWall(xa, ya, xb, yb);
	}

	/**
	 * show the room into the interface bi
	 * 
	 * @param bi
	 */
	public void showRoom(BuildingGUI bi) {
		doWall(bi, x1, y1, x1, y2); // show each of its walls
		doWall(bi, x2, y1, x2, y2);
		doWall(bi, x1, y2, x2, y2);
		doWall(bi, x1, y1, x2, y1);
	}

	/**
	 * get random xy position within rooom
	 * 
	 * @param r random generator to use
	 * @return coordinate
	 */
	public Point getRandom(Random r) {
		return new Point(x1 + 1 + r.nextInt(x2 - x1 - 2), y1 + 1 + r.nextInt(y2 - y1 - 2));
	}

	/**
	 * return x,y coordinate just inside or just outside door
	 * 
	 * @param forinside being 1 if inside 0 if at door and -1 if outside
	 * @return
	 */
	public Point getByDoor(int forinside) {
		int x = xd; // x,y position calculated
		int y = yd;
		// need to find which wall door is on, and act accordingly
		if (yd == y2) {
			y = yd + forinside;
			x = xd + ds / 2;
		} else if (yd == y1) {
			y = yd - forinside;
			x = xd + ds / 2;
		} else if (xd == x1) {
			x = xd - forinside;
			y = yd + ds / 2;
		} else {
			x = xd + forinside;
			y = yd + ds / 2;
		}
		return new Point(x, y);
	}

	/**
	 * report whether xy position is within room
	 * 
	 * @param xy
	 * @return
	 */
	public boolean isInRoom(Point xy) {
		return (xy.getX() > x1 && xy.getX() < x2 && xy.getY() > y1 && xy.getY() < y2);
	}

	/**
	 * return String describing room
	 */
	public String toString() {
		return "Room from " + x1 + "," + y1 + " to " + x2 + "," + y2 + " door at " + xd + "," + yd;
	}

	public static void main(String[] args) {
		// main function to test class
		Room r = new Room("0 0 5 5 0 2"); // create room
		System.out.println(r.toString()); // and print it
		Point P = new Point(7, 9);
		String S = (int) P.getX() + ", " + (int) P.getY() + " is ";
		if (r.isInRoom(P) == false)
			S = S + "not ";
		S = S + "in the room";
		System.out.println(S);
	}

}
