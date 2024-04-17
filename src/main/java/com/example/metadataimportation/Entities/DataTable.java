package com.example.metadataimportation.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "metadata") // Exclude metadata field from toString
@Entity
public class DataTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTable;
    private String name;
    private String source;
    private String description;
    private LocalDateTime creationDate;
    private Double size;
    private String creator;

    @JsonIgnore
    @OneToMany(mappedBy = "parentDataTable", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Schema> schemas;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Long getIdTable() {
        return idTable;
    }

    public void setIdTable(Long idTable) {
        this.idTable = idTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Set<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(Set<Schema> schemas) {
        this.schemas = schemas;
    }

    @Override
    public String toString() {
        return "DataTable{" +
                "idTable=" + idTable +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", size=" + size +
                ", creator='" + creator + '\'' +
                ", schemas=" + schemas +
                ", metadata=" + metadata +
                '}';
    }
}
