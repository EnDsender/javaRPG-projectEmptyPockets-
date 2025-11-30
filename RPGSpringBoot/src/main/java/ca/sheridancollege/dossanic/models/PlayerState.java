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
// Maps to the 'playerStates' collection in MongoDB
@Document(collection = "playerStates")
public class PlayerState {

    @Id
    private String id;
    
    // -- IDENTITY --
    private String name;
    private String archetype;  // e.g., "Gutter Runner"
    private String locationId; // e.g., "sector_4_sewers"
    
    // -- GAME STATS --
    private int health = 100;
    private int maxHealth = 100;
    
    private int grit;            // Combat Strength / HP
    private int streetSmarts;    // Initiative / Looting
    private int technicalSkill;  // Hacking / Repair
    private int armorClass;      // Evasion
    
    private int exp;             
    private int maxExp = 1000;   
    private int level = 1;       
    
    // -- ECONOMY (Indet Simulator) --
    // Using BigInteger because debt can exceed 2 Billion
    private BigInteger debtAmount;  // The Principal Debt
    private BigInteger ndraBalance; // The Wallet (Non-Debt Repayment Account)
    
    // -- INVENTORY & LOGS --
    // We use 'Item' so we can store both Weapons and Loot objects
    private List<Item> inventory = new ArrayList<>(); 
    
    // The Story Log (Battle results, Level ups)
    private List<String> history = new ArrayList<>(); 
    
    // Currently held weapon (Used by CombatService)
    private Weapon equippedWeapon;       
    
    // -- QUEST TRACKING --
    private List<ActiveQuest> activeContracts = new ArrayList<>();
    private List<String> completedContractIds = new ArrayList<>();
    
    // -- COMBAT FLAGS -- 
    private boolean isDead = false;
    
    // -- TEMPORARY BATTLE BUFFS --
    // These reset after every fight
    private int acidStacks = 0;
    private int attackDebuff = 0;
    
    // ==========================================
    // HELPER METHODS (The Brains)
    // ==========================================
    
    // 1. STAT MODIFIERS
    // Logic: (Score - 10) / 2. Example: 14 Grit = +2 Bonus.
    public int getGritModifier() {
        return (this.grit - 10) / 2;
    }
    
    public int getInitiativeBonus() {
        return (this.streetSmarts - 10) / 2;
    }

    // 2. BATTLE CLEANUP
    // Called by CombatService when an enemy dies
    public void resetBattleStats() {
        this.acidStacks = 0;
        this.attackDebuff = 0;
    }

    // 3. COMBAT & HEALTH
    public String takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            isDead = true;
            return name + " has been flatlined!";
        }
        return name + " took " + damage + " damage.";
    }

    // 4. ECONOMY HELPERS
    // BigInteger math is verbose, so we wrap it here.
    public void earnCredits(int amount) {
        if (this.ndraBalance == null) this.ndraBalance = BigInteger.ZERO;
        this.ndraBalance = this.ndraBalance.add(BigInteger.valueOf(amount));
    }
    
    public boolean spendCredits(int amount) {
        if (this.ndraBalance == null) this.ndraBalance = BigInteger.ZERO;
        BigInteger cost = BigInteger.valueOf(amount);
        
        if (this.ndraBalance.compareTo(cost) >= 0) {
            this.ndraBalance = this.ndraBalance.subtract(cost);
            return true; // Success
        }
        return false; // Not enough funds
    }
    
    // 5. PROGRESSION
    public void levelUp() {
        level++;
        maxExp = (int)(maxExp * 1.5); // Increase requirement by 50%
        
        // Stat Boosts
        maxHealth += 10;
        health = maxHealth; // Full heal on level up
        grit += 1;
        streetSmarts += 1;
        technicalSkill += 1;
        
        history.add("Level Up! Reached level " + level);
    }

	public void setCurrentLocation(String string) {
		this.locationId = string;
		
	}
}