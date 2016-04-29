package Model;

public abstract class Creature extends Moving{
	protected  Integer HP;
    
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
		if(this instanceof IA){
			((IA)this).dead = true;
	    	game.moveColMap(getPos());
	    	game.removeIA((IA)this);
		}
		else{
			//TODO Retour au menu;
		}
		
    }
	
}
