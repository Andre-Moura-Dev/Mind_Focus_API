package mind_focus.Mind_Focus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DespejoCerebralDTO {

    private Long idDespejoCerebral;
    private Long idUsuario;
    private String conteudo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime criadoEm;
}
