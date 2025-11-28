package ca.sheridancollege.dossanic.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Maps the Java class to MongoDB collection named 'playerStates'
@Document(collection = "playerStates")
public class PlayerState {
	@Id
	private String id;
	
	// --Character ID and Archetype--
	private String name;
	private String archetype;		// e.g., "Gutter Runner", "Work Junkie", "One of Those"
	private String locationId;		// The ID of the current game location (e.g., "CHECKPOINT DMV")
	
	// --Game Stats--
	private int health;
	private int maxHealth;
	private int grit;				// Combat, HP, Strength
	private int streetSmarts;		// Navigation, finding unique interactions
	private int technicalSkill;		// Hacking, techie skills
	private int armorClass;			// Difficulty to hit in combat
	private int exp;// Experience points
	private int level;				// Player level based on EXP
	private int nextLevelExp;		// EXP needed for next level
	
	// --Economy--
	private BigInteger debtAmount;	// Main progress tracker (e.g., 200539846 credits)
	private BigInteger ndraBalance;	// Personal Bank (Non debt repayment account)
	
	// --Inventory and Game History--
	private List<String> inventory = new ArrayList<>(); // Items being carried
	private List<String> history = new ArrayList<>();	// Story log displayed to user
	private Weapon equippedWeapon;		// Currently equipped weapon
	private List<ActiveQuest> activeContracts = new ArrayList<>();
    private List<String> completedContractIds = new ArrayList<>();
	
	// --Combat and Game State Flags-- 
	private boolean isDead = false;
	private Object currentEnemy;
	
	//--Temporary Buffs and Effects--
	
	private int acidStacks = 0;
	private int attackDebuff= 0;
	
	//-- Combat methods --
	
	public String takeDamage(int damage) 
	{
		health -= damage;
		if (health <= 0) 
		{
			health = 0;
			isDead = true;
			return name + " has been defeated!";
		}
		
		else 
		{
			return name + " is still standing!";
		}
	}
	
	public void levelUp() 
	{
		level++;
		nextLevelExp = (int)(nextLevelExp * 1.5);
		maxHealth += 10;
		health = maxHealth;
		grit += 2;
		streetSmarts += 2;
		technicalSkill += 2;
		armorClass += 1;
	}
	
	public void resetBattleStats() {
	    this.acidStacks = 0;
	    this.attackDebuff = 0;
	}
	
	public int getGritModifier() {
	    return (this.grit - 10) / 2;
	}

	// Used by CombatService to determine turn order
	public int getInitiativeBonus() {
	    // Street Smarts represents reflexes/awareness
	    return (this.streetSmarts - 10) / 2;
	}
	
	public void earnCredits(int amount) {
	    if (this.ndraBalance == null) {
	        this.ndraBalance = BigInteger.ZERO;
	    }
	    this.ndraBalance = this.ndraBalance.add(BigInteger.valueOf(amount));
	}

	public boolean spendCredits(int amount) {
	    BigInteger cost = BigInteger.valueOf(amount);
	    if (this.ndraBalance.compareTo(cost) >= 0) {
	        this.ndraBalance = this.ndraBalance.subtract(cost);
	        return true; // Success
	    }
	    return false; // Not enough cash
	}
	
}
