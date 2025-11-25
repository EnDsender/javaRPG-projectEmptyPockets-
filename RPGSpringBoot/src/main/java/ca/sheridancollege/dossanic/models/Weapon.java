package ca.sheridancollege.dossanic.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true) 
public class Weapon extends Item {


    private int baseMinDamage = 1;
    private int baseMaxDamage = 5;

    // Custom Constructor to help create Weapons easily
    public Weapon(String id, String name, String description, int price, int min, int max) {
        super(id, name, description, price); // Pass basics to Parent
        this.baseMinDamage = min;
        this.baseMaxDamage = max;
    }

    public int attack(Enemy enemy, PlayerState player) {
        // 1. Roll the die (JUST luck)
        int rawRoll = (int) (Math.random() * 20) + 1;

        // 2. Calculate the modifier (JUST skill)
        int gritModifier = player.getGrit() / 2;

        // 3. Calculate the total (Result)
        int totalToHit = rawRoll + gritModifier;

        // --- CHECK FOR CRITICALS FIRST ---

        if (rawRoll == 20) {
            int damage = baseMaxDamage * 2;
            enemy.takeDamage(damage);
            System.out.println("NATURAL 20! " + getName() + " critically hit " + enemy.getName() + " for " + damage + " damage!");
            return damage;
        }

        if (rawRoll == 1) {
            player.takeDamage(enemy.getBaseMinDamage());
            System.out.println("NATURAL 1! " + getName() + " fumbled! " + enemy.getName() + " counters for " + enemy.getBaseMinDamage() + " damage.");
            return 0;
        }

        // --- CHECK TOTAL FOR STANDARD HITS ---

        if (totalToHit >= enemy.getBaseArmorClass()) { // Make sure Enemy has getArmorClass or getBaseArmorClass
            int damage = (int) (Math.random() * (baseMaxDamage - baseMinDamage + 1)) + baseMinDamage;
            

            enemy.takeDamage(damage);
            System.out.println(getName() + " hit (Rolled " + rawRoll + "+" + gritModifier + "=" + totalToHit + ") for " + damage + " damage.");
            return damage;
        } else {
            System.out.println(getName() + " missed! (Rolled " + rawRoll + "+" + gritModifier + "=" + totalToHit + ") vs AC " + enemy.getBaseArmorClass());
            return 0;
        }
    }
}