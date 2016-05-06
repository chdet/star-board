package Model;

public class Potion extends Item{
	
	private String stat;
	private int quantity;

	public Potion(int[] pos, String stat, int quantity){
		super(pos);
		setStat(stat);
		setQuantity(quantity);
		setSprite(stat);
	}
	
	public String getStat() {
		return stat;
	}
	
	public void setStat(String stat) {
		if(stat == "PotionHP" || stat == "PotionMana"){
			this.stat = stat;
			System.out.println(stat);
		}
		else{
			this.stat = "PotionHP";
		}
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		if (quantity >= 0){
			this.quantity = quantity;
		}
		else{
			this.quantity = 10;
		}
	}
	
	@Override
	public void act(Creature creature) {
		creature.getGame().heal(this, creature);
	}

}
