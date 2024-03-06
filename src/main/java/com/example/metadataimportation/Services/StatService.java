package com.example.metadataimportation.Services;

import com.example.metadataimportation.Repositories.SchemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class StatService {

    @Autowired
    private SchemaRepository schemaRepository;

    public List<Object[]> countTags() {
        return schemaRepository.countTags();
    }
    public List<Object[]> countByName() {
        return schemaRepository.countByName();
    }
}

