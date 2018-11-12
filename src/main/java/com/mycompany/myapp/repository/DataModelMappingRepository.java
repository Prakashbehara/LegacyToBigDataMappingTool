package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DataModelMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DataModelMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataModelMappingRepository extends JpaRepository<DataModelMapping, Long>, JpaSpecificationExecutor<DataModelMapping> {

}
