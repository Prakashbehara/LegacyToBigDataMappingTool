package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DataModelMapping;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DataModelMapping.
 */
public interface DataModelMappingService {

    /**
     * Save a dataModelMapping.
     *
     * @param dataModelMapping the entity to save
     * @return the persisted entity
     */
    DataModelMapping save(DataModelMapping dataModelMapping);

    /**
     * Get all the dataModelMappings.
     *
     * @return the list of entities
     */
    List<DataModelMapping> findAll();


    /**
     * Get the "id" dataModelMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DataModelMapping> findOne(Long id);

    /**
     * Delete the "id" dataModelMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
