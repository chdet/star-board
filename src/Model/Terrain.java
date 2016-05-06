package Model;

import java.io.Serializable;

/**
 * Creator :  Charles
 * Date : 18-04-16
 */
public class Terrain implements Serializable{
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
