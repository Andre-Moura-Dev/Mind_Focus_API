package mind_focus.Mind_Focus.api.repository;

import mind_focus.Mind_Focus.api.model.SessaoFocoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SessaoFocoRepository extends JpaRepository<SessaoFocoEntity, Long> {

    List<SessaoFocoEntity> findByUsuario(Long idUsuario);

    List<SessaoFocoEntity> findByDataSessao(LocalDate data);

    @Query("SELECT SUM(s.duracaoMinutos) FROM SessaoFocoEntity s WHERE s.usuario.idUsuario = :idUsuario")
    Integer sumDuracaoByUsuario(Long idUsuario);

    boolean existsById(Long id);
}
