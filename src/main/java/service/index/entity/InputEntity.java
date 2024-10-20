package service.index.entity;

import jakarta.persistence.*;

@Table(name = "input")
@Entity
public class InputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    public Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "index_id", referencedColumnName = "id")
    public IndexEntity index;
}
