package ca.sheridancollege.dossanic.controllers;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.dossanic.models.PlayerState;
import ca.sheridancollege.dossanic.services.PlayerService;

@RestController
@RequestMapping("/api/player")
@CrossOrigin(origins = "http://localhost:4200") //for angular
public class PlayerController {
	private PlayerService playerService;
	
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@GetMapping("/state")
	public PlayerState getPlayerState() {
		String defaultPlayerName = "Test Player";
		
		try {
			return playerService.getPlayerStateByName(defaultPlayerName);
		} catch (NoSuchElementException e) {
			return playerService.saveDefaultPlayer();
		}
	}
}
