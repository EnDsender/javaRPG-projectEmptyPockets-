package ca.sheridancollege.dossanic.models;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Weapon {
	@Id
	private String id;

	private String name;
	private int baseMinDamage = 1;
	private int baseMaxDamage = 5;
	private String description;
	private int baseSellingPrice = 5;

	public int attack(Enemy enemy, PlayerState player) {
		// 1. Roll the die (JUST luck)
		int rawRoll = (int) (Math.random() * 20) + 1;

		// 2. Calculate the modifier (JUST skill)
		int gritModifier = player.getGrit() / 2;

		// 3. Calculate the total (Result)
		int totalToHit = rawRoll + gritModifier;

		// --- CHECK FOR CRITICALS FIRST ---

		if (rawRoll == 20) {
			// Critical hit (Natural 20)
			int damage = baseMaxDamage * 2;
			enemy.takeDamage(damage);
			System.out.println(
					"NATURAL 20! " + name + " critically hit " + enemy.getName() + " for " + damage + " damage!");
			return damage;
		}

		if (rawRoll == 1) {
			// Critical Miss (Natural 1)
			player.takeDamage(enemy.getBaseMinDamage());
			System.out.println("NATURAL 1! " + name + " fumbled! " + enemy.getName() + " counters for "
					+ enemy.getBaseMinDamage() + " damage.");
			return 0;
		}

		// --- CHECK TOTAL FOR STANDARD HITS ---

		// Compare the TOTAL (Roll + Stat) vs Armor Class
		if (totalToHit >= enemy.getBaseArmorClass()) {
			// Hit
			int damage = (int) (Math.random() * (baseMaxDamage - baseMinDamage + 1)) + baseMinDamage;

			enemy.takeDamage(damage);
			System.out.println(name + " hit (Rolled " + rawRoll + "+" + gritModifier + "=" + totalToHit + ") for "
					+ damage + " damage.");
			return damage;
		} else {
			// Miss
			System.out.println(name + " missed! (Rolled " + rawRoll + "+" + gritModifier + "=" + totalToHit
					+ enemy.getBaseArmorClass());
			return 0;
		}
	}
}