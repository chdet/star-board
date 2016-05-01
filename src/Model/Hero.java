package Model;

public class Hero extends Creature {
	
	public Hero(Game game, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(game,new int[]{1,1}, HPMax, manaMax, attack, defense);
	}

}
