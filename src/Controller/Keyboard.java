package Controller;

import Model.DungeonGeneration;
import Model.Game;
import View.Window;

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
			case KeyEvent.VK_U:
				game.getHero().setCurrentSpell(0);
				break;
			case KeyEvent.VK_I:
				game.getHero().setCurrentSpell(1);
				break;
			case KeyEvent.VK_O:
				game.getHero().setCurrentSpell(2);
				break;
			case KeyEvent.VK_P:
				game.getHero().setCurrentSpell(3);
				break;
			case KeyEvent.VK_5:
				game.getHero().setCurrentSpell(4);
				break;
			case KeyEvent.VK_CONTROL:
				game.getHero().useSpell();
				break;
			case KeyEvent.VK_SHIFT:
				game.getHero().useItem();
				break;
			case KeyEvent.VK_W:
				game.getHero().storeInventory(1);
				break;
			case KeyEvent.VK_X:
				game.getHero().storeInventory(-1);
				break;
			case KeyEvent.VK_F5:
				game.changeDungeon(DungeonGeneration.generateRandomDungeon(5));
				break;
			case KeyEvent.VK_S:
				game.save("Save.txt", game);
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