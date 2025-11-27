package ca.sheridancollege.dossanic.models;

import org.springframework.data.annotation.TypeAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TypeAlias("Loot")
public class Loot extends Item {

	private boolean isStackable = true;
	private boolean isQuestItem = false;
	private CraftingType craftingType;

	public Loot(String id, String name, String description, int value, CraftingType craftingType, boolean isQuestItem) {
		super(id, name, description, value);
		this.craftingType = craftingType;
		this.isQuestItem = isQuestItem();
	}
}