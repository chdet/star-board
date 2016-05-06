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

	int getDamage() {
		return damage;
	}

	private void setDamage(int damage) {
		this.damage = damage;
	}

	String getEffect() {
		return effect;
	}
	
	private void setEffect(String effect) {
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
