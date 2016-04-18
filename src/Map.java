import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.*;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class Map extends JPanel{
	private int[][] mapMatrix;
	private final int TILESIZE = 16;
	private final int HEROHEIGHT = 21;
	private int toRefresh; //0: toute la map, 1:la position du héros.
	private int[] oldHeroPos;
	private int orientation;
	private ArrayList<BufferedImage> img = new ArrayList<BufferedImage>();
	private int HP;
	private int[] ProjPos;
	private int projOrient;
	
	public Map(){
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		try{
			this.img.add(ImageIO.read(new File("Grass.png")));	//	0
			this.img.add(ImageIO.read(new File("Rock.png")));	//	1
			this.img.add(ImageIO.read(new File("HeroLeft.png")));	//	2	0
			this.img.add(ImageIO.read(new File("HeroRight.png")));	//	3	1
			this.img.add(ImageIO.read(new File("HeroUp.png")));		//	4	2
			this.img.add(ImageIO.read(new File("HeroDown.png")));	//	5	3
		}
		catch(IOException e){
        	e.printStackTrace();
        }
	}
	
	
	//public setMapMatrix(int[][] newMapMatrix){
	//	this.mapMatrix = int[][] newMapMatrix;
	//}

	public void paint(Graphics g){
        if(toRefresh == 0){
        	for(int i = 0; i < mapMatrix.length; i++){
           		for(int j=0; j < mapMatrix[0].length; j++){
           			g.drawImage(img.get(mapMatrix[i][j]), i*TILESIZE, j*TILESIZE, null);                   	                    	
           		}
           		
           		int x = Test.getHero().getHeroPos()[0];
       			int y = Test.getHero().getHeroPos()[0];
           		
       			
       			g.drawImage(img.get(2 + orientation), x*TILESIZE, y*TILESIZE - (HEROHEIGHT - TILESIZE), null);
       			g.setColor(Color.BLACK);
       			g.fillRect(0, mapMatrix[0].length*TILESIZE, mapMatrix[0].length*TILESIZE, 2*TILESIZE);
       			
        	}
        }
        else if(toRefresh == 1){
        	int oldX = oldHeroPos[0];
        	int oldY = oldHeroPos[1];
        	int newX = Test.getHero().getHeroPos()[0];
        	int newY = Test.getHero().getHeroPos()[1];
        	
        	g.drawImage(img.get(mapMatrix[oldX][oldY]), oldX*TILESIZE, oldY*TILESIZE, null);
        	g.drawImage(img.get(mapMatrix[oldX][oldY - 1]), oldX*TILESIZE, (oldY- 1)*TILESIZE, null);       		        		
        	g.drawImage(img.get(2 + orientation), newX*TILESIZE, newY*TILESIZE - (HEROHEIGHT - TILESIZE), null);
        }
        else if(toRefresh == 2){
        	g.setColor(Color.BLACK);
        	g.fillRect(0, mapMatrix[0].length*TILESIZE, mapMatrix[0].length*TILESIZE, 2*TILESIZE);
        	
        	g.setColor(Color.GREEN);
        	g.drawString(Test.getHero().getHP().toString(), 0, (mapMatrix[0].length+1)*TILESIZE);
        }
        else if(toRefresh == 3){
        	int x = ProjPos[0];
        	int y = ProjPos[1];
        	
        	int oldX = 0, oldY = 0;
        	System.out.println("projOrientation" + projOrient);
        	
        	if (projOrient == 0){
        		oldX = x + 1;
        		oldY = y;
        	}
        	
        	else if (projOrient == 1){
        		oldX = x - 1;
        		oldY = y;
        	}
        	
        	else if (projOrient == 2){
        		oldX = x;
        		oldY = y + 1;
        	}
        	
        	else if (projOrient == 3){
        		oldX = x;
        		oldY = y - 1;
        	}
        	
        	g.drawImage(img.get(mapMatrix[oldX][oldY]), oldX*TILESIZE, oldY*TILESIZE, null);
        	
        	
        	g.setColor(Color.MAGENTA);
        	g.fillOval(x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
        	System.out.println("Dans la map " + x);
        }
	}
	
	public void refreshMap(int[][] mapMatrix){
		this.mapMatrix = mapMatrix;
		this.toRefresh = 0;
		this.repaint();
	}
	
	public void refreshHero(int[] oldHeroPos, int orientation){
		this.oldHeroPos = oldHeroPos;
		this.orientation = orientation;
		this.toRefresh = 1;
		this.repaint();
	}
	
	public void refreshMenu(int HP){
		this.HP = HP;
		this.toRefresh = 2;
		this.repaint();
	}
	
	public void refreshProj(Projectile proj){
		this.ProjPos = proj.getProjPos(); //il faudrait une liste
		System.out.println("Dans la map " + this.ProjPos[0]);
		this.projOrient = proj.getProjOrient();
		this.toRefresh = 3;
		this.repaint();
	}
	
}
