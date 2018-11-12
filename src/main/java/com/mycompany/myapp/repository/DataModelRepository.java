package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DataModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataModelRepository extends JpaRepository<DataModel, Long>, JpaSpecificationExecutor<DataModel> {

}
