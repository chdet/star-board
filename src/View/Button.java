package View;

import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JButton{
	private String name;
	
	public Button(String name){
		super(name);
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
