package Model;

public class Hero extends Creature {
	
	public Hero(Game game, Integer HP){
		this.HP = HP;
        setPos(new int[]{1,1});
        setSprite("HeroDown"); // + Down/Left/Right/Up + .png
	}

}
