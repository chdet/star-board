package Model;

public abstract class Moving extends GameEntity{
	protected Game game;
	protected int orient;
	
	
	public Moving(Game game, int[] pos, int orient){
		this.game = game;
		setPos(pos);
        setOrient(orient);
	}
	
	public int getOrient() {
		return orient;
	}
	
	protected abstract void setOrient(int orient);		//TODO: Changement de sprite dynamique en fonction de la sprite "basique" (ex: Jedi, Trooper, Laser)
	
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
