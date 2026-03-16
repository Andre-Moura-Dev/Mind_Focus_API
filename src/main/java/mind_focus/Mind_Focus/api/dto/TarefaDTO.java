package mind_focus.Mind_Focus.api.dto;

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
    private UsuarioEntity usuario;
    private String titulo;
    private String descricao;
    private Prioridade prioridade;
    private Boolean completada = false;
    private LocalDate dataTarefa;
    private LocalDateTime criadaEm;
}
