import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;


public class GamblingMachine {

	int[][] list = {{100, 100, 100, 101, 101}, {10000, 5000, 100, 1, 5}, {5, 20, 35, 30, 10}}; //first row is the id of the item second is the quantity third is the chance
	private BufferedImage image;
	private double velocity;
	private double a; //acceleration
	private int numOfPics;
	private double x;
	private int prizeID;
	private int prizeQuantity;
	private BufferedImage silverCoin;
	private BufferedImage goldCoin;
	private boolean gavePrize;
	private Cannon cannon;

	public GamblingMachine(Cannon cannon) {
		x = 0;
		numOfPics = 50;
		velocity = 20;
		silverCoin = panel.loadSingleImage("/SilverCoin.png");
		goldCoin = panel.loadSingleImage("/GoldCoin.png");
		a = (double)(velocity*velocity)/(2*((numOfPics-1)*50 + (int)(Math.random()*51)-150-velocity/2));
		image = new BufferedImage(numOfPics * 50, 50, 1); //each item image wil be 50x50 pixels
		this.cannon = cannon;
		gavePrize = false;
	}

	public int getPrizeID() { return prizeID; }
	public int getPrizeQuantity() { return prizeQuantity; }

	public void spin() {
		gavePrize = false;
		velocity = 20;
		x = 0;
		image = new BufferedImage(numOfPics * 50, 50, 1);
		constructPrize();
		constructPicture();
	}

	public void constructPrize() {
		int random;
		int sum = 0;
		random = (int)(Math.random()*100) + 1;	//1-100
		System.out.println("random = " + random);
		for(int j = 0 ; j < list[0].length ; j++) {
			if(list[2][j] + sum >= random) {
				if(list[0][j] == 100) 
					prizeID = 100; 
				else 
					prizeID = 101;
				prizeQuantity = list[1][j];
				break;
			}
			sum += list[2][j];
		}
	}

	public void constructPicture() {
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setColor(Color.WHITE);
		g.setFont(new Font("Ariel", Font.BOLD, 17));
		int random;
		int sum = 0;
		next :  for(int i = 0 ; i < numOfPics ; i++) {
			sum = 0;
			random = (int)(Math.random()*100) + 1;	//1-100
			for(int j = 0 ; j < list[0].length ; j++) {
				if(list[2][j] + sum >= random) {
					g.drawRect(i*50, 0, 50, 50);
					if(list[0][j] == 100) {
						g.drawImage(silverCoin, i*50+1, 1, 24, 24, null);
						g.drawString("x", i*50+32, 18);
						g.drawString(""+list[1][j], i*50+1, 45);
					}
					else {
						g.drawImage(goldCoin, i*50+1, 1, 24, 24, null);
						g.drawString("x", i*50+32, 18);						
						g.drawString(""+list[1][j], i*50+1, 45);
					}
					continue next;
				}
				sum += list[2][j];
			}
		}
		g.setColor(Color.BLACK);
		g.fillRect((numOfPics-2)*50+1, 1, 48, 48);
		g.setColor(Color.WHITE);
		if(prizeID == 100) {
			g.drawImage(silverCoin, (numOfPics-2)*50+1, 1, 24, 24, null);			//numOfPics-2 is the 49th pic if there are 50.
			g.drawString("x", (numOfPics-2)*50+32, 18);
			g.drawString(""+prizeQuantity, (numOfPics-2)*50+1, 45);
		}
		else {
			g.drawImage(goldCoin, (numOfPics-2)*50+1, 1, 24, 24, null);
			g.drawString("x", (numOfPics-2)*50+32, 18);
			g.drawString(""+prizeQuantity, (numOfPics-2)*50+1, 45);
		}

	}

	public void update() {

		if(velocity < 0) {
			velocity = 0;
			if(!gavePrize) {
				gavePrize = true;
				cannon.chargeMoney(getPrizeQuantity(), getPrizeID());
			}
		}
		x += velocity;
		velocity -= a;	
	}



	public void draw(Graphics2D g) {
		g.setColor(Color.GRAY);
		g.fillRect(283, 75, 200, 100);
		Polygon pol = new Polygon();
		pol.addPoint(260 + 160, 140 - 50);
		pol.addPoint(240 + 160, 140 - 50);
		pol.addPoint(250 + 160, 160 - 50);
		g.setColor(Color.WHITE);
		g.drawImage(image.getSubimage((int)x, 0, 150, 50), 310, 100, null);
		g.fillPolygon(pol);
	}

}
