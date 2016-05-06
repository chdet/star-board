package View;

import javax.swing.JButton;

public class Button extends JButton{
	private String name;
	
	Button(String name){
		super(name);
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
