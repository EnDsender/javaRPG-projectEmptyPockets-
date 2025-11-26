package ca.sheridancollege.dossanic.models;

import org.springframework.data.annotation.TypeAlias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TypeAlias("Loot")
public class Loot extends Item {

	private boolean isStackable = true;
	private boolean isQuestItem = false;
	
	
}
