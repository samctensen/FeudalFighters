package a9;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class creates and starts the century long war between the
 * Feudal Fighters and the Sinister Sushi. The JFrame battlefield is 
 * generated and fighters of both sides are randomly placed and begin to fight.
 * 
 * @author Sam Christensen
 * @author Jack Doughty
 */
public class TheGreatBattle extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private static final int[] GRIDX = {25, 100, 175, 250, 325, 400, 475};
	private static final int[] GRIDY = {75, 150, 225, 300, 375};
	private static JButton sumoButton = new JButton("Summon: Sumo -200");
	private static JButton samuraiButton = new JButton("Summon: Samurai -150");
	private JLabel chiScore;
	private JLabel survivalTime;
	private Timer timer;
	// Fighters and sushi all go in here
	public ArrayList<Actor> actors; 
	private Random randGenerator;
	private int numRows;
	private int numCols;
	private int cellSize;
	private int type;
	private double difficulty;
	private int chi = 350;
	private double time = 0;
	private boolean mouseOn = false;
	private int samuraiCount;
	private double elapsedTime;
	private int elapsedTimeInt;

	/**
	 * Setup the basic info for the battle
	 */
	public TheGreatBattle() {
		super();
		// Define some quantities of the scene
		numRows = 5;
		numCols = 7;
		cellSize = 75;
		setPreferredSize(new Dimension(50 + numCols * cellSize, 50 + 50 + numRows * cellSize));
		randGenerator = new Random();
		// Store all the actors in here.
		actors = new ArrayList<>();
		// Panel for the menu bar.
		JPanel HUD = new JPanel();
		//JLabel to display time elapsed.
		survivalTime = new JLabel(" Time: 0 ");
		HUD.add(survivalTime);
		survivalTime.setBorder(BorderFactory.createLineBorder(Color.black));
		// Buttons to select a FeudalFighters to be placed.
		HUD.add(sumoButton);
		HUD.add(samuraiButton);
		// JLabel to display energy.
		chiScore = new JLabel(" Chi: " + chi + " ");
		chiScore.setBorder(BorderFactory.createLineBorder(Color.black));
		HUD.add(chiScore);
		HUD.setPreferredSize(new Dimension(50 + numCols * cellSize, 50));
		HUD.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		add(HUD, BorderLayout.PAGE_START);
		// Get the javax.swing Timer, not from util.
		sumoButton.addActionListener(this);
		samuraiButton.addActionListener(this);
		addMouseListener(this);
		// The timer updates the game each time it goes.
		timer = new Timer(30, this);
		timer.start();
	}

	/***
	 * Implement the paint method to draw the plants
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g, 0);
			actor.drawHealthBar(g);
		}
	}

	/**
	 * 
	 * This is triggered by the timer. It is the game loop of this test.
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Stores the elapsed survival time and puts it in the survivalTimeLabel
		elapsedTime += 0.03; 
		elapsedTimeInt = (int)elapsedTime;
		survivalTime.setText(" Time: " + elapsedTimeInt + " ") ;
		
		// Listener for the sumoButton to plant.
		if(e.getSource() == sumoButton) {
			//mouseOn = false;
			type = 1;
			mouseOn = true;
		}
		// Listener for the samuraiButton to plant.
		if(e.getSource() == samuraiButton) {
			//mouseOn = false;
			type = 2;
			mouseOn = true;
		}
		// Randomly place energy.
		int n = randGenerator.nextInt(1000);
		if (n > 980) {
			int x = randGenerator.nextInt(50 + numCols * cellSize);
			int y = randGenerator.nextInt(50 + numRows * cellSize) + 50;
			Point2D.Double position = new Point2D.Double(x, y);
			Chi chi = new Chi(position);
			actors.add(chi);
		}
		// Removes energy after 7 seconds.
		for (Actor actor: actors) {
			if ((actor.getDespawnTime() >= 7) && actor instanceof Chi) {
				actor.changeHealth(-1);
			}
		}
		// Ends the game when finish line is crossed.
		boolean gameOver = false;
		for (Actor actor: actors) {
			if (actor.getPosition().getX() <= -32 && actor instanceof SinisterSushi) {
				gameOver = true;
			}
		}
		// Adds the game over actor
		Point2D.Double middle = new Point2D.Double(((50 + numCols * cellSize) / 2) - 125, ((50 + 50 + numRows * cellSize) / 2) - 125);
		if (gameOver) {
			// Chi have a secondary constructor for the EndGame image.
			Chi theEnd = new Chi(middle, 0);
			actors.add(theEnd);
		}
		// Checks for the end game screen and freezes all code.
		for (Actor actor: actors) {
			if (actor.getPosition().equals(middle) && actor instanceof Chi) {
				// Wait for half a second, then freeze the screen
				if (actor.getDespawnTime() >= 0.5) {
					try {
						Thread.sleep(3000);
						// After the message is displayed and the code has froze, close the app.
				        System.exit(0);
					} 
					catch (Exception theEnd) {
				            System.out.println(theEnd);
				         }
				}
			}
		}
		// Changes the spawn rate of the enemy sushi every 10 seconds.
		// Because the timer iterates every 30 milliseconds, the timer must add 0.03 to make add up to 1000 milliseconds after 1 second.
		time = time + 0.03;
		// Rounds the number down without decimals.
		double timeSeconds = Math.round(time * 10) / 10.0;
		if (timeSeconds % 10 == 0) {
			difficulty = difficulty + 1.5;
		}
		// Randomly place sushi, the spawn rate increases by 1.5 every 10 seconds.
		if (n < (5 + difficulty)) {
			int row = randGenerator.nextInt(numRows);
			int type = randGenerator.nextInt(3);
			// Rolls have a third chance of spawning because they are stronger.
			if (type == 1) {
				Roll roll = new Roll(new Point2D.Double(500, GRIDY[row]));
				actors.add(roll);
			}
			else {
				Sashimi sashimi = new Sashimi(new Point2D.Double(500, GRIDY[row]));
				actors.add(sashimi);
			}
		}
		//Buffs all samurai's damage when 5 are on the field at once.
		samuraiCount = 0;
		for(Actor actor: actors) {
			if (actor instanceof Samurai) {
				samuraiCount++;
				if(samuraiCount >= 5) {
					actor.changeDamage(100);
				}
				// If 5 are placed and 1 dies, it must lower the damage to the original.
				if(samuraiCount < 5) {
					actor.changeDamage(15);
				}
			}
		}
		// This method is getting a little long, but it is mostly loop code
		// Increment their cooldowns and reset collision status
		for (Actor actor : actors) {
			actor.update();
		}
		// Try to attack 
		for (Actor actor : actors) {
			for (Actor other : actors) {
					actor.attack(other);
			}
		}
		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else {
				actor.removeAction(actors); // any special effect or whatever on removal
			}
		}
		actors = nextTurnActors;
		// Check for collisions between zombies and plants and set collision status
		for (Actor actor : actors) {
			for (Actor other : actors) {
				actor.setCollisionStatus(other);
			}
		}
		// Move the actors.
		for (Actor actor : actors) {
			actor.move(); // for Sushi, only moves if not colliding.
		}
		// Redraw the new scene
		repaint();
	}
	
	/**
	 * Listener for the mouse if it has been clicked.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int clickx = e.getX();
		int clicky = e.getY();
		// Checks if the mouse is clicked on Chi.
		for (Actor actor: actors) {
			if (actor.isCollidingPoint(new Point2D.Double(clickx, clicky)) && actor instanceof Chi) {
				actor.changeHealth(-1);
				chi = chi + 100;
			}
		}
		// MouseOn is true after either plant button has been pressed. It now listens for the click to plant.
		if(mouseOn) {
			boolean atLocation = false;
			// Rounds the coordinates to ints in the grid.
			int col = (e.getX() - 25) / 75;
			int row = (e.getY() - 75) / 75;
			Point2D.Double position = new Point2D.Double(GRIDX[col], GRIDY[row]);
			// Checks if the grid spot is taken.
			for (Actor actor: actors) {
				if (actor.getPosition().equals(position)) {
					atLocation = true;
				}
			}
			// If not taken then plant.
			if (!atLocation) {
				// Plant sumo if the sumoButton was pressed.
				if (type == 1 && chi >= 200) {
					Sumo sumo = new Sumo(new Point2D.Double(GRIDX[col], GRIDY[row]));
					actors.add(sumo);
					chi = chi - 200;
				}
				// Plant samurai if the samurai was pressed.
				if (type == 2 && chi >= 150) {
					Samurai samurai = new Samurai(new Point2D.Double(GRIDX[col], GRIDY[row]));
					actors.add(samurai);
					chi = chi - 150;
				}
			}
			// Reset the mouseListener
			mouseOn = false;
		}
		// Update the chiScore JLabel.
		chiScore.setText(" Chi: " + chi + " ");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {	
		
	}
	
	/**
	 * Make the game and run it
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame app = new JFrame("Feudal Fighters vs Sinister Sushi");
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				TheGreatBattle panel = new TheGreatBattle();
				app.setContentPane(panel);
				app.pack();
				app.setVisible(true);
			}
		});
	}
}