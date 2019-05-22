import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Cannon {

	private double x;
	private double y;
	private double angle;
	private int power;	//0 - 100
	private int maxPower;
	private double dx;
	private double dy;
	private double ax;
	private double ay;
	private double speed;
	private double verticalJump;
	private double gravity;
	private int radiusA;
	private int radiusB;
	private int money;							//id = 100(silver coin).
	private int deltaPower;
	private boolean changePower;
	private MoveText earnings;
	BufferedImage caliber;
	private boolean falling;
	private boolean left;
	private boolean right;
	public int goldCoin;						//id = 101
	private BufferedImage coinImage;
	private BufferedImage goldCoinImage;
	private long goldStartTime;
	private long goldTime;
	private boolean goldIsActive;
	private int radiusWhilePurple;
	private BufferedImage goldImage;
	ArrayList<CannonBall> balls;
	ArrayList<Coin> coins;
	private panel panel;

	public Cannon(double x, double y, ArrayList<CannonBall> balls, ArrayList<Coin> coins, panel panel) {
		this.x = x;
		this.y = y;
		//this.caliber = panel.loadSingleImage("/cannonCaliber.png");
		//this.caliber = panel.loadSingleImage("/snoopbong.png");
		this.caliber = panel.loadSingleImage("/cannonCaliber.png");

		angle = 0;
		this.balls = balls;
		power = 0;
		maxPower = 100;
		ax = 0.2;
		ay = 0.2;
		deltaPower = 5;
		changePower = true;
		money = 16000; //counter reference looooool
		falling = false;
		left = false;
		right = false;
		speed = 2;
		verticalJump = -7.5;
		gravity = 0.4;
		//radiusA = 50;
		//radiusB = 50;
		goldCoin = 0;
		coinImage = panel.loadSingleImage("/SilverCoin.png");
		goldCoinImage = panel.loadSingleImage("GoldCoin.png");
		goldTime = 15000000000l; //15 seconds.
		goldIsActive = false;
		radiusWhilePurple = 30;
		goldImage = panel.loadSingleImage("/collector.png");
		this.coins = coins;
		this.panel = panel;
	}

	public void setAngle(double a) { angle = a; 
	if(angle > 180)
		angle = 180;
	if(angle < 0)
		angle = 0;
	}
	public double getAngle() { return angle; }
	public double getPower() { return power; }
	public double getMaxPower() { return maxPower; }
	public void setChangePower(boolean b) { changePower = b; }
	public int getMoney() { return money; }
	public void setMoney(int m) { money = m; }
	public boolean getChangePower() { return changePower; }
	public void setLeft(boolean l) { left = l; }
	public void setRight(boolean r) { right = r; }
	public void addGoldCoin(int pk) { goldCoin += pk; }
	public int getGoldCoin() { return goldCoin; }
	public boolean isPurpleActivated() { return goldIsActive; }
	public double getX() { return x; }
	public double getY() { return y; }
	public int getRadiusWhilePurple() { return radiusWhilePurple; }

	public void jump() {
		if(!falling) {
			dy += verticalJump;
			falling = true;
			radiusB = 30;
		}
	}

	public void activateGoldCoin() {
		if(goldCoin > 0) {
			goldIsActive = true;
			goldCoin--;
			coins = new ArrayList<Coin>();
			goldStartTime = System.nanoTime();
		}
		else 
			System.out.println("Not enough gold coins.");
	}

	public void deActivateGoldCoin() {
		goldIsActive = false;
		for(int i = 0 ; i < coins.size() ; i++)
			coins.remove(i);
	}


	public void chargeMoney(int earn, int id) { 
		BufferedImage addon;
		if(id == 100) {
			money += earn;
			addon = coinImage;
		}
		else {
			goldCoin += earn;
			addon = goldCoinImage;
		}
		Color color = Color.GREEN;
		char prefix = '+';
		if(earn < 0) {
			color = Color.RED;
			earn = -earn;
			prefix = '-';
		}
		earnings = new MoveText("" + prefix + "    x" + earn, x, y-30, 0, -0.8, color, addon);
	}

	public void fire() {
		if(changePower != true) {
			double velocity = 0.22*power;
			double ballX = x + Math.cos(-Math.PI*(angle/180))*127; //127 = width of caliber pic.
			double ballY = y + Math.sin(-Math.PI*(angle/180))*127; // -''-
			CannonBall ball = new CannonBall(ballX, ballY, 20, gravity, Color.BLACK);
			ball.setVector(Math.cos(-Math.PI*(angle/180)) * velocity, Math.sin(-Math.PI*(angle/180)) * velocity);
			balls.add(ball);
			changePower = true;
			chargeMoney(-300, 100);
		}
		else {
			System.out.println("You must stop the power by pressing F");
		}
	}

	public void update() {
		y += dy;	
		x += dx;
		if(falling) {
			dy += gravity;
			if(y >= 400) {
				y = 400;
				dy = 0;
				falling = false;
			}
		}
		else
			radiusB = 50;
		if(left) {
			dx = -speed;
		}
		else if(right) {
			dx = speed;
		}
		else {
			dx = 0;
		}
		if(changePower) {
			power+=deltaPower;
			if(power >= 100){
				power = 100;
				deltaPower = -deltaPower;
			}	
			else if(power <= 0) {
				power = 0;
				deltaPower = -deltaPower;
			}
		}
		if(earnings != null) {
			earnings.update();
			if(earnings.reachedMax()) {
				earnings = null;
				System.out.println("reached");
			}
		}
		if(System.nanoTime() - goldStartTime >= goldTime)
			deActivateGoldCoin();

		//caluclating angle based on the cords of the mouse.
		Point p;
		if((p = panel.getMousePosition()) != null) {	
			double difX = p.x - (int)x;
			double slope = (p.y - (int)y)/(difX);
			angle = -(Math.atan(slope) * (180/Math.PI) + ((difX-Math.abs(difX))/difX)*90);
		}
		
	}

	public void draw(Graphics2D g) {
		System.out.println("drawing the cannon");
		g.setColor(Color.BLUE);
		//System.out.println("\nangle = " + -Math.PI*(angle/180) + " x = " + ((int)x + 50) + " y = " + ((int)y + 51));
		if(goldIsActive)
			g.drawImage(goldImage, (int)x - radiusWhilePurple, (int)y - radiusWhilePurple, radiusWhilePurple*2, radiusWhilePurple*2, null);
		else {
			g.rotate(-Math.PI*(angle/180), (int)x, (int)y + 1); //26 = caliber image height / 2
			g.drawImage(caliber, (int)x, (int)y -25, null);
			//System.out.println("angle = " + Math.PI*(angle/180) + " x = " + ((int)x + 50) + " y = " + ((int)y + 51));
			g.rotate(Math.PI*(angle/180), (int)x, (int)y + 1);
		}
		//g.fillOval((int)x - 25, (int)y - 25, radiusB, radiusA);
		if(earnings != null)
			earnings.draw(g);

	}
}
