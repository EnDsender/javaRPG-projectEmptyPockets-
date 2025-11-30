	package ca.sheridancollege.dossanic.bootstrap;
	
	import java.math.BigInteger;
	import java.util.ArrayList;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.CommandLineRunner;
	import org.springframework.stereotype.Component;
	
	import ca.sheridancollege.dossanic.models.GameLocation;
	import ca.sheridancollege.dossanic.models.LocationExit;
	import ca.sheridancollege.dossanic.models.PlayerState;
	import ca.sheridancollege.dossanic.models.Weapon;
	import ca.sheridancollege.dossanic.repositories.EnemyRepository;
	import ca.sheridancollege.dossanic.repositories.LocationRepository;
	import ca.sheridancollege.dossanic.repositories.PlayerStateRepository;
	import ca.sheridancollege.dossanic.repositories.QuestRepository;
	
	@Component
	public class GameDataInitializer implements CommandLineRunner {
	
		@Autowired
		private PlayerStateRepository playerRepo;
		@Autowired
		private EnemyRepository enemyRepo;
		@Autowired
		private LocationRepository locationRepo;
		@Autowired
		private QuestRepository questRepo;
	
		@Override
		public void run(String... args) throws Exception {
	
			// Clear existing data
			playerRepo.deleteAll();
			enemyRepo.deleteAll();
			locationRepo.deleteAll();
			questRepo.deleteAll();
	
			// Create Locations
	
			GameLocation start = new GameLocation();
			start.setId("drydock-checkpoint");
			start.setName("Drydock Checkpoint");
			start.setDescription(
					"A hastily put together checkpoint to process all the new indents after transport. Simple chainlink fences and a guard box which leads you to the lower sectors");
			start.setSafeZone(true);
			start.setSpawnRate(0.0);
	
			// exit to sector a2-market
			LocationExit toA2Market = new LocationExit("sector-a2-market",
					"Leave the checkpoint and head towards Sector A2 Market", false, null, 0);
			start.addExit(toA2Market);
			locationRepo.save(start);
	
			
			GameLocation market = new GameLocation();
			market.setId("sector-a2-market");
			market.setName("Sector A2 Market");
			market.setDescription("a bustling market filled with various stalls selling goods from all over the sector. Neon signs flicker above shops, and the aroma of street food fills the air.");
			market.setSafeZone(true); 
			market.setSpawnRate(0.0);
	
			
			market.addExit(new LocationExit(
			    "drydock-checkpoint", 
			    "Return to Checkpoint", 
			    false, 
			    null, 
			    0
			));
	
			locationRepo.save(market);
			
		}
		
	}
