package service.index.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.index.entity.IndexshareEntity;

import java.util.List;

public interface IndexshareRepository extends JpaRepository<IndexshareEntity, Long> {
    List<IndexshareEntity> findByShareName(String shareName);
}
