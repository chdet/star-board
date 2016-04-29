package Model;

import java.util.ArrayList;
import java.util.Iterator;
import View.Window;

public class Game{
    private Hero hero;
    private ArrayList<IA> IAs = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Terrain[][] terrainMatrix;
    private boolean[][] collisionMap;
    private View.Window window;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;


	public Game(Window window, Terrain[][] terrainMatrix){
		this.window = window;
        this.terrainMatrix = terrainMatrix;
        this.collisionMap = new boolean[terrainMatrix.length][terrainMatrix[0].length];
		this.hero = new Hero(this, 15);
		this.addIA(new IA(this, new int[]{1,1},15));
        window.buildMap(getTerrainMatrix());
        window.setHero(hero);
        window.setIAs(IAs);
        window.setProjectiles(projectiles);
        updateColMap();        
	}

    public void updateColMap(){
        for(int i = 0; i<terrainMatrix.length; i++){
            for(int j = 0; j<terrainMatrix[0].length; j++){
                collisionMap[i][j]= terrainMatrix[i][j].getCollision();
            }    
        }
        
        for(IA ia : IAs){
            int[] pos = ia.getPos();
            collisionMap[pos[0]][pos[1]] = true;
        }
        
        int[] posHero = hero.getPos();
        collisionMap[posHero[0]][posHero[1]] = true;
    }

    public void moveColMap(int[] pos, int[] newPos){
        collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
        //Il ne faut pas générer un moving sur une entité, sinon elle n'est plus pris en compte après.
        collisionMap[newPos[0]][newPos[1]] = true;
    }
    
    public void moveColMap(int[] pos){
    	collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
    }

	public Terrain[][] getTerrainMatrix(){
        return terrainMatrix;
    }

	public Hero getHero() {
		return hero;
	}
	
	public void addIA(IA ia){
        this.IAs.add(ia);
        Thread t = new Thread(ia);
        t.start();
    }

    void removeIA(IA ia){
        this.IAs.remove(ia);
    }
    
    public void addProjectile(Projectile projectile){
        this.projectiles.add(projectile);
        Thread t = new Thread(projectile);
        t.start();
    }

    void removeProjectile(Projectile projectile){
        this.projectiles.remove(projectile);
    }
    

	public boolean doesCollide(int[] pos){
        return collisionMap[pos[0]][pos[1]];
    }

	public void damage(Projectile projectile) {
		for(IA ia : IAs){
			for(int[] pos : projectile.getAoe()){
				System.out.println(pos[1]);
				System.out.println(ia.getHP());
				System.out.println(ia.getPos()[1]);
				if(pos[0] == ia.getPos()[0] && pos[1] == ia.getPos()[1]){
					System.out.println("touché");
					ia.setHP(ia.getHP() - projectile.getDamage());
					System.out.println(ia.getHP());
					//TODO effet
				}
			}
		}
		
	}

}
