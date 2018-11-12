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

import com.mycompany.myapp.domain.SourceFeedMapping;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SourceFeedMappingRepository;
import com.mycompany.myapp.service.dto.SourceFeedMappingCriteria;

/**
 * Service for executing complex queries for SourceFeedMapping entities in the database.
 * The main input is a {@link SourceFeedMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceFeedMapping} or a {@link Page} of {@link SourceFeedMapping} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceFeedMappingQueryService extends QueryService<SourceFeedMapping> {

    private final Logger log = LoggerFactory.getLogger(SourceFeedMappingQueryService.class);

    private final SourceFeedMappingRepository sourceFeedMappingRepository;

    public SourceFeedMappingQueryService(SourceFeedMappingRepository sourceFeedMappingRepository) {
        this.sourceFeedMappingRepository = sourceFeedMappingRepository;
    }

    /**
     * Return a {@link List} of {@link SourceFeedMapping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceFeedMapping> findByCriteria(SourceFeedMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SourceFeedMapping> specification = createSpecification(criteria);
        return sourceFeedMappingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SourceFeedMapping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceFeedMapping> findByCriteria(SourceFeedMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SourceFeedMapping> specification = createSpecification(criteria);
        return sourceFeedMappingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SourceFeedMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SourceFeedMapping> specification = createSpecification(criteria);
        return sourceFeedMappingRepository.count(specification);
    }

    /**
     * Function to convert SourceFeedMappingCriteria to a {@link Specification}
     */
    private Specification<SourceFeedMapping> createSpecification(SourceFeedMappingCriteria criteria) {
        Specification<SourceFeedMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceFeedMapping_.id));
            }
            if (criteria.getFieldName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldName(), SourceFeedMapping_.fieldName));
            }
            if (criteria.getFieldOrderNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldOrderNumber(), SourceFeedMapping_.fieldOrderNumber));
            }
            if (criteria.getFieldDataType() != null) {
                specification = specification.and(buildSpecification(criteria.getFieldDataType(), SourceFeedMapping_.fieldDataType));
            }
            if (criteria.getFieldScale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldScale(), SourceFeedMapping_.fieldScale));
            }
            if (criteria.getFieldPrecision() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldPrecision(), SourceFeedMapping_.fieldPrecision));
            }
            if (criteria.getCde() != null) {
                specification = specification.and(buildSpecification(criteria.getCde(), SourceFeedMapping_.cde));
            }
            if (criteria.getPii() != null) {
                specification = specification.and(buildSpecification(criteria.getPii(), SourceFeedMapping_.pii));
            }
            if (criteria.getDataCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getDataCategory(), SourceFeedMapping_.dataCategory));
            }
            if (criteria.getDataQualityRule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataQualityRule(), SourceFeedMapping_.dataQualityRule));
            }
            if (criteria.getSourceFeedId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceFeedId(),
                    root -> root.join(SourceFeedMapping_.sourceFeed, JoinType.LEFT).get(SourceFeed_.id)));
            }
            if (criteria.getDataModelMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataModelMappingId(),
                    root -> root.join(SourceFeedMapping_.dataModelMapping, JoinType.LEFT).get(DataModelMapping_.id)));
            }
            if (criteria.getSourceDatabaseMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceDatabaseMappingId(),
                    root -> root.join(SourceFeedMapping_.sourceDatabaseMapping, JoinType.LEFT).get(SourceDatabaseMapping_.id)));
            }
            if (criteria.getDataAssetId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataAssetId(),
                    root -> root.join(SourceFeedMapping_.dataAssets, JoinType.LEFT).get(DataAsset_.id)));
            }
        }
        return specification;
    }
}
