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
    }

    @Override
    public void paint(Graphics g) {
        try {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, MENU_TILES_WIDTH * SPRITESIZE * TILESIZE, TILES_PER_AXIS * SPRITESIZE * TILESIZE);

        } catch (Exception e) {
            System.out.println("ERREUR d'affichage du menu");
            e.printStackTrace();
        }
    }


    public void refresh() {
        this.repaint();
    }
}
