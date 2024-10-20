package service.index.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import service.index.entity.InputEntity;

@Repository
public interface InputRepository  extends JpaRepository<InputEntity, Long> {
}
