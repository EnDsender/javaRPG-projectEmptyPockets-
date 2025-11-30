package ca.sheridancollege.dossanic.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // This allows the .builder().build() syntax we used in the Service
public class CombatResponse {
    
    // The story text to display in the chat box
    private String combatLog; 
    
    // Flags to tell Angular to show "Game Over" or "Victory" screens
    private boolean playerDied;
    private boolean enemyDied;
    
    // Data to update the Health Bars immediately
    private int playerHealthRemaining;
    private int enemyHealthRemaining;
}