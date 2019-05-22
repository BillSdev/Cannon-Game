import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class MoveText {

	private String text;
	private double x;
	private double y;
	//private double xMax; // no x max because message goes up.
	private double traveledY;
	private double dx;
	private double dy;
	private BufferedImage addon;
	private Color color;
	
	public MoveText(String text, double x, double y, double dx, double dy, Color color, BufferedImage addon) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.color = color;
		this.addon = addon;
		traveledY = 50;
	}
	
	public boolean reachedMax() { return (traveledY <= 0); }
	
	public void update() {
		x += dx;
		y += dy;
		traveledY += dy;
	}
	
	public void draw(Graphics2D g) {
		g.setFont(new Font("Ariel", Font.BOLD, 50));
		g.setColor(color);
		g.drawString(text, (int)x, (int)y);
		g.drawImage(addon, (int)x + 24, (int)y - 40, 45, 45, null);
	}
	
}
