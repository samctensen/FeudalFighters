package a9;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Samurai class simplifies its superclass of FeudalFighter by
 * passing in final actor statistics and only having the starting location 
 * as a constructor parameter. The Samurai have a unique ability
 * to increase their damage when enough troops have been summoned.
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class Samurai extends FeudalFighters {
	
	private static final BufferedImage SAMURAI_IMAGE;
	private static final int SAMURAI_HEALTH = 100;
	private static final int SAMURAI_COOLDOWN = 10;
	private static int samuraiDamage = 15;
	static {
		BufferedImage samuraiImage = null;
		try {
			samuraiImage = ImageIO.read(new File("src/a9/Icons/samurai.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		SAMURAI_IMAGE = samuraiImage;
	}
	private static final Point2D.Double SAMURAI_HITBOX = new Point2D.Double(SAMURAI_IMAGE.getWidth(), SAMURAI_IMAGE.getHeight());
	
	/**
	 * Constructor for the samurai class. Passes character stats to the parent class and simplifies
	 * the constructor to only take position.
	 * 
	 * @param startingPosition the 2D grid location to add the actor.
	 */
	public Samurai(Point2D.Double startingPosition) {
		super(startingPosition, SAMURAI_HITBOX, SAMURAI_IMAGE, SAMURAI_HEALTH, SAMURAI_COOLDOWN, samuraiDamage);
	}
	
	/**
	 * Override attack method. Implements the samurai special ability, and if conditions are met,
	 * the Samurai will attack with higher damage.
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) {
		if(other instanceof SinisterSushi) {
			if (this.isReadyForAction()) {
				if (this != other && this.isCollidingOther(other)) {	
					other.changeHealth(-samuraiDamage);
					this.resetCoolDown();			
				}
			}
		}
	}
	
	/**
	 * Changes the samurais attack damage to the parameter.
	 * 
	 * @param samurais new attack damage. 
	 */
	@Override
	public void changeDamage(int change) {
		samuraiDamage = change;
	}
} 