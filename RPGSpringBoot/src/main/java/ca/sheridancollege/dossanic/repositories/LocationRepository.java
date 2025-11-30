package ca.sheridancollege.dossanic.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.dossanic.models.GameLocation;

@Repository
public interface LocationRepository extends MongoRepository<GameLocation, String> {
  
}