package Model;

public class Hero extends GameEntity {
	private  Integer HP;
    private int orient;
	
	public Hero(Integer HP){
		this.HP = HP;
        setPos(new int[]{1,1});
        setSprite("HeroDown"); // + Down/Left/Right/Up + .png
	}

	public  Integer getHP() {
		return HP;
	}
	
	void setHP(Integer HP){
		this.HP = HP;
	}

	public void attack(){}

	void setOrient(int orient) {
        this.orient = orient;
		switch(orient){
            case Game.LEFT: setSprite("HeroLeft"); break;
            case Game.RIGHT: setSprite("HeroRight"); break;
            case Game.UP: setSprite("HeroUp"); break;
            case Game.DOWN: setSprite("HeroDown"); break;
		}
	}

	public int getOrient() {
		return orient;
	}
}
