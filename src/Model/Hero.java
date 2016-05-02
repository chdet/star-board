package Model;

public class Hero extends Creature {
	
	public Hero(Game game, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(game,new int[]{1,1}, HPMax, manaMax, attack, defense);
	}
	
	protected void setOrient(int orient) {
        this.orient = orient;
        
        switch(orient){
        case Game.LEFT: setSprite("JediLeft"); break;
        case Game.RIGHT: setSprite("JediRight"); break;
        case Game.UP: setSprite("JediUp"); break;
        case Game.DOWN: setSprite("JediDown"); break;
        }
	}

}
