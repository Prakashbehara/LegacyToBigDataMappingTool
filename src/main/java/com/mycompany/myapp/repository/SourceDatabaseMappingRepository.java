package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SourceDatabaseMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SourceDatabaseMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceDatabaseMappingRepository extends JpaRepository<SourceDatabaseMapping, Long>, JpaSpecificationExecutor<SourceDatabaseMapping> {

}
