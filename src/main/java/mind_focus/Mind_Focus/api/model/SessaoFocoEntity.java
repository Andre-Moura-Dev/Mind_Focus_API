package mind_focus.Mind_Focus.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Sessoes_Foco")
public class SessaoFocoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sessao_foco")
    private Long idSessaoFoco;

    @ManyToOne
    @JoinColumn(
            name = "id_usuario",
            referencedColumnName = "id_usuario",
            nullable = false
    )
    private UsuarioEntity usuario;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    @Column(name = "humor_apos", nullable = false)
    private Integer humorApos;

    @Column(name = "data_sessao", nullable = false)
    private LocalDate dataSessao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "criada_em", nullable = false)
    private LocalDateTime criadaEm;

    @PrePersist
    public void prePersist() {
        this.criadaEm = LocalDateTime.now();
    }
}
