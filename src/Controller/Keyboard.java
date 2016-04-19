package Controller;

import Model.Game;
import Model.Projectile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	private Game game;
	public Keyboard(Game game){
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode();
		
		switch (key){
			case KeyEvent.VK_RIGHT: 
				System.out.println("Right");
				game.moveHeroRight();
				break;
			case KeyEvent.VK_LEFT:
				System.out.println("Left");
				game.moveHeroLeft();
				break;
			case KeyEvent.VK_DOWN:
				System.out.println("Down");
				game.moveHeroDown();
				break;
			case KeyEvent.VK_UP:
				System.out.println("Up");
				game.moveHeroUp();
				break;
			case KeyEvent.VK_SPACE:
				System.out.println("Do something...");
				int[] newProjPos = {1,1};
				Projectile proj = new Projectile(game, newProjPos, game.getHero().getOrient());
				game.setProj(proj);
				Thread t1 = new Thread(proj);
				t1.start();
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}