package Model;

import java.util.ArrayList;

public abstract class Creature extends Moving{
	boolean alive = true;
	private  Integer HP;
	private Integer HPMax;
	private  Integer mana;
	private Integer manaMax;
	private Float attack;
	private Float defense;
	
	private int currentSpell;
	protected ArrayList<String> spellList = new ArrayList<String>();
    
	public Creature(Game game, int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(game, pos, Game.DOWN);
		setHPMax(HPMax);
		setHP(this.HPMax);
		setManaMax(manaMax);
		setMana(this.manaMax);
		setAttack(attack);
		setDefense(defense);
		
		spellList.add("fireBall");
		setCurrentSpell(0);
	}

	
    public  Integer getHP() {
		return HP;
	}
	
    void setHP(Integer HP){
		if(HP <= 0){
			die();
		}
		else if(HP >HPMax){
			this.HP = HPMax;
		}
		else{
			this.HP = HP;
		}
	}
    
    public Integer getHPMax() {
		return HPMax;
	}

	public void setHPMax(Integer HPMax) {
		if(HPMax > 0){
			this.HPMax = HPMax;
		}
		else{
			System.out.println("HPMax doit �tre positif");
			this.HPMax = 0;
		}
	}
	
	public Integer getMana() {
		return mana;
	}
	
	public void setMana(Integer mana) {
		if(mana > manaMax){
			this.mana = manaMax;
		}
		else{
			this.mana = mana;
		}
		//le mana n'est jamais sens� �tre n�gatif puisqu'on v�rifie avant de lancer un sort.
	}
	
	public Integer getManaMax() {
		return manaMax;
	}
	
	public void setManaMax(Integer manaMax) {
		if(manaMax >= 0){
			this.manaMax = manaMax;
		}
		else{
			System.out.println("manaMax doit �tre positif");
			this.manaMax = 0;
		}
	}
	
	public Float getAttack() {
		return attack;
	}


	public void setAttack(Float attack) {
		if(attack >= 0){
			this.attack = attack;
		}
		else{
			System.out.println("attack doit �tre positif");
			this.attack = 0f;
		}
	}


	public Float getDefense() {
		return defense;
	}


	public void setDefense(Float defense) {
		if(defense > 0){
			this.defense = defense;
		}
		else{
			System.out.println("defense doit �tre positif et non-nul");
			this.defense = 1f;
		}
	}
	
	public void setCurrentSpell(int currentSpell) {
		if (currentSpell < spellList.size()){
			this.currentSpell = currentSpell;
		}
	}
		
	public void die(){
		if(!(this instanceof Hero)){
			this.alive = false;
	    	game.moveColMap(getPos());
	    	game.removeCreature(this);
		}
		else{
			this.alive = false;
	    	game.moveColMap(getPos());
	    	game.removeCreature(this);
			//TODO Retour au menu;
		}	
    }
	
	public void attack(){
		game.damage(this);
	}
	
	public void useSpell(){
		String spell = spellList.get(currentSpell);
		switch (spell){
			case "fireBall" : fireBall(); break;
			case "force" : force(); break;
		}		
	}
	
	private void fireBall(){
		FireBall fire = new FireBall(game, getPos(), getOrient());
		if(mana >= fire.getManaCost()){
			mana -= fire.getManaCost();
			game.addProjectile(fire);
		}
		else{
			fire = null;
		}
	}
	
	private void force(){
		//TODO
	}
	
}
