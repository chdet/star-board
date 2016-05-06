package Model;

public class Projectile extends Moving implements Runnable{
	private int WAIT = 50;
	private boolean collided = false;
	private float damage;
	private String effect;
	//protected ArrayList<int[]> aoe = new ArrayList<int[]>();
	private int aoe;
	private int manaCost;
	private int x;

    
	Projectile(Game game, int[] pos, int orient, String sprite){
		super(game, pos, orient, sprite);
		setDamage(0);
		setEffect("");
		setAoe(1);
		setManaCost(0);
	}

	public synchronized void  move(int orient){
		setOrient(orient);
		int[] newPos = inFront();
		this.setPos(newPos);
		if(getGame().doesCollide(newPos)){
			this.endCourse();
		}
	}
	
	protected void setOrient(int orient) {
        this.orient = orient;

        if (getSprite().equals("Laser")){
        	System.out.println("oui");
        	switch(orient){
        	case Game.LEFT : setSprite("LaserHorizontal"); break;
        	case Game.RIGHT : setSprite("LaserHorizontal"); break;
        	case Game.UP : setSprite("LaserVertical"); break;
        	case Game.DOWN : setSprite("LaserVertical"); break;
        	}
        }
        else if (getSprite().equals("Spike")){
        	switch(orient){
        	case Game.LEFT : setSprite("SpikeLeft"); break;
        	case Game.RIGHT : setSprite("SpikeRight"); break;
        	case Game.UP : setSprite("SpikeUp"); break;
        	case Game.DOWN : setSprite("SpikeDown"); break;
        	}
        }
	}
	
	void setWAIT(int WAIT){// TODO Ramener dans Moving
		if(WAIT >= 0){
			this.WAIT = WAIT;
		}
	}
	
	float getDamage() {
		return damage;
	}
	
	void setDamage(float damage) {
		this.damage = damage;
	}
	
	String getEffect() {
		return effect;
	}
	
	void setEffect(String effect) {
		this.effect = effect;
	}
	
	int getAoe() {
		return aoe;
	}
	
	void setAoe(int aoe) {
		if(aoe > 0){
			this.aoe = aoe;
		}
	}
	
	Integer getManaCost() {
		return manaCost;
	}
	
	void setManaCost(int manaCost) {
		if(manaCost > 0){
			this.manaCost = manaCost;
		}
	}

	private void endCourse(){
    	getGame().damage(this);
    	collided = true;
    	getGame().removeProjectile(this);
    }
    
	public void run(){
		while(!collided){
			try{
				if(getGame().doesCollide(getPos())){
					//TODO: ATTENTION DEGATS APPLIQUES DEVANT A CHANGER
					endCourse();
				}
				else{
					move(getOrient());
					Thread.sleep(WAIT);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("ERREUR PROJECTILE");
			}
		}
	}
    
}