package service.index.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "input")
@Entity
@Data
@EqualsAndHashCode(exclude = "indexshares")
public class CreationInputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "index_id", referencedColumnName = "id")
    IndexEntity index;
}
