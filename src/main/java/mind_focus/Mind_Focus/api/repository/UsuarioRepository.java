package mind_focus.Mind_Focus.api.repository;

import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
    boolean existsById(Long id);
}
