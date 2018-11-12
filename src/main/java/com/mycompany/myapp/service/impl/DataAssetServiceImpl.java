package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.DataAssetService;
import com.mycompany.myapp.domain.DataAsset;
import com.mycompany.myapp.repository.DataAssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing DataAsset.
 */
@Service
@Transactional
public class DataAssetServiceImpl implements DataAssetService {

    private final Logger log = LoggerFactory.getLogger(DataAssetServiceImpl.class);

    private final DataAssetRepository dataAssetRepository;

    public DataAssetServiceImpl(DataAssetRepository dataAssetRepository) {
        this.dataAssetRepository = dataAssetRepository;
    }

    /**
     * Save a dataAsset.
     *
     * @param dataAsset the entity to save
     * @return the persisted entity
     */
    @Override
    public DataAsset save(DataAsset dataAsset) {
        log.debug("Request to save DataAsset : {}", dataAsset);
        return dataAssetRepository.save(dataAsset);
    }

    /**
     * Get all the dataAssets.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataAsset> findAll() {
        log.debug("Request to get all DataAssets");
        return dataAssetRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the DataAsset with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DataAsset> findAllWithEagerRelationships(Pageable pageable) {
        return dataAssetRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one dataAsset by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DataAsset> findOne(Long id) {
        log.debug("Request to get DataAsset : {}", id);
        return dataAssetRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the dataAsset by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataAsset : {}", id);
        dataAssetRepository.deleteById(id);
    }
}
