package mind_focus.Mind_Focus.api.service;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.repository.DespejoCerebralRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mind_focus.Mind_Focus.api.dto.DespejoCerebralDTO;
import mind_focus.Mind_Focus.api.model.DespejoCerebralEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class DespejoCerebralService {

    @Autowired
    private DespejoCerebralRepository despejoCerebralRepository;

    public List<DespejoCerebralDTO> listarTodos() throws DefaultExceptionHandler {
        try {
            return despejoCerebralRepository.findAll()
                    .stream()
                    .map(despejoCerebral -> DespejoCerebralDTO.builder()
                            .idDespejoCerebral(despejoCerebral.getIdDespejoCerebral())
                            .usuario(despejoCerebral.getUsuario())
                            .conteudo(despejoCerebral.getConteudo())
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
    public DespejoCerebralDTO cadastrarDespejoCerebral(DespejoCerebralDTO dto) throws DefaultExceptionHandler {
        try {

            if (dto.getConteudo() == null || dto.getConteudo().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo conteúdo é obrigatório."
                );
            }

            DespejoCerebralEntity despejoCerebral = new DespejoCerebralEntity();

            despejoCerebral.setUsuario(dto.getUsuario());
            despejoCerebral.setConteudo(dto.getConteudo());

            DespejoCerebralEntity despejoCerebralSalvo = despejoCerebralRepository.save(despejoCerebral);

            return DespejoCerebralDTO.builder()
                    .idDespejoCerebral(despejoCerebralSalvo.getIdDespejoCerebral())
                    .usuario(despejoCerebralSalvo.getUsuario())
                    .conteudo(despejoCerebralSalvo.getConteudo())
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
    public DespejoCerebralDTO atualizarDespejoCerebral(Long id, DespejoCerebralDTO dto) throws DefaultExceptionHandler {
        try {

            if (dto.getConteudo() == null || dto.getConteudo().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo conteúdo é obrigatório."
                );
            }

            DespejoCerebralEntity despejoCerebral = despejoCerebralRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Despejo Cerebral não encontrado para atualziação."
                    ));

            despejoCerebral.setUsuario(dto.getUsuario());
            despejoCerebral.setConteudo(dto.getConteudo());

            DespejoCerebralEntity despejoCerebralAtualizado = despejoCerebralRepository.save(despejoCerebral);

            return DespejoCerebralDTO.builder()
                    .idDespejoCerebral(despejoCerebralAtualizado.getIdDespejoCerebral())
                    .usuario(despejoCerebralAtualizado.getUsuario())
                    .conteudo(despejoCerebralAtualizado.getConteudo())
                    .build();

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public void deletarDespejoCerebral(Long id) throws DefaultExceptionHandler {
        try {

            if (!despejoCerebralRepository.existsById(id)) {
                throw new DefaultExceptionHandler(
                        HttpStatus.NOT_FOUND.value(),
                        "Operação Inválida! Despejo Cerebral não encontrado para deletar."
                );
            }

            despejoCerebralRepository.deleteById(id);

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public DespejoCerebralEntity buscarPorId(Long id) throws DefaultExceptionHandler {
        try {
            DespejoCerebralEntity despejoCerebral = despejoCerebralRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Despejo Cerebral não encontrado."
                    ));

            return DespejoCerebralEntity.builder()
                    .idDespejoCerebral(despejoCerebral.getIdDespejoCerebral())
                    .usuario(despejoCerebral.getUsuario())
                    .conteudo(despejoCerebral.getConteudo())
                    .build();

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }
}
