package ca.sheridancollege.dossanic.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import ca.sheridancollege.dossanic.models.PlayerState;

public interface PlayerStateRepository extends MongoRepository<PlayerState, String> {
	//finder method for character selection
	PlayerState findByName(String name);

}
