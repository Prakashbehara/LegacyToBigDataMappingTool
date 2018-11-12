package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DataModelService;
import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.repository.DataModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DataModel.
 */
@Service
@Transactional
public class DataModelServiceImpl implements DataModelService {

    private final Logger log = LoggerFactory.getLogger(DataModelServiceImpl.class);

    private final DataModelRepository dataModelRepository;

    public DataModelServiceImpl(DataModelRepository dataModelRepository) {
        this.dataModelRepository = dataModelRepository;
    }

    /**
     * Save a dataModel.
     *
     * @param dataModel the entity to save
     * @return the persisted entity
     */
    @Override
    public DataModel save(DataModel dataModel) {
        log.debug("Request to save DataModel : {}", dataModel);
        return dataModelRepository.save(dataModel);
    }

    /**
     * Get all the dataModels.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataModel> findAll() {
        log.debug("Request to get all DataModels");
        return dataModelRepository.findAll();
    }


    /**
     * Get one dataModel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataModel> findOne(Long id) {
        log.debug("Request to get DataModel : {}", id);
        return dataModelRepository.findById(id);
    }

    /**
     * Delete the dataModel by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataModel : {}", id);
        dataModelRepository.deleteById(id);
    }
}
