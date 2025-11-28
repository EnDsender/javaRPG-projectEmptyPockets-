package ca.sheridancollege.dossanic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestObjective {
	
	//TYPE: What kind of objective it is
	private ObjectiveType type;
	
	//TARGET: What are we checking? (e.g., item ID, location ID, NPC ID)
	private String targetId;
	
	
	//AMOUNT: How many of the target are needed?
	private int requiredAmount;
	private int currentAmount;
	
	
	//FLAVORTEXT: Description of the objective
	private String flavorText;
	
	//helper to check progress
	public boolean isCompleted() {
		return currentAmount >= requiredAmount;
	}

}
