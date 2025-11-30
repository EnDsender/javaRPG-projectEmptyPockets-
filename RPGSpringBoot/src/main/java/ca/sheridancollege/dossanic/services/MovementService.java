package ca.sheridancollege.dossanic.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.sheridancollege.dossanic.models.GameLocation;
import ca.sheridancollege.dossanic.models.LocationExit;
import ca.sheridancollege.dossanic.models.PlayerState;
import ca.sheridancollege.dossanic.repositories.LocationRepository;
import ca.sheridancollege.dossanic.repositories.PlayerStateRepository;


@Service
public class MovementService {

    @Autowired
    private PlayerStateRepository playerRepo;

    @Autowired
    private LocationRepository locationRepo;

 
    public String movePlayer(String playerId, String targetLocationId) {
        PlayerState player = playerRepo.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("Player not found: " + playerId));

        if (player.getLocationId() == null) {
            return "Error: Player has no current location. (Did you seed the data?)";
        }

        GameLocation currentLocation = locationRepo.findById(player.getLocationId())
                .orElseThrow(() -> new NoSuchElementException("Current location not found: " + player.getLocationId()));

        LocationExit selectedExit = currentLocation.getExits().stream()
                .filter(exit -> exit.getTargetLocationId().equals(targetLocationId))
                .findFirst()
                .orElse(null);

        if (selectedExit == null) {
            return "You cannot go that way from here.";
        }

        if (selectedExit.isLocked()) {
           
            return "Access Denied: The way is locked.";
        }

        if (selectedExit.getTravelcost() > 0) {
            if (!player.spendCredits(selectedExit.getTravelcost())) {
                return "INSUFFICIENT FUNDS. Transit authority denies access. Cost: " + selectedExit.getTravelcost() + " CR.";
            }
        }

        GameLocation newLocation = locationRepo.findById(targetLocationId)
                .orElseThrow(() -> new NoSuchElementException("Target location data missing: " + targetLocationId));

        player.setLocationId(targetLocationId);
        
        playerRepo.save(player);

        return "Travel successful. You have arrived at " + newLocation.getName() + ".";
    }
}