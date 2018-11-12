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

import com.mycompany.myapp.domain.SourceDatabase;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SourceDatabaseRepository;
import com.mycompany.myapp.service.dto.SourceDatabaseCriteria;

/**
 * Service for executing complex queries for SourceDatabase entities in the database.
 * The main input is a {@link SourceDatabaseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceDatabase} or a {@link Page} of {@link SourceDatabase} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceDatabaseQueryService extends QueryService<SourceDatabase> {

    private final Logger log = LoggerFactory.getLogger(SourceDatabaseQueryService.class);

    private final SourceDatabaseRepository sourceDatabaseRepository;

    public SourceDatabaseQueryService(SourceDatabaseRepository sourceDatabaseRepository) {
        this.sourceDatabaseRepository = sourceDatabaseRepository;
    }

    /**
     * Return a {@link List} of {@link SourceDatabase} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceDatabase> findByCriteria(SourceDatabaseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SourceDatabase> specification = createSpecification(criteria);
        return sourceDatabaseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SourceDatabase} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceDatabase> findByCriteria(SourceDatabaseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SourceDatabase> specification = createSpecification(criteria);
        return sourceDatabaseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SourceDatabaseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SourceDatabase> specification = createSpecification(criteria);
        return sourceDatabaseRepository.count(specification);
    }

    /**
     * Function to convert SourceDatabaseCriteria to a {@link Specification}
     */
    private Specification<SourceDatabase> createSpecification(SourceDatabaseCriteria criteria) {
        Specification<SourceDatabase> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceDatabase_.id));
            }
            if (criteria.getTableName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTableName(), SourceDatabase_.tableName));
            }
            if (criteria.getSchema() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchema(), SourceDatabase_.schema));
            }
            if (criteria.getTableType() != null) {
                specification = specification.and(buildSpecification(criteria.getTableType(), SourceDatabase_.tableType));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(SourceDatabase_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getSourceDatabaseMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceDatabaseMappingId(),
                    root -> root.join(SourceDatabase_.sourceDatabaseMappings, JoinType.LEFT).get(SourceDatabaseMapping_.id)));
            }
        }
        return specification;
    }
}
