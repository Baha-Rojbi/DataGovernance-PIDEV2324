package com.example.metadataimportation.Repositories;

import com.example.metadataimportation.Entities.DataTable;
import com.example.metadataimportation.Entities.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface  MetadataRepository extends JpaRepository<Metadata,Long> {
    Metadata findByDataTable_IdTable(Long idTable);

}
