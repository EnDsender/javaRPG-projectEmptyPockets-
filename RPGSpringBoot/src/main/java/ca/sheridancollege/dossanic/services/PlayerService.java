package ca.sheridancollege.dossanic.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import ca.sheridancollege.dossanic.models.*;
import ca.sheridancollege.dossanic.repositories.PlayerStateRepository;

@Service
public class PlayerService {

    private final PlayerStateRepository playerRepo;

    public PlayerService(PlayerStateRepository playerRepo) {
        this.playerRepo = playerRepo;
    }

    public PlayerState getPlayerStateByName(String name) {
        return playerRepo.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Player not found: " + name));
    }
    
    public PlayerState getPlayerById(String id) {
        return playerRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Player not found: " + id));
    }

    public PlayerState createNewCharacter(String name, String archetypeStr) {
        PlayerState p = new PlayerState();

        // 1. IDENTITY
        p.setName(name);
        p.setLocationId("drydock-checkpoint"); 
        p.setLevel(1);
        p.setExp(0);
        p.setMaxExp(1000);
        
        // 2. ECONOMY
        p.setDebtAmount(new BigInteger("2005390846"));
        p.setNdraBalance(new BigInteger("0")); 

        // 3. ARCHETYPE LOGIC 
        if (archetypeStr.equalsIgnoreCase("Gutter Runner")) {
            p.setArchetype("Gutter Runner");
            p.setGrit(10);
            p.setStreetSmarts(14); 
            p.setTechnicalSkill(8);
            p.setMaxHealth(18);
            p.setArmorClass(12);
                    } 
        else if (archetypeStr.equalsIgnoreCase("Work Junkie")) {
            p.setArchetype("Work Junkie");
            p.setGrit(8);
            p.setStreetSmarts(10);
            p.setTechnicalSkill(14); 
            p.setMaxHealth(16);
            p.setArmorClass(11);
            
        } 
        else {
            // Default: One of Those
            p.setArchetype("One of Those");
            p.setGrit(14); 
            p.setStreetSmarts(8);
            p.setTechnicalSkill(10);
            p.setMaxHealth(24);
            p.setArmorClass(13);

        }

        // 4. INITIALIZE EMPTY LISTS
        p.setHealth(p.getMaxHealth()); 
        p.setInventory(new ArrayList<>()); 
        p.setHistory(new ArrayList<>());
        p.setActiveContracts(new ArrayList<>());
        p.setCompletedContractIds(new ArrayList<>());
        
       
        p.setEquippedWeapon(null); 
        
        return playerRepo.save(p);
        
        
        
    }
}