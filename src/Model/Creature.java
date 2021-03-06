package Model;

import java.util.ArrayList;
import java.lang.Math;

public abstract class Creature extends Moving{
	private boolean alive = true;
	private  Integer HP;
	private Integer HPMax;
	private  Integer mana;
	private Integer manaMax;
	private Float attack;
	private Float defense;

	private String status = "";
    private long statusBegin = 0;
    private float statusDuration = 0;
    private int DOTStep; // La nombre de fois que des HP ont �t� retir� par un m�me DOT
	private int DOTDamage;
	
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

		if(!getGame().doesCollide(newPos)){
			this.setPos(newPos);
			getGame().moveColMap(pos, newPos);
			for(int i = 0; i< getGame().getItems().size(); i++){
            	if (getGame().getItems().get(i).getPos()[0] == newPos[0] && getGame().getItems().get(i).getPos()[1] == newPos[1]){
            		walkOn(getGame().getItems().get(i));
            	}
            }
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

	private void setHPMax(Integer HPMax) {
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
	
	void setMana(Integer mana) {
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
	
	private void setManaMax(Integer manaMax) {
		if(manaMax >= 0){
			this.manaMax = manaMax;
		}
		else{
			System.out.println("manaMax doit �tre positif");
			this.manaMax = 0;
		}
	}
	
	Float getAttack() {
		return attack;
	}

	private void setAttack(Float attack) {
		if(attack >= 0){
			this.attack = attack;
		}
		else{
			System.out.println("attack doit �tre positif");
			this.attack = 0f;
		}
	}

	Float getDefense() {
		return defense;
	}

	private void setDefense(Float defense) {
		if(defense > 0){
			this.defense = defense;
		}
		else{
			System.out.println("defense doit �tre positif et non-nul");
			this.defense = 1f;
		}
	}

	String getStatus() {
		return status;
	}

	void setStatus(String status) {
		this.status = status;
	}

	long getStatusBegin() {
		return statusBegin;
	}

	void setStatusBegin(long statusBegin) {
		this.statusBegin = statusBegin;
	}

	float getStatusDuration() {
		return statusDuration;
	}

	void setStatusDuration(float statusDuration) {
		if (statusDuration >= 0){
			this.statusDuration = statusDuration;
		}
	}

	int getDOTDamage() {
		return DOTDamage;
	}

	void setDOTDamage(int DOTDamage) {
		this.DOTDamage = DOTDamage;
	}

	int getDOTStep() {
		return DOTStep;
	}

	void setDOTStep(int DOTStep) {
		if(DOTStep >= 0){
			this.DOTStep = DOTStep;
		}
		else{
			System.out.println("Incoh�rence DOTStep");
		}
	}

    public int getCurrentSpell() {
        return currentSpell;
    }

    public void setCurrentSpell(int currentSpell) {
        if (currentSpell < spellList.size()){
            this.currentSpell = currentSpell;
		}
	}
	
	public ArrayList<String> getSpellList() {
		return spellList;
	}

	void addToSpellList(String spell) {
		if(spell.equals("Laser" )|| spell.equals("Force") || spell.equals("Rally") || spell.equals("Spike") || spell.equals("Ice")){
            System.out.println("Sort ajouté");
            spellList.add(spell);
		}
		else{
			System.out.println("Sort incorrect");
		}
	}
	
	public boolean isAlive() {
		return alive;
	}

	private void die(){
		this.alive = false;
		if(!(this instanceof Hero)){
	    	getGame().getHero().setExp((int)(getGame().getHero().getExp() + 25)); //Si on a le temps: des ennemis plus fort donnent plus d'exp
	    	drop();
	    	if (getGame().getHero().getExp() > 100/* * game.getHero().getLevel() */){
	    		System.out.println("Taille AVANT: " + getGame().getHero().getSpellList().size());
	    		getGame().getHero().levelUp();
	    		System.out.println("LEVEL UP: " + getGame().getHero().getLevel());
	    		System.out.println("Taille APRES: " + getGame().getHero().getSpellList().size());
	    	}
		}
		getGame().moveColMap(getPos());
		getGame().removeCreature(this);
    }

	private void drop() {
		System.out.println("DROP");
		double x = Math.random();
		if (x < 0.25){
			this.getGame().addItem(new Potion(this.getPos(), "PotionHP", 50));
		}
		else if (0.25 < x && x < 0.5){
			this.getGame().addItem(new Potion(this.getPos(), "PotionMana", 50));
		}
	}

	public void attack(){
		getGame().damage(this);
	}
	
	public void useSpell(){
		String spell = spellList.get(currentSpell);
		Projectile projectile = new Projectile(getGame(), inFront(), getOrient(), spell);
		System.out.println("Sort");
		switch (spell){
		case "Laser" : 
			projectile.setDamage(2);
			projectile.setEffect("DOT");
			projectile.setAoe(1);
			projectile.setManaCost(5);
			break;
			
		case "Force" :
			projectile.setWAIT(0);
			projectile.setDamage(0);
			projectile.setEffect("push");
			projectile.setAoe(4);
			projectile.setManaCost(5);
			break;
			
		case "Rally":
			projectile.setDamage(0);
			projectile.setEffect("rally");
			projectile.setAoe(1);
			projectile.setManaCost(50);
			break;
		
		case "Spike":
			projectile.setDamage(0);
			projectile.setEffect("slow");
			projectile.setAoe(1);
			projectile.setManaCost(5);
			break;
		
		case "Ice":
			projectile.setDamage(0);
			projectile.setEffect("stun");
			projectile.setAoe(2);
			projectile.setManaCost(5);
			break;
			
		}
		
		if(mana >= projectile.getManaCost()){
			mana -= projectile.getManaCost();
			getGame().addProjectile(projectile);
		}
		else{
			projectile = null;
		}
	}

	public abstract void walkOn(Item item);

	public double distanceTo(int[] pos){
		return(Math.sqrt((this.getPos()[0]-pos[0])*(this.getPos()[0]-pos[0])+(this.getPos()[1]-pos[1])*(this.getPos()[1]-pos[1])));
	}
	
}
