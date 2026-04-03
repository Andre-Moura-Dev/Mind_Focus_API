package mind_focus.Mind_Focus.api.service;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import mind_focus.Mind_Focus.api.repository.SessaoFocoRepository;
import mind_focus.Mind_Focus.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mind_focus.Mind_Focus.api.dto.SessaoFocoDTO;
import mind_focus.Mind_Focus.api.model.SessaoFocoEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class SessaoFocoService {

    @Autowired
    private SessaoFocoRepository sessaoFocoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<SessaoFocoDTO> listarTodos() throws DefaultExceptionHandler {
        try {
            return sessaoFocoRepository.findAll()
                    .stream()
                    .map(sessaoFoco -> SessaoFocoDTO.builder()
                            .idSessaoFoco(sessaoFoco.getIdSessaoFoco())
                            .idUsuario(sessaoFoco.getUsuario().getIdUsuario())
                            .duracaoMinutos(sessaoFoco.getDuracaoMinutos())
                            .humorApos(sessaoFoco.getHumorApos())
                            .dataSessao(sessaoFoco.getDataSessao())
                            .criadaEm(sessaoFoco.getCriadaEm())
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
    public SessaoFocoDTO cadastrarSessaoFoco(SessaoFocoDTO dto) throws DefaultExceptionHandler {
        try {

            if (dto.getDuracaoMinutos() <= 0) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Duração deve ser maior que zero."
                );
            }

            if (dto.getHumorApos() < 1 || dto.getHumorApos() > 5) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Humor deve estar entre 1 e 5."
                );
            }

            UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação inválida! Usuário não encontrado"
                    ));

            SessaoFocoEntity sessaoFoco = new SessaoFocoEntity();

            sessaoFoco.setUsuario(usuario);
            sessaoFoco.setDuracaoMinutos(dto.getDuracaoMinutos());
            sessaoFoco.setHumorApos(dto.getHumorApos());
            sessaoFoco.setDataSessao(dto.getDataSessao());

            SessaoFocoEntity sessaoFocoSalva = sessaoFocoRepository.save(sessaoFoco);

            return SessaoFocoDTO.builder()
                    .idSessaoFoco(sessaoFocoSalva.getIdSessaoFoco())
                    .idUsuario(sessaoFocoSalva.getUsuario().getIdUsuario())
                    .duracaoMinutos(sessaoFocoSalva.getDuracaoMinutos())
                    .humorApos(sessaoFocoSalva.getHumorApos())
                    .dataSessao(sessaoFocoSalva.getDataSessao())
                    .criadaEm(sessaoFocoSalva.getCriadaEm())
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
    public SessaoFocoDTO atualizarSessaoFoco(Long id, SessaoFocoDTO dto) throws DefaultExceptionHandler {
        try {

            if (dto.getDuracaoMinutos() <= 0) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Duração deve ser maior que zero."
                );
            }

            if (dto.getHumorApos() < 1 || dto.getHumorApos() > 5) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Humor deve estar entre 1 e 5."
                );
            }

            UsuarioEntity usuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação inválida! Usuário não encontrado"
                    ));

            SessaoFocoEntity sessaoFoco = sessaoFocoRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Sessão Foco não encontrado para atualização."
                    ));

            sessaoFoco.setUsuario(usuario);
            sessaoFoco.setDuracaoMinutos(dto.getDuracaoMinutos());
            sessaoFoco.setHumorApos(dto.getHumorApos());
            sessaoFoco.setDataSessao(dto.getDataSessao());
            sessaoFoco.setCriadaEm(sessaoFoco.getCriadaEm());

            SessaoFocoEntity sessaoFocoAtualizada = sessaoFocoRepository.save(sessaoFoco);

            return SessaoFocoDTO.builder()
                    .idSessaoFoco(sessaoFocoAtualizada.getIdSessaoFoco())
                    .idUsuario(sessaoFocoAtualizada.getUsuario().getIdUsuario())
                    .duracaoMinutos(sessaoFocoAtualizada.getDuracaoMinutos())
                    .humorApos(sessaoFocoAtualizada.getHumorApos())
                    .dataSessao(sessaoFocoAtualizada.getDataSessao())
                    .criadaEm(sessaoFocoAtualizada.getCriadaEm())
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
    public void deletarSessaoFoco(Long id) throws DefaultExceptionHandler {
        try {

            if (!sessaoFocoRepository.existsById(id)) {
                throw new DefaultExceptionHandler(
                        HttpStatus.NOT_FOUND.value(),
                        "Operação Inválida! Sessão foco não encontrada para deletar."
                );
            }

            sessaoFocoRepository.deleteById(id);

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public SessaoFocoEntity buscarPorId(Long id) throws DefaultExceptionHandler {
        try {
            return sessaoFocoRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Sessão Foco não encontrada."
                    ));
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<SessaoFocoDTO> listarSessoesFocoPorUsuario(Long idUsuario) throws DefaultExceptionHandler {
        try {
            return sessaoFocoRepository.findByUsuario_IdUsuario(idUsuario)
                    .stream()
                    .map(sessaoFoco -> SessaoFocoDTO.builder()
                            .idSessaoFoco(sessaoFoco.getIdSessaoFoco())
                            .idUsuario(sessaoFoco.getUsuario().getIdUsuario())
                            .duracaoMinutos(sessaoFoco.getDuracaoMinutos())
                            .humorApos(sessaoFoco.getHumorApos())
                            .dataSessao(sessaoFoco.getDataSessao())
                            .build()
                    ).toList();
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public List<SessaoFocoDTO> listarPorData(LocalDate data) throws DefaultExceptionHandler {
        try {
            return sessaoFocoRepository.findByDataSessao(data)
                    .stream()
                    .map(sessaoFoco -> SessaoFocoDTO.builder()
                            .idSessaoFoco(sessaoFoco.getIdSessaoFoco())
                            .idUsuario(sessaoFoco.getUsuario().getIdUsuario())
                            .duracaoMinutos(sessaoFoco.getDuracaoMinutos())
                            .humorApos(sessaoFoco.getHumorApos())
                            .dataSessao(sessaoFoco.getDataSessao())
                            .criadaEm(sessaoFoco.getCriadaEm())
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

    public Integer totalMinutosFoco(Long idUsuario) throws DefaultExceptionHandler {
        try {
            return sessaoFocoRepository.sumDuracaoByUsuario(idUsuario);
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }
}
