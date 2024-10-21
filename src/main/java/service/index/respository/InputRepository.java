package service.index.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.index.entity.CreationInputEntity;

@Repository
public interface InputRepository  extends JpaRepository<CreationInputEntity, Long> {
}
