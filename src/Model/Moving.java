package Model;

public abstract class Moving extends GameEntity{
	private Game game;
	protected int orient;
	
	
	public int getOrient() {
		return orient;
	}
	
	void setOrient(int orient) {
        this.orient = orient;
		switch(orient){
            case Game.LEFT: setSprite("HeroLeft"); break;
            case Game.RIGHT: setSprite("HeroRight"); break;
            case Game.UP: setSprite("HeroUp"); break;
            case Game.DOWN: setSprite("HeroDown"); break;
		}
	}
	
	public void move(int orient){
        setOrient(orient);
        int[] pos = getPos();
        int[] newPos = inFront();
        System.out.println(game.doesCollide(newPos));
        if(!game.doesCollide(newPos)){
            this.setPos(newPos);
            game.moveColMap(pos, newPos);
        }
               
        else{
        	if(this.getClass().toString().equals("class Model.Projectile")){
	            //TODO: Ajouter un check si le proj se trouver SUR un collidable(dans la cas où une Creature se déplace sur le projectile)
	            ((Projectile)(this)).endCourse();
	            //TODO: Appeler la méthode de Game qui gère les dégats (newPos et Area of Damage en attribut)
	        }
        }
        System.out.println(pos[0]);
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
