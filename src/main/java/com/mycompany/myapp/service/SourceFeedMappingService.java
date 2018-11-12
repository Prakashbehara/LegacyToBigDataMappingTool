package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SourceFeedMapping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SourceFeedMapping.
 */
public interface SourceFeedMappingService {

    /**
     * Save a sourceFeedMapping.
     *
     * @param sourceFeedMapping the entity to save
     * @return the persisted entity
     */
    SourceFeedMapping save(SourceFeedMapping sourceFeedMapping);

    /**
     * Get all the sourceFeedMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SourceFeedMapping> findAll(Pageable pageable);

    /**
     * Get all the SourceFeedMapping with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<SourceFeedMapping> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" sourceFeedMapping.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SourceFeedMapping> findOne(Long id);

    /**
     * Delete the "id" sourceFeedMapping.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
