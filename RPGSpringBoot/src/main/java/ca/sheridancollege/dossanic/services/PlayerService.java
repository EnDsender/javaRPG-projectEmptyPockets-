package ca.sheridancollege.dossanic.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import ca.sheridancollege.dossanic.models.PlayerState;
import ca.sheridancollege.dossanic.repositories.PlayerStateRepository;

@Service
public class PlayerService {
	private PlayerStateRepository playerStateRepo;
	
	
	public PlayerService(PlayerStateRepository playerStateRepo) {
        this.playerStateRepo = playerStateRepo;
    }
	
	public PlayerState getPlayerStateByName(String name) {
		return playerStateRepo.findByName(name)
				.orElseThrow(() -> new NoSuchElementException("Player not found with name: " + name));
	}
	
	//default player for testing purposes
	public PlayerState saveDefaultPlayer() {
		final BigInteger initialDebt = new BigInteger("2005390846");
		PlayerState defaultPlayer = new PlayerState(
		        null, 				// id
		        "Test Player", 		// name
		        "Gutter Runner", 	// archetype
		        "CHECKPOINT DMV", 	// locationId
		        100, 				// current health
		        100, 				// maxHealth
		        5, 					// grit
		        5, 					// streetSmarts
		        5, 					// technicalSkill
		        12, 					// armorClass
		        0, 					// exp
		        100, 				// level
		        1, 					// nextLevelExp
		        initialDebt, 		// debtAmount (Initial value set here)
		        new BigInteger("50"), // ndraBalance
		        new ArrayList<String>(), // inventory
		        new ArrayList<String>(), // history
		        false, 				// isDead
		        null, 				// currentEnemy
		        0, 					// acidStacks
		        0 					// attackDebuff
		    );
        return playerStateRepo.save(defaultPlayer);
    }
}
