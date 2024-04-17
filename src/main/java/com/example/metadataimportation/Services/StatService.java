package com.example.metadataimportation.Services;

import com.example.metadataimportation.Entities.Schema;
import com.example.metadataimportation.Repositories.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service

public class StatService {

    @Autowired
    private SchemaRepository schemaRepository;

    public List<Object[]> countTags() {
        return schemaRepository.countTags();
    }
    public List<Object[]> countByName() {
        return schemaRepository.countByNameWithCount();
    }

    public Long countColumns() {
        return schemaRepository.count();
    }

    public Map<String, Integer> displayTags() {
        List<Schema> schemas = schemaRepository.findAll();
        Map<String, Integer> tagCounts = new HashMap<>();
        for (Schema schema : schemas) {
            Set<String> tags = schema.getTags();
            for (String tag : tags) {
                tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
            }
        }
        return tagCounts;
    }
}

