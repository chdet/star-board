
import Controller.Keyboard;
import Model.Game;
import View.Window;
import Model.Game;
import Model.Dungeon;
import Model.DungeonGeneration;
import Model.Terrain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Terrain[][] generateTerrainMatrix(){
        Terrain[][] terrainMatrix;
        Terrain floor = new Terrain("Floor", false);
        Terrain wall = new Terrain("Wall", true);
        try{
            //TODO: Déplacer dans une autre classe ?
            String line;
            int x = 0;
            int y = 0;
            BufferedReader tempfile = new BufferedReader(new FileReader("map.txt"));
            if(((line = tempfile.readLine()) != null)){
                x = line.length();
                y++;
            }
            while((tempfile.readLine()) != null){
                y++;
            }
            terrainMatrix = new Terrain[x][y];

            BufferedReader mapfile = new BufferedReader(new FileReader("map.txt"));
            int j = 0;
            while((line = mapfile.readLine()) != null){
                for(int i = 0; i < x; i++){
                    char tile = line.charAt(i);
                    switch(tile){
                        case 'g': terrainMatrix[i][j] = floor; break;
                        case 'r': terrainMatrix[i][j] = wall; break;
                    }
                }
                j++;
            }
        }

        catch(IOException e){
            final int XSIZE = 25;
            final int YSIZE = 25;
            terrainMatrix = new Terrain[XSIZE][YSIZE];

            for(int i = 0; i < XSIZE; i++){
                terrainMatrix[i][0] = wall;
                terrainMatrix[i][YSIZE-1] = wall;
                for(int j = 1;j < YSIZE-1; j++){
                    terrainMatrix[i][j] = floor;
                    terrainMatrix[0][j] = wall;
                    terrainMatrix[XSIZE-1][j] = wall;
                }
            }
        }
        return terrainMatrix;
    }

	public static void main(String[] args) {
		Window window = new Window();
        System.out.println("fin du main");
	}
	
}
