package ca.sheridancollege.dossanic.models;

import java.util.List;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("Rat")
public class Rat extends Enemy {

    public Rat() {
        super();
        this.setName("Giant Rat");
        
        // RECOMMENDATION: Use the variance helper we made!
        // Base 5, Variance 1 -> Spawns with 4, 5, or 6 HP
        this.setHealthWithVariance(5, 1); 
        
        this.setBaseMinDamage(1);
        this.setBaseMaxDamage(3);
        this.setDescription("A large, aggressive rat with sharp teeth.");
        this.setBaseArmorClass(12);
        this.setChargeCounter(0);
        this.addType(EnemyType.RAT);
        this.addType(EnemyType.BEAST);
    }

    @Override
    public int getMoveCount() {
        return 3; 
    }

    // UPDATE: Added 'List<Enemy> allies' to match the Parent signature
    @Override
    public int performAttack(int moveIndex, PlayerState player, Weapon playerWeapon, List<Enemy> allies) {
        
        // 1. AI OVERRIDE: If charging, ignore the random move and finish the charge
        if (this.getChargeCounter() > 0) {
            return hyperFang(player);
        }

        // 2. OPTIONAL: PACK TACTICS LOGIC HERE
        // (You can add the accuracy bonus code here using 'allies' list)

        switch (moveIndex) {
            case 1: return gnaw(player);
            case 2: return acidSpray(player, playerWeapon);
            case 3: return hyperFang(player);
            default: return super.attack(player, playerWeapon);
        }
    }

    // --- MOVES ---

    private int gnaw(PlayerState player) {
        // Added space before "Gnaws"
        System.out.println(this.getName() + " Gnaws at your boots!");
        
        // Logic: Guaranteed small damage (Nibble)
        int damage = getBaseMinDamage();
        player.takeDamage(damage);
        return damage;
    }

    private int acidSpray(PlayerState player, Weapon playerWeapon) {
        System.out.println(this.getName() + " sprays corrosive acid!");

        int damage = super.attack(player, playerWeapon);

        if (damage > 0) {
            // FIX: Increment the variable!
            int newStackCount = player.getAcidStacks() + 1;
            player.setAcidStacks(newStackCount);

            System.out.println("Acid burns! Stack " + newStackCount + "/3");

            // CHECK THRESHOLD using the NEW number
            if (newStackCount >= 3) {
                
                // Assuming you have 'setAttackDebuff' in PlayerState (watch capitalization!)
                player.setAttackDebuff(player.getAttackDebuff() + 1);

                player.setAcidStacks(0); // Reset

                System.out.println("CRITICAL MELT! Your weapon weakens! Damage -1 for this battle.");
            }
        }
        return damage;
    }

    private int hyperFang(PlayerState player) {
        // STAGE 1
        if (this.getChargeCounter() == 0) {
            System.out.println(this.getName() + " crouches low... It's gathering power! (Charge 1/2)");
            this.setChargeCounter(1);
            return 0; 
        }
        // STAGE 2
        else if (this.getChargeCounter() == 1) {
            System.out.println(this.getName() + "'s teeth glow red! It's about to strike! (Charge 2/2)");
            this.setChargeCounter(2);
            return 0; 
        }
        // STAGE 3 (ATTACK)
        else {
            System.out.println(this.getName() + " unleashes HYPER FANG!");

            int criticalDamage = getBaseMaxDamage() * 2;
            player.takeDamage(criticalDamage);
            
            System.out.println(this.getName() + " bites down with immense force, dealing " + criticalDamage + " damage!");

            // Reset charge
            this.setChargeCounter(0);

            return criticalDamage;
        }
    }
}