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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import vn.vanlanguni.ponggame.Sound;

/**
 * 
 * @author Invisible Man
 *
 */
public class PongPanel extends JPanel implements ActionListener, KeyListener,
		MouseListener, MouseMotionListener {
	static String namePlayer1 = "", namePlayer2 = "";

	private static final long serialVersionUID = -1097341635155021546L;

	private boolean showTitleScreen = true;
	private boolean playing;
	private boolean gameOver;
	private int xRandom;
	private int yRandom;
	private int timeDisplay;
	private int diameterRan = 300;
	private JRadioButton optball1, optball2, optball3;
	private ImageIcon imgball1;
	private ImageIcon imgball2;
	private ImageIcon imgball3;
	private ButtonGroup btgSelect;
	private JPanel panbox;
	private boolean showRandom;
	private int oRandom = 2;
	private Timer timer;
	private int time = 600 / 60;
	private int cooldown = 3;
	private int side;

	/** Background. */
	ImageIcon imgpt = new ImageIcon("Image/avenger.png");
	ImageIcon imgdr = new ImageIcon("Image/Welcome1.jpg");
	ImageIcon imgdri = new ImageIcon("Image/gameover.jpg");

	/** State on the control keys. */
	private boolean upPressed;
	private boolean downPressed;
	private boolean wPressed;
	private boolean sPressed;

	/** The ball: position, diameter */
	private int ballX = 250;
	private int ballY = 250;
	private int diameter = 25;
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
	// declare Rectangle is Button
	Rectangle rctBall = new Rectangle(360, 5, 100, 30);
	// declare NumTypeball
	static int NumTypeBall = 0;
	static boolean rectinBall = false;

	/** Construct a PongPanel. */
	public PongPanel() {
		// ball
		imgball = new ImageIcon("Image/ball_45.png");
		imgpanel = new ImageIcon("Image/panel.png");
		imgpanel2 = new ImageIcon("Image/panel2.png");
		// add
		panbox = new JPanel();
		panbox.setLayout(new BorderLayout(14, 10));
		optball1 = new JRadioButton("Ball 1");
		optball2 = new JRadioButton("Ball 2");
		optball3 = new JRadioButton("Ball 3");
		btgSelect = new ButtonGroup();
		btgSelect.add(optball1);
		btgSelect.add(optball2);
		btgSelect.add(optball3);
		optball1.setSelected(true);
		imgball1 = new ImageIcon("Image/ball_45.png");
		imgball2 = new ImageIcon("Image/gai.png");
		imgball3 = new ImageIcon("Image/kirby.png");
		// listen to key presses
		setFocusable(true);
		addKeyListener(this);

		timeDisplay = ThreadLocalRandom.current().nextInt(5, 15 + 1) * 1000;

		timer();
	}

	public void timer() {
		// call step() 60 fps
		timer = new Timer(time, this);
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
				Sound.play("Sound/SoundWel.wav");
			}

			// will the ball go off the left side?
			if (nextBallLeft < playerOneRight) {
				// is it going to miss the paddle?
				if (nextBallTop > playerOneBottom
						|| nextBallBottom < playerOneTop) {

					playerTwoScore++;
					Sound.play("Sound/Jump3.wav");

					// Player 2 Win, restart the game
					if (playerTwoScore == 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 200;
					ballY = 200;
				} else {
					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("Sound/SoundWel.wav");
					side = 1;
				}
			}

			// will the ball go off the right side?
			if (nextBallRight > playerTwoLeft) {
				// is it going to miss the paddle?
				if (nextBallTop > playerTwoBottom
						|| nextBallBottom < playerTwoTop) {

					playerOneScore++;
					Sound.play("Sound/Jump3.wav");

					// Player 1 Win, restart the game
					if (playerOneScore == 3) {
						playing = false;
						gameOver = true;
					}
					ballX = 200;
					ballY = 200;
				} else {

					// If the ball hitting the paddle, it will bounce back
					// FIXME Something wrong here
					ballDeltaX *= -1;
					Sound.play("Sound/SoundWel.wav");
					side = 2;
				}
			}

			// move the ball
			ballX += ballDeltaX;
			ballY += ballDeltaY;

			timeDisplay -= 1000 / 60;
			// System.out.println(timeDisplay);
			if (timeDisplay < 0) {
				if (!showRandom) {
					showRandom = true;
					// oRandom = ThreadLocalRandom.current().nextInt(1, 5 + 1);
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
							if (side == 1) {
								playerOneHeight -= 10;
							} else if (side == 2) {
								playerTwoHeight -= 10;
							}
							cooldown -= 1;
							System.out.println(cooldown);
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 2) {
							if (side == 1) {
								playerOneHeight += 10;
							} else if (side == 2) {
								playerTwoHeight += 10;
							}
							cooldown -= 1;
							System.out.println(cooldown);
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
						} else if (oRandom == 3) {
							diameter += 10;
							cooldown -= 1;
							System.out.println(cooldown);
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
							if (cooldown == 0) {
								diameter += 0;
							}
						} else if (oRandom == 4) {
							diameter -= 10;
							cooldown -= 1;
							System.out.println(cooldown);
							showRandom = false;
							timeDisplay = ThreadLocalRandom.current().nextInt(
									5, 15 + 1) * 1000;
							if (cooldown == 0) {
								diameter -= 0;
							}
						} else if (oRandom == 5) {
							time -= 100 / 60;
							timer();
							paddleSpeed = 3;
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
			this.add(panbox);
			panbox.setVisible(false);
			// Location selected ball
			panbox.add(optball1, BorderLayout.WEST);
			panbox.add(optball2, BorderLayout.CENTER);
			panbox.add(optball3, BorderLayout.EAST);
			panbox.setBorder(BorderFactory
					.createLineBorder(Color.LIGHT_GRAY, 2));
			panbox.setSize(210, 30);
			panbox.setLocation(110, 230);
			// Draw game title and start message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
			g.drawImage(imgdr.getImage(), 0, 0, 500, 500, null);
			g.setColor(Color.RED);
			g.drawString("Pong Game", 130, 125);

			// FIXME Wellcome message below show smaller than game title
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 27));
			g.setColor(Color.RED);
			g.drawString("Press 'P' to play.", 130, 400);
			//
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 27));
			g.setColor(Color.RED);
			g.drawString("Press 'N' to set.", 130, 440);
		} else if (playing) {
			g.drawImage(imgpt.getImage(), 0, 0, 500, 500, null);
			/* Game is playing */
			panbox.setVisible(false);
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

			// g.fillOval(ballX, ballY, diameter, diameter);
			if (NumTypeBall == 0) {
				g.drawImage(imgball1.getImage(), ballX, ballY, diameter,
						diameter, null);
			} else if (NumTypeBall == 1) {
				g.drawImage(imgball2.getImage(), ballX, ballY, diameter,
						diameter, null);
			} else if (NumTypeBall == 2) {
				g.drawImage(imgball3.getImage(), ballX, ballY, diameter,
						diameter, null);
			}
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
				g.drawImage(imgdri.getImage(), 0, 0, 500, 500, null);

				g.drawString(namePlayer1 + " is the Winner", 120, 100);
				// g.drawString("Player 1 Wins!", 165, 200);
			} else {
				g.setColor(Color.RED);
				g.drawImage(imgdri.getImage(), 0, 0, 500, 500, null);

				g.drawString(namePlayer2 + " is the Winner", 120, 100);
				// g.drawString("Player 2 Wins!", 165, 200);
			}

			// Draw Restart message
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
			// TODO Draw a restart message
			g.drawString("Press 'Spacebar' to Restart", 130, 400);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (showTitleScreen) {
			if (e.getKeyChar() == 'p') {
				showTitleScreen = false;
				playing = true;
				Sound.play("Sound/Draven.wav");
			} else if (e.getKeyChar() == 'P') {
				showTitleScreen = false;
				playing = true;
				Sound.play("Sound/Draven.wav");
			}
			if (e.getKeyCode() == KeyEvent.VK_N) { // nhan phim N De set
				// NamePlayer
				PlayerAndBall f = new PlayerAndBall();
				f.setVisible(true);
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

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (rctBall.contains(e.getX(), e.getY())) {
			PlayerAndBall f = new PlayerAndBall();
			f.setVisible(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
