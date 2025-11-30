package ca.sheridancollege.dossanic.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.dossanic.models.*;
import ca.sheridancollege.dossanic.repositories.EnemyRepository;
import ca.sheridancollege.dossanic.repositories.PlayerStateRepository; 

@Service
public class CombatService {

    @Autowired
    private PlayerStateRepository playerRepo;

    @Autowired
    private EnemyRepository enemyRepo;

    
    public CombatResponse performCombatTurn(String playerId, String enemyId, String roomId) {
        StringBuilder log = new StringBuilder();

        // 1. FETCH ENTITIES
        PlayerState player = playerRepo.findById(playerId).orElseThrow();
        Enemy enemy = (Enemy) enemyRepo.findById(enemyId).orElseThrow();

        // 2. EQUIP WEAPON
        // We check the specific slot. If null, use Fists.
        Weapon weapon = player.getEquippedWeapon();
        
        if (weapon == null) {
            // Default "Fists" weapon if nothing is equipped
            weapon = new Weapon("fists", "Fists", "Basic unarmed strike", 0, 1, 2);
        }

        // 3. ROLL INITIATIVE (Dynamic Turn Order)
        int playerInitBonus = player.getInitiativeBonus();
        int playerRoll = (int) (Math.random() * 20) + 1 + playerInitBonus;
        int enemyRoll = (int) (Math.random() * 20) + 1; 

        log.append("Initiative: You (").append(playerRoll).append(") vs ")
           .append(enemy.getName()).append(" (").append(enemyRoll).append(")\n");

        boolean playerGoesFirst = playerRoll >= enemyRoll;

        // 4. EXECUTE TURN ORDER
        if (playerGoesFirst) {
            log.append(">> You react faster!\n");

            // A. Player Attack
            processPlayerAttack(player, enemy, weapon, log);

            // Check if Enemy died
            if (enemy.getCurrentHealth() <= 0) {
                return handleEnemyDeath(player, enemy, log);
            }

            // B. Enemy Counter-Attack
            processEnemyAttack(enemy, player, weapon, roomId, log);

        } else {
            log.append(">> ").append(enemy.getName()).append(" moves before you can react!\n");

            // A. Enemy Attack
            processEnemyAttack(enemy, player, weapon, roomId, log);

            // Check if Player died
            if (player.getHealth() <= 0) {
                log.append("\nCRITICAL FAILURE. VITAL SIGNS STOPPED.");
                playerRepo.save(player);
                return buildResponse(log, true, false, 0, enemy.getCurrentHealth());
            }

            // B. Player Counter-Attack
            processPlayerAttack(player, enemy, weapon, log);
            
            // Check if Enemy died from counter
            if (enemy.getCurrentHealth() <= 0) {
                return handleEnemyDeath(player, enemy, log);
            }
        }

        // 5. SAVE STATE
        playerRepo.save(player);
        enemyRepo.save(enemy);

        return buildResponse(log, 
                player.getHealth() <= 0, 
                enemy.getCurrentHealth() <= 0, 
                player.getHealth(), 
                enemy.getCurrentHealth());
    }

    // --- HELPER METHODS ---

    private void processPlayerAttack(PlayerState player, Enemy enemy, Weapon weapon, StringBuilder log) {
        int dmg = weapon.attack(enemy, player);
        log.append("You attacked ").append(enemy.getName())
           .append(" with ").append(weapon.getName())
           .append(" for ").append(dmg).append(" damage.\n");
    }

    private void processEnemyAttack(Enemy enemy, PlayerState player, Weapon weapon, String roomId, StringBuilder log) {
        log.append("\n--- ENEMY MOVE ---\n");

        // PACK TACTICS LOGIC:
        List<Enemy> roomEnemies;
        try {
             roomEnemies = enemyRepo.findByLocationId(roomId);
        } catch (Exception e) {
             roomEnemies = new ArrayList<>(); // Fallback
        }

        int moveCount = enemy.getMoveCount();
        int randomMove = (int) (Math.random() * moveCount) + 1;

        int dmg = enemy.performAttack(randomMove, player, weapon, roomEnemies);

        log.append(enemy.getName()).append(" attacked you for ").append(dmg).append(" damage.\n");
    }

    private CombatResponse handleEnemyDeath(PlayerState player, Enemy enemy, StringBuilder log) {
        log.append("\nVICTORY: ").append(enemy.getName()).append(" has been neutralized.\n");

        // 1. GENERATE REWARDS
        List<String> droppedItemIds = enemy.generateLoot();
        int goldDrop = enemy.generateGold();

        log.append("Loot Found: ").append(droppedItemIds.toString())
           .append(" and ").append(goldDrop).append(" credits.\n");

        

        // 3. UPDATE QUESTS
        updateQuestsLocal(player, enemy, log);

        // 4. CLEANUP
        log.append("Adrenaline fades. Status effects cleared.\n");
        player.resetBattleStats();

        // 5. SAVE & DELETE
        playerRepo.save(player);
        enemyRepo.delete(enemy); 

        return buildResponse(log, false, true, player.getHealth(), 0);
    }

    private void updateQuestsLocal(PlayerState player, Enemy deadEnemy, StringBuilder log) {
        for (ActiveQuest quest : player.getActiveContracts()) {
            if (quest.isReadyToTurnIn()) continue;

            for (QuestObjective obj : quest.getObjectives()) {
                if (obj.getType() == ObjectiveType.ELIMINATE) {
                    if (deadEnemy.getName().equalsIgnoreCase(obj.getTargetId()) || 
                        deadEnemy.getId().equals(obj.getTargetId())) {
                        
                        // Increment
                        obj.setCurrentAmount(obj.getCurrentAmount() + 1);
                        
                        log.append(">> Contract Update: ").append(obj.getFlavorText()) // Assuming getDescription() exists, or getFlavorText()
                           .append(" (").append(obj.getCurrentAmount())
                           .append("/").append(obj.getRequiredAmount()).append(")\n");
                    }
                }
            }
            
            // Check full completion
            if (quest.checkCompletion()) {
                log.append(">> CONTRACT FULFILLED: ").append(quest.getTitle()).append("\n");
            }
        }
    }

    private CombatResponse buildResponse(StringBuilder log, boolean pDead, boolean eDead, int pHp, int eHp) {
        return CombatResponse.builder()
                .combatLog(log.toString())
                .playerDied(pDead)
                .enemyDied(eDead)
                .playerHealthRemaining(pHp)
                .enemyHealthRemaining(eHp)
                .build();
    }
}