package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.DataAsset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing DataAsset.
 */
public interface DataAssetService {

    /**
     * Save a dataAsset.
     *
     * @param dataAsset the entity to save
     * @return the persisted entity
     */
    DataAsset save(DataAsset dataAsset);

    /**
     * Get all the dataAssets.
     *
     * @return the list of entities
     */
    List<DataAsset> findAll();

    /**
     * Get all the DataAsset with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<DataAsset> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" dataAsset.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DataAsset> findOne(Long id);

    /**
     * Delete the "id" dataAsset.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
