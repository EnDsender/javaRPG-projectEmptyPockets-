package ca.sheridancollege.dossanic.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ca.sheridancollege.dossanic.models.Enemy;


public interface EnemyRepository extends MongoRepository<Enemy, String> {
	
	Optional findById(String id);

	List<Enemy> findByLocationId(String roomId);
	



}
