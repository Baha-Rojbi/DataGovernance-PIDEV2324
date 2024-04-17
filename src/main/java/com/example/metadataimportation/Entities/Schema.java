package com.example.metadataimportation.Entities;

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


    @ElementCollection // This annotation is used to denote a collection of simple elements
    @CollectionTable(name = "schema_tags", joinColumns = @JoinColumn(name = "id_schema")) // This specifies the table that stores the collection
    @Column(name = "tag") // Name of the column that stores the tags
    private Set<String> tags = new HashSet<>(); // Using a Set to avoid duplicate tags

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_table")
    private DataTable parentDataTable;



    public Long getIdSchema() {
        return idSchema;
    }

    public void setIdSchema(Long idSchema) {
        this.idSchema = idSchema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public DataTable getParentDataTable() {
        return parentDataTable;
    }

    public void setParentDataTable(DataTable parentDataTable) {
        this.parentDataTable = parentDataTable;
    }
}
