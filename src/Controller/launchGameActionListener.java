package Controller;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Model.Game;
import Model.Projectile;
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
			window.load("Save.txt");
			System.out.println("Load Game");
			Game game = window.getGame();
			Keyboard keyboard = new Keyboard(game);

			window.getFrame().getContentPane().remove(window.getMenu());

			window.getFrame().getContentPane().add(window.getMap(), BorderLayout.WEST);
			window.getFrame().getContentPane().add(window.getInventory(), BorderLayout.EAST);
			window.getMap().requestFocusInWindow(); //Ne pas oublier
			
			window.setGame(game);
			window.setCurrentDungeon(game.getDungeon());
			window.buildMap(game.getTerrainMatrix());
			window.setCreatures(game.getCreatures());
			
			ArrayList<Projectile> projectiles = new ArrayList<>();
			game.setProjectiles(projectiles);
			window.setProjectiles(new ArrayList<>());
			window.setItems(game.getItems());
			window.setHero(game.getHero());
			window.setKeyListener(keyboard);
			window.getFrame().pack();

			Thread t = new Thread(window);
			t.start();
		}
		else{
			if(window.getMenu().getRoomCount() > 0){
				Game game = new Game(whichGame, window.getMenu().getRoomCount());
				Keyboard keyboard = new Keyboard(game);

				window.getFrame().getContentPane().remove(window.getMenu());

				window.getFrame().getContentPane().add(window.getMap(), BorderLayout.WEST);
				window.getFrame().getContentPane().add(window.getInventory(), BorderLayout.EAST);
				window.getMap().requestFocusInWindow(); //Ne pas oublier
				
				window.setGame(game);
				window.setCurrentDungeon(game.getDungeon());
				window.buildMap(game.getTerrainMatrix());
				window.setCreatures(game.getCreatures());
				window.setProjectiles(game.getProjectiles());
				window.setItems(game.getItems());
				window.setHero(game.getHero());
				window.setKeyListener(keyboard);
				window.getFrame().pack();

				Thread t = new Thread(window);
				t.start();
			}
		}
	}
}
