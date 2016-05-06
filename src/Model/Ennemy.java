package Model;

import java.util.Random;

public class Ennemy extends AICreature{

	public Ennemy(Game game, int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(game, pos, HPMax, manaMax, attack, defense);
		setHostility(HOSTILE);
		setSprite("TrooperDown");
	}

	public Ennemy(int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(pos, HPMax, manaMax, attack, defense);
		setHostility(HOSTILE);
		setSprite("TrooperDown");
	}

	
	protected void setOrient(int orient) {
		if(0 <= orient && orient < 4){
			this.orient = orient;
		}
        
        switch(orient){
        case Game.LEFT: setSprite("TrooperLeft"); break;
        case Game.RIGHT: setSprite("TrooperRight"); break;
        case Game.UP: setSprite("TrooperUp"); break;
        case Game.DOWN: setSprite("TrooperDown"); break;
    	}
        	
	}
	
	protected void nextAction(){
		Random random = new Random();
		int[] pos = getPos();
		int[] enemyPos = null;
		try{
			enemyPos = getGame().closestEnemy(this).getPos();
			System.out.println(enemyPos[0]);

		}catch(NullPointerException e){}	//No close enemies
		if(enemyPos != null){
			int dX = enemyPos[0] - pos[0];
			int dY = enemyPos[1] - pos[1];

			if((dX == 0 && dY == 1) || (dX == 0 && dY == -1) || (dX == 1 && dY == 0) || (dX == -1 && dY == 0)){
				this.attack();
			}

			if(!pathBlocked(dX,dY)) {
				if (Math.min(Math.abs(dX), Math.abs(dY)) == Math.abs(dX)) {
					if (dX > 0) {
						this.move(Game.RIGHT);
					} else if (dX < 0) {
						this.move(Game.LEFT);
					}
					if (dX == 0) {
						if (dY > 0) {
							this.move(Game.DOWN);
						} else if (dY < 0) {
							this.move(Game.UP);
						}
					}
				} else if (Math.min(Math.abs(dX), Math.abs(dY)) == Math.abs(dY)) {
					if (dY > 0) {
						this.move(Game.DOWN);
					} else if (dY < 0) {
						this.move(Game.UP);
					}
					if (dY == 0) {
						if (dX > 0) {
							this.move(Game.RIGHT);
						} else if (dX < 0) {
							this.move(Game.LEFT);
						}
					}
				}
			}else{
				int direction = 0;
				do{
					direction = random.nextInt(4);	//4 Directions (the argument of nextInt is exclusive)
				}while(getGame().doesCollide(this.inDirection(direction)));
				this.move(direction);
			}
		}else{
			int direction = 0;
			do{
				direction = random.nextInt(4);	//4 Directions (the argument of nextInt is exclusive)
			}while(getGame().doesCollide(this.inDirection(direction)));
			this.move(direction);
		}

	}

	private boolean pathBlocked(int dX, int dY){
		if(dX !=0){
			for(int i = 1; i < Math.abs(dX); i++){
				if(dX > 0){
					if(getGame().doesCollide(new int[]{getPos()[0]+i,getPos()[1]})){return true;}
				}else if (dX < 0){
					if(getGame().doesCollide(new int[]{getPos()[0]-i,getPos()[1]})){return true;}
				}
			}
		}
		if(dY != 0){
			for(int i = 1; i < Math.abs(dY); i++){
				if(dY > 0){
					if(getGame().doesCollide(new int[]{getPos()[0],getPos()[1]+i})){return true;}

				}else if (dY < 0){
					if(getGame().doesCollide(new int[]{getPos()[0],getPos()[1]-i})){return true;}
				}
			}
		}
		return false;
	}

}
