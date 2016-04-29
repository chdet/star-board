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
				game.getHero().move(Game.RIGHT);
				break;
			case KeyEvent.VK_LEFT:
				game.getHero().move(Game.LEFT);
				break;
			case KeyEvent.VK_DOWN:
				game.getHero().move(Game.DOWN);
				break;
			case KeyEvent.VK_UP:
				game.getHero().move(Game.UP);
				break;
			case KeyEvent.VK_SPACE:
				game.getHero().attack();
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