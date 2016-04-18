import java.util.*;

import java.awt.Color;

public class Projectile implements Runnable{
	private Test test;
	private static int time = 250;
	private int[] projPos;
	private int projOrient;
	private static Color color = Color.MAGENTA;


	public Projectile(Test test, int[] projPos, int projOrient){
		this.test = test;
		this.projPos = projPos;
		this.projOrient = projOrient;	
	}
	
	public void run(){
		try{
			for(int i = 0; i < 100; i++){
				test.moveProj(this);
				Thread.sleep(time);
				System.out.println("Dans le run" + i);
			}
		}
		catch(Exception e){}
		
	}
	
	public int[] getProjPos() {
		return projPos;
	}
	
	public void setProjPos(int[] newProjPos){
		this.projPos = newProjPos;
		System.out.println("Dans le setProjPos " + newProjPos[0]);
	}
	
	public int getProjOrient() {
		return projOrient;
	}

}