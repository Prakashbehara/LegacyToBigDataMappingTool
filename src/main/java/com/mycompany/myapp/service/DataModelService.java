package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DataModel;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DataModel.
 */
public interface DataModelService {

    /**
     * Save a dataModel.
     *
     * @param dataModel the entity to save
     * @return the persisted entity
     */
    DataModel save(DataModel dataModel);

    /**
     * Get all the dataModels.
     *
     * @return the list of entities
     */
    List<DataModel> findAll();


    /**
     * Get the "id" dataModel.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DataModel> findOne(Long id);

    /**
     * Delete the "id" dataModel.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
