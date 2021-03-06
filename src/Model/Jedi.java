package Model;

class Jedi extends Hero{
	
	Jedi(Game game, int[] pos){
		super(game, pos, 150, 200, 2f,5f);	//hpMax, manaMax, attack, defense
		setHP(this.getHPMax());
		setMana(this.getManaMax());
	}
	
	protected void setOrient(int orient) {
		if(0 <= orient && orient < 4){
			this.orient = orient;
		}
		
        switch(orient){
        case Game.LEFT: setSprite("JediLeft"); break;
        case Game.RIGHT: setSprite("JediRight"); break;
        case Game.UP: setSprite("JediUp"); break;
        case Game.DOWN: setSprite("JediDown"); break;
        }
	}
	
	public void levelUp(){
		setExp(0);
		setLevel(getLevel() + 1);
		switch(getLevel()){
		case 2:
			addToSpellList("Force");
			break;
		case 5:
			addToSpellList("Spike");
			break;
		}
	}
	
}
