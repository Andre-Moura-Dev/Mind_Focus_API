package mind_focus.Mind_Focus.api.repository;

import mind_focus.Mind_Focus.api.model.TarefaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<TarefaEntity, Long> {

    List<TarefaEntity> findByDataTarefaBeforeAndCompletada(LocalDate data, Boolean completada);
    List<TarefaEntity> findByDataTarefa(LocalDate data);
    List<TarefaEntity> findByCompletada(Boolean completada);
    List<TarefaEntity> findByPrioridade(String prioridade);
    List<TarefaEntity> findByUsuario(Long idUsuario);
    boolean existsById(Long id);
    long countByUsuario(Long idUsuario);
}
