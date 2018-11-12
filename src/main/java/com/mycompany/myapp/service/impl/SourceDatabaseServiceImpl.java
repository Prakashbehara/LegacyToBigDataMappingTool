package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.SourceDatabaseService;
import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.repository.SourceDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SourceDatabase.
 */
@Service
@Transactional
public class SourceDatabaseServiceImpl implements SourceDatabaseService {

    private final Logger log = LoggerFactory.getLogger(SourceDatabaseServiceImpl.class);

    private final SourceDatabaseRepository sourceDatabaseRepository;

    public SourceDatabaseServiceImpl(SourceDatabaseRepository sourceDatabaseRepository) {
        this.sourceDatabaseRepository = sourceDatabaseRepository;
    }

    /**
     * Save a sourceDatabase.
     *
     * @param sourceDatabase the entity to save
     * @return the persisted entity
     */
    @Override
    public SourceDatabase save(SourceDatabase sourceDatabase) {
        log.debug("Request to save SourceDatabase : {}", sourceDatabase);
        return sourceDatabaseRepository.save(sourceDatabase);
    }

    /**
     * Get all the sourceDatabases.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SourceDatabase> findAll() {
        log.debug("Request to get all SourceDatabases");
        return sourceDatabaseRepository.findAll();
    }


    /**
     * Get one sourceDatabase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SourceDatabase> findOne(Long id) {
        log.debug("Request to get SourceDatabase : {}", id);
        return sourceDatabaseRepository.findById(id);
    }

    /**
     * Delete the sourceDatabase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourceDatabase : {}", id);
        sourceDatabaseRepository.deleteById(id);
    }
}
