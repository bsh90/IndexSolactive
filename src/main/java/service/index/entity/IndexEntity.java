package service.index.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Table(name = "index")
@Entity
@Data
@EqualsAndHashCode(exclude = "indexshares")
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
