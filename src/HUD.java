import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class HUD {

	private Cannon cannon;
	private int powerWidth;
	private int powerHeight;
	private BufferedImage coin;
	private BufferedImage goldCoin;
	private long goldCoinActivateStartTime;
	private long goldCoinActivateTime;
	private boolean showActivate;
	private boolean helpMenu;
	private BufferedImage helpMenuImage;

	public HUD(Cannon cannon) {

		this.cannon = cannon;
		powerHeight = 50;
		powerWidth = 200;
		coin = panel.loadSingleImage("/SilverCoin.png");
		goldCoin = panel.loadSingleImage("/GoldCoin.png");
		showActivate = false;
		goldCoinActivateTime = 1000000000/2;
		helpMenu = false;
		constructHelpMenu();
			
	}
	
	public void setHelpMenu(boolean b) { helpMenu = b; }
	public boolean getHelpMenu() { return helpMenu; }
	
	public void constructHelpMenu() {
		helpMenuImage = new BufferedImage(400, 400, 1);
		Graphics2D gTemp = (Graphics2D)helpMenuImage.getGraphics();
		gTemp.setColor(Color.BLACK);
		gTemp.fillRect(0, 0, 400, 400);
		gTemp.setColor(Color.WHITE);
		gTemp.fillRect(25, 25, 350, 350);
		gTemp.setColor(Color.BLACK);
		gTemp.setFont(new Font("Ariel", Font.BOLD, 30));
		gTemp.drawString("Help", 165, 60);
		gTemp.fillRect(165, 70, 65, 5); //out line for "Help" title;
		gTemp.setFont(new Font("Ariel", Font.BOLD, 20));
		String[] instructions = {"Change cannon angle : 'A' / 'D' .", 
				"Move left : left arrow .", "Move right : right arrow .",
				"Jump : up arrow .", "Stop power meter : 'F' .",
				"Fire : space .", "Activate gold coin : 'E' .", 
				"Open/Close Spin : 'O' .", "Spin : 'G' ."};
		for(int i = 0 ; i < instructions.length ; i++)
			gTemp.drawString(instructions[i], 45, 110 + i*20);
		
	}
	
	public void update() {
		if(!showActivate) {
			if(cannon.getGoldCoin() > 0) {
				goldCoinActivateStartTime = System.nanoTime();
				showActivate = true;
			}
		}
		else {
			if(cannon.getGoldCoin() < 1)
				showActivate = false;
		}
	}

	public void draw(Graphics2D g) {
		g.drawRect(30, 530, powerWidth, powerHeight);
		g.setFont(new Font("Ariel", Font.BOLD, 25));
		g.setColor(Color.BLUE);
		g.drawString("Power-meter", 50, 520);
		g.setColor(Color.RED);
		g.fillRect(30, 530, (int)((cannon.getPower()/cannon.getMaxPower()) * powerWidth), powerHeight+1);
		g.setFont(new Font("Ariel", Font.BOLD, 35));
		g.setColor(Color.GREEN);
		g.drawString("Silver :        x " + cannon.getMoney(), 420, 490);
		g.drawImage(coin, 540, 445, 60, 60, null);
		g.drawImage(goldCoin, 540, 520, 60, 60, null);
		g.drawString(" x " + cannon.getGoldCoin(), 605, 565);
		if(showActivate) {
			if(((int)((System.nanoTime() - goldCoinActivateStartTime)/goldCoinActivateTime)) % 2 == 0) {
				g.setFont(new Font("Ariel", Font.BOLD, 23));
				g.drawString("Activate Gold Coin 'E'", 250, 560);
			}
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Ariel", Font.BOLD, 10));
		g.drawString("help : 'H'", 20, 20);
		
		if(helpMenu == true) {
			g.drawImage(helpMenuImage, 200, 100, null);
		}
	}
}
