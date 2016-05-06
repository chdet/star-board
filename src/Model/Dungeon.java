package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Creator :  Charles
 * Date : 01-05-16
 */
public class Dungeon implements Serializable{
    private Terrain[][] terrainMatrix;
    private int[] startPoint; //Position the hero starts at
    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<Trap> traps = new ArrayList<>();
    private int roomCount;

    public Dungeon(int[] size){
        Terrain wall = new Terrain("Wall", true);
        terrainMatrix = new Terrain[size[0]][size[1]];
        for(int i = 0; i < size[0]; i++){
            for(int j = 0; j < size[1]; j++){
                terrainMatrix[i][j] = wall;
            }
        }
    }

    public Dungeon(Terrain[][] terrainMatrix){
        this.terrainMatrix = terrainMatrix;
    }

    void modifyArea(int[] start, int[] end, Terrain type){
        for(int i = 0; i <= Math.abs(end[0]-start[0]); i++){
            for(int j = 0; j <= Math.abs(end[1]-start[1]); j++){
                if(end[0] >= start[0]){
                    if(end[1] >= start[1]){
                        this.terrainMatrix[start[0]+i][start[1]+j] = type;
                    }
                    else{
                        this.terrainMatrix[start[0]+i][start[1]-j] = type;
                    }
                }
                else{
                    if(end[1] >= start[1]){
                        this.terrainMatrix[start[0]-i][start[1]+j] = type;
                    }
                    else{
                        this.terrainMatrix[start[0]-i][start[1]-j] = type;
                    }
                }
            }
        }
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public Terrain[][] getTerrainMatrix() {
        return terrainMatrix;
    }

    public ArrayList<Creature> getCreatures(Game game) {    //Called when Game loads Dungeon
        for(Creature creature : this.creatures){
            creature.setGame(game);
        }
        return creatures;
    }

    public ArrayList<Trap> getTraps() {    //Called when Game loads Dungeon
        return traps;
    }

    public void addCreatures(Creature[] creatures) {
        for(Creature creature : creatures){
            this.creatures.add(creature);
        }
    }

    public void addTrap(Trap trap) {
        this.traps.add(trap);
    }

    public int[] getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int[] startPoint) {
        this.startPoint = startPoint;
    }
}
