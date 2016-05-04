package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Window  implements  Runnable{
	private Map map = new Map();
	private Game game;
	private Dungeon currentDungeon;
	
    private static final int FPS = 60; //Frames per second

	public Window(Game game){
	    JFrame frame = new JFrame("StarBoard");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().add(this.map);

//	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setUndecorated(false);
		frame.setResizable(false);
	    frame.setVisible(true);
		this.game = game;
		this.currentDungeon = game.getDungeon();
	    buildMap(game.getTerrainMatrix());
		setCreatures(game.getCreatures());
		setProjectiles(game.getProjectiles());
		setHero(game.getHero());
		frame.pack();
	}

    public void run(){
		while(true){
			try {
				if(this.currentDungeon != game.getDungeon()){
					this.currentDungeon= game.getDungeon();
					buildMap(game.getTerrainMatrix());
					setCreatures(game.getCreatures());
				}
				refreshMap();
				Thread.sleep(1000/FPS);
			}catch (Exception e){}
		}
}

    public void buildMap(Terrain[][] terrainMatrix){
		this.map.buildMap(terrainMatrix);
	}

    public void setCreatures(ArrayList<Creature> creatures){
        this.map.setCreatures(creatures);
    }

	public void setProjectiles(ArrayList<Projectile> projectiles){
		this.map.setProjectiles(projectiles);
	}

	public void setHero(Hero hero) {
		this.map.setHero(hero);
	}

    public void refreshMap(){
        this.map.refresh();
    }
	
	public void setKeyListener(KeyListener keyboard){
	    this.map.addKeyListener(keyboard);
	}
}
