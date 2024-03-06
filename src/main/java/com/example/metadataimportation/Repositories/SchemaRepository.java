package com.example.metadataimportation.Repositories;

import com.example.metadataimportation.Entities.DataTable;
import com.example.metadataimportation.Entities.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchemaRepository extends JpaRepository<Schema,Long> {
    void deleteByParentDataTable(DataTable fileInfo);
    //Repository
    @Query(value = "SELECT t.tag AS tag, COUNT(t.tag) AS count FROM schema_tags t GROUP BY t.tag", nativeQuery = true)
    List<Object[]> countTags();
    @Query("SELECT s.name, COUNT(s.name) FROM Schema s GROUP BY s.name")
    List<Object[]> countByName();

}
