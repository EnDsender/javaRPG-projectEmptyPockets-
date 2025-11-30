package ca.sheridancollege.dossanic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LootDrop {
    private String itemId;   // The ID of the Item
    private double chance;   // 0.0 to 1.0
}