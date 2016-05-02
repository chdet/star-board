package Model;

import java.util.ArrayList;

public class Game implements Runnable{
    private Hero hero;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private boolean[][] collisionMap;

	//Dungeon
	private ArrayList<Creature> creatures;
    private Terrain[][] terrainMatrix;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	public Game(Dungeon dungeon){
        this.terrainMatrix = dungeon.getTerrainMatrix();
        this.collisionMap = new boolean[terrainMatrix.length][terrainMatrix[0].length];
		this.hero = new Hero(this, 15, 150000, 5f, 1500f);
		this.hero.setPos(dungeon.getStartPoint());
		this.creatures = dungeon.getCreatures();
		int[] pos = {dungeon.getStartPoint()[0], dungeon.getStartPoint()[1] +4};
        this.addCreature(hero);
        this.addCreature(new Ennemy(this, pos, 15, 0,1f, 1f));
        
        //this.addCreature(new Ennemy(this, new int[]{1,1}, 15, 15,1f, 1500f));
		//this.addCreature(new Ennemy(this, new int[]{2,1}, 15, 0,1f, 1f));
		//this.addCreature(new Ennemy(this, new int[]{3,1}, 15, 0,1f, 1f));
        startAI();
        updateColMap();
        
        Thread t = new Thread(this);
        t.start();
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

	private void startAI(){
		for(Creature creature : this.creatures){
			if(creature instanceof AICreature){
				Thread t = new Thread((AICreature)creature);
				t.start();
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
		
		for(int[] pos : aoePos){
			for(int i = 0; i< creatures.size(); i++){
					if(pos[0] == creatures.get(i).getPos()[0] && pos[1] == creatures.get(i).getPos()[1]){
					System.out.println("touché par un projectile");
					if(creatures.get(i) instanceof AICreature){
						((AICreature)(creatures.get(i))).setHostility(AICreature.HOSTILE);
					}
					creatures.get(i).setHP((int)(creatures.get(i).getHP() - projectile.getDamage()/creatures.get(i).getDefense()));
					
										
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
						creatures.get(i).setStatusDuration(20000f);
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
	
	private void checkStatus() { //TODO Si on veut que le hero subisse aussi des statuts faut que ce soit un thread (on implement Runnable dans Moving et y met tous les getWAIT,... et on rtire les cast)
		for(int i = 1 /*0*/; i < creatures.size(); i++){
			if(creatures.get(i).getStatus() != ""){
				if(creatures.get(i).getStatusDuration() + 50 >= System.currentTimeMillis() - creatures.get(i).getStatusBegin()){
					switch(creatures.get(i).getStatus()){
					case "stun": 
						((AICreature) creatures.get(i)).setWAIT((int) (((AICreature) creatures.get(i)).getWAIT()*1.05));
						break;
						
					case "snare": 
						((AICreature) creatures.get(i)).setWAIT((int)(creatures.get(i).getStatusDuration()));
						break;
					
					case "DOT":
						float x = 3f; //nombre de fois qu'on va subir des dégâts
						if(creatures.get(i).getDOTStep() < x){
							if((System.currentTimeMillis() - creatures.get(i).getStatusBegin())/creatures.get(i).getStatusDuration() >= (creatures.get(i).getDOTStep() + 1)/x){
								creatures.get(i).setDOTStep(creatures.get(i).getDOTStep() + 1);
								creatures.get(i).setHP((int) (creatures.get(i).getHP() - 0.2*creatures.get(i).getHPMax()));
								try{
									System.out.println("HP: " + creatures.get(i).getHP());
									System.out.println("STEP: " + creatures.get(i).getDOTStep());
									System.out.println("Ecoulé: " + ( System.currentTimeMillis() - creatures.get(i).getStatusBegin() ));
									System.out.println("Portion: " + ( System.currentTimeMillis() - creatures.get(i).getStatusBegin() )/creatures.get(i).getStatusDuration());
									System.out.println("Duration: " + creatures.get(i).getStatusDuration());
								}
								catch(Exception e){
									System.out.println("L'ennemi est surement deja mort");
									e.printStackTrace();  
								}
								
							}
						}
						/*
						if(creatures.get(i).getStatusDuration()/3 >= (System.currentTimeMillis() - creatures.get(i).getStatusBegin())%3){
							creatures.get(i).setLastDOT(System.currentTimeMillis());
							creatures.get(i).setHP((int) (creatures.get(i).getHP() - 0.05*creatures.get(i).getHPMax()));
							//System.out.println(creatures.get(i).getHP());
						}*/
						
						break;
					}
				}
				else{
					try{
						System.out.println("FIN: " + ( System.currentTimeMillis() - creatures.get(i).getStatusBegin() ));
						System.out.println("HP: " + creatures.get(i).getHP());
						creatures.get(i).setStatus("");
						creatures.get(i).setStatusBegin(0);
						creatures.get(i).setStatusDuration(0);
						((AICreature) creatures.get(i)).setWAIT(((AICreature) creatures.get(i)).getWAITMin());
						creatures.get(i).setDOTStep(0);
						System.out.println("Fin d'effet");
					}
					catch(Exception e){
						System.out.println("L'ennemi est surement deja mort");
						e.printStackTrace();  
					}
					
				}
			}
		}
	}

	@Override
	public void run() {
		while(getHero().alive){
			try {
				checkStatus();
				Thread.sleep(50);
			} catch (InterruptedException e) {
				System.out.println("ERREUR STATUS");
				e.printStackTrace();
			}
		}
	}

}
