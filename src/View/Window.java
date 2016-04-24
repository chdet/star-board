package View;

import Model.GameEntity;
import Model.Hero;
import Model.Projectile;
import Model.Terrain;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Window  implements  Runnable{
	private Map map = new Map();

    private static final int FPS = 60; //Frames per second

	public Window(){
	    JFrame frame = new JFrame("StarBoard");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBounds(0, 0, 815, 900);
	    frame.add(this.map);
	    
//	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setUndecorated(false);
	    frame.setVisible(true);
	    
	}

    public void run(){
        try {
            while (true) {
                refreshMap();
                Thread.sleep(1000/FPS);
            }
        }catch (Exception e){}
    }

    public void buildMap(Terrain[][] terrainMatrix){
		this.map.buildMap(terrainMatrix);
	}

	public void setHero(Hero hero){
		this.map.setHero(hero);
	}

    public void setCreatures(ArrayList<GameEntity> creatures){
        this.map.setCreatures(creatures);
    }

	public void setProjectiles(ArrayList<Projectile> projectiles){
		this.map.setProjectiles(projectiles);
	}

    public void refreshMap(){
        this.map.refresh();
    }
	
	public void setKeyListener(KeyListener keyboard){
	    this.map.addKeyListener(keyboard);
	}
}
