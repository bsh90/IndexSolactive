package service.index.entity;

import jakarta.persistence.*;

@Entity
public class InputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    public Long id;

    @Column(name = "index")
    public IndexEntity index;
}
