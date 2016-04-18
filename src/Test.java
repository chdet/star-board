public class Test {
	private /*static final*/ int size = 50;
	private int[][] mapMatrix = new int[size][size];
	private static Hero hero;
	private Projectile proj;
	private Window window;
	
	
	private final int LEFT = 0;
	private final int RIGHT = 1;
	private final int UP = 2;
	private final int DOWN = 3;
	
	public Test(Window window){
		this.window = window;
		this.hero = new Hero(15);
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int[][] getMapMatrix() {
		return mapMatrix;
	}
	
	public void setMapMatrix(int[][] mapMatrix) {
		this.mapMatrix = mapMatrix;
	}
	
	public static Hero getHero() {
		return /*this.*/hero;
	}
	
	public void setProj(Projectile proj) {
		this.proj = proj;
	}
	
	
	public void moveHeroLeft(){
			hero.setHeroOrient(0);
			int[] oldHeroPos = hero.getHeroPos();
			int[] newHeroPos = {hero.getHeroPos()[0] - 1, hero.getHeroPos()[1]};
			if(mapMatrix[newHeroPos[0]][newHeroPos[1]] == 0){
				hero.setHeroPos(newHeroPos);
			}
			window.refreshHero(oldHeroPos,LEFT);
	}
	
	public void moveHeroRight(){
			hero.setHeroOrient(1);
			int[] oldHeroPos = hero.getHeroPos();
			int[] newHeroPos = {hero.getHeroPos()[0] + 1, hero.getHeroPos()[1]};
			if(mapMatrix[newHeroPos[0]][newHeroPos[1]] == 0){
				hero.setHeroPos(newHeroPos);
			}
			window.refreshHero(oldHeroPos,RIGHT);
	}
	
	public void moveHeroUp(){
			hero.setHeroOrient(2);
			int[] oldHeroPos = hero.getHeroPos();
			int[] newHeroPos = {hero.getHeroPos()[0],hero.getHeroPos()[1] - 1};
			if(mapMatrix[newHeroPos[0]][newHeroPos[1]] == 0){
				hero.setHeroPos(newHeroPos);
			}
			window.refreshHero(oldHeroPos, UP);
	}
	
	public void moveHeroDown(){
			hero.setHeroOrient(3);
			int[] oldHeroPos = hero.getHeroPos();
			int[] newHeroPos = {hero.getHeroPos()[0],hero.getHeroPos()[1] + 1};
			if(mapMatrix[newHeroPos[0]][newHeroPos[1]] == 0){
				hero.setHeroPos(newHeroPos);
			}
			window.refreshHero(oldHeroPos,DOWN);
	}
	
	public void moveProj(Projectile proj){
		window.refreshProj(proj);
		System.out.println("dans le test" + proj.getProjPos()[0]);
		
		int x = proj.getProjPos()[0];
		int y = proj.getProjPos()[1];
		int[] newProjPos = new int[2];
		
		if(proj.getProjOrient() == 0){
			newProjPos[0] = x - 1;
			newProjPos[1] = y;
		}
		else if(proj.getProjOrient() == 1){
			newProjPos[0] = x + 1;
			newProjPos[1] = y;
		}
		else if(proj.getProjOrient() == 2){
			newProjPos[0] = x;
			newProjPos[1] = y - 1;
		}
		else if(proj.getProjOrient() == 3){
			newProjPos[0] = x;
			newProjPos[1] = y + 1;
		}
		
		proj.setProjPos(newProjPos);
	}
	
}
