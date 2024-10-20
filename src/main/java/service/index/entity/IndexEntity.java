package service.index.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Table(name = "index")
@Entity
public class IndexEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    public Long id;

    @OneToOne(mappedBy = "index")
    public InputEntity inputEntity;

    @NotBlank
    @Column(name = "indexName")
    public String indexName;

    @OneToMany(mappedBy = "index", cascade=CascadeType.ALL)
    public List<IndexshareEntity> indexshares;
}
