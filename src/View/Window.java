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
	private Inventory inventory = new Inventory();
	private Dungeon currentDungeon;

    private static final int FPS = 60; //Frames per second

	public Window(){
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    frame.setUndecorated(false);
		frame.setResizable(false);
		frame.setVisible(true);

	    frame.getContentPane().add(getMenu());
	    frame.pack();
	}

    public void run(){
		while(true){
			try {
				if(this.currentDungeon != game.getDungeon()){
					this.currentDungeon= game.getDungeon();
					buildMap(game.getTerrainMatrix());
					setCreatures(game.getCreatures());
					setItems(game.getItems());
				}
				refreshMap();
				refreshInventory();
				Thread.sleep(1000/FPS);
			}catch (Exception e){
				e.printStackTrace();
			}
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

	public Inventory getInventory() {
		return inventory;
	}

	public Map getMap() {
		return map;
	}

	public  void setGame(Game game){
		this.game = game;
	}

	public void setCurrentDungeon(Dungeon dungeon){
		this.currentDungeon = dungeon;
	}

	public void setCreatures(ArrayList<Creature> creatures){
        this.map.setCreatures(creatures);
    }

	public void setProjectiles(ArrayList<Projectile> projectiles){
		this.map.setProjectiles(projectiles);
	}

	public void setHero(Hero hero) {
		this.map.setHero(hero);
		this.inventory.setHero(hero);
	}
	
	public void setItems(ArrayList<Item> items) {
		this.map.setItems(items);
	}

    public void refreshMap(){
        this.map.refresh();
    }
    
    public void refreshInventory(){
        this.inventory.refresh();
    }
	
	public void setKeyListener(KeyListener keyboard){
	    this.map.addKeyListener(keyboard);
	}

}
