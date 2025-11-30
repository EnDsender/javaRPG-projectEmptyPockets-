package ca.sheridancollege.dossanic.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.dossanic.models.Quest;

@Repository
public interface QuestRepository extends MongoRepository<Quest, String> {
   
}