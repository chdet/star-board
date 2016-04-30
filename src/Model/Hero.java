package Model;

public class Hero extends Creature {
	
	public Hero(Game game, Integer HPMax, Integer manaMax){
		super(game,new int[]{1,10}, HPMax, manaMax);
	}

}
