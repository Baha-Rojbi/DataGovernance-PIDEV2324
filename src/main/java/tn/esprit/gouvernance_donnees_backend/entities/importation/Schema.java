package tn.esprit.gouvernance_donnees_backend.entities.importation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Schema implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSchema;
    private String name;
    private String type;
    private String description;
    @ElementCollection // This assumes tags are stored as a collection of simple elements
    @CollectionTable(name = "schema_tags", joinColumns = @JoinColumn(name = "id_schema") )
    @Column(name = "tag" )
    private Set<String> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_table")
    private DataTable parentDataTable;
}
