package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Status implements Runnable, Serializable{
	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

	private ArrayList<Creature> creatures;
	
	public Status(ArrayList<Creature> creatures){
		this.creatures = creatures;
		Thread t = new Thread(this);
        t.start();
	}
	
	public ArrayList<Creature> getCreatures() {
		return creatures;
	}
	
	private void checkStatus() { //TODO Si on veut que le hero subisse aussi des statuts faut que ce soit un thread (on implement Runnable dans Moving et y met tous les getWAIT,... et on rtire les cast)
		for(int i = 0; i < creatures.size(); i++){
			if(creatures.get(i).getStatus() != ""){
				if(creatures.get(i).getStatusDuration() + 50 >= System.currentTimeMillis() - creatures.get(i).getStatusBegin()){
                    //for, attaquant
					switch(creatures.get(i).getStatus()){
					case "DOT":
						float x = 3f; //nombre de fois qu'on va subir des d�g�ts
						if(creatures.get(i).getDOTStep() < x){
							if((System.currentTimeMillis() - creatures.get(i).getStatusBegin())/creatures.get(i).getStatusDuration() >= (creatures.get(i).getDOTStep() + 1)/x){
								creatures.get(i).setDOTStep(creatures.get(i).getDOTStep() + 1);
								creatures.get(i).setHP((int) (creatures.get(i).getHP() - creatures.get(i).getDOTDamage()/creatures.get(i).getDefense()));
								try{
									System.out.println("HP: " + creatures.get(i).getHP());
									System.out.println("STEP: " + creatures.get(i).getDOTStep());
									System.out.println("Ecoul�: " + ( System.currentTimeMillis() - creatures.get(i).getStatusBegin() ));
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
                        if(creatures.get(i) instanceof AICreature){
                            ((AICreature) creatures.get(i)).setWAIT(((AICreature) creatures.get(i)).getWAITMin());
                            ((AICreature) creatures.get(i)).setHostility(AICreature.HOSTILE);
                            System.out.println("test");

                        }
						creatures.get(i).setDOTStep(0);
                        creatures.get(i).setDOTDamage(0);
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
		while(getCreatures().size() > 0){
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
