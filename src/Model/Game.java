package Model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable{
    private Hero hero;
	private transient ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Item> items = new ArrayList<>();
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

	public Game(String heroClass, int roomCount){
		dungeon = DungeonGeneration.generateRandomDungeon(roomCount);
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

		this.creatures = dungeon.getCreatures(this);
		this.addCreature(hero);

		int[] pos = {dungeon.getStartPoint()[0], dungeon.getStartPoint()[1] - 2};
		this.addItem(new Trap(pos, 1, "snare"));

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
		status.setCreatures(this.creatures);
		this.items.clear();
		updateColMap();
		startAI();
	}

    void moveColMap(int[] pos, int[] newPos){
        collisionMap[pos[0]][pos[1]] = terrainMatrix[pos[0]][pos[1]].getCollision();
        //Il ne faut pas g�n�rer un moving sur une entit�, sinon elle n'est plus pris en compte apr�s.
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

    public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public Hero getHero() {
		return hero;
	}

    public Status getStatus() {
		return status;
	}

	public void startAI(){
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
    }

    void removeCreature(Creature creature){
        this.creatures.remove(creature);
		if(this.creatures.size() == 1 && this.creatures.contains(this.hero)){
			changeDungeon(DungeonGeneration.generateRandomDungeon(dungeon.getRoomCount()+1));
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
    
    public ArrayList<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void removeItem(Item item) {
		this.items.remove(item);
	}

	boolean doesCollide(int[] pos){
		try{
			return collisionMap[pos[0]][pos[1]];
		}catch(ArrayIndexOutOfBoundsException e){
			return true;	//Any tile outside the map is off-limits
		}
    }

	Creature closestEnemy(AICreature aiCreature){
		/*	Return the closest enemy to the AICreature, if there is none,
		*  returns null */
		int[] pos = aiCreature.getPos();
		int range = 10; //TODO: AJOUTER RANGE DANS LE CONSTRUCTEUR DE CREATURE
		int behavior= aiCreature.getHostility();
		if(behavior == AICreature.HOSTILE && getHero().distanceTo(pos) < range) {
			return  hero;
		}
		else if(behavior == AICreature.FRIENDLY) {
			double temp = -1;    //Holds the distance of the current closest ennemy and its distance.
			Creature closestCreature = null;
			for (Creature creature : this.creatures) {
				double distance = creature.distanceTo(pos);
				if (distance < range) {    //temp is the distance to pos
					if (temp == -1 || distance < temp) {
						temp = distance;
						closestCreature = creature;
					}
				}
			}
			return closestCreature;
		}
		else{
			return null;
		}
	}

	public void damage(Projectile projectile) {
		ArrayList<int[]> aoePos = new ArrayList<int[]>();
		int[] center = projectile.getPos();
		
		for(int i = -projectile.getAoe() + 1; i < projectile.getAoe(); i++){
			for(int j = -projectile.getAoe() + 1; j < projectile.getAoe(); j++){
				int x = center[0] + i;
				int y = center[1] + j;
				int[] pos = new int[] {x,y};

				if( !(x == hero.getPos()[0] && y == hero.getPos()[1])){
					aoePos.add(pos);
				}
			}
		}
		
		for(int[] pos : aoePos){
			for(int i = 0; i< creatures.size(); i++){
					if(pos[0] == creatures.get(i).getPos()[0] && pos[1] == creatures.get(i).getPos()[1]){
					System.out.println("touch� par un projectile");
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
	
	public void damage(Trap trap, Creature creature) {
		if(trap.getPos()[0] == creature.getPos()[0] && trap.getPos()[1] == creature.getPos()[1]){
			System.out.println("pris dans un pi�ge");
			if (creature.getStatus() == ""){
				switch(trap.getEffect()){						
				case "stun" : 
					creature.setStatus("stun");
					creature.setStatusBegin(System.currentTimeMillis());
					creature.setStatusDuration(5000f);
					break;
				
				case "snare" :
					creature.setStatus("snare");
					creature.setStatusBegin(System.currentTimeMillis());
					creature.setStatusDuration(5000f);
					break;
				
				case "DOT" :
					creature.setStatus("DOT");
					creature.setStatusBegin(System.currentTimeMillis());
					creature.setStatusDuration(5000f);
					break;
					
				}
			}
			creature.setHP((int)(creature.getHP() - trap.getDamage()/creature.getDefense()));
		}
	}
	
	public void damage(Creature attacker){
		for(int i = 0; i< creatures.size(); i++){
			if(attacker.inFront()[0] == creatures.get(i).getPos()[0] && attacker.inFront()[1] == creatures.get(i).getPos()[1]){
				System.out.println("touch� au corps � corps");
				creatures.get(i).setHP((int)(creatures.get(i).getHP() - attacker.getAttack()/creatures.get(i).getDefense()));
			}
		}
	}
	
	public void heal(Potion potion, Creature creature){
		switch(potion.getStat()){
		case "PotionHP": creature.setHP(creature.getHP() + potion.getQuantity()); break;
		case "PotionMana": creature.setMana(creature.getMana() + potion.getQuantity()); break;
		}
		
	}

	public static void save(String filename, Game game) {
		FileOutputStream file;
		ObjectOutputStream o;

		try {
			file = new FileOutputStream(filename);
			o = new ObjectOutputStream(file);
			o.writeObject(game);
			o.close();
			System.out.println("sauv�");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
