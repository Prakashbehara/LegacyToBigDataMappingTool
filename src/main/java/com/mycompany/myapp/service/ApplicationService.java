package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Application.
 */
public interface ApplicationService {

    /**
     * Save a application.
     *
     * @param application the entity to save
     * @return the persisted entity
     */
    Application save(Application application);

    /**
     * Get all the applications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Application> findAll(Pageable pageable);


    /**
     * Get the "id" application.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Application> findOne(Long id);

    /**
     * Delete the "id" application.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
