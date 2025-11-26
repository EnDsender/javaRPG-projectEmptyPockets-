package ca.sheridancollege.dossanic.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ActiveQuest {
    
    private String questId; // Reference to the Template
    private String title;
    private List<QuestObjective> objectives; // COPIES of the tasks
    private boolean isReadyToTurnIn = false;

    // CONSTRUCTOR: Takes a Template and creates a Player Copy
    public ActiveQuest(Quest template) {
        this.questId = template.getId();
        this.title = template.getTitle();
        
        // DEEP COPY the objectives so we don't modify the database template!
        this.objectives = template.getObjectives().stream()
            .map(obj -> new QuestObjective(
                obj.getType(), 
                obj.getTargetId(), 
                obj.getRequiredAmount(), 
                0, // Reset progress to 0 for the player
                obj.getFlavorText()))
            .collect(Collectors.toList());
    }

    // Check if the whole job is done
    public boolean checkCompletion() {
        boolean allDone = objectives.stream().allMatch(QuestObjective::isCompleted);
        if (allDone) this.isReadyToTurnIn = true;
        return allDone;
    }
}