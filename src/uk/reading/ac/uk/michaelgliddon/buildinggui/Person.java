package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.awt.Point;
import java.io.Serializable;

/**
 * @author Michael-Lee This is for people moving in building
 */
public class Person extends Things implements Serializable {

	private static final long serialVersionUID = 1L;

	private int check = 0;

	/**
	 * create person at the given xy position
	 * 
	 * @param xys position
	 */
	protected Person(Point xys) {
		super(xys);
		xy = xys;
		size = 3;
		stopped = true; // by default not moving
		col = 'r';
	}

	private boolean equalXY(Point pathXY) {
		return ((int) pathXY.getX() == (int) xy.getX()) && ((int) pathXY.getY() == (int) xy.getY());
	}
/**
 * displaying "Person" on the information panel
 * @return Person
 */
	protected String getStrType() {
		return "Person ";
	}

	/**
	 * move one step towards the given position
	 * 
	 * @param pathXY
	 */
	private void moveTowards(Point pathXY) {
		int dx = 0; // amount by which it will move in x .. and y, set to -1, 0 or 1
		int dy = 0;
		if (xy.getX() < pathXY.getX())
			dx = 1;
		else if (xy.getX() > pathXY.getX())
			dx = -1;
		if (xy.getY() < pathXY.getY())
			dy = 1;
		else if (xy.getY() > pathXY.getY())
			dy = -1;
		xy.translate(dx, dy); // now move
	}

	/**
	 * attempt to move person unless it is stopped
	 */
	public void update() {
		if (check == path.size()) {
			check = 0;
		}
		else if (equalXY(path.get(check))) { // if at next point on path
			check++;// as one point in this version, say stopped
		} else
			moveTowards(path.get(check)); // move closer to next destination
	}
}