package ca.sheridancollege.dossanic.models;

import java.util.List;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("Rat")
public class Rat extends Enemy {

    public Rat() {
        super();
        this.setName("Giant Rat");
        
        // Health: 4, 5, or 6
        this.setHealthWithVariance(5, 1); 
        
        this.setBaseMinDamage(1);
        this.setBaseMaxDamage(3);
        this.setDescription("A large, aggressive rat with sharp teeth.");
        this.setBaseArmorClass(12);
        this.setChargeCounter(0);
        this.addType(EnemyType.RAT);
        this.addType(EnemyType.BEAST);

        Loot normalTail = new Loot(
            "rat_tail",
            "Rat Tail",
            "Used for crafting.",
            3,
            CraftingType.ORGANICS, 
            false // Not a Quest Item
        );

        this.addLootDrop("rat_tail", 0.60); // 60% chance to drop
    }

    @Override
    public int getMoveCount() {
        return 3; 
    }

    @Override
    public int performAttack(int moveIndex, PlayerState player, Weapon playerWeapon, List<Enemy> allies) {
        
        if (this.getChargeCounter() > 0) {
            return hyperFang(player);
        }

        int packBonus = 0;
        if (allies != null) {
            for (Enemy ally : allies) {
                if ((ally.isType(EnemyType.RAT) || ally.isType(EnemyType.BEAST)) 
                     && ally.getCurrentHealth() > 0 && ally != this) {
                    packBonus++;
                }
            }
        }
        
        if (packBonus > 0) {
            System.out.println("Swarm Protocol Active: +" + packBonus + " Accuracy");
        }
        

        switch (moveIndex) {
            case 1: return gnaw(player, packBonus);
            case 2: return acidSpray(player, playerWeapon);
            case 3: return hyperFang(player);
            default: return super.attack(player, playerWeapon);
        }
    }

    // --- MOVES ---

    private int gnaw(PlayerState player, int bonus) {
        System.out.println(this.getName() + " Gnaws at your boots!");
        
        int totalDamage = getBaseMinDamage() + bonus;
        
        player.takeDamage(totalDamage);
        
        if (bonus > 0) {
            System.out.println("(Includes +" + bonus + " damage from Swarm Protocol!)");
        }
        
        return totalDamage;
    }

    private int acidSpray(PlayerState player, Weapon playerWeapon) {
        System.out.println(this.getName() + " sprays corrosive acid!");

        int damage = super.attack(player, playerWeapon);

        if (damage > 0) {
            int newStackCount = player.getAcidStacks() + 1;
            player.setAcidStacks(newStackCount);

            System.out.println("Acid burns! Stack " + newStackCount + "/3");

            if (newStackCount >= 3) {
                player.setAttackDebuff(player.getAttackDebuff() + 1);
                player.setAcidStacks(0); 
                System.out.println("CRITICAL MELT! Your weapon weakens! Damage -1 for this battle.");
            }
        }
        return damage;
    }

    private int hyperFang(PlayerState player) {
        if (this.getChargeCounter() == 0) {
            System.out.println(this.getName() + " crouches low... It's gathering power! (Charge 1/2)");
            this.setChargeCounter(1);
            return 0; 
        }
        else if (this.getChargeCounter() == 1) {
            System.out.println(this.getName() + "'s teeth glow red! It's about to strike! (Charge 2/2)");
            this.setChargeCounter(2);
            return 0; 
        }
        else {
            System.out.println(this.getName() + " unleashes HYPER FANG!");
            int criticalDamage = getBaseMaxDamage() * 2;
            player.takeDamage(criticalDamage);
            System.out.println(this.getName() + " bites down with immense force, dealing " + criticalDamage + " damage!");
            this.setChargeCounter(0);
            return criticalDamage;
        }
    }
}