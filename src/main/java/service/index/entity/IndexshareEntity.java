package service.index.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = "indexshares")
public class IndexshareEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    public Long id;

    @NotBlank
    @Column(name = "shareName", unique = true, nullable = false, updatable = false)
    public String shareName;

    @Min(value = 0L, message = "The value must be positive")
    @Column(name = "sharePrice")
    public Double sharePrice;

    @Min(value = 0L, message = "The value must be positive")
    @Column(name = "numberOfshares")
    public Double numberOfshares;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="index_id")
    public IndexEntity index;
}
