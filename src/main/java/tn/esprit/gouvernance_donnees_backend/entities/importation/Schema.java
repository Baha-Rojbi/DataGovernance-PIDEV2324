package tn.esprit.gouvernance_donnees_backend.entities.importation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.esprit.gouvernance_donnees_backend.entities.importation.DataTable;


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
    // Lineage information

    private String sourceColumn; // Originating column name
    private String sourceTable; // Originating table name
    @ElementCollection // This annotation is used to denote a collection of simple elements
    @CollectionTable(name = "schema_tags", joinColumns = @JoinColumn(name = "id_schema")) // This specifies the table that stores the collection
    @Column(name = "tag") // Name of the column that stores the tags
    private Set<String> tags = new HashSet<>(); // Using a Set to avoid duplicate tags

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_table")
    private DataTable parentDataTable;
}
