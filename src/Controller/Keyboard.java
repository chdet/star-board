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
				game.moveHero(Game.RIGHT);
				break;
			case KeyEvent.VK_LEFT:
				game.moveHero(Game.LEFT);
				break;
			case KeyEvent.VK_DOWN:
				game.moveHero(Game.DOWN);
				break;
			case KeyEvent.VK_UP:
				game.moveHero(Game.UP);
				break;
			case KeyEvent.VK_SPACE:
				game.addProjectile(new Projectile(game, game.getHero().getPos(), game.getHero().getOrient()));
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