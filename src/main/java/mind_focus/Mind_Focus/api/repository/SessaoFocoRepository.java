package mind_focus.Mind_Focus.api.repository;

import mind_focus.Mind_Focus.api.model.SessaoFocoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SessaoFocoRepository extends JpaRepository<SessaoFocoEntity, Long> {

    List<SessaoFocoEntity> findByUsuario_IdUsuario(Long idUsuario);

    List<SessaoFocoEntity> findByDataSessao(LocalDate data);

    @Query("SELECT SUM(s.duracaoMinutos) FROM SessaoFocoEntity s WHERE s.usuario.idUsuario = :idUsuario")
    Integer sumDuracaoByUsuario(@Param("idUsuario") Long idUsuario);

    boolean existsById(Long id);
}
