package View;

import Model.Creature;
import Model.Hero;
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

	private final int SPRITESIZE = 32;
	private final int HEROHEIGHT = 34;

    private final int TILESIZE = 1;
    private final int TILES_PER_AXIS = 25;

    private Hero hero;
    private ArrayList<Creature> creatures;
    private ArrayList<Projectile> projectiles;
	
    
	public Map(){
		this.setFocusable(true);
        this.setPreferredSize(new Dimension(SPRITESIZE*TILES_PER_AXIS,SPRITESIZE*TILES_PER_AXIS));
		this.requestFocusInWindow();
		
		try{
			this.img.put("Floor",ImageIO.read(new File("Floor.png")));
			this.img.put("Wall",ImageIO.read(new File("Wall.png")));
			this.img.put("JediLeft",ImageIO.read(new File("JediLeft.png")));
			this.img.put("JediRight",ImageIO.read(new File("JediRight.png")));
			this.img.put("JediUp",ImageIO.read(new File("JediUp.png")));
			this.img.put("JediDown",ImageIO.read(new File("JediDown.png")));
			
			this.img.put("TrooperDown",ImageIO.read(new File("TrooperDown.png")));
            this.img.put("TrooperUp",ImageIO.read(new File("TrooperUp.png")));
            this.img.put("TrooperRight",ImageIO.read(new File("TrooperRight.png")));
            this.img.put("TrooperLeft",ImageIO.read(new File("TrooperLeft.png")));

			this.img.put("LaserHorizontal",ImageIO.read(new File("LaserHorizontal.png")));
			this.img.put("LaserVertical",ImageIO.read(new File("LaserVertical.png")));
		}
		catch(IOException e){
        	e.printStackTrace();
        }
	}

	public void paint(Graphics g) {
        int[] heroPos = hero.getPos();

        for (int i = 0; i < TILES_PER_AXIS; i++) {
            for (int j = 0; j < TILES_PER_AXIS; j++) {
                int ii = heroPos[0]-(TILES_PER_AXIS/2)+i;
                int jj = heroPos[1]-(TILES_PER_AXIS/2)+j;

                if(ii < 0 || jj < 0 || ii >= terrainMap.length || jj >= terrainMap[0].length){
                    g.drawImage(img.get("Wall"), i * SPRITESIZE * TILESIZE, j * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
                }
                else{
                    g.drawImage(img.get(terrainMap[ii][jj].getSprite()), i * SPRITESIZE * TILESIZE, j * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
                }
            }
        }

        g.setColor(Color.RED);
        g.drawString("HP: " + creatures.get(0).getHP() + "/" + creatures.get(0).getHPMax(), SPRITESIZE * TILESIZE, (terrainMap[0].length + 1) * SPRITESIZE * TILESIZE);
        
        g.setColor(Color.BLUE);
        g.drawString("Mana: " + creatures.get(0).getMana() + "/" + creatures.get(0).getManaMax(), 3*SPRITESIZE * TILESIZE, (terrainMap[0].length + 1)* SPRITESIZE * TILESIZE);
        
        for(int i = 0; i< creatures.size(); i++){
        	int x = creatures.get(i).getPos()[0];
            int y = creatures.get(i).getPos()[1];
            g.drawImage(img.get(creatures.get(i).getSprite()), (x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE - (HEROHEIGHT - SPRITESIZE), SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
        }
        
        for(int i = 0; i< projectiles.size(); i++){
        	int x = projectiles.get(i).getPos()[0];
            int y = projectiles.get(i).getPos()[1];
            g.drawImage(img.get(projectiles.get(i).getSprite()), (x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
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

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public void refresh(){
        this.repaint();
    }
	
}
