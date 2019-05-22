import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Coin {

	private double x;
	private double y;
	private double dx;
	private double dy;
	private double gravity;
	private int radius;
	private double angle; //in radians
	private double deltaAngle; //in radians
	private BufferedImage image;
	
	public Coin(double x, double y, double dx, double dy, int radius) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.radius = radius;
		gravity = 0.4;
		angle = 0;
		deltaAngle = ((double)20/180)*Math.PI;
		image = panel.loadSingleImage("/SilverCoin.png");
	}
	
	public boolean outOfBouds() {
		return (x < 0) || (x > panel.WIDTH)	
				||(y < 0) || (y > 425-radius);
	}
	
	public boolean intersecsWithCannonBall(Cannon cannon) {
		double distance = Math.sqrt((x-cannon.getX())*(x-cannon.getX()) + (y-cannon.getY())*(y-cannon.getY()));
		if(distance <= radius + cannon.getRadiusWhilePurple())
			return true;
		return false;
	}
	
	public void update() {
		x += dx;
		y += dy;
		dy += gravity;
		angle += deltaAngle;
	}
	
	public void draw(Graphics2D g) {
		g.rotate(-angle, (int)x, (int)y);
		g.drawImage(image, (int)x - radius, (int)y - radius, radius*2, radius*2, null);
		g.rotate(angle, (int)x, (int)y);
	}
	
	
	
}
