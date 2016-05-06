package Model;

import java.io.Serializable;

public abstract class Moving extends GameEntity{
	protected int orient;
	
	public Moving(Game game, int[] pos, int orient, String sprite){
		setGame(game);
		setPos(pos);
		setSprite(sprite);
		setOrient(orient);

	}

	public Moving(Game game, int[] pos, int orient){
		setGame(game);
		setPos(pos);
		setOrient(orient);

	}

	public Moving(int[] pos, int orient){
		setPos(pos);
		setOrient(orient);
	}
	
	int getOrient() {
		return orient;
	}
	
	protected abstract void setOrient(int orient);		//TODO: Changement de sprite dynamique en fonction de la sprite "basique" (ex: Jedi, Trooper, Laser)
	
	public abstract  void  move(int orient);
	
	int[] inFront(){
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

	int[] inDirection(int orient){
		int[] pos = getPos();
		int[] inDirection = pos;
		switch (orient){
			case Game.LEFT: inDirection = new int[]{pos[0]-1,pos[1]}; break;
			case Game.RIGHT: inDirection = new int[]{pos[0]+1,pos[1]}; break;
			case Game.UP: inDirection = new int[]{pos[0],pos[1]-1}; break;    //L'origine est en haut à gauche
			case Game.DOWN: inDirection = new int[]{pos[0],pos[1]+1}; break;
		}
		return inDirection;
	}
	
}
