package ca.sheridancollege.dossanic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.sheridancollege.dossanic.models.*;
import ca.sheridancollege.dossanic.services.*;
import ca.sheridancollege.dossanic.repositories.LocationRepository; // For direct repo access if needed

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:4200") 
public class GameController {

    @Autowired private PlayerService playerService;
    @Autowired private CombatService combatService;
    @Autowired private MovementService movementService;
    @Autowired private LocationRepository locationRepo; 

   
    @GetMapping("/location/{id}")
    public GameLocation getLocation(@PathVariable String id) {
        return locationRepo.findById(id).orElseThrow(() -> new RuntimeException("Location not found: " + id));
    }


    @PostMapping("/move")
    public String movePlayer(@RequestParam String playerId, @RequestParam String targetLocationId) {
        return movementService.movePlayer(playerId, targetLocationId);
    }

   
    @PostMapping("/attack")
    public CombatResponse attack(
            @RequestParam String playerId, 
            @RequestParam String enemyId,
            @RequestParam String roomId) {
        
        return combatService.performCombatTurn(playerId, enemyId, roomId);
    }
    
    
}