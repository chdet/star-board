package Model;

public class Projectile extends Moving implements Runnable{
	private int WAIT = 50;
	private boolean collided = false;
	private float damage; //mettre un négatif pour faire un soin.
	protected String effect;
	//protected ArrayList<int[]> aoe = new ArrayList<int[]>();
	private int aoe;
	private int manaCost;
	private int x;

    
	public Projectile(Game game, int[] pos, int orient, String sprite){
		super(game, pos, orient);
		setSprite(sprite);
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
        System.out.println("orient changée");
        System.out.println(getSprite());
        System.out.println(getSprite() == "Laser");
        
        if (getSprite() == "Laser"){
        	System.out.println("oui");
        	switch(orient){
        	case Game.LEFT : setSprite("LaserHorizontal"); break;
        	case Game.RIGHT : setSprite("LaserHorizontal"); break;
        	case Game.UP : setSprite("LaserVertical"); break;
        	case Game.DOWN : setSprite("LaserVertical"); break;
        	}
        }
        else if (getSprite() == "Spike"){
        	switch(orient){
        	case Game.LEFT : setSprite("SpikeLeft"); break;
        	case Game.RIGHT : setSprite("SpikeRight"); break;
        	case Game.UP : setSprite("SpikeUp"); break;
        	case Game.DOWN : setSprite("SpikeDown"); break;
        	}
        }
	}
	
	public void setWAIT(int WAIT){// TODO Ramener dans Moving
		if(WAIT >= 0){
			this.WAIT = WAIT;
		}
	}
	
	public float getDamage() {
		return damage;
	}
	
	public void setDamage(float damage) {
		this.damage = damage;
	}
	
	public String getEffect() {
		return effect;
	}
	
	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	public int getAoe() {
		return aoe;
	}
	
	public void setAoe(int aoe) {
		if(aoe > 0){
			this.aoe = aoe;
		}
	}
	
	public Integer getManaCost() {
		return manaCost;
	}
	
	public void setManaCost(int manaCost) {
		if(manaCost > 0){
			this.manaCost = manaCost;
		}
	}

	public void endCourse(){
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