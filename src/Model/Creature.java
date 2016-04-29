package Model;

public abstract class Creature extends Moving{
	protected  Integer HP;
	boolean alive = true;
    
	public Creature(Game game, int[] pos, Integer HP){
		super(game, pos, Game.DOWN);
		setHP(HP);
	}
	
    public  Integer getHP() {
		return HP;
	}
	
	void setHP(Integer HP){
		this.HP = HP;
		if(HP <= 0){
			die();
		} 
	}
	
	public void attack(){
			game.addProjectile(new FireBall(game, getPos(), getOrient()));
	}
	
	public void die(){
		if(!(this instanceof Hero)){
			this.alive = false;
	    	game.moveColMap(getPos());
	    	game.removeCreature(this);
		}
		else{
			//TODO Retour au menu;
		}
		
    }
	
}
