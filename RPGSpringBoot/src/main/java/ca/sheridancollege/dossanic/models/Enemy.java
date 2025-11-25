package ca.sheridancollege.dossanic.models;

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
    private int baseHealth;
    private int baseMinDamage = 1;
    private int baseMaxDamage = 5;
    private String description;
    private int baseArmorClass =10; // Default armor class

    public String takeDamage(int damage) {
        baseHealth -= damage;
        if (baseHealth <= 0) { 
            baseHealth = 0;
            return name + " has been defeated!";
        } else {
            return name + " is still standing!";
        }
    }
    
  
    public int attack(PlayerState player, Weapon playerWeapon) {
        int roll = (int)(Math.random() * 20) + 1; 
        
        //CRITICAL HIT
        if (roll == 20) {
            int damage = baseMaxDamage * 2;
            player.takeDamage(damage); 
            System.out.println(name + " critically hit " + player.getName() + " for " + damage + " damage!");
            return damage;
        }
        
      
        if (roll == 1) {
            System.out.println(name + " stumbled! " + player.getName() + " gets a counter-attack opportunity!");
            int damageDealtByPlayer = playerWeapon.attack(this, player);
            return 0; // The enemy dealt 0 damage (they missed)
        }
        
        // NORMAL HIT
        if (roll >= player.getArmorClass()) {
            int damage = (int)(Math.random() * (baseMaxDamage - baseMinDamage + 1)) + baseMinDamage;
            player.takeDamage(damage);
            System.out.println(name + " hit " + player.getName() + " for " + damage + " damage.");
            return damage;
        } else {
            //MISS
            System.out.println(name + " missed " + player.getName() + "!");
            return 0; 
        }
    }
}