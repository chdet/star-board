package View;

import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.launchGameActionListener;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Menu extends JPanel{
	private JTextField textField = new JTextField(20);;

	public Menu(Window window){
		this.setFocusable(true);
        this.setPreferredSize(new Dimension(401,220));
		this.requestFocusInWindow();
		
		Button button1 = new Button("New Game: Jedi");
		Button button2 = new Button("New Game: Sith");
		Button button3 = new Button("Load Game");
	    this.add(button1);
	    this.add(button2);
	    this.add(button3);
	    button1.addActionListener(new launchGameActionListener(window, button1));
	    button2.addActionListener(new launchGameActionListener(window, button2));
	    button3.addActionListener(new launchGameActionListener(window, button3));
	    
	    this.add(textField);
	}

	public int getRoomCount() {
		int roomCount;
		try {
			roomCount = Integer.parseInt(textField.getText());
		}catch (NumberFormatException e){
			roomCount = 0;
		}
		return roomCount;
	}


	@Override
	  protected void paintComponent(Graphics g) {
		try{
	    	BufferedImage img = ImageIO.read(new File("StarBoard.png"));
	    	super.paintComponent(g);
		    g.drawImage(img, 0, 20, null);
	    }
	    catch(Exception e){
	    	System.out.println("ERREUR d'affichage du menu");
	    	e.printStackTrace();
	    }
	}
	
}
