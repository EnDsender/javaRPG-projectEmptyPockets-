package ca.sheridancollege.dossanic.controllers;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.*;

import ca.sheridancollege.dossanic.models.PlayerState;
import ca.sheridancollege.dossanic.services.PlayerService;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "http://localhost:4200") 
public class PlayerController {
    
    private final PlayerService playerService;
    
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @GetMapping("/state")
    public PlayerState getPlayerState() {
        String defaultName = "Test Player";
        
        try {
            return playerService.getPlayerStateByName(defaultName);
        } catch (NoSuchElementException e) {
            // UPDATED: Use the smart factory method we built!
            // This ensures they get the correct starting items and stats.
            System.out.println("Creating new Default Player...");
            return playerService.createNewCharacter(defaultName, "Gutter Runner");
        }
    }

  
    @PostMapping("/create")
    public PlayerState createPlayer(@RequestParam String name, @RequestParam String archetype) {
        return playerService.createNewCharacter(name, archetype);
    }
    
   
    @GetMapping("/{id}")
    public PlayerState getPlayerById(@PathVariable String id) {
        return playerService.getPlayerById(id);
    }
}