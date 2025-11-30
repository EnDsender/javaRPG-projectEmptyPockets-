package ca.sheridancollege.dossanic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationExit {
	
	private String targetLocationId;
	private String directionDescription;// example: walk north to Sector A3
	
	private boolean isLocked = false;
	private String requiredItemId; // Item ID needed to unlock, if locked
	private int travelcost = 0; // credits cost to travel this exit
	
	

}
