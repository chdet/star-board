package Model;

/**
 * Creator :  Charles
 * Date : 18-04-16
 */
public abstract class GameEntity {
    protected Game game;
    private int[] pos;
    private String sprite;

    public int[] getPos(){
        return this.pos;
    }

    protected void setPos(int[] pos) {
        this.pos = pos;
    }

    public String getSprite(){
        return sprite;
    }

    protected void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
