package Model;

import java.io.Serializable;

/**
 * Creator :  Charles
 * Date : 18-04-16
 */
public abstract class GameEntity implements Serializable{
    private Game game;
    
	private int[] pos;
    private String sprite;

    
    public Game getGame() {
		return game;
	}
    
    public int[] getPos(){
        return this.pos;
    }

    protected void setPos(int[] pos) {
        this.pos = pos;
    }

    public String getSprite(){
        return sprite;
    }

    void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
