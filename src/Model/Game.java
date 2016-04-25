package Model;

import java.util.ArrayList;
import java.util.Iterator;
import View.Window;

public class Game{
    private Hero hero;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
    private ArrayList<GameEntity> creatures = new ArrayList<>();
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
        window.buildMap(getTerrainMatrix());
        window.setCreatures(creatures);
        window.setHero(hero);
        window.setProjectiles(projectiles);
        updateColMap();
        
	}



    public void updateColMap(){
        for(int i = 0; i<terrainMatrix.length; i++){
            for(int j = 0; j<terrainMatrix[0].length; j++){
                collisionMap[i][j]= terrainMatrix[i][j].getCollision();
            }
        }
                
        for(GameEntity creature : creatures){
            int[] pos = creature.getPos();
            collisionMap[pos[0]][pos[1]] = true;
        }
        
        int[] pos = hero.getPos();
        collisionMap[pos[0]][pos[1]] = true;
    }

    public void moveColMap(int[] pos, int[] newPos){
        collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
        collisionMap[newPos[0]][newPos[1]] = true;
    }

	public Terrain[][] getTerrainMatrix(){
        return terrainMatrix;
    }

	public Hero getHero() {
		return this.hero;
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
	
}
