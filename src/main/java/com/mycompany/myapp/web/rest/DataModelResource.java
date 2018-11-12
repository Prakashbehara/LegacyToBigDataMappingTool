package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DataModel;
import com.mycompany.myapp.service.DataModelService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.DataModelCriteria;
import com.mycompany.myapp.service.DataModelQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataModel.
 */
@RestController
@RequestMapping("/api")
public class DataModelResource {

    private final Logger log = LoggerFactory.getLogger(DataModelResource.class);

    private static final String ENTITY_NAME = "dataModel";

    private final DataModelService dataModelService;

    private final DataModelQueryService dataModelQueryService;

    public DataModelResource(DataModelService dataModelService, DataModelQueryService dataModelQueryService) {
        this.dataModelService = dataModelService;
        this.dataModelQueryService = dataModelQueryService;
    }

    /**
     * POST  /data-models : Create a new dataModel.
     *
     * @param dataModel the dataModel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataModel, or with status 400 (Bad Request) if the dataModel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/data-models")
    @Timed
    public ResponseEntity<DataModel> createDataModel(@RequestBody DataModel dataModel) throws URISyntaxException {
        log.debug("REST request to save DataModel : {}", dataModel);
        if (dataModel.getId() != null) {
            throw new BadRequestAlertException("A new dataModel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataModel result = dataModelService.save(dataModel);
        return ResponseEntity.created(new URI("/api/data-models/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-models : Updates an existing dataModel.
     *
     * @param dataModel the dataModel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataModel,
     * or with status 400 (Bad Request) if the dataModel is not valid,
     * or with status 500 (Internal Server Error) if the dataModel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/data-models")
    @Timed
    public ResponseEntity<DataModel> updateDataModel(@RequestBody DataModel dataModel) throws URISyntaxException {
        log.debug("REST request to update DataModel : {}", dataModel);
        if (dataModel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataModel result = dataModelService.save(dataModel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dataModel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /data-models : get all the dataModels.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of dataModels in body
     */
    @GetMapping("/data-models")
    @Timed
    public ResponseEntity<List<DataModel>> getAllDataModels(DataModelCriteria criteria) {
        log.debug("REST request to get DataModels by criteria: {}", criteria);
        List<DataModel> entityList = dataModelQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /data-models/count : count all the dataModels.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/data-models/count")
    @Timed
    public ResponseEntity<Long> countDataModels (DataModelCriteria criteria) {
        log.debug("REST request to count DataModels by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataModelQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /data-models/:id : get the "id" dataModel.
     *
     * @param id the id of the dataModel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataModel, or with status 404 (Not Found)
     */
    @GetMapping("/data-models/{id}")
    @Timed
    public ResponseEntity<DataModel> getDataModel(@PathVariable Long id) {
        log.debug("REST request to get DataModel : {}", id);
        Optional<DataModel> dataModel = dataModelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataModel);
    }

    /**
     * DELETE  /data-models/:id : delete the "id" dataModel.
     *
     * @param id the id of the dataModel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/data-models/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataModel(@PathVariable Long id) {
        log.debug("REST request to delete DataModel : {}", id);
        dataModelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
