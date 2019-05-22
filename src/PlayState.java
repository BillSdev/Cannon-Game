import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class PlayState {

	private Cannon cannon;
	private HUD hud;
	private boolean spinOpen;
	private panel panel;
	
	MoneyBox moneyBox;
	ArrayList<CannonBall> balls;
	ArrayList<Coin> coins;
	GamblingMachine machine;

	public PlayState(panel panel) {
		this.panel = panel;
		coins = new ArrayList<Coin>();
		balls = new ArrayList<CannonBall>();
		cannon = new Cannon(300, 400, balls, coins, this.panel);
		hud = new HUD(cannon);
		moneyBox = new MoneyBox(Math.random()*751 + 25, Math.random()*376 + 25);
		machine = new GamblingMachine(cannon);
		spinOpen = false;
	}

	public void update() {
		if(hud.getHelpMenu() == false) {
			cannon.update();
			if(moneyBox == null)
				moneyBox = new MoneyBox(Math.random()*751 + 25, Math.random()*376 + 25);
			else
				moneyBox.update();
			for(int i = 0 ; i < balls.size() ; i++) { 
				if(moneyBox != null && moneyBox.intersecsWithCannonBall(balls.get(i))) {
					cannon.chargeMoney(moneyBox.getMoney(), moneyBox.getContedID());
					moneyBox = null;
				}	
				balls.get(i).update();
				if(balls.get(i).getVectorY() == 0)
					balls.remove(i);
			}
			hud.update();
			for(int i = 0 ; i < coins.size() ; i++) {
				coins.get(i).update();
				if(coins.get(i).outOfBouds()) {
					coins.remove(i);
					i--;
					continue;
				}
				if(coins.get(i).intersecsWithCannonBall(cannon)) {
					cannon.chargeMoney(50, 100);
					coins.remove(i);
				}
			}
			if(cannon.isPurpleActivated()) {
				if((int)(Math.random() * 13) == 12) {
					coins.add(new Coin(0, Math.random()*200 + 200, Math.random()*10+5, -(Math.random()*5+3), 30));
					coins.add(new Coin(780, Math.random()*200 + 200, -(Math.random()*10 + 5), -(Math.random()*5+3), 30));
				}
			}
			if(spinOpen)
				machine.update();
		}
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 425, panel.WIDTH, panel.HEIGHT-425);
		cannon.draw(g);
		for(int i = 0 ; i < balls.size() ; i++) {
			balls.get(i).draw(g);
		}
		if(moneyBox != null)
			moneyBox.draw(g);
		hud.draw(g);
		for(int i = 0 ; i < coins.size() ; i++) {
			coins.get(i).draw(g);
		}
		if(spinOpen)
			machine.draw(g);
	}

	public void keyPressed(int key) {

		if(key == 32 && !cannon.isPurpleActivated()) {
			cannon.fire();
			System.out.println("FIRE");
		}
		if(key == KeyEvent.VK_UP)
			cannon.jump();
		if(key == KeyEvent.VK_LEFT)
			cannon.setLeft(true);
		if(key == KeyEvent.VK_RIGHT)
			cannon.setRight(true);
		if(key == 'F' && !cannon.isPurpleActivated()) {
			if(cannon.getChangePower() == true)
				cannon.setChangePower(false);
			else {
				cannon.setChangePower(true);
				cannon.chargeMoney(-250, 100);
				System.out.println("250$ fee for retry");
			}
		}
		if(key == 'H') {
			if(hud.getHelpMenu() == false)
				hud.setHelpMenu(true);
			else
				hud.setHelpMenu(false);
		}
		if(key == 'E')
			cannon.activateGoldCoin();
		if(spinOpen) {
			if(key == 'G') {
				if(cannon.getMoney() < 3500)
					System.out.println("Not enough money for spin.");
				else {
					cannon.chargeMoney(-3500, 100);
					machine.spin();
				}
			}
		}
		if(key == 'O') {
			if(spinOpen)
				spinOpen = false;
			else
				spinOpen = true;
		}

	}

	public void keyReleased(int key) {
		if(key == KeyEvent.VK_LEFT)
			cannon.setLeft(false);
		if(key == KeyEvent.VK_RIGHT)
			cannon.setRight(false);
	}

}
