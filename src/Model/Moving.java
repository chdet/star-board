package Model;

public abstract class Moving extends GameEntity{
	protected Game game;
	private int orient;
	
	
	public Moving(Game game, int[] pos, int orient){
		this.game = game;
		setPos(pos);
        setOrient(orient);
	}
	
	public int getOrient() {
		return orient;
	}
	
	protected void setOrient(int orient) {
        this.orient = orient;
        if (getSprite() == "Laser"){
        	switch(orient){
        	case Game.LEFT : setSprite("LaserHorizontal"); break;
        	case Game.RIGHT : setSprite("LaserHorizontal"); break;
        	case Game.UP : setSprite("LaserVertical"); break;
        	case Game.DOWN : setSprite("LaserVertical"); break;
        	}
        }
        else if (this instanceof Hero) {
        	switch(orient){
            case Game.LEFT: setSprite("HeroLeft"); break;
            case Game.RIGHT: setSprite("HeroRight"); break;
            case Game.UP: setSprite("HeroUp"); break;
            case Game.DOWN: setSprite("HeroDown"); break;
        	}
        }
        else if (this instanceof Ennemy){
        	switch(orient){
            case Game.LEFT: setSprite("IALeft"); break;
            case Game.RIGHT: setSprite("IARight"); break;
            case Game.UP: setSprite("IAUp"); break;
            case Game.DOWN: setSprite("IADown"); break;
        	}
        }
		
	}
	
	public synchronized void  move(int orient){
        setOrient(orient);
        int[] pos = getPos();
        int[] newPos = inFront();
        
        if(!game.doesCollide(newPos)){
            this.setPos(newPos);
            game.moveColMap(pos, newPos);
        }
               
        else{
        	if(this instanceof Projectile){
	            //TODO: Ajouter un check si le proj se trouver SUR un collidable(dans la cas où une Creature se déplace sur le projectile)
	            ((Projectile)(this)).endCourse();
	            //TODO: Appeler la méthode de Game qui gère les dégats (newPos et Area of Damage en attribut)
	        }
        }
    }
	
	public int[] inFront(){
		int[] pos = getPos();
        int[] inFront = pos;
		switch (orient){
	        case Game.LEFT: inFront = new int[]{pos[0]-1,pos[1]}; break;
	        case Game.RIGHT: inFront = new int[]{pos[0]+1,pos[1]}; break;
	        case Game.UP: inFront = new int[]{pos[0],pos[1]-1}; break;    //L'origine est en haut à gauche
	        case Game.DOWN: inFront = new int[]{pos[0],pos[1]+1}; break;
	    }
		return inFront;
	}
	
}
