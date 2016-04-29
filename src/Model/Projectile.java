package Model;

import java.awt.Color;
import java.util.ArrayList;

public class Projectile extends Moving implements Runnable{
	private static int WAIT = 50;
	private boolean collided = false;
	protected int damage;
	protected String effect;
	protected ArrayList<int[]> aoe = new ArrayList<int[]>();
	//private int manaCost;
	
	//private Color color = Color.BLUE;
	
    
    
    
	public Projectile(Game game, int[] pos, int orient){
		super(game, pos, orient);
	}
	
	public int getDamage() {
		return damage;
	}
	
	public String getEffect() {
		return effect;
	}
	
	public void setAoe(){};

	public ArrayList<int[]> getAoe() {
		return aoe;
	}

    public void endCourse(){
    	setAoe();
    	game.damage(this);
    	game.moveColMap(getPos());
    	collided = true;
    	game.removeProjectile(this);
    }
    
	public void run(){
		try{
			while(!collided){
				move(getOrient());
				Thread.sleep(WAIT);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("ERREUR");
		}
	}
    
}