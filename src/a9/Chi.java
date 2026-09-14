package a9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Chi class simplifies its superclass of Actor by passing 
 * in final character statistics and only having the starting location as a
 * constructor parameter. The Chi are your energy source during the game. They
 * appear randomly on the map and when clicked, they give 100 chi to be used to 
 * summon the fighters. They do not attack or move.
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class Chi extends Actor {

	private static final BufferedImage CHI_IMAGE;
	private static final int CHI_HEALTH = 1;
	private static final int CHI_COOLDOWN = 0;
	private static final int CHI_DAMAGE = 0;
	static {
		BufferedImage knifeImage = null;
		try {
			knifeImage = ImageIO.read(new File("src/a9/Icons/cleaver.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		CHI_IMAGE = knifeImage;
	}
	private static final Point2D.Double KNIFE_HITBOX = new Point2D.Double(CHI_IMAGE.getWidth(), CHI_IMAGE.getHeight());
	private static final BufferedImage END;
	static {
		BufferedImage endImage = null;
		try {
			endImage = ImageIO.read(new File("src/a9/Icons/END.png"));
		} catch (IOException e) {
			System.out.println("A file was not found");
			System.exit(0);
		}
		END = endImage;
	}
	
	/**
	 * Constructor for the Chi class. Passes character stats to the parent class and simplifies
	 * the constructor to only take starting position.
	 * @param startingPosition the location to place the actor
	 */
	public Chi(Point2D.Double startingPosition) {
		super(startingPosition, KNIFE_HITBOX, CHI_IMAGE, CHI_HEALTH, CHI_COOLDOWN, 0, CHI_DAMAGE);
	}
	
	/**
	 * Secondary constructor for the end game. Has its own image.
	 * @param startingPosition the location to place the actor
	 * @param theEnd a pointless variable to overload the constructor to use a different image
	 */
	public Chi(Point2D.Double startingPosition, int theEnd) {
		super(startingPosition, KNIFE_HITBOX, END, CHI_HEALTH, CHI_COOLDOWN, 0, CHI_DAMAGE);
	}
	
	/**
	 * This override is empty so that none of the Chi will have health bars.
	 */
	@Override
	public void drawHealthBar(Graphics g) {
		
	}
}