package Controller;

import java.awt.event.ActionListener;

import Model.Game;
import View.Button;
import View.Window;

import java.awt.event.ActionEvent;

public class launchGameActionListener implements ActionListener{
	private String whichGame;
	private Window window;
	
	public launchGameActionListener(Window window, Button button){
		this.whichGame = button.getName();
		this.window = window;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		launchGame(whichGame);
	}


	void launchGame(String whichGame){
		if(whichGame == "Load Game"){
			System.out.println("Load Game");
		}
		else{
			if(window.getMenu().getRoomCount() > 0){
				Game game = new Game(whichGame, window.getMenu().getRoomCount());
				Keyboard keyboard = new Keyboard(game);

				window.getMenu().removeAll();
				window.getFrame().remove(window.getMenu());

				window.getFrame().getContentPane().add(window.getMap());
				window.getMap().requestFocusInWindow(); //Ne pas oublier

				window.getFrame().setPreferredSize(window.getMap().getPreferredSize());
				window.buildMap(game.getTerrainMatrix());
				window.setCreatures(game.getCreatures());
				window.setProjectiles(game.getProjectiles());
				window.setHero(game.getHero());
				window.setKeyListener(keyboard);
				window.getFrame().pack();

				Thread t = new Thread(window);
				t.start();
			}
		}
	}
}
