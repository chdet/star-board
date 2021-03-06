package View;

import Model.Creature;
import Model.Hero;
import Model.Item;
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
    private ArrayList<Item> items;

    
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
			
			this.img.put("SithLeft",ImageIO.read(new File("SithLeft.png")));
			this.img.put("SithRight",ImageIO.read(new File("SithRight.png")));
			this.img.put("SithUp",ImageIO.read(new File("SithUp.png")));
			this.img.put("SithDown",ImageIO.read(new File("SithDown.png")));
			
			this.img.put("TrooperDown",ImageIO.read(new File("TrooperDown.png")));
            this.img.put("TrooperUp",ImageIO.read(new File("TrooperUp.png")));
            this.img.put("TrooperRight",ImageIO.read(new File("TrooperRight.png")));
            this.img.put("TrooperLeft",ImageIO.read(new File("TrooperLeft.png")));

			this.img.put("LaserHorizontal",ImageIO.read(new File("LaserHorizontal.png")));
			this.img.put("LaserVertical",ImageIO.read(new File("LaserVertical.png")));
			
			this.img.put("SpikeLeft",ImageIO.read(new File("SpikeLeft.png")));
			this.img.put("SpikeRight",ImageIO.read(new File("SpikeRight.png")));
			this.img.put("SpikeUp",ImageIO.read(new File("SpikeUp.png")));
			this.img.put("SpikeDown",ImageIO.read(new File("SpikeDown.png")));
			
			this.img.put("Ice",ImageIO.read(new File("Ice.png")));
            this.img.put("Rally",ImageIO.read(new File("Heart.png")));


            this.img.put("PotionHP",ImageIO.read(new File("PotionHP.png")));
			this.img.put("PotionMana",ImageIO.read(new File("PotionMana.png")));

			this.img.put("Trap",ImageIO.read(new File("Trap.png")));

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

        for(int i = 0; i< creatures.size(); i++){
        	int x = creatures.get(i).getPos()[0];
            int y = creatures.get(i).getPos()[1];
            g.drawImage(img.get(creatures.get(i).getSprite()), (x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE - (HEROHEIGHT - SPRITESIZE), SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
            g.setColor(Color.RED);
            g.fillRect((x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE - (HEROHEIGHT - SPRITESIZE) - 15, SPRITESIZE*TILESIZE*(creatures.get(i).getHP())/(creatures.get(i).getHPMax()), 5);
            g.setColor(Color.BLUE);
            if(creatures.get(i).getManaMax() > 0){
                g.fillRect((x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE - (HEROHEIGHT - SPRITESIZE) - 10, SPRITESIZE*TILESIZE*(creatures.get(i).getMana())/(creatures.get(i).getManaMax()), 5);

            }
        }
        
        for(int i = 0; i< projectiles.size(); i++){
        	int x = projectiles.get(i).getPos()[0];
            int y = projectiles.get(i).getPos()[1];
            g.drawImage(img.get(projectiles.get(i).getSprite()), (x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
        }

        for(int i = 0; i< items.size(); i++){
        	int x = items.get(i).getPos()[0];
            int y = items.get(i).getPos()[1];
            g.drawImage(img.get(items.get(i).getSprite()), (x-heroPos[0]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, (y-heroPos[1]+(TILES_PER_AXIS/2)) * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
        }
        
        if (!hero.isAlive()){
        	g.setColor(Color.RED);
            g.fillRect(0, 0, TILES_PER_AXIS * SPRITESIZE * TILESIZE, TILES_PER_AXIS * SPRITESIZE * TILESIZE);
        }
    }

	public void buildMap(Terrain[][] terrainMatrix){
        this.terrainMap = terrainMatrix;
        this.repaint();
    }

	public void setItems(ArrayList<Item> items) {
		this.items = items;
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
