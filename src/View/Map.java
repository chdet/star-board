package View;

import Model.GameEntity;
import Model.Hero;
import Model.Projectile;
import Model.Terrain;

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

    private Hero hero;
    private Projectile projectile;
    private ArrayList<GameEntity> creatures;
    private ArrayList<Projectile> projectiles;
	
	public Map(){
		this.setFocusable(true);
		this.requestFocusInWindow();
		
		try{
			this.img.put("Grass",ImageIO.read(new File("Grass.png")));	//	0
			this.img.put("Rock",ImageIO.read(new File("Rock.png")));	//	1
			this.img.put("HeroLeft",ImageIO.read(new File("HeroLeft.png")));	//	2	0
			this.img.put("HeroRight",ImageIO.read(new File("HeroRight.png")));	//	3	1
			this.img.put("HeroUp",ImageIO.read(new File("HeroUp.png")));		//	4	2
			this.img.put("HeroDown",ImageIO.read(new File("HeroDown.png")));	//	5	3
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

            int x = hero.getPos()[0];
            int y = hero.getPos()[1];
            g.drawImage(img.get(hero.getSprite()), x * SPRITESIZE * TILESIZE, y * SPRITESIZE * TILESIZE - (HEROHEIGHT - SPRITESIZE), SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);


        }
        g.setColor(Color.BLACK);
        g.fillRect(0, terrainMap[0].length * SPRITESIZE * TILESIZE, terrainMap[0].length * SPRITESIZE * TILESIZE, 2 * SPRITESIZE * TILESIZE);

        for (Projectile projectile : projectiles) {
            g.setColor(projectile.getColor());
            int[] pPos = projectile.getPos();
            g.fillOval(pPos[0] * TILESIZE * SPRITESIZE, pPos[1] * TILESIZE * SPRITESIZE, TILESIZE * SPRITESIZE, TILESIZE * SPRITESIZE);

        }
    }

	public void buildMap(Terrain[][] terrainMatrix){
        this.terrainMap = terrainMatrix;
        this.repaint();
    }

    public void setHero(Hero hero){
        this.hero = hero;
    }

    public void setProjectiles(ArrayList<Projectile> projectiles){
        this.projectiles = projectiles;
    }

    public void setCreatures(ArrayList<GameEntity> creatures){
        this.creatures = creatures;
    }


    public void refresh(){
        this.repaint();
    }
	
}