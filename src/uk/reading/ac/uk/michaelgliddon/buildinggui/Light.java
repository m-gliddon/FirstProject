package uk.reading.ac.uk.michaelgliddon.buildinggui;

import java.awt.Point;
import java.io.Serializable;

/**
 * class for a Light inside the building
 * 
 * @author michael-lee gliddon
 *
 */
public class Light extends Things implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * setting the properties of the light
	 * 
	 * @param pxy
	 */
	Light(Point pxy) {
		super(pxy);
		xy = pxy;
		size = 6;
		col = 'k';
		thingsID = 100000;
	}

	/**
	 * displays light on pane
	 * 
	 * @param name of Light
	 */
	protected String getStrType() {
		return "Light ";
	}

	public void update() {

	}

}
