package com.example.metadataimportation.Services;

import com.example.metadataimportation.Entities.Metadata;

import java.util.List;
import java.util.Optional;

public interface MetadataService {
     Metadata createMetadata(Long dataTableId, Metadata metadata) ;
     Optional<Metadata> findMetadataByDataTableId(Long id);
     List<Metadata> getAllMetadata() ;
    }
