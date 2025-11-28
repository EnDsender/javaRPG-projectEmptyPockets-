package ca.sheridancollege.dossanic.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ActiveQuest {
    
    private String questId; 
    private String title;
    private List<QuestObjective> objectives; 
    private boolean isReadyToTurnIn = false;

    public ActiveQuest(Quest template) {
        this.questId = template.getId();
        this.title = template.getTitle();
        
        // DEEP COPY
        this.objectives = template.getObjectives().stream()
            .map(obj -> new QuestObjective(
                obj.getType(), 
                obj.getTargetId(), 
                // REMOVED 'questId' HERE
                obj.getRequiredAmount(), 
                0, // Reset progress
                obj.getFlavorText()))
            .collect(Collectors.toList());
    }

    public boolean checkCompletion() {
        boolean allDone = objectives.stream().allMatch(QuestObjective::isCompleted);
        if (allDone) this.isReadyToTurnIn = true;
        return allDone;
    }
}