/*
 * PONG GAME REQUIREMENTS
 * This simple "tennis like" game features two paddles and a ball, 
 * the goal is to defeat your opponent by being the first one to gain 3 point,
 *  a player gets a point once the opponent misses a ball. 
 *  The game can be played with two human players, one on the left and one on 
 *  the right. They use keyboard to start/restart game and control the paddles. 
 *  The ball and two paddles should be red and separating lines should be green. 
 *  Players score should be blue and background should be black.
 *  Keyboard requirements:
 *  + P key: start
 *  + Space key: restart
 *  + W/S key: move paddle up/down
 *  + Up/Down key: move paddle up/down
 *  
 *  Version: 0.5
 */
package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener {
	String namePlayer1;
	String namePlayer2;
	
	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;
	private int xRandom;
	private int yRandom;
	private int timeDisplay;
	private int diameterRan = 100;
	private boolean showRandom;
	private int oRandom ;
	private Timer timer;

	/** Background. */
	private Color backgroundColor = Color.BLACK;
	ImageIcon imgpt = new ImageIcon("Image/avenger.png");
	ImageIcon imgdr = new ImageIcon("Image/welcome.jpg");
	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 250;
	private int ballY = 250;
	private int diameter = 20;
	private int ballDeltaX = -1;
	private int ballDeltaY = 3;
	ImageIcon imgball;
	ImageIcon imgpanel;
	ImageIcon imgpanel2;
	/** Player 1's paddle: position and size */
	private int playerOneX = 0;
	private int playerOneY = 250;
	private int playerOneWidth = 10;
	private int playerOneHeight = 50;

	/** Player 2's paddle: position and size */
	private int playerTwoX = 472;
	private int playerTwoY = 250;
	private int playerTwoWidth = 10;
	private int playerTwoHeight = 50;

	/** Speed of the paddle - How fast the paddle move. */
	private int paddleSpeed = 5;

	/** Player score, show on upper left and right. */
	private int playerOneScore;
	private int playerTwoScore;

	/** Construct a PongPanel. */
	public PongPanel() {
		setBackground(backgroundColor);
		// ball
		imgball = new ImageIcon("Image/ball_45.png");
		imgpanel = new ImageIcon("Image/panel.png");
		imgpanel2 = new ImageIcon("Image/panel2.png");
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		timeDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;

		// call step() 60 fps
		timer = new Timer(600 / 60, this);
		timer.start();
	}

	/** Implement actionPerformed */
	public void actionPerformed(ActionEvent e) {
		step();
	}

	/** Repeated task */
	public void step() {

		if (playing) {

			/* Playing mode */

			// move player 1
			// Move up if after moving, paddle is not outside the screen
			if (upPressed && playerOneY - paddleSpeed > 0) {
				playerOneY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (downPressed
					&& playerOneY + playerOneHeight + paddleSpeed < getHeight()) {
				playerOneY += paddleSpeed;
			}

			// move player 2
			// Move up if after moving paddle is not outside the screen
			if (wPressed && playerTwoY - paddleSpeed > 0) {
				playerTwoY -= paddleSpeed;
			}
			// Move down if after moving paddle is not outside the screen
			if (sPressed
					&& playerTwoY + playerTwoHeight + paddleSpeed < getHeight()) {
				playerTwoY += paddleSpeed;
			}

			/*
			 * where will the ball be after it moves? calculate 4 corners: Left,
			 * Right, Top, Bottom of the ball used to determine whether the ball
			 * was out yet
			 */
			int nextBallLeft = ballX + ballDeltaX;
			int nextBallRight = ballX + diameter + ballDeltaX;
			// FIXME Something not quite right here
			int nextBallTop = ballY + ballDeltaY;
			int nextBallBottom = ballY + diameter + ballDeltaY;

			// Player 1's paddle position
			int playerOneRight = playerOneX + playerOneWidth;
			int playerOneTop = playerOneY;
			int playerOneBottom = playerOneY + playerOneHeight;

			// Player 2's paddle position
			float playerTwoLeft = playerTwoX;
			float playerTwoTop = playerTwoY;
			float playerTwoBottom = playerTwoY + playerTwoHeight;

			// ball bounces off top and bottom of screen
			if (nextBallTop < 0 || nextBallBottom > getHeight()) {
				ballDeltaY *= -1;
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom
						|| nextBallBottom < playerOneTop) {

					playerTwoScore++;

					// Player 2 Win, restart the game
					if (playerTwoScore == 100) {
						playing = false;
						gameOver = true;
					}
					ballX = 200;
					ballY = 200;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom
						|| nextBallBottom < playerTwoTop) {

					playerOneScore++;

					// Player 1 Win, restart the game
					if (playerOneScore == 100) {
						playing = false;
						gameOver = true;
					}
					ballX = 200;
					ballY = 200;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;

			timeDisplay -= 1000 / 60;
			System.out.println(timeDisplay);
			if (timeDisplay < 0) {
				if (!showRandom) {
					showRandom = true;
					oRandom = ThreadLocalRandom.current().nextInt(1, 6 + 1);
					xRandom = ThreadLocalRandom.current().nextInt(50, 450 + 1);
					yRandom = ThreadLocalRandom.current().nextInt(50, 450 + 1);
				} else {
					Point ballCenter = new Point(ballX + diameter / 2, ballY
							+ diameter / 2);
					Point ranCenter = new Point(xRandom + diameterRan / 2,
							yRandom + diameterRan / 2);
					double distance2center = getPointDistance(ballCenter,
							ranCenter);
					if (distance2center < (diameter / 2 + diameterRan / 2)) {
						if (oRandom == 1) {
							playerOneHeight -= 10;
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 2) {
							playerOneHeight += 10;
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 3) {
							diameter += 20;
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 4) {
							diameter -= 5;
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 5) {
							timer = new Timer(300 / 60, this);
							paddleSpeed = 5;
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 6) {
							timer = new Timer(1000 / 60, this);
							paddleSpeed = 5;
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						}
					}
				}
				if (timeDisplay < -10000) {
					showRandom = false;
					timeDisplay = ThreadLocalRandom.current()
							.nextInt(5, 15 + 1) * 1000;
				}
			}
		}

		// stuff has moved, tell this JPanel to repaint itself
		repaint();
	}

	public double getPointDistance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	/** Paint the game screen. */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (showTitleScreen) {

			/* Show welcome screen */

			// Draw game title and start message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawImage(imgdr.getImage(), 0, 0, 500, 500, null);
			g.setColor(Color.BLUE);
			g.drawString("Pong Game", 130, 125);

			// FIXME Wellcome message below show smaller than game title
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 27));
			g.setColor(Color.BLUE);
			g.drawString("Press 'P' to play.", 130, 400);
		} else if (playing) {
			g.drawImage(imgpt.getImage(), 0, 0, 500, 500, null);
			/* Game is playing */

			// set the coordinate limit
			int playerOneRight = playerOneX + playerOneWidth;
			int playerTwoLeft = playerTwoX;

			// draw dashed line down center
			g.setColor(Color.GREEN);
			for (int lineY = 0; lineY < getHeight(); lineY += 50) {
				g.drawLine(250, lineY, 250, lineY + 25);
			}

			// draw "goal lines" on each side
			g.drawLine(playerOneRight, 0, playerOneRight, getHeight());
			g.drawLine(playerTwoLeft, 0, playerTwoLeft, getHeight());

			// draw the scores
			g.setColor(Color.BLUE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100); // Player 1
																	// score
			g.drawString(namePlayer1, 50, 50);
			g.drawString(String.valueOf(playerTwoScore), 400, 100); // Player 2
																	// score
			g.drawString(namePlayer2, 350, 50);

			// draw the ball
			g.setColor(Color.RED);

			//g.fillOval(ballX, ballY, diameter, diameter);
			g.drawImage(imgball.getImage(), ballX, ballY, diameter, diameter,
					null);
			// draw the paddles
			g.drawImage(imgpanel.getImage(), playerOneX, playerOneY,
					playerOneWidth, playerOneHeight, null);
			g.drawImage(imgpanel2.getImage(), playerTwoX, playerTwoY,
					playerTwoWidth, playerTwoHeight, null);
			if (showRandom) {
				if (oRandom == 1) {
					g.fillOval(xRandom, yRandom, diameterRan, diameterRan);
				} else if (oRandom == 2) {
					g.setColor(Color.BLUE);
					g.fillOval(xRandom, yRandom, diameterRan, diameterRan);
				} else if (oRandom == 3) {
					g.setColor(Color.ORANGE);
					g.fillOval(xRandom, yRandom, diameterRan, diameterRan);
				} else if (oRandom == 4) {
					g.setColor(Color.GREEN);
					g.fillOval(xRandom, yRandom, diameterRan, diameterRan);
				} else if (oRandom == 5) {
					g.setColor(Color.PINK);
					g.fillOval(xRandom, yRandom, diameterRan, diameterRan);
				} else if (oRandom == 6) {
					g.setColor(Color.WHITE);
					g.fillOval(xRandom, yRandom, diameterRan, diameterRan);
				}
			}
		} else if (gameOver) {

			/* Show End game screen with winner name and score */

			// Draw scores
			// TODO Set Blue color
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawString(String.valueOf(playerOneScore), 100, 100);
			g.drawString(String.valueOf(playerTwoScore), 400, 100);

			// Draw the winner name
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			if (playerOneScore > playerTwoScore) {
				g.setColor(Color.WHITE);
				g.drawString(namePlayer1, 150, 200);
				//g.drawString("Player 1 Wins!", 165, 200);
			} else {
				g.setColor(Color.WHITE);
				g.drawString(namePlayer2, 150, 200);
				//g.drawString("Player 2 Wins!", 165, 200);
			}

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			// TODO Draw a restart message
			g.drawString("Press 'Spacebar' to Restart", 130, 300);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyChar() == 'p') {
				showTitleScreen = false;
				playing = true;
			} else if (e.getKeyChar() == 'P') {
				showTitleScreen = false;
				playing = true;
			}
		} else if (playing) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				wPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				sPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				upPressed = true;
			} else if (e.getKeyCode() == KeyEvent.VK_S) {
				downPressed = true;
			}
		} else if (gameOver && e.getKeyCode() == KeyEvent.VK_SPACE) {
			gameOver = false;
			showTitleScreen = true;
			playerOneY = 250;
			playerTwoY = 250;
			playerOneScore = 0;
			playerTwoScore = 0;
			ballX = 250;
			ballY = 250;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			wPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			sPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			upPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			downPressed = false;
		}
	}

}
