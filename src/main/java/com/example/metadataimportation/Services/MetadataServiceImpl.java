package com.example.metadataimportation.Services;

import com.example.metadataimportation.Entities.DataTable;
import com.example.metadataimportation.Entities.Metadata;
import com.example.metadataimportation.Repositories.MetadataRepository;
import com.example.metadataimportation.Repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    private MetadataRepository metadataRepository;
    @Autowired
    private TableRepository tableRepository;

    @Override
    public Metadata createMetadata(Long dataTableId, Metadata metadata) {
        DataTable dataTable = tableRepository.findById(dataTableId).orElseThrow(() -> new RuntimeException("DataTable not found")); // Handle not found scenario as needed
        metadata.setDataTable(dataTable);
        metadata.setFormat(dataTable.getSource());
        dataTable.setMetadata(metadata);
        return metadataRepository.save(metadata);
    }


    @Override
    public Optional<Metadata> findMetadataByDataTableId(Long dataTableId) {
        DataTable dataTable = tableRepository.findById(dataTableId)
                .orElseThrow(() -> new RuntimeException("DataTable not found"));

        if (dataTable.getMetadata() != null) {
            return metadataRepository.findById(dataTable.getMetadata().getId());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Metadata> getAllMetadata() {
        return metadataRepository.findAll();
    }

    public Map<String, Long> keywordFrequency() {
        return metadataRepository.findAll().stream()
                .flatMap(metadata -> Arrays.stream(metadata.getKeywords().split(", ")))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }



    public void getDeleteMetadataById(Long id) {
         metadataRepository.deleteById(id);
    }

}