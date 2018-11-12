package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SourceDatabase;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SourceDatabase.
 */
public interface SourceDatabaseService {

    /**
     * Save a sourceDatabase.
     *
     * @param sourceDatabase the entity to save
     * @return the persisted entity
     */
    SourceDatabase save(SourceDatabase sourceDatabase);

    /**
     * Get all the sourceDatabases.
     *
     * @return the list of entities
     */
    List<SourceDatabase> findAll();


    /**
     * Get the "id" sourceDatabase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SourceDatabase> findOne(Long id);

    /**
     * Delete the "id" sourceDatabase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
