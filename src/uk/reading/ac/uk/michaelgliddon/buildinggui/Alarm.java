package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.awt.Point;
import java.io.Serializable;

public class Alarm extends Things implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * setting the properties of the alarm
	 * 
	 * @param pxy
	 */
	protected Alarm(Point pxy) {
		super(pxy);
		xy = pxy;
		size = 4;
		col = 'b';
		thingsID = 90;
	}

	public String getStrType() {
		return ("Alarm ");
	}

	public void update() {

	}
}
