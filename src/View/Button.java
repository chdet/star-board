package View;

import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Model.Game;

public class Button extends JButton implements MouseListener{
	private Menu menu; 
	private String name;
	public Button(Menu menu, String name){
		super(name);
		this.name = name;
		this.menu = menu;
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		switch(name){
		case "New Game: Jedi":
			menu.setWhichGame("New Game: Jedi");
			break;
		case "New Game: Sith":
			menu.setWhichGame("New Game: Sith");
			break;
		case "Load Game":
			menu.setWhichGame("Load Game");
			System.out.println("Load Game");
			break;
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

}
