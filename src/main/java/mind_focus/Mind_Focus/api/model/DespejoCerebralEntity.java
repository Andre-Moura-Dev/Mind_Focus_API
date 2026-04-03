package mind_focus.Mind_Focus.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Despejos_Cerebrais")
public class DespejoCerebralEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despejo_cerebral")
    private Long idDespejoCerebral;

    @ManyToOne
    @JoinColumn(
            name = "id_usuario",
            referencedColumnName = "id_usuario",
            nullable = false
    )
    private UsuarioEntity usuario;

    @Column(name = "conteudo", nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
