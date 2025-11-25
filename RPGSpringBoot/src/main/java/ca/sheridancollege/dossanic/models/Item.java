package ca.sheridancollege.dossanic.models;

import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Item {
    @Id
    private String id;
    private String name;
    private String description;
    private int baseSellingPrice;
}