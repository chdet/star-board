package Model;

/**
 * Creator :  Charles
 * Date : 18-04-16
 */
public class Terrain{
    private String sprite;
    private boolean collision;

    public Terrain(String sprite, boolean collision){
        this.sprite = sprite;
        this.collision = collision;
    }

    public boolean getCollision(){
        return this.collision;
    }

    public String getSprite(){
        return sprite;
    }
    
}
