package Model;

public class Ennemy extends AICreature{

	public Ennemy(Game game, int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(game, pos, HPMax, manaMax, attack, defense);
		setHostility(HOSTILE);
		setSprite("TrooperDown");
	}
	
	protected void setOrient(int orient) {
        this.orient = orient;
        
        switch(orient){
        case Game.LEFT: setSprite("TrooperLeft"); break;
        case Game.RIGHT: setSprite("TrooperRight"); break;
        case Game.UP: setSprite("TrooperUp"); break;
        case Game.DOWN: setSprite("TrooperDown"); break;
    	}
        	
	}
	
	protected void nextAction(){
		boolean[][] colMap = game.getCollisionMap();
		int[] pos = getPos();
		int[] heroPos = game.getHero().getPos(); //Temporaire, Ennemy ne devrait pas avoir accès à Hero
		int dX = heroPos[0] - pos[0];
		int dY = heroPos[1] - pos[1];
		
		if (this.getMana() != 0){
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
						this.useSpell();
					}
					else if(dY < 0){
						this.setOrient(Game.UP);
						this.useSpell();
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
						this.useSpell();
					}
					else if(dX < 0){
						this.setOrient(Game.LEFT);
						this.useSpell();
					}
				}
			}
		}
		else{
			if(Math.min(Math.abs(dX),Math.abs(dY)) == Math.abs(dX)){
				if(dX > 0){
					this.move(Game.RIGHT);
				}
				else if(dX < 0){
					this.move(Game.LEFT);
				}
				if(dX == 0){
					if(dY > 0){
						this.move(Game.DOWN);
					}
					else if(dY < 0){
						this.move(Game.UP);
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
						this.move(Game.RIGHT);
					}
					else if(dX < 0){
						this.move(Game.LEFT);
					}
				}
			}
			
			if(Math.abs(dX - dY) == 1){
				this.attack();
			} 
		}
	}
		
}
