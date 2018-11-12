package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DataModelMappingService;
import com.mycompany.myapp.domain.DataModelMapping;
import com.mycompany.myapp.repository.DataModelMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DataModelMapping.
 */
@Service
@Transactional
public class DataModelMappingServiceImpl implements DataModelMappingService {

    private final Logger log = LoggerFactory.getLogger(DataModelMappingServiceImpl.class);

    private final DataModelMappingRepository dataModelMappingRepository;

    public DataModelMappingServiceImpl(DataModelMappingRepository dataModelMappingRepository) {
        this.dataModelMappingRepository = dataModelMappingRepository;
    }

    /**
     * Save a dataModelMapping.
     *
     * @param dataModelMapping the entity to save
     * @return the persisted entity
     */
    @Override
    public DataModelMapping save(DataModelMapping dataModelMapping) {
        log.debug("Request to save DataModelMapping : {}", dataModelMapping);
        return dataModelMappingRepository.save(dataModelMapping);
    }

    /**
     * Get all the dataModelMappings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataModelMapping> findAll() {
        log.debug("Request to get all DataModelMappings");
        return dataModelMappingRepository.findAll();
    }


    /**
     * Get one dataModelMapping by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataModelMapping> findOne(Long id) {
        log.debug("Request to get DataModelMapping : {}", id);
        return dataModelMappingRepository.findById(id);
    }

    /**
     * Delete the dataModelMapping by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataModelMapping : {}", id);
        dataModelMappingRepository.deleteById(id);
    }
}
