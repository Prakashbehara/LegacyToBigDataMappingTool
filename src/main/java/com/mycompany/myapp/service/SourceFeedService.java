package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SourceFeed;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SourceFeed.
 */
public interface SourceFeedService {

    /**
     * Save a sourceFeed.
     *
     * @param sourceFeed the entity to save
     * @return the persisted entity
     */
    SourceFeed save(SourceFeed sourceFeed);

    /**
     * Get all the sourceFeeds.
     *
     * @return the list of entities
     */
    List<SourceFeed> findAll();


    /**
     * Get the "id" sourceFeed.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SourceFeed> findOne(Long id);

    /**
     * Delete the "id" sourceFeed.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
