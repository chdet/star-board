package Model;

import java.util.ArrayList;

public class Game{
    private Hero hero;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private boolean[][] collisionMap;

	//Dungeon
	private Dungeon dungeon;
	private ArrayList<Creature> creatures;
    private Terrain[][] terrainMatrix;

    private Status status;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public Game(String heroClass, int roomNumber){
		dungeon = DungeonGeneration.generateRandomDungeon(roomNumber);
        this.terrainMatrix = dungeon.getTerrainMatrix();
        this.collisionMap = new boolean[terrainMatrix.length][terrainMatrix[0].length];

        switch(heroClass){
        case "New Game: Jedi":
        	this.hero = new Jedi(this, dungeon.getStartPoint());
        	break;
        case "New Game: Sith":
        	this.hero = new Sith(this, dungeon.getStartPoint());
        	break;
        }

		this.creatures = dungeon.getCreatures();
		this.addCreature(hero);

		status = new Status(this.creatures);

		updateColMap();
        startAI();
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
    }

	public void changeDungeon(Dungeon dungeon){
		stopAI();
		this.dungeon = dungeon;
		this.removeCreature(this.hero);
		this.terrainMatrix = dungeon.getTerrainMatrix();
		this.collisionMap = new boolean[terrainMatrix.length][terrainMatrix[0].length];
		this.hero.setPos(dungeon.getStartPoint());
		this.creatures = dungeon.getCreatures(this);
		this.addCreature(hero);
		updateColMap();
		startAI();

	}

    void moveColMap(int[] pos, int[] newPos){
        collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
        //Il ne faut pas générer un moving sur une entité, sinon elle n'est plus pris en compte après.
        collisionMap[newPos[0]][newPos[1]] = true;
    }
    
    void moveColMap(int[] pos){
    	collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
    }

	public Dungeon getDungeon() {
		return dungeon;
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

	private void startAI(){
		for(Creature creature : this.creatures){
			if(creature instanceof AICreature){
				((AICreature) creature).setActive(true);
				Thread t = new Thread((AICreature)creature);
				t.start();
			}
		}
	}

	private void stopAI(){
		for(Creature creature : this.creatures){
			if(creature instanceof AICreature){
				((AICreature) creature).setActive(false);
			}
		}
	}

	void addCreature(Creature creature){
        this.creatures.add(creature);
        /*if(creature instanceof AICreature){	// On en a plus besoin vu que c'est fait dans startAI?
            Thread t = new Thread((AICreature)creature);
            t.start();
        }*/
    }

    void removeCreature(Creature creature){
        this.creatures.remove(creature);
		if(this.creatures.size() == 1 && this.creatures.contains(this.hero)){
			changeDungeon(DungeonGeneration.generateRandomDungeon(5));
		}
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

	int[] closestEnemy(AICreature aiCreature){
		/*	Return the position of the closest enemy to the AICreature, if there is none,
		*  returns the creatures' own position */
		int[] pos = aiCreature.getPos();
		int range = 10; //TODO: AJOUTER RANGE DANS LE CONSTRUCTEUR DE CREATURE
		int behavior= aiCreature.getHostility();
		int[] enemyPos;
		if(behavior == AICreature.HOSTILE && getHero().distanceTo(pos) < range) {
			enemyPos = new int[]{getHero().getPos()[0],getHero().getPos()[1]};
			return  enemyPos;
		}
		else if(behavior == AICreature.FRIENDLY) {
			double[] temp;    //Holds the position of the current ennemy and its distance.
			double[] tempClosest = null; //Holds the position of the current closest ennemy and its distance.
			try {
				for (Creature creature : this.creatures) {
					temp = new double[]{creature.getPos()[0], creature.getPos()[1], creature.distanceTo(pos)};
					if (temp[2] < range) {    //temp[2] is the distance to pos
						if (tempClosest == null) {
							tempClosest = temp;
						} else if (temp[2] < tempClosest[2]) {
							tempClosest = temp;
						}
					}
				}
				enemyPos = new int[]{(int) tempClosest[0], (int) tempClosest[1]};
				return enemyPos;
			} catch (NullPointerException e) {    //In case there are no enemies close and tempClosest is null
				return pos;
			}
		}
		else{
			return pos;		//In this case no movement is made
		}
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
		
		for(int[] pos : aoePos){
			for(int i = 0; i< creatures.size(); i++){
					if(pos[0] == creatures.get(i).getPos()[0] && pos[1] == creatures.get(i).getPos()[1]){
					System.out.println("touché par un projectile");
					if(creatures.get(i) instanceof AICreature){
						((AICreature)(creatures.get(i))).setHostility(AICreature.HOSTILE);
					}
					if (creatures.get(i).getStatus() == ""){
						switch(projectile.getEffect()){
						case "push" :
							for(int j = 0; j < 5; j++){
								creatures.get(i).move(projectile.getOrient());
							}
							break;

						case "rally" :
							if(creatures.get(i) instanceof AICreature){
								((AICreature)(creatures.get(i))).setHostility(AICreature.FRIENDLY);
							}
							break;

						case "stun" :
							creatures.get(i).setStatus("stun");
							creatures.get(i).setStatusBegin(System.currentTimeMillis());
							creatures.get(i).setStatusDuration(5000f);
							break;

						case "snare" :
							creatures.get(i).setStatus("snare");
							creatures.get(i).setStatusBegin(System.currentTimeMillis());
							creatures.get(i).setStatusDuration(5000f);
							break;

						case "DOT" :
							creatures.get(i).setStatus("DOT");
							creatures.get(i).setStatusBegin(System.currentTimeMillis());
							creatures.get(i).setStatusDuration(5000f);
							break;

						}
					}
					creatures.get(i).setHP((int)(creatures.get(i).getHP() - projectile.getDamage()/creatures.get(i).getDefense()));
				}
			}
		}
	}
	
	public void damage(Creature attacker){
		for(int i = 0; i< creatures.size(); i++){
			if(attacker.inFront()[0] == creatures.get(i).getPos()[0] && attacker.inFront()[1] == creatures.get(i).getPos()[1]){
				System.out.println("touché au corps à corps");
				creatures.get(i).setHP((int)(creatures.get(i).getHP() - attacker.getAttack()/creatures.get(i).getDefense()));
			}
		}
	}

}
