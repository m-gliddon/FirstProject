package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Michael-Lee Defines a things abstract class to be added to the
 *         building
 */
public abstract class Things implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Point xy; // person's position
	protected ArrayList<Point> path; // path it follows .. a series of xy points moves between
	protected boolean stopped; // is it moving
	protected char col;
	protected int thingsID;
	static int thingscounter = 0;
	protected int size;
	protected boolean Off;
	protected int score;
	// public int LightID;

	protected Things(Point ip) {
		xy = ip;
		size = 3;
		thingsID = thingscounter++;
		path = new ArrayList<Point>(); // create empty path
		// LightID = 1;

	}

	public Point getXY() {
		return xy;
	}

	/**
	 * get x coordinate of person
	 * 
	 * @return x
	 */
	public int getX() {
		return (int) xy.getX();
	}

	/**
	 * get y coordinate of person
	 * 
	 * @return y
	 */
	public int getY() {
		return (int) xy.getY();
	}

	/**
	 * set the person's position
	 * 
	 * @param pxy new position
	 */
	public void setXY(Point pxy) {
		xy = pxy;
	}

	/**
	 * set person as being stopped or not
	 * 
	 * @param isStopped
	 */
	public void setStopped(boolean isStopped) {
		stopped = isStopped;
	}

	/**
	 * Is person stopped
	 * 
	 * @return if so
	 */
	public boolean getStopped() {
		return stopped;
	}

	/**
	 * show person in the given building interface
	 * 
	 * @param bi
	 */

	public void clearPath() {
		path.clear();
	}

	/**
	 * add new xy to path
	 * 
	 * @param xyp new position
	 */
	public void setPath(Point xyp) {
		path.add(xyp);
	}

	/**
	 * getting the ID of the thing
	 * 
	 * @return thingsID
	 */
	public int getID() {
		return thingsID;
	}

	/**
	 * showing the things on the GUI
	 * 
	 * @param bi
	 */
	public void showThings(BuildingGUI bi) {
		bi.showItem((int) xy.getX(), (int) xy.getY(), size, col);
	}

	/**
	 * 
	 * @return name of thing
	 */
	protected String getStrType() {
		return "Things";
	}

	public String toString() {
		return getStrType() + (int) xy.getX() + ", " + (int) xy.getY();
	}

	public void update() {

	}

}
