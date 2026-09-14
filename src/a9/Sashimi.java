package a9;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Sashimi class simplifies its superclass of SinisterSushi by
 * passing in final actor statistics and only having the starting 
 * location as a constructor parameter. The Sashimi have moderate health 
 * but a slow speed. 
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class Sashimi extends SinisterSushi {
	
	private static final BufferedImage SASHIMI_IMAGE;
	private static final int SASHIMI_HEALTH = 100;
	private static final int SASHIMI_COOLDOWN = 50;
	private static final double SASHIMI_SPEED = -0.5;
	private static final int SASHIMI_DAMAGE = 30;
	static {
		BufferedImage sashimiImage = null;
		try {
			sashimiImage = ImageIO.read(new File("src/a9/Icons/sashimi.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		SASHIMI_IMAGE = sashimiImage;
	}
	private static final Point2D.Double SASHIMI_HITBOX = new Point2D.Double(SASHIMI_IMAGE.getWidth(), SASHIMI_IMAGE.getHeight());
	
	/**
	 * Constructor for the sushi class. Passes character stats to the parent class and simplifies
	 * the constructor to only take position.
	 * @param startingPosition the location to place the actor
	 */
	public Sashimi(Double startingPosition) {
		super(startingPosition, SASHIMI_HITBOX, SASHIMI_IMAGE, SASHIMI_HEALTH, SASHIMI_COOLDOWN, SASHIMI_SPEED, SASHIMI_DAMAGE);
	}
}