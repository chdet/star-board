package Model;

import java.util.ArrayList;

public class Game{
    private Hero hero;
    private ArrayList<Creature> creatures = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private Terrain[][] terrainMatrix;
    private boolean[][] collisionMap;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public Game(Terrain[][] terrainMatrix){
        this.terrainMatrix = terrainMatrix;
        this.collisionMap = new boolean[terrainMatrix.length][terrainMatrix[0].length];
		this.hero = new Hero(this, 15, 150);
        this.addCreature(hero);
		this.addCreature(new Ennemy(this, new int[]{1,1}, 15, 0));
		this.addCreature(new Ennemy(this, new int[]{2,1}, 15, 0));
		this.addCreature(new Ennemy(this, new int[]{3,1}, 15, 0));
		updateColMap();        
	}

	void updateColMap(){
        for(int i = 0; i<terrainMatrix.length; i++){
            for(int j = 0; j<terrainMatrix[0].length; j++){
                collisionMap[i][j]= terrainMatrix[i][j].getCollision();
            }    
        }
        
        for(Creature creature : creatures){
            int[] pos = creature.getPos();
            collisionMap[pos[0]][pos[1]] = true;
        }
        
        int[] posHero = hero.getPos();
        collisionMap[posHero[0]][posHero[1]] = true;
    }

    void moveColMap(int[] pos, int[] newPos){
        collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
        //Il ne faut pas générer un moving sur une entité, sinon elle n'est plus pris en compte après.
        collisionMap[newPos[0]][newPos[1]] = true;
    }
    
    void moveColMap(int[] pos){
    	collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
    }

	public Terrain[][] getTerrainMatrix(){
        return terrainMatrix;
    }

    public boolean[][] getCollisionMap() {
        return collisionMap;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public Hero getHero() {
		return hero;
	}
	
	void addCreature(Creature creature){
        this.creatures.add(creature);
        if(creature instanceof AICreature){
            Thread t = new Thread((AICreature)creature);
            t.start();
        }
    }

    void removeCreature(Creature creature){
        this.creatures.remove(creature);
    }
    
    void addProjectile(Projectile projectile){
        this.projectiles.add(projectile);
        Thread t = new Thread(projectile);
        t.start();
    }

    void removeProjectile(Projectile projectile){
        this.projectiles.remove(projectile);
    }
    

	boolean doesCollide(int[] pos){
        return collisionMap[pos[0]][pos[1]];
    }

	public void damage(Projectile projectile) {
		ArrayList<int[]> aoePos = new ArrayList<int[]>();
		int[] center = projectile.inFront();
		
		for(int i = -projectile.getAoe() + 1; i < projectile.getAoe(); i++){
			for(int j = -projectile.getAoe() + 1; j < projectile.getAoe(); j++){
				int x = center[0] + i;
				int y = center[1] + j;
				int[] pos = new int[] {x,y};
				aoePos.add(pos);
			}
		}
		//System.out.println("size");
		//System.out.println(aoePos.size());
		for(int[] pos : aoePos){
			for(int i = 0; i< creatures.size(); i++){
					if(pos[0] == creatures.get(i).getPos()[0] && pos[1] == creatures.get(i).getPos()[1]){
					System.out.println("touché");
					creatures.get(i).setHP(creatures.get(i).getHP() - projectile.getDamage());
					//TODO effet
				}
			}
		}
	}

}
