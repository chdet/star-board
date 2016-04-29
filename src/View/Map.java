package View;

import Model.Creature;
import Model.Terrain;
import Model.Projectile;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

public class Map extends JPanel{
    private Terrain[][] terrainMap;
    private Hashtable<String,BufferedImage> img = new Hashtable<>();

	private final int SPRITESIZE = 16;
	private final int HEROHEIGHT = 18;

    private final int TILESIZE = 2;

    private ArrayList<Creature> creatures;
    private ArrayList<Projectile> projectiles;
	
    
	public Map(){
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		try{
			this.img.put("Grass",ImageIO.read(new File("Floor.png")));
			this.img.put("Rock",ImageIO.read(new File("Wall.png")));
			this.img.put("HeroLeft",ImageIO.read(new File("JediLeft.png")));
			this.img.put("HeroRight",ImageIO.read(new File("JediRight.png")));
			this.img.put("HeroUp",ImageIO.read(new File("JediUp.png")));
			this.img.put("HeroDown",ImageIO.read(new File("JediDown.png")));
			
			this.img.put("IADown",ImageIO.read(new File("TrooperDown.png")));
            this.img.put("IAUp",ImageIO.read(new File("TrooperUp.png")));
            this.img.put("IARight",ImageIO.read(new File("TrooperRight.png")));
            this.img.put("IALeft",ImageIO.read(new File("TrooperLeft.png")));

			this.img.put("FireBall",ImageIO.read(new File("FireBall.png")));
		}
		catch(IOException e){
        	e.printStackTrace();
        }
	}

	public void paint(Graphics g) {
        for (int i = 0; i < terrainMap.length; i++) {
            for (int j = 0; j < terrainMap[0].length; j++) {
                g.drawImage(img.get(terrainMap[i][j].getSprite()), i * SPRITESIZE * TILESIZE, j * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
            }
        }
        
        g.setColor(Color.BLACK);
        g.fillRect(0, terrainMap[0].length * SPRITESIZE * TILESIZE, terrainMap[0].length * SPRITESIZE * TILESIZE, 2 * SPRITESIZE * TILESIZE);

        
        for (Creature creature : creatures) {
        	int x = creature.getPos()[0];
            int y = creature.getPos()[1];
            g.drawImage(img.get(creature.getSprite()), x * SPRITESIZE * TILESIZE, y * SPRITESIZE * TILESIZE - (HEROHEIGHT - SPRITESIZE), SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
        }
        
        for (Projectile projectile : projectiles) {
        	
            /*
            g.setColor(projectile.getColor());
            int[] pPos = projectile.getPos();
            g.fillOval(pPos[0] * TILESIZE * SPRITESIZE, pPos[1] * TILESIZE * SPRITESIZE, TILESIZE * SPRITESIZE, TILESIZE * SPRITESIZE);
			*/
        	
        	int x = projectile.getPos()[0];
            int y = projectile.getPos()[1];
            g.drawImage(img.get(projectile.getSprite()), x * SPRITESIZE * TILESIZE, y * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
        }
        
        
    }

	public void buildMap(Terrain[][] terrainMatrix){
        this.terrainMap = terrainMatrix;
        this.repaint();
    }


    public void setProjectiles(ArrayList<Projectile> projectiles){
        this.projectiles = projectiles;
    }

    public void setCreatures(ArrayList<Creature> creatures){
        this.creatures = creatures;
    }


    public void refresh(){
        this.repaint();
    }
	
}
