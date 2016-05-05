package View;

import Model.Hero;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Creator :  Charles
 * Date : 05-05-16
 */
public class Inventory extends JPanel {
    private Hashtable<String, BufferedImage> img = new Hashtable<>();
    private Hero hero;

	private final int SPRITESIZE = 32;

    private final int TILESIZE = 1;
    private final int TILES_PER_AXIS = 25;

    private final int MENU_TILES_WIDTH = 5;

    public Inventory() {
        this.setPreferredSize(new Dimension(MENU_TILES_WIDTH * SPRITESIZE * TILESIZE, SPRITESIZE * TILES_PER_AXIS));
        try {
			this.img.put("PotionHP",ImageIO.read(new File("PotionHP.png")));
			this.img.put("PotionMana",ImageIO.read(new File("PotionMana.png")));
        } catch (IOException e) {
        	System.out.println("ERREUR de lecture dans Inventory");
			e.printStackTrace();
		}
		
    }
    
    public void setHero(Hero hero) {
		this.hero = hero;
	}
    
    @Override
    public void paint(Graphics g) {
        try {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, MENU_TILES_WIDTH * SPRITESIZE * TILESIZE, TILES_PER_AXIS * SPRITESIZE * TILESIZE);
            
			g.setColor(Color.RED);
	        g.drawString("HP: " + hero.getHP() + "/" + hero.getHPMax(), SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE);
	        
	        g.setColor(Color.BLUE);
	        g.drawString("Mana: " + hero.getMana() + "/" + hero.getManaMax(), SPRITESIZE * TILESIZE, 2 * SPRITESIZE * TILESIZE);
	    
	        for(int i = 0; i < hero.getInventory().size(); i++){
	        	g.drawImage(img.get(hero.getInventory().get(i).getSprite()), 2 * SPRITESIZE * TILESIZE, (3 + i) * SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, SPRITESIZE * TILESIZE, null);
	        }
        } catch (Exception e) {
            System.out.println("ERREUR d'affichage du menu");
            e.printStackTrace();
        }
    }

    public void refresh() {
        this.repaint();
    }
}
