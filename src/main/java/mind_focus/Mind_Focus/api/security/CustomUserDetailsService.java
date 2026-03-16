package mind_focus.Mind_Focus.api.security;

import lombok.RequiredArgsConstructor;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;
import mind_focus.Mind_Focus.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário não encontrado.");
                });

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities("ROLE_USER")
                .build();
    }
}
