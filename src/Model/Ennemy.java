package Model;

public class Ennemy extends AICreature{

	public Ennemy(Game game, int[] pos, Integer HP){
		super(game, pos, HP);
		setHostility(HOSTILE);
	}

	protected void nextAction(){
		this.move(getOrient());
	}
		
}
