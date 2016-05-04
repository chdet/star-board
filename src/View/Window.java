package View;

import Model.*;

import javax.swing.*;

import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Window  implements  Runnable{
	private JFrame frame = new JFrame("StarBoard");
	private Game game;
	private Menu menu = new Menu(this);
	private Map map = new Map();

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

	public JFrame getFrame() {
		return frame;
	}

	public Menu getMenu() {
		return menu;
	}

	public Map getMap() {
		return map;
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
