package mind_focus.Mind_Focus.api.dto;

import lombok.*;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessaoFocoDTO {

    private Long idSessaoFoco;
    private UsuarioEntity usuario;
    private Integer duracaoMinutos;
    private Integer humorApos;
    private LocalDate dataSessao;
    private LocalDateTime criadaEm;
}
