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

import com.mycompany.myapp.domain.SourceFeed;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SourceFeedRepository;
import com.mycompany.myapp.service.dto.SourceFeedCriteria;

/**
 * Service for executing complex queries for SourceFeed entities in the database.
 * The main input is a {@link SourceFeedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SourceFeed} or a {@link Page} of {@link SourceFeed} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SourceFeedQueryService extends QueryService<SourceFeed> {

    private final Logger log = LoggerFactory.getLogger(SourceFeedQueryService.class);

    private final SourceFeedRepository sourceFeedRepository;

    public SourceFeedQueryService(SourceFeedRepository sourceFeedRepository) {
        this.sourceFeedRepository = sourceFeedRepository;
    }

    /**
     * Return a {@link List} of {@link SourceFeed} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SourceFeed> findByCriteria(SourceFeedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SourceFeed> specification = createSpecification(criteria);
        return sourceFeedRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link SourceFeed} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceFeed> findByCriteria(SourceFeedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SourceFeed> specification = createSpecification(criteria);
        return sourceFeedRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SourceFeedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SourceFeed> specification = createSpecification(criteria);
        return sourceFeedRepository.count(specification);
    }

    /**
     * Function to convert SourceFeedCriteria to a {@link Specification}
     */
    private Specification<SourceFeed> createSpecification(SourceFeedCriteria criteria) {
        Specification<SourceFeed> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SourceFeed_.id));
            }
            if (criteria.getFeedCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeedCode(), SourceFeed_.feedCode));
            }
            if (criteria.getFileNamePattern() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileNamePattern(), SourceFeed_.fileNamePattern));
            }
            if (criteria.getHeaderCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeaderCount(), SourceFeed_.headerCount));
            }
            if (criteria.getTrailerCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTrailerCount(), SourceFeed_.trailerCount));
            }
            if (criteria.getTrailerRecordStartsWith() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTrailerRecordStartsWith(), SourceFeed_.trailerRecordStartsWith));
            }
            if (criteria.getFeedFrequency() != null) {
                specification = specification.and(buildSpecification(criteria.getFeedFrequency(), SourceFeed_.feedFrequency));
            }
            if (criteria.getSla() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSla(), SourceFeed_.sla));
            }
            if (criteria.getApplicationId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationId(),
                    root -> root.join(SourceFeed_.application, JoinType.LEFT).get(Application_.id)));
            }
            if (criteria.getSourceDatabaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceDatabaseId(),
                    root -> root.join(SourceFeed_.sourceDatabase, JoinType.LEFT).get(SourceDatabase_.id)));
            }
            if (criteria.getSourceFeedMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceFeedMappingId(),
                    root -> root.join(SourceFeed_.sourceFeedMappings, JoinType.LEFT).get(SourceFeedMapping_.id)));
            }
        }
        return specification;
    }
}
