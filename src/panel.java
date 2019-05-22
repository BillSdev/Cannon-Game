import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class panel extends JPanel implements Runnable, KeyListener{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private Thread thread;
	private boolean running;
	public static int fps = 0;
	private int count = 0;
	private long startTime;
	private long startTimeFPS;
	private final int FPS = 45;
	private BufferedImage image;
	private Graphics2D g;
	private Graphics g2;
	private PlayState playState;
	//private Point mouseCords;
	
	public panel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		if(this.thread == null) {
			this.thread = new Thread(this);
			addKeyListener(this);
			this.thread.start();
		}
	}


	private void init() {

		image = new BufferedImage(WIDTH, HEIGHT, 1);
		running = true;
		g = (Graphics2D)image.getGraphics();
		playState = new PlayState(this);
		
	}

	public void run() {
		init();
		startTime = System.nanoTime();
		startTimeFPS = System.nanoTime();
		while(running) {
			if(System.nanoTime() - startTime >= 1000000000/FPS) {
				startTime = System.nanoTime();
				update();
				draw();
				drawToScreen();

				count++;
				if(System.nanoTime() - startTimeFPS >= 1000000000) {
					fps = count;
					System.out.println("FPS = " + fps);
					count = 0;
					startTimeFPS = System.nanoTime();
				}
			}
		}

	}

	public void update() {
		playState.update();
	}

	public void draw() {

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		playState.draw(g);

	}

	public void drawToScreen() {
		
		g2 = getGraphics();
		g2.drawImage(image, 0 , 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key) {

	}

	public void keyPressed(KeyEvent key) {
		playState.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		playState.keyReleased(key.getKeyCode());
	}

	public static BufferedImage loadSingleImage(String s) {

		try{
			return ImageIO.read(panel.class.getResourceAsStream(s));
		}
		catch(Exception e) {
			System.out.println("Problem loading image");
			e.printStackTrace();
		}
		return null;

	}
	
}
