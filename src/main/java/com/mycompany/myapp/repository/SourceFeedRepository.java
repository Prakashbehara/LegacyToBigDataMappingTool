package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SourceFeed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SourceFeed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceFeedRepository extends JpaRepository<SourceFeed, Long>, JpaSpecificationExecutor<SourceFeed> {

}
