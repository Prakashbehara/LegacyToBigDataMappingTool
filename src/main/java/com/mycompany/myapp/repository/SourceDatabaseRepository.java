package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SourceDatabase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SourceDatabase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceDatabaseRepository extends JpaRepository<SourceDatabase, Long>, JpaSpecificationExecutor<SourceDatabase> {

}
