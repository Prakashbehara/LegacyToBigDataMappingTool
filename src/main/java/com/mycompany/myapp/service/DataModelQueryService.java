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

import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.DataModelRepository;
import com.mycompany.myapp.service.dto.DataModelCriteria;

/**
 * Service for executing complex queries for DataModel entities in the database.
 * The main input is a {@link DataModelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataModel} or a {@link Page} of {@link DataModel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataModelQueryService extends QueryService<DataModel> {

    private final Logger log = LoggerFactory.getLogger(DataModelQueryService.class);

    private final DataModelRepository dataModelRepository;

    public DataModelQueryService(DataModelRepository dataModelRepository) {
        this.dataModelRepository = dataModelRepository;
    }

    /**
     * Return a {@link List} of {@link DataModel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataModel> findByCriteria(DataModelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataModel> specification = createSpecification(criteria);
        return dataModelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DataModel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataModel> findByCriteria(DataModelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataModel> specification = createSpecification(criteria);
        return dataModelRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataModelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataModel> specification = createSpecification(criteria);
        return dataModelRepository.count(specification);
    }

    /**
     * Function to convert DataModelCriteria to a {@link Specification}
     */
    private Specification<DataModel> createSpecification(DataModelCriteria criteria) {
        Specification<DataModel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataModel_.id));
            }
            if (criteria.getEntitiyName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEntitiyName(), DataModel_.entitiyName));
            }
            if (criteria.getDomain() != null) {
                specification = specification.and(buildSpecification(criteria.getDomain(), DataModel_.domain));
            }
            if (criteria.getDataModelMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataModelMappingId(),
                    root -> root.join(DataModel_.dataModelMappings, JoinType.LEFT).get(DataModelMapping_.id)));
            }
        }
        return specification;
    }
}
