package Model;

import java.awt.Color;
import java.util.ArrayList;

public class Projectile extends Moving implements Runnable{
	private static int WAIT = 50;
	private boolean collided = false;
	private int damage; //mettre un négatif pour faire un soin.
	protected String effect;
	//protected ArrayList<int[]> aoe = new ArrayList<int[]>();
	private int aoe;
	private int manaCost;
	
	//private Color color = Color.BLUE;

    
	public Projectile(Game game, int[] pos, int orient){
		super(game, pos, orient);
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public String getEffect() {
		return effect;
	}
	
	public int getAoe() {
		return aoe;
	}
	
	public void setAoe(int aoe) {
		if(aoe > 0){
			this.aoe = aoe;
		}
		else{}
	}
	
	public Integer getManaCost() {
		return manaCost;
	}
	
	public void setManaCost(int manaCost) {
		if(manaCost > 0){
			this.manaCost = manaCost;
		}
		else{}
	}

	public void endCourse(){
    	game.damage(this);
    	game.moveColMap(getPos());
    	collided = true;
    	game.removeProjectile(this);
    }
    
	public void run(){
		while(!collided){
			try{
				move(getOrient());
				Thread.sleep(WAIT);
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("ERREUR PROJECTILE");
			}
		}
	}
    
}