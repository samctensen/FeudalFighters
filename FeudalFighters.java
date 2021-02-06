package a9;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

/**
 * The FeudalFighters class extends Actor to make a distinction from the Sushi class.
 * The FeudalFighters have a set speed of 0, meaning they do not move throughout the 
 * battle.
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class FeudalFighters extends Actor {
	
	public FeudalFighters(Point2D.Double startingPosition, Point2D.Double initHitbox, BufferedImage img, int health, int coolDown, int attackDamage) {
		super(startingPosition, initHitbox, img, health, coolDown, 0, attackDamage);
	}

	/**
	 * An attack means the two hotboxes are overlapping and the
	 * Actor is ready to attack again (based on its cooldown).
	 * 
	 * Plants only attack Zombies.
	 * 
	 * @param other
	 */
	@Override
	public void attack(Actor other) {
		if (other instanceof SinisterSushi) {
			super.attack(other);
		}
	}
}