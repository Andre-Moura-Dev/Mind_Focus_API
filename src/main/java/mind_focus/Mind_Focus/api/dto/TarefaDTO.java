package mind_focus.Mind_Focus.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import mind_focus.Mind_Focus.api.enums.Prioridade;
import mind_focus.Mind_Focus.api.model.UsuarioEntity;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarefaDTO {

    private Long idTarefa;
    private Long idUsuario;
    private String titulo;
    private String descricao;
    private Prioridade prioridade = Prioridade.MEDIA;
    private Boolean completada = false;
    private LocalDate dataTarefa;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime criadaEm;
}
