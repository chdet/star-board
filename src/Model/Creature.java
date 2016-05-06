package Model;

import java.util.ArrayList;
import java.lang.Math;

public abstract class Creature extends Moving{
	boolean alive = true;
	private  Integer HP;
	private Integer HPMax;
	private  Integer mana;
	private Integer manaMax;
	private Float attack;
	private Float defense;
	private int range; //Vision range
	
	private String status = "";
    private long statusBegin = 0;
    private float statusDuration = 0;
    private int DOTStep; // La nombre de fois que des HP ont été retiré par un même DOT
	
	private int currentSpell;
	private ArrayList<String> spellList = new ArrayList<String>();
    
	public Creature(Game game, int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){
		super(game, pos, Game.DOWN);
		setHPMax(HPMax);
		setHP(this.HPMax);
		setManaMax(manaMax);
		setMana(this.manaMax);
		setAttack(attack);
		setDefense(defense);

		spellList.add("Laser");
		setCurrentSpell(0);
	}

	public Creature(int[] pos, Integer HPMax, Integer manaMax, Float attack, Float defense){	//Used to generate dungeons
		super(pos, Game.DOWN);
		setHPMax(HPMax);
		setHP(this.HPMax);
		setManaMax(manaMax);
		setMana(this.manaMax);
		setAttack(attack);
		setDefense(defense);

		spellList.add("Laser");
		setCurrentSpell(0);
	}

	public synchronized void  move(int orient){
		setOrient(orient);
		int[] pos = getPos();
		int[] newPos = inFront();

		if(!game.doesCollide(newPos)){
			this.setPos(newPos);
			game.moveColMap(pos, newPos);
		}
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
			System.out.println("HPMax doit être positif");
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
		//le mana n'est jamais sensé être négatif puisqu'on vérifie avant de lancer un sort.
	}
	
	public Integer getManaMax() {
		return manaMax;
	}
	
	public void setManaMax(Integer manaMax) {
		if(manaMax >= 0){
			this.manaMax = manaMax;
		}
		else{
			System.out.println("manaMax doit être positif");
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
			System.out.println("attack doit être positif");
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
			System.out.println("defense doit être positif et non-nul");
			this.defense = 1f;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getStatusBegin() {
		return statusBegin;
	}

	public void setStatusBegin(long statusBegin) {
		this.statusBegin = statusBegin;
	}

	public float getStatusDuration() {
		return statusDuration;
	}

	public void setStatusDuration(float statusDuration) {
		if (statusDuration >= 0){
			this.statusDuration = statusDuration;
		}
	}
	
	public int getDOTStep() {
		return DOTStep;
	}

	public void setDOTStep(int DOTStep) {
		if(DOTStep >= 0){
			this.DOTStep = DOTStep;
		}
		else{
			System.out.println("Incohérence DOTStep");
		}
	}
	
	public void setCurrentSpell(int currentSpell) {
		if (currentSpell < spellList.size()){
			this.currentSpell = currentSpell;
		}
	}
	
	public ArrayList<String> getSpellList() {
		return spellList;
	}

	public void addToSpellList(String spell) {
		if(spell == "Laser" || spell == "Force" || spell == "Rally" || spell == "Spike" || spell == "Ice"){
			spellList.add(spell);
		}
		else{
			System.out.println("Sort incorrect");
		}
	}
	
	public void die(){
		this.alive = false;
		if(!(this instanceof Hero)){
	    	game.moveColMap(getPos());
	    	game.removeCreature(this);
	    	game.getHero().setExp((int)(game.getHero().getExp() + 25)); //Si on a le temps: des ennemis plus fort donnent plus d'exp
	    	if (game.getHero().getExp() > 100/* * game.getHero().getLevel() */){
	    		System.out.println("Taille AVANT: " + game.getHero().getSpellList().size());
	    		game.getHero().levelUp();
	    		System.out.println("LEVEL UP: " + game.getHero().getLevel());
	    		System.out.println("Taille APRES: " + game.getHero().getSpellList().size());
	    	}
		}
		else{
	    	game.moveColMap(getPos());
	    	game.removeCreature(this);
	    	System.out.println("mort");
			//TODO Retour au menu;
		}	
    }
	
	public void attack(){
		game.damage(this);
	}
	
	public void useSpell(){
		String spell = spellList.get(currentSpell);
		Projectile projectile = new Projectile(game, inFront(), getOrient(), spell);

		switch (spell){
		case "Laser" : 
			projectile.setDamage(/*level * */0);
			projectile.setEffect("DOT");
			projectile.setAoe(1);
			projectile.setManaCost(5);
			break;
			
		case "Force" :
			projectile.setWAIT(0);
			projectile.setDamage(0);
			projectile.setEffect("push");
			projectile.setAoe(2);
			projectile.setManaCost(5);
			break;
			
		case "Rally":
			projectile.setWAIT(0);
			projectile.setDamage(0);
			projectile.setEffect("");
			projectile.setAoe(1);
			projectile.setManaCost(50);
			break;
		
		case "Spike":
			projectile.setDamage(0);
			projectile.setEffect("stun");
			projectile.setAoe(1);
			projectile.setManaCost(5);
			break;
		
		case "Ice":
			projectile.setDamage(0);
			projectile.setEffect("snare");
			projectile.setAoe(2);
			projectile.setManaCost(5);
			break;
			
		}
		
		if(mana >= projectile.getManaCost()){
			mana -= projectile.getManaCost();
			game.addProjectile(projectile);
		}
		else{
			projectile = null;
		}
	}

	public double distanceTo(int[] pos){
		return(Math.sqrt((this.getPos()[0]-pos[0])*(this.getPos()[0]-pos[0])+(this.getPos()[1]-pos[1])*(this.getPos()[1]-pos[1])));
	}
	
}
