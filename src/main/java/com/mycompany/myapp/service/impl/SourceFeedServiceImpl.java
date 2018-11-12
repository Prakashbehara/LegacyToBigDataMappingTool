package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SourceFeedService;
import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.repository.SourceFeedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SourceFeed.
 */
@Service
@Transactional
public class SourceFeedServiceImpl implements SourceFeedService {

    private final Logger log = LoggerFactory.getLogger(SourceFeedServiceImpl.class);

    private final SourceFeedRepository sourceFeedRepository;

    public SourceFeedServiceImpl(SourceFeedRepository sourceFeedRepository) {
        this.sourceFeedRepository = sourceFeedRepository;
    }

    /**
     * Save a sourceFeed.
     *
     * @param sourceFeed the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceFeed save(SourceFeed sourceFeed) {
        log.debug("Request to save SourceFeed : {}", sourceFeed);
        return sourceFeedRepository.save(sourceFeed);
    }

    /**
     * Get all the sourceFeeds.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SourceFeed> findAll() {
        log.debug("Request to get all SourceFeeds");
        return sourceFeedRepository.findAll();
    }


    /**
     * Get one sourceFeed by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SourceFeed> findOne(Long id) {
        log.debug("Request to get SourceFeed : {}", id);
        return sourceFeedRepository.findById(id);
    }

    /**
     * Delete the sourceFeed by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourceFeed : {}", id);
        sourceFeedRepository.deleteById(id);
    }
}
