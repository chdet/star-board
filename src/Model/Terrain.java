package Model;

/**
 * Creator :  Charles
 * Date : 18-04-16
 */
public class Terrain extends GameEntity {
    private int[] pos;

    public Terrain(int [] pos){
        this.pos = pos;
        this.sprite = "Rock";
    }
}
