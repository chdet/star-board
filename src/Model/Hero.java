package Model;

public class Hero extends GameEntity {
	private  int[] pos = {1,1};
	private int heroOrient = 1;
	private  Integer HP;
	
	public Hero(Integer HP){
		this.HP = HP;
        this.sprite = "Hero"; // + Down/Left/Right/Up + .png
	}

	public  Integer getHP() {
		return HP;
	}
	
	public void setHP(Integer HP){
		this.HP = HP;
	}
	
	public int[] getHeroPos(){
		return pos;
	}
	
	public void setHeroPos(int[] heroPos) {// à changer
		this.pos = heroPos;
	}
	
	public void attack(){}

	public void setHeroOrient(int heroOrient) {
		this.heroOrient = heroOrient;
	}

	public int getOrient() {
		return heroOrient;
	}
}
