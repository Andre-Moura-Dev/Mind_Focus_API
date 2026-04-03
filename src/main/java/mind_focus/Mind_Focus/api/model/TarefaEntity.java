package mind_focus.Mind_Focus.api.model;

import lombok.*;
import jakarta.persistence.*;
import mind_focus.Mind_Focus.api.enums.Prioridade;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tarefas")
public class TarefaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarefa")
    private Long idTarefa;

    @ManyToOne
    @JoinColumn(
            name = "id_usuario",
            referencedColumnName = "id_usuario",
            nullable = false
    )
    private UsuarioEntity usuario;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false, length = 50)
    private Prioridade prioridade = Prioridade.MEDIA;

    @Column(name = "completada", nullable = false)
    private Boolean completada = false;

    @Column(name = "data_tarefa", nullable = false)
    private LocalDate dataTarefa;

    @Column(name = "criada_em", nullable = false)
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        this.criadaEm = LocalDateTime.now();
    }
}
