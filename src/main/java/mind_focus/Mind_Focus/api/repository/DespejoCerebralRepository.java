package mind_focus.Mind_Focus.api.repository;

import mind_focus.Mind_Focus.api.model.DespejoCerebralEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespejoCerebralRepository extends JpaRepository<DespejoCerebralEntity, Long> {

    boolean existsById(Long id);
}
