package service.index.entity;

import jakarta.persistence.*;

@Entity
public class IndexshareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    public Long id;

    @Column(name = "shareName", unique = true, nullable = false, updatable = false)
    public String shareName;

    @Column(name = "sharePrice")
    public Double sharePrice;

    @Column(name = "numberOfshares")
    public Double numberOfshares;

    @ManyToOne
    @JoinColumn(name="index_id")
    public IndexEntity index;
}
