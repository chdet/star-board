package Model;

public class FireBall extends Projectile{

	public FireBall(Game game, int[] pos, int orient/*, level*/) {
		super(game, pos, orient);
		this.damage = /*level * */15;
		this.effect = "";
		pos = new int[] {2,2};
	}
	public void setAoe(){
		this.aoe.add(inFront());
	}
}
