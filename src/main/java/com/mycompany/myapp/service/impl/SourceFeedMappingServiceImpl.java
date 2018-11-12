package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SourceFeedMappingService;
import com.mycompany.myapp.domain.SourceFeedMapping;
import com.mycompany.myapp.repository.SourceFeedMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SourceFeedMapping.
 */
@Service
@Transactional
public class SourceFeedMappingServiceImpl implements SourceFeedMappingService {

    private final Logger log = LoggerFactory.getLogger(SourceFeedMappingServiceImpl.class);

    private final SourceFeedMappingRepository sourceFeedMappingRepository;

    public SourceFeedMappingServiceImpl(SourceFeedMappingRepository sourceFeedMappingRepository) {
        this.sourceFeedMappingRepository = sourceFeedMappingRepository;
    }

    /**
     * Save a sourceFeedMapping.
     *
     * @param sourceFeedMapping the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceFeedMapping save(SourceFeedMapping sourceFeedMapping) {
        log.debug("Request to save SourceFeedMapping : {}", sourceFeedMapping);
        return sourceFeedMappingRepository.save(sourceFeedMapping);
    }

    /**
     * Get all the sourceFeedMappings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SourceFeedMapping> findAll(Pageable pageable) {
        log.debug("Request to get all SourceFeedMappings");
        return sourceFeedMappingRepository.findAll(pageable);
    }

    /**
     * Get all the SourceFeedMapping with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<SourceFeedMapping> findAllWithEagerRelationships(Pageable pageable) {
        return sourceFeedMappingRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one sourceFeedMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SourceFeedMapping> findOne(Long id) {
        log.debug("Request to get SourceFeedMapping : {}", id);
        return sourceFeedMappingRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the sourceFeedMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourceFeedMapping : {}", id);
        sourceFeedMappingRepository.deleteById(id);
    }
}
