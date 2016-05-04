package Controller;

import java.awt.event.ActionListener;

import javax.swing.JTextField;

import View.Window;

import java.awt.event.ActionEvent;

public class setRoomNumberActionListener implements ActionListener{
	private Window window;
	private JTextField textField;

	public setRoomNumberActionListener(Window window, JTextField textField){
		this.window = window;
		this.textField = textField;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	    textField.selectAll();
		window.setRoomNumber((int)(Integer.parseInt(textField.getText())));
	}

	
	
	
	
}
