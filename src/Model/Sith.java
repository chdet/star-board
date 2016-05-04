package Model;

public class Sith extends Hero{
		
	public Sith(Game game, int[] pos){
		super(game, pos);
		addToSpellList("Laser");
		
		setHPMax(250);
		setHP(this.getHPMax());
		setManaMax(175);
		setMana(this.getManaMax());
		setAttack(10f);
		setDefense(5f);
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
