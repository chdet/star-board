package Model;

public abstract class Hero extends Creature {
	
	private int level = 1;
	private int exp = 0;
	
	public Hero(Game game,int[] pos){
		super(game,pos);
		setCurrentSpell(0);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if(level >=1){
			this.level = level;
		}
	}
	
	public abstract void levelUp();

	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp){
		if(exp >= 0){
			this.exp = exp;
		}
	}

}
