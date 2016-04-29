package Model;

public class IA extends Creature implements Runnable{
	private static int WAIT = 250;
	private boolean hostility;
	
	boolean dead = false;
	
	public IA(Game game, int[] pos,Integer HP){
		super(game, pos, HP);
		this.hostility = true;
	}
	
	
	
	@Override
	public void run() {
		try {
			while(!dead){
				this.move(getOrient());
				Thread.sleep(WAIT);
			}
		} catch (Exception e) {
			System.out.println("ERREUR");
			e.printStackTrace();
		}
	}
		
}
