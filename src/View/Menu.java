package View;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Model.Game;

public class Menu extends JPanel{
		
	public Menu(Window window){
		this.setFocusable(true);
        this.setPreferredSize(new Dimension(401,201));
		this.requestFocusInWindow();
		
		Button button1 = new Button("New Game: Jedi");
		Button button2 = new Button("New Game: Sith");
		Button button3 = new Button("Load Game");
	    this.add(button1);
	    this.add(button2);
	    this.add(button3);
	    button1.addActionListener(new setGameActionListener(window, button1));
	    button2.addActionListener(new setGameActionListener(window, button2));
	    button3.addActionListener(new setGameActionListener(window, button3));
	    
	    JTextField textField = new JTextField(20);
	    this.add(textField);
	    textField.addActionListener(new setRoomNumberActionListener(window, textField));
	    
	    
	}
	
	@Override
	  protected void paintComponent(Graphics g) {
		try{
	    	BufferedImage img = ImageIO.read(new File("StarBoard.png"));
	    	super.paintComponent(g);
		    g.drawImage(img, 0, 0, null);
	    }
	    catch(Exception e){
	    	System.out.println("ERREUR d'affichage du menu");
	    	e.printStackTrace();
	    }
	}
	
}
