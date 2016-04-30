package Model;

/**
 * Creator :  Charles
 * Date : 29-04-16
 */
public abstract class AICreature extends Creature implements Runnable {
    private static int WAIT = 250;
    private int hostility;          //0 is neutral; 1 is hostile; 2 is friendly

    public static final int NEUTRAL = 0;
    public static final int HOSTILE = 1;
    public static final int FRIENDLY = 2;


    public AICreature(Game game, int[] pos, Integer HPMax, Integer manaMax){
        super(game, pos, HPMax, manaMax);
    }

    public int getHostility() {
        return hostility;
    }

    public void setHostility(int hostility) {
        this.hostility = hostility;
    }

    protected abstract void nextAction();       //Problème d'accès ici. Pour redéfinir nextAction dans les sous-classes, la méthode doit être au plus protected -> tout Model peut acceder à nextAction -> Problème

    @Override
    public void run(){
        while(alive){
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
