package Model;

public class Sith extends Hero{
		
	public Sith(Game game, int[] pos){
		super(game, pos, 150, 200, 2f,5f);	//hpMax, manaMax, attack, defense
		setHP(this.getHPMax());
		setMana(this.getManaMax());
	}
	
	protected void setOrient(int orient) {
		if(0 <= orient && orient < 4){
			this.orient = orient;
		}
		
		switch(orient){
	    case Game.LEFT: setSprite("SithLeft"); break;
	    case Game.RIGHT: setSprite("SithRight"); break;
	    case Game.UP: setSprite("SithUp"); break;
	    case Game.DOWN: setSprite("SithDown"); break;
	    }
	}
		
	public void levelUp(){
		setExp(0);
		setLevel(getLevel() + 1);
		switch(getLevel()){
		case 2:
			addToSpellList("Rally");
			break;
		case 5:
			addToSpellList("Ice");
			break;
		}
	}
	
}
