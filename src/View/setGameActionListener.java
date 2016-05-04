package View;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class setGameActionListener implements ActionListener{
	private Window window;
	private String whichGame;
	
	public setGameActionListener(Window window, Button button){
		this.window = window;
		this.whichGame = button.getName();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.setGame(whichGame);
	}
}
