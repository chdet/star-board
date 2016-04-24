package Model;

import java.awt.Color;

public class Projectile extends GameEntity implements Runnable{
	private Game game;
	private static int WAIT = 300;
	private int direction;
	private Color color = Color.RED;

    private boolean collided = false;

	public Projectile(Game game, int[] pos, int direction){
		this.game = game;
		setPos(pos);
		this.direction = direction;
        setSprite(null);
	}
	
	public void run(){
		try{
			while(!collided){
				this.move();
				Thread.sleep(WAIT);
			}
		}
		catch(Exception e){}
		
	}

    public void move(){
        int[] pos = getPos();
        int[] newPos = pos;
        switch (direction){
            case Game.LEFT: newPos = new int[]{pos[0]-1,pos[1]}; break;
            case Game.RIGHT: newPos = new int[]{pos[0]+1,pos[1]}; break;
            case Game.UP: newPos = new int[]{pos[0],pos[1]-1}; break; //Origine en haut à gauche
            case Game.DOWN: newPos = new int[]{pos[0],pos[1]+1}; break;
        }
        if(!game.doesCollide(newPos)){
            setPos(newPos);
        }
        else{
            //TODO: Ajouter un check si le proj se trouver SUR un collidable(dans la cas où une Creature se déplace sur le projectile)
            this.collided = true;
            game.removeProjectile(this);
            //TODO: Appeler la méthode de Game qui gère les dégats (newPos et Area of Damage en attribut)
        }
    }


    public Color getColor() {
        return color;
    }

    public int getDirection() {
		return direction;
	}

}