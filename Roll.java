package a9;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Roll class simplifies its superclass of SinisterSushi by passing 
 * in final character statistics and only having the starting location as a
 * constructor parameter. The Roll have a unique ability to increase their speed 
 * every four seconds, giving them the appearance of rolling on the battlefield.
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class Roll extends SinisterSushi {
	
	private static final BufferedImage ROLL_IMAGE;
	private static final int ROLL_HEALTH = 100;
	private static final int ROLL_COOLDOWN = 50;
	private static final double ROLL_SPEED = -1.5;
	private static final int ROLL_DAMAGE = 10;
	static {
		BufferedImage rollImage = null;
		try {
			rollImage = ImageIO.read(new File("src/a9/Icons/roll.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		ROLL_IMAGE = rollImage;
	}
	private static final Point2D.Double ROLL_HITBOX = new Point2D.Double(ROLL_IMAGE.getWidth(), ROLL_IMAGE.getHeight());
	private boolean isRolling;
	
	/**
	 * Constructor for the sushi class. Passes character stats to the parent class and simplifies
	 * the constructor to only take position.
	 * @param startingPosition the location to place the actor
	 */
	public Roll(Double startingPosition) {
		super(startingPosition, ROLL_HITBOX, ROLL_IMAGE, ROLL_HEALTH, ROLL_COOLDOWN, ROLL_SPEED, ROLL_DAMAGE);
	}
	
	/**
	 * Override move method. Checks the timer from the Actor super class. Every four seconds
	 * the roll increases its speed for a brief moment, giving it the appearance of rolling.
	 */
	@Override
	public void move() {
		if (getDespawnTime() % 4 == 0) {
			isRolling = true;
		}
		if (!super.getColliding()) {
			if (isRolling) {
				shiftPosition(new Point2D.Double(getSpeed() * 1.5, 0));
				isRolling = false;
			}
			else {
				shiftPosition(new Point2D.Double(getSpeed(), 0));
			}
		}
	}
	
	/**
	 * Override attack method. Checks if the Roll is rolling. If it is,
	 * it attacks twice, if not, it attacks normally.
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) {
		if (other instanceof FeudalFighters) {
			if (isRolling) {
				super.attack(other);
			}
			super.attack(other);
		}
	}
}