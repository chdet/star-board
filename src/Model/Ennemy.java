

package Model;

public class Ennemy extends AICreature{

	public Ennemy(Game game, int[] pos, Integer HP){
		super(game, pos, HP);
		setHostility(HOSTILE);
		setSprite("IADown");
	}

	protected void nextAction(){
		boolean[][] colMap = game.getCollisionMap();
		int[] pos = getPos();
		int[] heroPos = game.getHero().getPos(); //Temporaire, Ennemy ne devrait pas avoir accès à Hero
		int dX = heroPos[0] - pos[0];
		int dY = heroPos[1] - pos[1];
		if(Math.min(Math.abs(dX),Math.abs(dY)) == Math.abs(dX)){
			if(dX > 0){
				this.move(Game.RIGHT);
			}
			else if(dX < 0){
				this.move(Game.LEFT);
			}
			if(dX == 0){
				if(dY > 0){
					this.setOrient(Game.DOWN);
					this.attack();
				}
				else if(dY < 0){
					this.setOrient(Game.UP);
					this.attack();
				}
			}
		}
		else if(Math.min(Math.abs(dX),Math.abs(dY)) == Math.abs(dY)){
			if(dY > 0){
				this.move(Game.DOWN);
			}
			else if(dY < 0){
				this.move(Game.UP);
			}
			if(dY == 0){
				if(dX > 0){
					this.setOrient(Game.RIGHT);
					this.attack();
				}
				else if(dX < 0){
					this.setOrient(Game.LEFT);
					this.attack();
				}
			}
		}
	}
		
}