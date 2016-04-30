package Model;

public class FireBall extends Projectile{

	public FireBall(Game game, int[] pos, int orient/*, level*/) {
		super(game, pos, orient);
		setDamage(/*level * */5);
		this.effect = "";
		setAoe(2);
		setManaCost(5);
	}
	/*public void setAoe(){
		this.aoe.add(inFront());
	}*/
}
