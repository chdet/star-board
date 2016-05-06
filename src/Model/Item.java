package Model;

import java.io.Serializable;

public abstract class Item extends GameEntity{
	
	Item(int[] pos){
		setPos(pos);
	}
	public abstract void act(Creature creature);

}
