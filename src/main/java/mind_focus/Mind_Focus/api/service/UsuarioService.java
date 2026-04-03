package mind_focus.Mind_Focus.api.service;

import mind_focus.Mind_Focus.api.exceptions.DefaultExceptionHandler;
import mind_focus.Mind_Focus.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mind_focus.Mind_Focus.api.dto.UsuarioDTO;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Spring Security

    public List<UsuarioDTO> listarTodos() throws DefaultExceptionHandler {
        try {
            return usuarioRepository.findAll()
                    .stream()
                    .map(usuario -> UsuarioDTO.builder()
                            .idUsuario(usuario.getIdUsuario())
                            .nome(usuario.getNome())
                            .email(usuario.getEmail())
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
    public UsuarioDTO cadastrarUsuario(UsuarioDTO dto) throws DefaultExceptionHandler {
        try {

            // Validações
            if (dto.getNome() == null || dto.getNome().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo nome é obrigatório."
                );
            }

            if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo email é obrigatório."
                );
            }

            if (dto.getSenha() == null || dto.getSenha().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo senha é obrigatório."
                );
            }

            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação Inválida! Já existe um Usuário com este email."
                );
            }

            UsuarioEntity usuario = new UsuarioEntity();

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(passwordEncoder.encode(dto.getSenha())); // Criptografia de senha

            UsuarioEntity usuarioSalvo = usuarioRepository.save(usuario);

            return UsuarioDTO.builder()
                    .idUsuario(usuarioSalvo.getIdUsuario())
                    .nome(usuarioSalvo.getNome())
                    .email(usuarioSalvo.getEmail())
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
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO dto) throws DefaultExceptionHandler {
        try {

            // Validações
            if (dto.getNome() == null || dto.getNome().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo nome é obrigatório."
                );
            }

            if (dto.getEmail() == null || dto.getEmail().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo email é obrigatório."
                );
            }

            if (dto.getSenha() == null || dto.getSenha().isBlank()) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação inválida! Campo senha é obrigatório."
                );
            }

            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new DefaultExceptionHandler(
                        HttpStatus.BAD_REQUEST.value(),
                        "Operação Inválida! Já existe um Usuário com este email."
                );
            }

            UsuarioEntity usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Usuário não encontrado para atualização."
                    ));

            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());

            // Criptografa senha quando o usuário é atualizado
            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            }

            UsuarioEntity usuarioAtualizado = usuarioRepository.save(usuario);

            return UsuarioDTO.builder()
                    .idUsuario(usuarioAtualizado.getIdUsuario())
                    .nome(usuarioAtualizado.getNome())
                    .email(usuarioAtualizado.getEmail())
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
    public void deletarUsuario(Long id) throws DefaultExceptionHandler {
        try {

            if (!usuarioRepository.existsById(id)) {
                throw new DefaultExceptionHandler(
                        HttpStatus.NOT_FOUND.value(),
                        "Operação Inválida! Usuário não encontrado para deletar."
                );
            }

            usuarioRepository.deleteById(id);

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public UsuarioDTO buscarPorEmail(String email) throws DefaultExceptionHandler {
        try {
            return usuarioRepository.findByEmail(email)
                    .map(usuario -> UsuarioDTO.builder()
                            .idUsuario(usuario.getIdUsuario())
                            .nome(usuario.getNome())
                            .email(usuario.getEmail())
                            .build()
                    )
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação Inválida! Usuário não encontrado."
                    ));

        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public boolean existsByEmail(String email) throws DefaultExceptionHandler {
        try {
            return usuarioRepository.existsByEmail(email);
        } catch (Exception e) {
            if (e instanceof DefaultExceptionHandler) {
                throw e;
            } else {
                throw new DefaultExceptionHandler(e);
            }
        }
    }

    public UsuarioDTO buscarPorId(Long id) throws DefaultExceptionHandler {
        try {
            UsuarioEntity usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new DefaultExceptionHandler(
                            HttpStatus.NOT_FOUND.value(),
                            "Operação inválida! Usuário não encontrado."
                    ));

            return UsuarioDTO.builder()
                    .idUsuario(usuario.getIdUsuario())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
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
