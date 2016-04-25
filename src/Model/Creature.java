package Model;

public class Creature extends Moving{
	protected  Integer HP;
    
    public  Integer getHP() {
		return HP;
	}
	
	void setHP(Integer HP){
		this.HP = HP;
	}
	
	public void attack(){}
}
