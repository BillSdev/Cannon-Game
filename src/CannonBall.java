import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class CannonBall {

	private double x;
	private double y;
	private double dx;
	private double dy;
	private double gravity;
	private int radius;
	private Color color;
	private BufferedImage image;

	public CannonBall(double x, double y, int radius, double gravity, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.gravity = gravity;
		this.color = color;
		image = panel.loadSingleImage("/cannonBall.png");
	}
	
	public double getVectorY() { return dy; }
	
	public double getGravity() { return gravity; }
	
	public double getX() { return x; }
	
	public double getY() { return y; }
	
	public int getRadius() { return radius; }

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void update() {
		x = x + dx;
		y = y + dy;
		dy += gravity;
		if(y >= 400) {
			dy = -dy*0.5;
			if(-dy < 1)
				dy = 0;	
			y = 400;
		}
		
	}

	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillOval((int)x - radius, (int)y - radius, radius*2, radius*2);
		g.drawImage(image, (int)x - radius, (int)y - radius, radius*2, radius*2, null); //20 = radius of stonerball image;
	}
}
