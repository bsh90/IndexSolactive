package service.index.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Table(name = "index")
@Entity
@Data
public class IndexEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    Long id;

    @OneToOne(mappedBy = "index")
    CreationInputEntity creationInputEntity;

    @NotBlank
    @Column(name = "indexName")
    String indexName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "index", cascade=CascadeType.ALL)
    List<IndexshareEntity> indexshares;
}
