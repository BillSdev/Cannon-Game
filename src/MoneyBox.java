import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class MoneyBox {

	private double x;
	private double y;
	private double dx;
	private double dy;
	private double speed;
	private double maxSpeed;
	private double minSpeed;
	private double distanceTraveled;
	private double maxDistance;
	private int coin;
	private int radius = 25;
	private int contentID;
	private BufferedImage image;

	public MoneyBox(double x, double y) {
		this.x = x;
		this.y = y;
		contentID = 100; //coin
		maxSpeed = 20;
		minSpeed = 10;
		maxDistance = Math.random()*150 + 350;
		distanceTraveled = 0;
		speed = Math.random()*(maxSpeed - minSpeed) + minSpeed;
		dx = Math.random()*speed;
		dy = Math.sqrt(speed*speed - dx*dx) * (2 * (int)(Math.random()*2) - 1); 
		image = panel.loadSingleImage("/MoneyBox.png");							
		coin = (int)(Math.random()*601);										
		if((int)(Math.random()*10) == 1) {										
			coin = 1;	//goldCoin = 1														        													
			contentID = 101;																
		}																		
	}																			
																				
	public int getMoney() { return coin; }
	public int getContedID() { return contentID; }
	
	public boolean outOfBounds() { //x : 0 - width , y : 0 - floor (the green floor)	
		return (x < 0) || (x > panel.WIDTH - 50)	//for some reason  x = 800 is a little bit of screen
				||(y < 0) || (y > 400); //400 - height is the floor.
	}

	public boolean intersecsWithCannonBall(CannonBall ball) {
		double distance = Math.sqrt((x-ball.getX())*(x-ball.getX()) + (y-ball.getY())*(y-ball.getY()));
		if(distance <= radius + ball.getRadius())
			return true;
		return false;
	}

	public void update() {
		x += dx;
		y += dy;
		distanceTraveled += speed;
		if(distanceTraveled > maxDistance || outOfBounds()) {
			distanceTraveled = 0;
			dx = -dx;
			dy = -dy;
		}
	}
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x - radius, (int)y - radius, null);
	}



}
