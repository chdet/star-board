package Model;

public class Trap extends Item{
	private int damage;
	private String effect;

	public Trap(int[] pos, int damage, String effect){
		super(pos);
		setDamage(damage);
		setEffect(effect);
		setSprite("Trap");
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getEffect() {
		return effect;
	}
	
	public void setEffect(String effect) {
		if(effect.equals("stun" )|| effect.equals("snare") || effect.equals("DOT")){
			this.effect = effect;
		}
		else{
			this.effect = "stun";
		}
	}
	
	@Override
	public void act(Creature creature) {
		creature.getGame().damage(this, creature);
	}

}
