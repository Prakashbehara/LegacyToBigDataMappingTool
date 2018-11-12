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

import com.mycompany.myapp.domain.DataModelMapping;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.DataModelMappingRepository;
import com.mycompany.myapp.service.dto.DataModelMappingCriteria;

/**
 * Service for executing complex queries for DataModelMapping entities in the database.
 * The main input is a {@link DataModelMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataModelMapping} or a {@link Page} of {@link DataModelMapping} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataModelMappingQueryService extends QueryService<DataModelMapping> {

    private final Logger log = LoggerFactory.getLogger(DataModelMappingQueryService.class);

    private final DataModelMappingRepository dataModelMappingRepository;

    public DataModelMappingQueryService(DataModelMappingRepository dataModelMappingRepository) {
        this.dataModelMappingRepository = dataModelMappingRepository;
    }

    /**
     * Return a {@link List} of {@link DataModelMapping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataModelMapping> findByCriteria(DataModelMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataModelMapping> specification = createSpecification(criteria);
        return dataModelMappingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataModelMapping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataModelMapping> findByCriteria(DataModelMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataModelMapping> specification = createSpecification(criteria);
        return dataModelMappingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataModelMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataModelMapping> specification = createSpecification(criteria);
        return dataModelMappingRepository.count(specification);
    }

    /**
     * Function to convert DataModelMappingCriteria to a {@link Specification}
     */
    private Specification<DataModelMapping> createSpecification(DataModelMappingCriteria criteria) {
        Specification<DataModelMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataModelMapping_.id));
            }
            if (criteria.getFieldName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldName(), DataModelMapping_.fieldName));
            }
            if (criteria.getFieldDataType() != null) {
                specification = specification.and(buildSpecification(criteria.getFieldDataType(), DataModelMapping_.fieldDataType));
            }
            if (criteria.getFieldOrderNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldOrderNumber(), DataModelMapping_.fieldOrderNumber));
            }
            if (criteria.getFieldScale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldScale(), DataModelMapping_.fieldScale));
            }
            if (criteria.getFieldPrecision() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFieldPrecision(), DataModelMapping_.fieldPrecision));
            }
            if (criteria.getPii() != null) {
                specification = specification.and(buildSpecification(criteria.getPii(), DataModelMapping_.pii));
            }
            if (criteria.getDataCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getDataCategory(), DataModelMapping_.dataCategory));
            }
            if (criteria.getDataModelId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataModelId(),
                    root -> root.join(DataModelMapping_.dataModel, JoinType.LEFT).get(DataModel_.id)));
            }
        }
        return specification;
    }
}
