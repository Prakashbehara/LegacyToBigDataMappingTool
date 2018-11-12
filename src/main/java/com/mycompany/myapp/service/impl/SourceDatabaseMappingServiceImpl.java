package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SourceDatabaseMappingService;
import com.mycompany.myapp.domain.SourceDatabaseMapping;
import com.mycompany.myapp.repository.SourceDatabaseMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SourceDatabaseMapping.
 */
@Service
@Transactional
public class SourceDatabaseMappingServiceImpl implements SourceDatabaseMappingService {

    private final Logger log = LoggerFactory.getLogger(SourceDatabaseMappingServiceImpl.class);

    private final SourceDatabaseMappingRepository sourceDatabaseMappingRepository;

    public SourceDatabaseMappingServiceImpl(SourceDatabaseMappingRepository sourceDatabaseMappingRepository) {
        this.sourceDatabaseMappingRepository = sourceDatabaseMappingRepository;
    }

    /**
     * Save a sourceDatabaseMapping.
     *
     * @param sourceDatabaseMapping the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceDatabaseMapping save(SourceDatabaseMapping sourceDatabaseMapping) {
        log.debug("Request to save SourceDatabaseMapping : {}", sourceDatabaseMapping);
        return sourceDatabaseMappingRepository.save(sourceDatabaseMapping);
    }

    /**
     * Get all the sourceDatabaseMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SourceDatabaseMapping> findAll() {
        log.debug("Request to get all SourceDatabaseMappings");
        return sourceDatabaseMappingRepository.findAll();
    }


    /**
     * Get one sourceDatabaseMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SourceDatabaseMapping> findOne(Long id) {
        log.debug("Request to get SourceDatabaseMapping : {}", id);
        return sourceDatabaseMappingRepository.findById(id);
    }

    /**
     * Delete the sourceDatabaseMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourceDatabaseMapping : {}", id);
        sourceDatabaseMappingRepository.deleteById(id);
    }
}
