/**
 * Authors: Alexander Meng & Anton Lee
 * Course: ICS4UE Mr. Benum
 * Description: This is a class called "BrickBreaker" that extends the "Game" class.
 * It contains variables and methods for the game mechanics of a two-player Pong game.
 * The setup() method initializes the game objects while the act() method controls the game logic.
 * This is also the main-driver code, meaning you would run this class to run the game.
 */

import java.awt.*;
import java.util.Random;
import java.lang.Math;
import javax.swing.JLabel;


/**
 * The BrickBreaker game.
 */
public class BrickBreaker extends Game {
	// Requires classes for Paddle, Bouncing ball, Power up, Bricks
	// Tracks HP, Level, Current Score, Brick Size with fields
	// Tracks Best Score with an external file so that it can be saved through multiple sessions

	// Miscellaneous:
	private Random rand = new Random();

	// Player:
	private int health = 3, level = 1, score, highScore = 0;
	private boolean scored = false;

	// Statistic Labels:
	private JLabel levelLabel, scoreLabel, highScoreLabel, healthLabel;
	public int levelX, scoreX, highScoreX, livesX, textY;

	// Paddle:
	private Paddle player;
	private final int PAD_START_WIDTH = 40, PAD_THICKNESS = 6, PAD_OFFSET = 50;

	// Bricks — Arrays of bricks for each level:
	private Brick[] level1Bricks, level2Bricks, level3Bricks;
	private final int BRICK_WIDTH = 35, BRICK_HEIGHT = 15;
	private int brickSize = 5;

	// Ball:
	private Ball b;
	private double velocity = (rand.nextDouble() + 1) * (rand.nextInt(2) == 1 ? 1 : -1);
	private final int BALL_SIZE = 10;
	private int currBallX, currBallY = 0;

	/**
	 * Tells the game what to do before the actual play begins.
	 */
	public void setup() {
		// Setting the game delay, in milliseconds.
		setDelay(10);

		// Adding the ball.
		b = new Ball(getFieldWidth() / 2, getFieldHeight() / 2, BALL_SIZE, Color.CYAN, velocity + 1);
		add(b);

		// Adding the paddle.
		player = new Paddle((getFieldWidth() - PAD_START_WIDTH)/2,
				getFieldHeight() - PAD_OFFSET, PAD_THICKNESS, PAD_START_WIDTH);
		add(player);

		// Levels of bricks, which are all added to their respective arrays below.
		// "lxby" stands for "level #x brick #y".
		// Level 1:
		Brick l1b1 = new Brick(0, 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b2 = new Brick(BRICK_WIDTH + 2, 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b3 = new Brick(2 * (BRICK_WIDTH + 2), 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b4 = new Brick(3 * (BRICK_WIDTH + 2), 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b5 = new Brick(4 * (BRICK_WIDTH + 2), 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b6 = new Brick(5 * (BRICK_WIDTH + 2), 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b7 = new Brick(6 * (BRICK_WIDTH + 2), 0, BRICK_WIDTH, BRICK_HEIGHT, 1),
				l1b8 = new Brick(7 * (BRICK_WIDTH + 2), 0, BRICK_WIDTH, BRICK_HEIGHT, 1);
		level1Bricks = new Brick[] {l1b1, l1b2, l1b3, l1b4, l1b5, l1b6, l1b7, l1b8};
//		for (Brick b: level1Bricks) {
//			add(b);
//		}
		// Level 2:
		Brick l2b1 = new Brick(BRICK_WIDTH + 2, 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b2 = new Brick(2 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b3 = new Brick(3 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b4 = new Brick(4 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b5 = new Brick(5 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b6 = new Brick(6 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b7 = new Brick(7 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b8 = new Brick(8 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b9 = new Brick(BRICK_WIDTH + 2, 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b10 = new Brick(2 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b11 = new Brick(3 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b12 = new Brick(4 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b13 = new Brick(5 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b14 = new Brick(6 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b15 = new Brick(7 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b16 = new Brick(8 * (BRICK_WIDTH + 2), 6 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b17 = new Brick(BRICK_WIDTH + 2, 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b18 = new Brick(2 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b19 = new Brick(3 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b20 = new Brick(4 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b21 = new Brick(5 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b22 = new Brick(6 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b23 = new Brick(7 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2),
				l2b24 = new Brick(8 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 2);
		level2Bricks = new Brick[] {l2b1, l2b2, l2b3, l2b4, l2b5, l2b6, l2b7, l2b8,
				l2b9, l2b10, l2b11, l2b12, l2b13, l2b14, l2b15, l2b16,
				l2b17, l2b18, l2b19, l2b20, l2b21, l2b22, l2b23, l2b24};
//		for (Brick b: level2Bricks) {
//			add(b);
//		}
		Brick l3b1 = new Brick(BRICK_WIDTH + 2, 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b2 = new Brick(3 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b3 = new Brick(5 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b4 = new Brick(7 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b5 = new Brick(9 * (BRICK_WIDTH + 2), 3 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b6 = new Brick(2 * (BRICK_WIDTH + 2), 5 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b7 = new Brick(4 * (BRICK_WIDTH + 2), 5 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b8 = new Brick(6 * (BRICK_WIDTH + 2), 5 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b9 = new Brick(8 * (BRICK_WIDTH + 2), 5 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b10 = new Brick(BRICK_WIDTH + 2, 7 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b11 = new Brick(3 * (BRICK_WIDTH + 2), 7 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b12 = new Brick(5 * (BRICK_WIDTH + 2), 7 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b13 = new Brick(7 * (BRICK_WIDTH + 2), 7 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b14 = new Brick(9 * (BRICK_WIDTH + 2), 7 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b15 = new Brick(2 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b16 = new Brick(4 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b17 = new Brick(6 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b18 = new Brick(8 * (BRICK_WIDTH + 2), 9 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b19 = new Brick(BRICK_WIDTH + 2, 11 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b20 = new Brick(3 * (BRICK_WIDTH + 2), 11 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b21 = new Brick(5 * (BRICK_WIDTH + 2), 11 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b22 = new Brick(7 * (BRICK_WIDTH + 2), 11 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b23 = new Brick(9 * (BRICK_WIDTH + 2), 11 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b24 = new Brick(2 * (BRICK_WIDTH + 2), 13 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b25 = new Brick(4 * (BRICK_WIDTH + 2), 13 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b26 = new Brick(6 * (BRICK_WIDTH + 2), 13 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3),
				l3b27 = new Brick(8 * (BRICK_WIDTH + 2), 13 * (BRICK_HEIGHT + 2), BRICK_WIDTH, BRICK_HEIGHT, 3);
		level3Bricks = new Brick[] {l3b1, l3b2, l3b3, l3b4, l3b5, l3b6, l3b7, l3b8, l3b9,
				 l3b10, l3b11, l3b12, l3b13, l3b14, l3b15, l3b16, l3b17, l3b18,
				 l3b19, l3b20, l3b21, l3b22, l3b23, l3b24, l3b25, l3b26, l3b27};
		level = 3;
		for (Brick b: level3Bricks) {
			add(b);
		}

		calculateLabelCoordinates();

		// Customizing the Level Label:
		levelLabel = new JLabel("Level: " + level);
		levelLabel.setForeground(Color.WHITE);
		levelLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		levelLabel.setBounds(levelX, textY, 100, 30);
		add(levelLabel);

		// Customizing the Score Label:
		scoreLabel = new JLabel("Score: " + score);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		scoreLabel.setBounds(scoreX, textY, 100, 30);
		add(scoreLabel);

		// Customizing the High Score Label:
		highScoreLabel = new JLabel("High Score: " + highScore);
		highScoreLabel.setForeground(Color.WHITE);
		highScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		highScoreLabel.setBounds(highScoreX, textY, 100, 30);
		add(highScoreLabel);

		// Customizing the Health Label:
		healthLabel = new JLabel("HP/Lives: " + health);
		healthLabel.setForeground(Color.WHITE);
		healthLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		healthLabel.setBounds(livesX, textY, 100, 30);
		add(healthLabel);
	}

	/**
	 * Tells the playing field what to do from one moment to the next.
	 */
	public void act() {
		// The ball should never be only moving in one component's direction.
		if (currBallX == b.getX()) {
			b.setXMov(velocity);
		} else if (currBallY == b.getY()) {
			b.setYMov(velocity);
		}

		// X-collision with the lateral bounds:
		// Left wall collision.
		if (b.getX() <= 0) {
			b.setX(1);
			b.setXMov(Math.abs(b.getXMov()));
		}
		// Right wall collision.
		if (b.getX() >= getFieldWidth() - BALL_SIZE) {
			b.setX(getFieldWidth() - BALL_SIZE);
			b.setXMov(Math.abs(b.getXMov())*-1);
		}

		// Y-collision with the ceiling and disappear at bottom:
		// Collision with the roof.
		if (b.getY() <= 0) {
			b.setY(1);
			b.setYMov(b.getYMov()*-1);
		}
		// Hits the floor case.
		if (b.getY() >= getFieldHeight() - BALL_SIZE) {
			reset();
		}

		// Handling key-presses:
		// Paddle left movement.
		if (AKeyPressed() && player.getX() > 0) {
			player.moveLeft();
		}
		// Paddle right movement.
		if (DKeyPressed() && player.getX() < getFieldWidth() - player.getWidth()) {
			player.moveRight();
		}

		// Collision with paddle with pseudo-physics:

		// if paddle is going left, SUBTRACT slight random factor from ball's x-movement.
		// if paddle is going right, ADD slight random factor to ball's x-movement.
		if (player.collides(b)) {
			bounceBall(b, true);

			// Moving right:
			if (DKeyPressed() && player.getX() < getFieldWidth() - player.getWidth()) {
				b.setXMov(b.getXMov() + rand.nextDouble(0.2, 0.7));
			}
			// Moving left:
			if (AKeyPressed() && player.getX() > 0) {
				b.setXMov(b.getXMov() - rand.nextDouble(0.2, 0.7));
			}
		}

		// Collision with Bricks:
		for (Brick brick: level == 1 ? level1Bricks : level == 2 ? level2Bricks : level3Bricks) {
			if (brick.collides(b) && brick.getHealth() > 0) {
				boolean trueCollide = false; //check if it satisfies one of the four definitions of collision in brick breaker
				// Top & Bottom collision:
				if (b.getX() + BALL_SIZE / 2 > brick.getX() && b.getX() - BALL_SIZE / 2 < brick.getX() + BRICK_WIDTH) {
					// Top:
					if (b.getY() < brick.getY() - BRICK_HEIGHT / 2) {
						b.setY(brick.getY() - BALL_SIZE - 1);
						b.setYMov(Math.abs(b.getYMov()) * -1);
						trueCollide = true;
					}
					// Bottom:
					if (b.getY() > brick.getY() + BRICK_HEIGHT / 2) {
						b.setY(brick.getY() + BRICK_HEIGHT + 1);
						b.setYMov(Math.abs(b.getYMov()));
						trueCollide = true;
					}
				}
				// Left and Right collision:
				if (b.getY() + BALL_SIZE / 2 > brick.getY() && b.getY() - BALL_SIZE / 2 < brick.getY() + BRICK_HEIGHT) {
					//Left
					if (b.getX() < brick.getX() + BRICK_WIDTH / 2) {
						b.setX(brick.getX() - BALL_SIZE - 1);
						b.setXMov(Math.abs(b.getXMov()) * - 1);
						trueCollide = true;
					}
					// Right:
					if (b.getX() > brick.getX() + BRICK_WIDTH / 2) {
						b.setX(brick.getX() + BRICK_WIDTH + 1);
						b.setXMov(Math.abs(b.getXMov()));
						trueCollide = true;
					}
				}
				if (trueCollide) {
					score++;
					scoreLabel.setText("Score: " + score);
					brick.setHealth(brick.getHealth() - 1);
					if (brick.getHealth() <= 0) remove(brick);
				}
			}
		}
		repaint();

		// Pausing the game:
		if (EscapeKeyPressed()) {
			togglePause();
			playerPauses();
		}

		// Storing the immediate previous ball position:
		currBallX = b.getX();
		currBallY = b.getY();
	}


	/**
	 * "Bounces" the ball by manipulating the direction of its x- and/or y-components.
	 */
	public void bounceBall(Ball b, boolean direction) {
		// If direction is positive, then the ball should go up, otherwise, down.
		b.setYMov(Math.abs(b.getYMov()) * (direction ? -1 : 1));
	}

	/**
	 * Resets the game by teleporting the ball to the middle of the screen with a new random velocity.
	 */
	public void reset() {
		b.setX(getFieldWidth() / 2);
		b.setY(getFieldHeight() / 2);
		velocity = (rand.nextDouble() + 1) * (rand.nextInt(2) == 1 ? 1 : -1);
		b.setXMov(velocity);
		b.setYMov(velocity);
	}

	/**
	 * Determines the initial label coordinates.
	 */
	public void calculateLabelCoordinates() {
		levelX = (int) (getFieldWidth() * 0.2);
		scoreX = (int) (getFieldWidth() * 0.4);
		highScoreX = (int) (getFieldWidth() * 0.6);
		livesX = (int) (getFieldWidth() * 0.8);
		textY = getFieldHeight() - 37;
	}

	/**
	 * The main driver code.
	 */
	public static void main(String[] args) {
		BrickBreaker BB = new BrickBreaker();
		BB.setVisible(true);
		BB.initComponents();
	}
}
