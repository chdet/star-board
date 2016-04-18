
public class Hero {
	private  int[] heroPos = {1,1};
	private int heroOrient = 1;
	private  Integer HP;
	
	public Hero(Integer HP){
		this.HP = HP;
	}
	
	public  Integer getHP() {
		return HP;
	}
	
	public void setHP(Integer HP){
		this.HP = HP;
	}
	
	public int[] getHeroPos(){
		return heroPos;
	}
	
	public void setHeroPos(int[] heroPos) {// à changer
		this.heroPos = heroPos;
	}
	
	public void attack(){}

	public void setHeroOrient(int heroOrient) {
		this.heroOrient = heroOrient;
	}

	public int getOrient() {
		return heroOrient;
	}
}
