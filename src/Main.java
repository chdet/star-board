import Controller.Keyboard;
import Model.Game;
import View.Window;

public class Main {
	public static void main(String[] args) {
		
		Window window = new Window();
		Game game = new Game(window);
		Keyboard keyboard = new Keyboard(game);
		window.setKeyListener(keyboard);
		
		
		
		int size = game.getSize();
		int[][] newMapMatrix = game.getMapMatrix();
		
		for(int i = 0; i < size; i++){
			newMapMatrix[i][0] = 1;
			newMapMatrix[i][size-1]=1;
			for(int j = 1;j < size-1; j++){
				newMapMatrix[i][j] = 0;
				newMapMatrix[0][j] = 1;
				newMapMatrix[size-1][j] = 1;
			}
		}
		
		game.setMapMatrix(newMapMatrix);
		window.refreshMap(newMapMatrix, game.getHero().getHP());
		
	}
}
