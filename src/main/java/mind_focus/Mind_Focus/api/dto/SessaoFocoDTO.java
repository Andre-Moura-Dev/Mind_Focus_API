package mind_focus.Mind_Focus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long idUsuario;
    private Integer duracaoMinutos;
    private Integer humorApos;
    private LocalDate dataSessao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime criadaEm;
}
