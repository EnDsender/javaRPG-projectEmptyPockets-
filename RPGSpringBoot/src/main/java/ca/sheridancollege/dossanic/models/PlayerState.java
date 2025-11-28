package ca.sheridancollege.dossanic.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Maps the Java class to MongoDB collection named 'playerStates'
@Document(collection = "playerStates")
public class PlayerState {
	@Id
	private String id;
	
	// --Character ID and Archetype--
	private String name;
	private String archetype;		// e.g., "Gutter Runner", "Work Junkie", "One of Those"
	private String locationId;		// The ID of the current game location (e.g., "CHECKPOINT DMV")
	
	// --Game Stats--
	private int health = 100;
	private int maxHealth = 100;
	
	private int grit;				// Combat, HP, Strength
	private int streetSmarts;		// Navigation, finding unique interactions
	private int technicalSkill;		// Hacking, techie skills
	private int armorClass;			// Difficulty to hit in combat
	
	private int exp;					// Experience points
	private int maxExp;				// EXP needed for next level
	private int level;				// Player level based on EXP
	
	// --Economy--
	private BigInteger debtAmount;	// Main progress tracker (e.g., 200539846 credits)
	private BigInteger ndraBalance;	// Personal Bank (Non debt repayment account)
	
	// --Inventory and Game History--
	private List<String> inventory = new ArrayList<>(); // Items being carried
	private List<String> history = new ArrayList<>();	// Story log displayed to user
	//private Weapon equippedWeapon;		// Currently equipped weapon
	
	// --Combat and Game State Flags-- 
	private boolean isDead = false;
	private Object currentEnemy;
	
	//--Temporary Buffs and Effects--
	
	private int acidStacks = 0;
	private int attackDebuff= 0;
		
}
