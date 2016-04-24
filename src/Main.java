import Controller.Keyboard;
import Model.Game;
import View.Window;
import Model.Game;
import Model.Terrain;



public class Main {
	public static void main(String[] args) {

        final int SIZE = 25;
        Terrain[][] terrainMatrix = new Terrain[SIZE][SIZE];

        Terrain grass = new Terrain("Grass", false);
        Terrain rock = new Terrain("Rock", true);

		for(int i = 0; i < SIZE; i++){
            terrainMatrix[i][0] = rock;
            terrainMatrix[i][SIZE-1] = rock;
			for(int j = 1;j < SIZE-1; j++){
                terrainMatrix[i][j] = grass;
                terrainMatrix[0][j] = rock;
                terrainMatrix[SIZE-1][j] = rock;
			}
		}

		Window window = new Window();
		Game game = new Game(window, terrainMatrix);
		Keyboard keyboard = new Keyboard(game);
		window.setKeyListener(keyboard);
		Thread t = new Thread(window);
		t.start();



		/* window.refreshMap(newMapMatrix, game.getHero().getHP());*/
		
	}
}
