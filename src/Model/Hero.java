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

	public void setLevel(int level) {
		if(level >=1){
			this.level = level;
		}
	}
	
	public abstract void levelUp();

	public int getExp() {
		return exp;
	}
	
	public void setExp(int exp){
		if(exp >= 0){
			this.exp = exp;
		}
	}
	
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	
	public void storeInventory(int x) {
		if (x == -1){ 
			ArrayList<Item> newInventory = (ArrayList<Item>) inventory.clone();
			for(int i = 0; i < inventory.size() - 1; i++){
				newInventory.set(i + 1, inventory.get(i));
			}
			newInventory.set(0, inventory.get(inventory.size() - 1));
			inventory = newInventory;
		}
		else if (x == 1){
			ArrayList<Item> newInventory = (ArrayList<Item>) inventory.clone();
			for(int i = inventory.size() - 1; i > 0; i--){
				newInventory.set(i - 1, inventory.get(i));
			}
			newInventory.set(inventory.size() - 1 , inventory.get(0));
			inventory = newInventory;
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
		}
		
		catch(IndexOutOfBoundsException e){
			System.out.println("L'inventaire est vide");
		}
	}

}
