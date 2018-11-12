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

import com.mycompany.myapp.domain.SourceDatabaseMapping;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SourceDatabaseMappingRepository;
import com.mycompany.myapp.service.dto.SourceDatabaseMappingCriteria;

/**
 * Service for executing complex queries for SourceDatabaseMapping entities in the database.
 * The main input is a {@link SourceDatabaseMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceDatabaseMapping} or a {@link Page} of {@link SourceDatabaseMapping} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceDatabaseMappingQueryService extends QueryService<SourceDatabaseMapping> {

    private final Logger log = LoggerFactory.getLogger(SourceDatabaseMappingQueryService.class);

    private final SourceDatabaseMappingRepository sourceDatabaseMappingRepository;

    public SourceDatabaseMappingQueryService(SourceDatabaseMappingRepository sourceDatabaseMappingRepository) {
        this.sourceDatabaseMappingRepository = sourceDatabaseMappingRepository;
    }

    /**
     * Return a {@link List} of {@link SourceDatabaseMapping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceDatabaseMapping> findByCriteria(SourceDatabaseMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SourceDatabaseMapping> specification = createSpecification(criteria);
        return sourceDatabaseMappingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SourceDatabaseMapping} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceDatabaseMapping> findByCriteria(SourceDatabaseMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SourceDatabaseMapping> specification = createSpecification(criteria);
        return sourceDatabaseMappingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SourceDatabaseMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SourceDatabaseMapping> specification = createSpecification(criteria);
        return sourceDatabaseMappingRepository.count(specification);
    }

    /**
     * Function to convert SourceDatabaseMappingCriteria to a {@link Specification}
     */
    private Specification<SourceDatabaseMapping> createSpecification(SourceDatabaseMappingCriteria criteria) {
        Specification<SourceDatabaseMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceDatabaseMapping_.id));
            }
            if (criteria.getDbColumnName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDbColumnName(), SourceDatabaseMapping_.dbColumnName));
            }
            if (criteria.getDbDataType() != null) {
                specification = specification.and(buildSpecification(criteria.getDbDataType(), SourceDatabaseMapping_.dbDataType));
            }
            if (criteria.getDbFieldScale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDbFieldScale(), SourceDatabaseMapping_.dbFieldScale));
            }
            if (criteria.getDbFieldPrecision() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDbFieldPrecision(), SourceDatabaseMapping_.dbFieldPrecision));
            }
            if (criteria.getSourceDatabaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceDatabaseId(),
                    root -> root.join(SourceDatabaseMapping_.sourceDatabase, JoinType.LEFT).get(SourceDatabase_.id)));
            }
        }
        return specification;
    }
}
