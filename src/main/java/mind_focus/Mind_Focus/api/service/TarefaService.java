package mind_focus.Mind_Focus.api.service;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mind_focus.Mind_Focus.api.dto.TarefaDTO;
import mind_focus.Mind_Focus.api.model.TarefaEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<TarefaDTO> listarTodos() throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findAll()
                    .stream()
                    .map(tarefa -> TarefaDTO.builder()
                            .idTarefa(tarefa.getIdTarefa())
                            .usuario(tarefa.getUsuario())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .prioridade(tarefa.getPrioridade())
                            .completada(tarefa.getCompletada())
                            .dataTarefa(tarefa.getDataTarefa())
                            .criadaEm(tarefa.getCriadaEm())
                            .build()
                    )
                    .toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public TarefaDTO cadastrarTarefa(TarefaDTO dto) throws DefaultExceptionHandler {
        try {

            // Validação de Campos
            if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo título é obrigatório."
                );
            }

            if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo descrição é obrigatório."
                );
            }

            TarefaEntity tarefa = new TarefaEntity();

            tarefa.setUsuario(dto.getUsuario());
            tarefa.setTitulo(dto.getTitulo());
            tarefa.setDescricao(dto.getDescricao());
            tarefa.setPrioridade(dto.getPrioridade());
            tarefa.setCompletada(dto.getCompletada());
            tarefa.setDataTarefa(dto.getDataTarefa());
            tarefa.setCriadaEm(dto.getCriadaEm());

            TarefaEntity tarefaSalva = tarefaRepository.save(tarefa);

            return TarefaDTO.builder()
                    .idTarefa(tarefaSalva.getIdTarefa())
                    .usuario(tarefaSalva.getUsuario())
                    .titulo(tarefaSalva.getTitulo())
                    .descricao(tarefaSalva.getDescricao())
                    .prioridade(tarefaSalva.getPrioridade())
                    .completada(tarefaSalva.getCompletada())
                    .dataTarefa(tarefaSalva.getDataTarefa())
                    .criadaEm(tarefaSalva.getCriadaEm())
                    .build();

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public TarefaDTO atualizarTarefa(Long id, TarefaDTO dto) throws DefaultExceptionHandler {
        try {

            // Validação de Campos
            if (dto.getTitulo() == null || dto.getTitulo().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo título é obrigatório."
                );
            }

            if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo descrição é obrigatório."
                );
            }

            TarefaEntity tarefa = tarefaRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Tarefa não encontrada para atualização."
                    ));

            tarefa.setTitulo(dto.getTitulo());
            tarefa.setDescricao(dto.getDescricao());
            tarefa.setPrioridade(dto.getPrioridade());
            tarefa.setCompletada(dto.getCompletada());
            tarefa.setDataTarefa(dto.getDataTarefa());

            TarefaEntity tarefaAtualizada = tarefaRepository.save(tarefa);

            return TarefaDTO.builder()
                    .idTarefa(tarefaAtualizada.getIdTarefa())
                    .usuario(tarefaAtualizada.getUsuario())
                    .titulo(tarefaAtualizada.getTitulo())
                    .descricao(tarefaAtualizada.getDescricao())
                    .prioridade(tarefaAtualizada.getPrioridade())
                    .completada(tarefaAtualizada.getCompletada())
                    .dataTarefa(tarefaAtualizada.getDataTarefa())
                    .criadaEm(tarefaAtualizada.getCriadaEm())
                    .build();

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public void deletarTarefa(Long id) throws DefaultExceptionHandler {
        try {

            if (!tarefaRepository.existsById(id)) {
                throw new DefaultExceptionHandler(
                        HttpStatus.NOT_FOUND.value(),
                        "Operação Inválida! Tarefa não encontrada para deletar."
                );
            }

            tarefaRepository.deleteById(id);

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public TarefaEntity buscarPorId(Long id) throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação inválida! Tarefa não encontrada."
                    ));
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<TarefaDTO> listarTarefasPorUsuario(Long idUsuario) throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findByUsuario(idUsuario)
                    .stream()
                    .map(tarefa -> TarefaDTO.builder()
                            .idTarefa(tarefa.getIdTarefa())
                            .usuario(tarefa.getUsuario())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .prioridade(tarefa.getPrioridade())
                            .completada(tarefa.getCompletada())
                            .dataTarefa(tarefa.getDataTarefa())
                            .criadaEm(tarefa.getCriadaEm())
                            .build()
                    )
                    .toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }


    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public TarefaDTO concluirTarefa(Long id) throws DefaultExceptionHandler {
        try {

            TarefaEntity tarefa = tarefaRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação inválida! Tarefa não encontrada."
                    ));

            tarefa.setCompletada(true);

            TarefaEntity tarefaAtualizada = tarefaRepository.save(tarefa);

            return TarefaDTO.builder()
                    .idTarefa(tarefaAtualizada.getIdTarefa())
                    .usuario(tarefaAtualizada.getUsuario())
                    .titulo(tarefaAtualizada.getTitulo())
                    .descricao(tarefaAtualizada.getDescricao())
                    .prioridade(tarefaAtualizada.getPrioridade())
                    .completada(tarefaAtualizada.getCompletada())
                    .dataTarefa(tarefaAtualizada.getDataTarefa())
                    .criadaEm(tarefaAtualizada.getCriadaEm())
                    .build();

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }


    @Transactional(rollbackFor = DefaultExceptionHandler.class)
    public TarefaDTO reabrirTarefa(Long id) throws DefaultExceptionHandler {
        try {

            TarefaEntity tarefa = tarefaRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação inválida! Tarefa não encontrada."
                    ));

            tarefa.setCompletada(false);

            TarefaEntity tarefaAtualizada = tarefaRepository.save(tarefa);

            return TarefaDTO.builder()
                    .idTarefa(tarefaAtualizada.getIdTarefa())
                    .usuario(tarefaAtualizada.getUsuario())
                    .titulo(tarefaAtualizada.getTitulo())
                    .descricao(tarefaAtualizada.getDescricao())
                    .prioridade(tarefaAtualizada.getPrioridade())
                    .completada(tarefaAtualizada.getCompletada())
                    .dataTarefa(tarefaAtualizada.getDataTarefa())
                    .criadaEm(tarefaAtualizada.getCriadaEm())
                    .build();

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<TarefaDTO> listarPendentes() throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findByCompletada(false)
                    .stream()
                    .map(tarefa -> TarefaDTO.builder()
                            .idTarefa(tarefa.getIdTarefa())
                            .usuario(tarefa.getUsuario())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .prioridade(tarefa.getPrioridade())
                            .completada(tarefa.getCompletada())
                            .dataTarefa(tarefa.getDataTarefa())
                            .criadaEm(tarefa.getCriadaEm())
                            .build()
                    )
                    .toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<TarefaDTO> listarAtrasadas() throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findByDataTarefaBeforeAndCompletada(
                            java.time.LocalDate.now(),
                            false
                    )
                    .stream()
                    .map(tarefa -> TarefaDTO.builder()
                            .idTarefa(tarefa.getIdTarefa())
                            .usuario(tarefa.getUsuario())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .prioridade(tarefa.getPrioridade())
                            .completada(tarefa.getCompletada())
                            .dataTarefa(tarefa.getDataTarefa())
                            .criadaEm(tarefa.getCriadaEm())
                            .build()
                    )
                    .toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<TarefaDTO> filtrarPorPrioridade(String prioridade) throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findByPrioridade(prioridade)
                    .stream()
                    .map(tarefa -> TarefaDTO.builder()
                            .idTarefa(tarefa.getIdTarefa())
                            .usuario(tarefa.getUsuario())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .prioridade(tarefa.getPrioridade())
                            .completada(tarefa.getCompletada())
                            .dataTarefa(tarefa.getDataTarefa())
                            .criadaEm(tarefa.getCriadaEm())
                            .build()
                    )
                    .toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<TarefaDTO> buscarPorData(LocalDate data) throws DefaultExceptionHandler {
        try {
            return tarefaRepository.findByDataTarefa(data)
                    .stream()
                    .map(tarefa -> TarefaDTO.builder()
                            .idTarefa(tarefa.getIdTarefa())
                            .usuario(tarefa.getUsuario())
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .prioridade(tarefa.getPrioridade())
                            .completada(tarefa.getCompletada())
                            .dataTarefa(tarefa.getDataTarefa())
                            .criadaEm(tarefa.getCriadaEm())
                            .build()
                    )
                    .toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }
}
