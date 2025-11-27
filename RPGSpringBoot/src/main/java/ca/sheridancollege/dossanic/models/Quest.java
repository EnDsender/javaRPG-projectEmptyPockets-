package ca.sheridancollege.dossanic.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "quests")
@TypeAlias("WorkOrder")
public class Quest {

    @Id
    private String id; // e.g., "contract_sewer_01"
    
    private String title;       // "Sector 4 Sanitation"
    private String clientName;  // "Municipality" or "Fixer"
    private String briefing;    // "Rats are chewing the cables. Fix it."
    
    // THE TASKS
    private List<QuestObjective> objectives = new ArrayList<>();

    // THE PAYOUT
    private BigInteger ndraReward;      // Cash for wallet
    private BigInteger debtReduction;   // Direct payment to principal
    private int xpReward;
    
    // Helper to build quests easily
    public void addObjective(ObjectiveType type, String target, int count, String desc) {
        this.objectives.add(new QuestObjective(type, target, count, 0, desc));
    }
}