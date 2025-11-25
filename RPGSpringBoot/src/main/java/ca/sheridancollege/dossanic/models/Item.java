package ca.sheridancollege.dossanic.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Item {
	@Id
	private String id;
	
	private String itemName;
	private String itemDescription;
	private String itemType; //weapons, consumables, gear etc.
	
	// --Value--
	private int value;
	
	// --Stats--
	private int damage;
	private int defence;
	
	private int gritModifier;
	private int streetSmartsModifier;
	private int technicalSkillModifier;
}
