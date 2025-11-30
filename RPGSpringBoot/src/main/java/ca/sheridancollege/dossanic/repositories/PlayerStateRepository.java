package ca.sheridancollege.dossanic.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.dossanic.models.PlayerState;

@Repository
public interface PlayerStateRepository extends MongoRepository<PlayerState, String> {

	Optional <PlayerState> findByName(String name);

}
