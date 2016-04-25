package Model;

import java.awt.Color;

public class Projectile extends Moving implements Runnable{
	private Game game;
	private static int WAIT = 50;
	private Color color = Color.RED;

    private boolean collided = false;

	public Projectile(Game game, int[] pos, int orient){
		this.game = game;
		setPos(pos);
		this.orient = orient;
        setSprite(null);
	}
	
    public Color getColor() {
        return color;
    }

    public void endCourse(){
    	this.collided = true;
        game.removeProjectile(this);
    }
    
    public void run(){
		try{
			while(!collided){
				this.move(orient);
				Thread.sleep(WAIT);
			}
		}
		catch(Exception e){}
	}
}