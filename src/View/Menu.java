package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

import Model.Game;

public class Menu extends JPanel{
	
	private String whichGame = "";
	
	public Menu(){
		this.setFocusable(true);
        this.setPreferredSize(new Dimension(401,201));
		this.requestFocusInWindow();
		
		Button button1 = new Button(this, "New Game: Jedi");
		Button button2 = new Button(this, "New Game: Sith");
	    Button button3 = new Button(this, "Load Game");
	    this.add(button1);
	    this.add(button2);
	    this.add(button3);
	}
	
	@Override
	  protected void paintComponent(Graphics g) {
		try{
	    	BufferedImage img = ImageIO.read(new File("StarBoard.png"));
	    	super.paintComponent(g);
		    g.drawImage(img, 0, 0, null);
		    System.out.println("Menu créé");
	    }
	    catch(Exception e){
	    	System.out.println("ERREUR d'affichage du menu");
	    	e.printStackTrace();
	    }
	}
	
	public String whichGame() {
		return whichGame;
	}

	public void setWhichGame(String whichGame) {
		this.whichGame = whichGame;
	}
}
