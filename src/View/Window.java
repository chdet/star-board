package View;

import Model.Projectile;

import javax.swing.*;
import java.awt.event.KeyListener;

public class Window {

	/*private*/ public Map map = new Map();
	
	public Window(){	    
	    JFrame frame = new JFrame("Model.Game");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBounds(0, 0, 1000, 1020);
	    //frame.getContentPane().setBackground(Color.gray);
	    frame.add(this.map);
	    
	    
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    //frame.setUndecorated(true);
	    frame.setVisible(true);
	    
	}
	
	public void refreshMap(int[][] newMapMatrix, int HP){
		this.map.refreshMap(newMapMatrix);
		//this.map.refreshMenu(HP);
	}
	
	public void refreshHero(int[] oldHeroPos, int orientation){
		this.map.refreshHero(oldHeroPos, orientation);
	}
	
	public void refreshProj(Projectile proj){
		this.map.refreshProj(proj);
		System.out.println("Dans le window" + proj.getProjPos()[0]);
	}
	
	public void setKeyListener(KeyListener keyboard){
	    this.map.addKeyListener(keyboard);
	}
}
