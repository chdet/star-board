package Model;

import java.util.ArrayList;

public abstract class Hero extends Creature {
	
	private int level = 1;
	private int exp = 0;
	private ArrayList<Item> inventory = new ArrayList<>();
	private int currentItemIndex = 0;
	
	public Hero(Game game,int[] pos, int hpMax, int manaMax, float attack, float defense){
		super(game,pos, hpMax, manaMax, attack, defense);
		setCurrentSpell(0);
	}

	public int getLevel() {
		return level;
	}

	void setLevel(int level) {
		if(level >=1){
			this.level = level;
		}
	}
	
	public abstract void levelUp();

	int getExp() {
		return exp;
	}
	
	void setExp(int exp){
		if(exp >= 0){
			this.exp = exp;
		}
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public int getCurrentItemIndex() {
		return currentItemIndex;
	}

	public void setCurrentItemIndex(int currentItemIndex) {
		if(currentItemIndex >= 0){
			if(currentItemIndex < inventory.size()){
				this.currentItemIndex = currentItemIndex;
			}else if(currentItemIndex >= inventory.size()){
				this.currentItemIndex = 0;
			}
		}else if(currentItemIndex < 0){
			this.currentItemIndex = inventory.size()+currentItemIndex;
		}

	}


	public void walkOn(Item item){
		if(item instanceof Trap){
			item.act(this);
		}
		else{
			if (inventory .size() < 5){
				inventory.add(item);
			}
		}
		getGame().removeItem(item);
	}
	
	public void useItem(){
		try{
			inventory.get(currentItemIndex).act(this);
			inventory.remove(currentItemIndex);
			setCurrentItemIndex(currentItemIndex+1);
		}
		
		catch(IndexOutOfBoundsException e){
			System.out.println("L'inventaire est vide");
		}
	}

}
