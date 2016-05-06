package Model;

/**
 * Creator :  Charles
 * Date : 29-04-16
 */
public abstract class AICreature extends Creature implements Runnable {
	private int WAIT = 300;
    private int WAITMin = 300;
    private boolean active = true;
	
    private int hostility;          //0 is neutral; 1 is hostile; 2 is friendly

    static final int NEUTRAL = 0;
    static final int HOSTILE = 1;
    static final int FRIENDLY = 2;


    public AICreature(Game game, int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
        super(game, pos, HPMax, manaMax, attack, defense);
    }

    public AICreature(int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
        super(pos, HPMax, manaMax, attack, defense);
    }
    
    public int getWAIT() {
		return WAIT;
	}

	void setWAIT(int WAIT) {
		if (WAIT > 0){
			this.WAIT = WAIT;
		}
	}

	int getWAITMin() {
		return WAITMin;
	}

	public void setWAITMin(int WAITMin) {
		if (WAITMin > 0){
			this.WAITMin = WAITMin;
		}
	}

    int getHostility() {
        return hostility;
    }

    void setHostility(int hostility) {
        this.hostility = hostility;
    }

    void setActive(boolean active){
        this.active = active;
    }

    protected abstract void nextAction();       //Problème d'accès ici. Pour redéfinir nextAction dans les sous-classes, la méthode doit être au plus protected -> tout Model peut acceder à nextAction -> Problème

    public void walkOn(Item item){
    	item.act(this);
		getGame().removeItem(item);
	}
    
    @Override
    public void run(){
        while(isAlive() && active){
            try{
                nextAction();
                Thread.sleep(WAIT);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
