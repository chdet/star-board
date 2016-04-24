import Controller.Keyboard;
import Model.Game;
import View.Window;
import Model.Game;
import Model.Terrain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Main {
	public static void main(String[] args) {
        Window window = new Window();

        Terrain[][] terrainMatrix;
        Terrain grass = new Terrain("Grass", false);
        Terrain rock = new Terrain("Rock", true);

        try{
            //TODO: Déplacer dans une autre classe ? Gérer l'exception du fichier vide(créer map par défaut)
            String line;
            int x = 0;
            int y = 0;
            BufferedReader tempfile = new BufferedReader(new FileReader("map.txt"));
            if(((line = tempfile.readLine()) != null)){x = line.length(); y++;}
            while((tempfile.readLine()) != null){y++;}
            terrainMatrix = new Terrain[x][y];

            BufferedReader mapfile = new BufferedReader(new FileReader("map.txt"));
            int i = 0;
            while((line = mapfile.readLine()) != null){
                for(int j = 0; i < x; i++){
                    char tile = line.charAt(i);
                    switch(tile){
                        case 'g': terrainMatrix[i][j] = grass; break;
                        case 'r': terrainMatrix[i][j] = rock; break;
                    }
                }
                i++;
            }
            Game game = new Game(window, terrainMatrix);
            Keyboard keyboard = new Keyboard(game);
            window.setKeyListener(keyboard);
        }

        catch(IOException e){
            final int SIZE = 25;
            terrainMatrix = new Terrain[SIZE][SIZE];

            for(int i = 0; i < SIZE; i++){
                terrainMatrix[i][0] = rock;
                terrainMatrix[i][SIZE-1] = rock;
                for(int j = 1;j < SIZE-1; j++){
                    terrainMatrix[i][j] = grass;
                    terrainMatrix[0][j] = rock;
                    terrainMatrix[SIZE-1][j] = rock;
                }
            }
            Game game = new Game(window, terrainMatrix);
            Keyboard keyboard = new Keyboard(game);
            window.setKeyListener(keyboard);
        }

        Thread t = new Thread(window);
        t.start();

	}
}
