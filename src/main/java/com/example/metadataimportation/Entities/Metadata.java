package com.example.metadataimportation.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Metadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String accessRights;
    private String notes;
    private String context;
    private String keywords;
    private String format;
    private String dataType;
    private String status;
    private boolean sensitive;

    @JsonIgnore
    @OneToOne(mappedBy = "metadata", cascade = CascadeType.ALL)
    private DataTable dataTable;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", accessRights='" + accessRights + '\'' +
                ", notes='" + notes + '\'' +
                ", context='" + context + '\'' +
                ", keywords='" + keywords + '\'' +
                ", format='" + format + '\'' +
                ", dataType='" + dataType + '\'' +
                ", status='" + status + '\'' +
                ", sensitive=" + sensitive +
                ", dataTable=" + dataTable +
                '}';
    }
}