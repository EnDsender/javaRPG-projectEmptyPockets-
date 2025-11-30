package ca.sheridancollege.dossanic.models;
import java.util.List;
import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Document(collection = "game_locations")
public class GameLocation {
	@Id
	private String id; //example: Sector-A2
	
	private String name; //example: Sector A2
	private String description; //example: A dimly lit industrial sector with flickering lights and abandoned machinery.
	
	private boolean isSafeZone; //example: false
	
	private List<LocationExit> exits = new ArrayList<>();
	
	private List<String> enemySpawns = new ArrayList<>(); // List of Enemy IDs that can spawn here
	private double spawnRate; //example: 0.3 (30% chance of enemy encounter)
	
	public void addExit(LocationExit exit) {
		this.exits.add(exit);
	}
	

}
