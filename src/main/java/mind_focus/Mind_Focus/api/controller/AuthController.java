package mind_focus.Mind_Focus.api.controller;

import mind_focus.Mind_Focus.api.dto.LoginRequestDTO;
import mind_focus.Mind_Focus.api.dto.LoginResponseDTO;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import mind_focus.Mind_Focus.api.repository.UsuarioRepository;
import mind_focus.Mind_Focus.api.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO request) {

        UsuarioEntity usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        return new LoginResponseDTO(token);
    }
}
