package Model;

import java.awt.Color;

public class Projectile extends GameEntity implements Runnable{
	private Game game;
	private static int time = 250;
	private int projOrient;
	private int[] pos;
	private static Color color = Color.MAGENTA;


	public Projectile(Game game, int[] projPos, int projOrient){
		this.game = game;
		this.pos = projPos;
		this.projOrient = projOrient;
        this.sprite = "";
	}
	
	public void run(){
		try{
			for(int i = 0; i < 100; i++){
				game.moveProj(this);
				Thread.sleep(time);
				System.out.println("Dans le run" + i);
			}
		}
		catch(Exception e){}
		
	}
	
	public int[] getProjPos() {
		return pos;
	}
	
	public void setProjPos(int[] newProjPos){
		this.pos = newProjPos;
		System.out.println("Dans le setProjPos " + newProjPos[0]);
	}
	
	public int getProjOrient() {
		return projOrient;
	}

}