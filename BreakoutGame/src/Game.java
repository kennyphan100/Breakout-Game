import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * @author Kenny Phan
 * This class implements the main features of the game.
 */
public class Game extends JPanel implements KeyListener, ActionListener{

	private static final long serialVersionUID = 1L;
	
	Random random = new Random();
	
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private int delay = 5;
	private int playerX = 300;
	private int ballPosX = random.nextInt(700);
	private int ballPosY = 350;
	private int ballXDir = -1;
	private int ballYDir = -2;
	private Timer timer;
	private Map map;
	
	public Game() {
		map = new Map(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//Background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//Draw Map
		map.draw((Graphics2D) g);
		
		//Score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score, 590, 30);
		
		//Borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(681, 0, 3, 592);

		//Paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//Ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		//Defeat Text
		if (ballPosY >= 600) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("Consolas", Font.BOLD, 80));
			g.drawString("Game Over", 150, 300);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Score: " + score, 250, 350);
			g.drawString("Press Space to Restart", 150, 400);
		}
		
		//Victory Text
		if (totalBricks <= 0) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("Consolas", Font.BOLD, 60));
			g.drawString("You won!", 200, 300);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Score: " + score, 250, 350);
			g.drawString("Press Space to Restart", 150, 400);
		}
		
		g.dispose();
	}
	
	public void moveRight() {
		play = true;
		playerX += 30;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 30;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if (play) {
			ballPosX += ballXDir;
			ballPosY += ballYDir;
			
			if (ballPosX < 0) {
				ballXDir = -ballXDir;
			}
			if (ballPosY < 0) {
				ballYDir = -ballYDir;
			}
			if (ballPosX > 670 ) {
				ballXDir = -ballXDir;
			}
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100,8))) {
				ballYDir = -ballYDir;
			}
			
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect = new Rectangle (ballPosX,ballPosY, 20,20);
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if (ballPosX+19 <= brickRect.x || ballPosX+1 >= brickRect.x + brickRect.width) {
								ballXDir = -ballXDir;
								
							} else {
								ballYDir = -ballYDir;
							}
							break A;
							
						}
					}
				}
			}
		}
		repaint();	
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			if (playerX >= 581) {
				playerX = 581;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if (playerX <= 3) {
				playerX = 3;
			} else {
				moveLeft();
			}
		}
	
		// Reset the game
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			if (!play) {
				play = true;
				ballPosX = random.nextInt(700);
				ballPosY = 350;
				ballXDir = -1;
				ballYDir = -2;
				playerX = 300;
				score = 0;
				totalBricks = 21;
				map = new Map(3, 7);
		
				repaint();
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
