package mind_focus.Mind_Focus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    private Long idUsuario;
    private String nome;
    private String email;

    // Senha não aparece quando lista e cadastro o usuário
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;
}
