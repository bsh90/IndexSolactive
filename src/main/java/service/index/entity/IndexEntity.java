package service.index.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @Column(name = "indexName")
    public String indexName;

    @OneToMany(mappedBy = "index", cascade=CascadeType.ALL)
    public List<IndexshareEntity> indexshares;
}
