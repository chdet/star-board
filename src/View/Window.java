package View;

import Model.*;

import javax.swing.*;

import Controller.Keyboard;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Window  implements  Runnable{
	private JFrame frame = new JFrame("StarBoard");
	private Game game;
	private Menu menu = new Menu(this);
	private Map map = new Map();
	private int roomNumber = 0;
	
    private static final int FPS = 60; //Frames per second

	public Window(){
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setUndecorated(false);
		frame.setResizable(false);
		frame.setVisible(true);
		
	    frame.setContentPane(menu);
	    frame.pack();
	}

	public void setGame(String whichGame/*,int roomNumber*/){
		if(whichGame == "Load Game"){
			System.out.println("Load Game");
		}
		else{
			if(roomNumber != 0){
				game = new Game(whichGame, roomNumber);
			    Keyboard keyboard = new Keyboard(game);
			    
			    menu.removeAll();
			    frame.remove(menu);
			    
			    frame.getContentPane().add(this.map);
			    this.map.requestFocusInWindow(); //Ne pas oublier
	
			    frame.setPreferredSize(map.getPreferredSize());
			    buildMap(game.getTerrainMatrix());
			    setCreatures(game.getCreatures());
			    setProjectiles(game.getProjectiles());
			    setHero(game.getHero());
			    this.setKeyListener(keyboard);
			    frame.pack();
			          
			    Thread t = new Thread(this);
			    t.start();
		    }
		}
	}
	
	public void setRoomNumber(int roomNumber) {
    	if(roomNumber > 0){
    		this.roomNumber = roomNumber;
    	}
	}
	
    public void run(){
		while(true){
			try {
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
