package Controller;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	private void launchGame(String whichGame){
		if(whichGame.equals("Load Game")){
			Game game = load("Save.txt");
			System.out.println("Load Game");
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
			window.setProjectiles(projectiles);
			window.setItems(game.getItems());
			window.setHero(game.getHero());
			window.setKeyListener(keyboard);
			window.getFrame().pack();
			
			game.startAI();
			Thread t = new Thread(game.getStatus());
			t.start();
			Thread t2 = new Thread(window);
			t2.start();
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
	private Game load(String filename){
		FileInputStream file;
		ObjectInputStream i;
		Game game = null;
		try {
			file = new FileInputStream(filename);
			i = new ObjectInputStream(file);
			game = (Game) i.readObject();
			
			
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		/*for(Projectile projectile: game.getProjectiles()){
			Thread t = new Thread(projectile);
			t.start();
		}*/
		
		return game;
	}

	
}
