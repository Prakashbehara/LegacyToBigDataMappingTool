package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.DataAsset;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.DataAssetRepository;
import com.mycompany.myapp.service.dto.DataAssetCriteria;

/**
 * Service for executing complex queries for DataAsset entities in the database.
 * The main input is a {@link DataAssetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataAsset} or a {@link Page} of {@link DataAsset} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataAssetQueryService extends QueryService<DataAsset> {

    private final Logger log = LoggerFactory.getLogger(DataAssetQueryService.class);

    private final DataAssetRepository dataAssetRepository;

    public DataAssetQueryService(DataAssetRepository dataAssetRepository) {
        this.dataAssetRepository = dataAssetRepository;
    }

    /**
     * Return a {@link List} of {@link DataAsset} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataAsset> findByCriteria(DataAssetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataAsset> specification = createSpecification(criteria);
        return dataAssetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataAsset} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataAsset> findByCriteria(DataAssetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataAsset> specification = createSpecification(criteria);
        return dataAssetRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataAssetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataAsset> specification = createSpecification(criteria);
        return dataAssetRepository.count(specification);
    }

    /**
     * Function to convert DataAssetCriteria to a {@link Specification}
     */
    private Specification<DataAsset> createSpecification(DataAssetCriteria criteria) {
        Specification<DataAsset> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataAsset_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DataAsset_.name));
            }
            if (criteria.getAssetFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetFileName(), DataAsset_.assetFileName));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), DataAsset_.type));
            }
            if (criteria.getFrequency() != null) {
                specification = specification.and(buildSpecification(criteria.getFrequency(), DataAsset_.frequency));
            }
            if (criteria.getStoredProcedureName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStoredProcedureName(), DataAsset_.storedProcedureName));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), DataAsset_.remarks));
            }
            if (criteria.getEdhAssetName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEdhAssetName(), DataAsset_.edhAssetName));
            }
            if (criteria.getEmailDistribution() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailDistribution(), DataAsset_.emailDistribution));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(DataAsset_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getSourceFeedId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceFeedId(),
                    root -> root.join(DataAsset_.sourceFeeds, JoinType.LEFT).get(SourceFeed_.id)));
            }
            if (criteria.getSourceDatabaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceDatabaseId(),
                    root -> root.join(DataAsset_.sourceDatabases, JoinType.LEFT).get(SourceDatabase_.id)));
            }
        }
        return specification;
    }
}
