package ca.sheridancollege.dossanic.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enemy {

    @Id
    private String id;

    private String name;
    
    // -- HEALTH SYSTEM --
    private int baseHealth;     // The "Standard" (e.g., 10)
    private int maxHealth;      // The "Rolled" Cap (e.g., 12)
    private int currentHealth;  // The Actual Life (e.g., 5/12)

    private int baseMinDamage = 1;
    private int baseMaxDamage = 5;
    private String description;
    private int baseArmorClass = 10; 
    
    private List <EnemyType> type = new ArrayList<>();
    
    private int chargeCounter = 0;
    
    // -- LOOT SYSTEM (NEW) --
    private List<LootDrop> lootTable = new ArrayList<>();
    private int minGoldDrop = 0;
    private int maxGoldDrop = 0;

    // -- TYPE METHODS --
    public List<EnemyType> getTypes() {
        return type;
    }
    
    public void addType(EnemyType type) {
        this.type.add(type);
    }
    
    public boolean isType(EnemyType checkType) {
        return this.type.contains(checkType);
    }

    // -- VARIABLE HEALTH HELPER --
    protected void setHealthWithVariance(int base, int variance) {
        this.baseHealth = base;
        
        int modifier = (int)(Math.random() * (variance * 2 + 1)) - variance;
        
        int finalHealth = base + modifier;
        if (finalHealth < 1) finalHealth = 1; 
        
        this.maxHealth = finalHealth;
        this.currentHealth = finalHealth;
        
        if (modifier < 0) this.name = "Scrawny " + this.name;
        if (modifier > 0) this.name = "Tough " + this.name;
    }
    
    // -- LOOT METHODS (NEW) --
    
    public void addLootDrop(String itemId, double chance) {
        this.lootTable.add(new LootDrop(itemId, chance));
    }
    
    // Generates a list of Item IDs that dropped
    public List<String> generateLoot() {
        List<String> droppedItems = new ArrayList<>();
        
        for (LootDrop drop : lootTable) {
            if (Math.random() <= drop.getChance()) {
                droppedItems.add(drop.getItemId());
                System.out.println("Loot Drop! Found item ID: " + drop.getItemId());
            }
        }
        return droppedItems;
    }
    
    public int generateGold() {
        if (maxGoldDrop == 0) return 0;
        return (int)(Math.random() * (maxGoldDrop - minGoldDrop + 1)) + minGoldDrop;
    }

    // -- COMBAT METHODS --

    public String takeDamage(int damage) {
        currentHealth -= damage;
        
        if (currentHealth <= 0) {
            currentHealth = 0;
            return name + " has been defeated!";
        } else {
            return name + " is still standing (" + currentHealth + "/" + maxHealth + ")!";
        }
    }

    public int attack(PlayerState player, Weapon playerWeapon) {
        int roll = (int) (Math.random() * 20) + 1;

        // CRITICAL HIT
        if (roll == 20) {
            int damage = baseMaxDamage * 2;
            player.takeDamage(damage);
            System.out.println(name + " critically hit " + player.getName() + " for " + damage + " damage!");
            return damage;
        }

        // CRITICAL MISS (Clash Mechanic)
        if (roll == 1) {
            System.out.println(name + " stumbled! " + player.getName() + " gets a counter-attack opportunity!");
            int damageDealtByPlayer = playerWeapon.attack(this, player);
            return 0; 
        }

        // NORMAL HIT
        if (roll >= player.getArmorClass()) {
            int damage = (int) (Math.random() * (baseMaxDamage - baseMinDamage + 1)) + baseMinDamage;
            player.takeDamage(damage);
            System.out.println(name + " hit " + player.getName() + " for " + damage + " damage.");
            return damage;
        } else {
            // MISS
            System.out.println(name + " missed " + player.getName() + "!");
            return 0;
        }
    }

    public int performAttack(int moveIndex, PlayerState player, Weapon playerWeapon, List<Enemy> allies) {
        return this.attack(player, playerWeapon);
    }

    public int getMoveCount() {
        return 1;
    }
}