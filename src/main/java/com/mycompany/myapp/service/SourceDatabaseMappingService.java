package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SourceDatabaseMapping;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SourceDatabaseMapping.
 */
public interface SourceDatabaseMappingService {

    /**
     * Save a sourceDatabaseMapping.
     *
     * @param sourceDatabaseMapping the entity to save
     * @return the persisted entity
     */
    SourceDatabaseMapping save(SourceDatabaseMapping sourceDatabaseMapping);

    /**
     * Get all the sourceDatabaseMappings.
     *
     * @return the list of entities
     */
    List<SourceDatabaseMapping> findAll();


    /**
     * Get the "id" sourceDatabaseMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SourceDatabaseMapping> findOne(Long id);

    /**
     * Delete the "id" sourceDatabaseMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
