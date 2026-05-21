package mind_focus.Mind_Focus.api.controller;

import mind_focus.Mind_Focus.api.dto.LoginRequestDTO;
import mind_focus.Mind_Focus.api.dto.LoginResponseDTO;
import mind_focus.Mind_Focus.api.dto.RefreshTokenDTO;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import mind_focus.Mind_Focus.api.repository.UsuarioRepository;
import mind_focus.Mind_Focus.api.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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

        String accessToken = jwtService.generateToken(usuario.getEmail());
        String refreshToken = jwtService.generateRefreshToken(usuario.getEmail());

        return new LoginResponseDTO(accessToken, refreshToken);

    }

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer_")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer_".length());
                String email = jwtService.extractUsernameFromRefreshToken(refreshToken);

                UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário do token não encontrado"));

                if (jwtService.isRefreshTokenValid(refreshToken, usuario.getEmail())) {
                    String novoAccessToken = jwtService.generateToken(usuario.getEmail());

                    RefreshTokenDTO responseDTO = new RefreshTokenDTO();
                    responseDTO.setAccessToken(novoAccessToken);

                    return ResponseEntity.ok(responseDTO);
                }

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh Token inválido ou expirado");

            } catch (Exception e) {
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh Token ausente");
        }
    }
}
