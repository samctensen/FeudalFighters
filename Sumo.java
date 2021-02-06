package a9;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Sumo class simplifies its superclass of FeudalFighter by
 * passing in final actor statistics. The Sumo have large health and 
 * heavy attacks that come with the cost of a long cooldown.
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class Sumo extends FeudalFighters {
	
	private static final BufferedImage SUMO_IMAGE;
	private static final int SUMO_HEALTH = 150;
	private static final int SUMO_COOLDOWN = 25;
	private static final int SUMO_DAMAGE = 25;
	static {
		BufferedImage sumoImage = null;
		try {
			sumoImage = ImageIO.read(new File("src/a9/Icons/sumo.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		SUMO_IMAGE = sumoImage;
	}
	private static final Point2D.Double SUMO_HITBOX = new Point2D.Double(SUMO_IMAGE.getWidth(), SUMO_IMAGE.getHeight());
	
	/**
	 * Constructor for the Sumo class. Passes character stats to the parent class and simplifies
	 * the constructor to only take position.
	 * @param startingPosition the 2D grid location to add the actor.
	 */
	public Sumo(Point2D.Double startingPosition) {
		super(startingPosition, SUMO_HITBOX, SUMO_IMAGE, SUMO_HEALTH, SUMO_COOLDOWN, SUMO_DAMAGE);
	}
}