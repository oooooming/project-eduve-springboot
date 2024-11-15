package tricode.eduve.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Character {

    @Id
    @Column(name = "character_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;
}
