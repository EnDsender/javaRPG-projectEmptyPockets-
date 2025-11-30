package ca.sheridancollege.dossanic.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.sheridancollege.dossanic.models.*;
import ca.sheridancollege.dossanic.repositories.PlayerStateRepository;
import ca.sheridancollege.dossanic.utils.StoryScript; // Import the script for dialogue

@Service
public class InteractionService {

    @Autowired private PlayerStateRepository playerRepo;

    public String handleGoblinChoice(String playerId, String choice) {
        PlayerState player = playerRepo.findById(playerId).orElseThrow();

        // 1. Define the Weapons (Templates)
        // Using your exact descriptions
        Weapon ancientPistol = new Weapon(
            "ancient_ones_way", "Ancient One's Way",
            "A M1911 pistol with Godd iz Gud engraved on the slide. There is also a beautiful poem immaculately spelt, about life and death inscribed on the grip, you're too lazy to read it though.",
            5, 5, 7
        );

        Weapon smartPistol = new Weapon(
            "starter_smart_pistol_data_knife", "Smart Pistol and Data Knife",
            "A sleek high tech pistol... its a blow torch with SMRT written with sharpie, BUT the data knife is a masterwork of enginnering perfectly machined from Clear Ste.... its a glass shard with a usb taped to it, sigh",
            2, 3, 8
        );

        Weapon babyBaton = new Weapon(
            "baby_baton", "Baby's First Baton",
            "a sturdy metal baton, a little short but perfect for police brutality for ages 3 and under. Most wishlisted toy of 3144 (baby's first firearm requires parental consent and sold seperately.)",
            3, 4, 7
        );

        // 2. Select based on Choice
        Weapon selectedWeapon = null;
        String dialogue = "";

        if (choice == null) {
             return "The Goblin stares at you, waiting for an answer.";
        }

        switch (choice.toLowerCase()) {
            case "pistol":
                selectedWeapon = ancientPistol;
                dialogue = StoryScript.GOBLIN_GIVE_PISTOL;
                break;
            case "smart":
                selectedWeapon = smartPistol;
                dialogue = StoryScript.GOBLIN_GIVE_SMART;
                break;
            case "baton":
                selectedWeapon = babyBaton;
                dialogue = StoryScript.GOBLIN_GIVE_BATON;
                break;
            default:
                return StoryScript.GOBLIN_CONFUSED;
        }

        // 3. Equip & Save Logic
        player.setEquippedWeapon(selectedWeapon);
        
        if (player.getHistory() == null) {
            player.setHistory(new java.util.ArrayList<>());
        }
        
        // Add the story beat to the log
        player.getHistory().add(">> INTERACTION: Goblin Merchant");
        player.getHistory().add(dialogue);
        player.getHistory().add(">> ACQUIRED: " + selectedWeapon.getName());

        playerRepo.save(player);

        return dialogue;
    }
}