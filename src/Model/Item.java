package Model;

public abstract class Item extends GameEntity{
	
	public Item(int[] pos){
		setPos(pos);
	}
	public abstract void act(Creature creature);

}
