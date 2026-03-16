package mind_focus.Mind_Focus.api.dto;

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
    private UsuarioEntity usuario;
    private String conteudo;
    private LocalDateTime criadoEm;
}
