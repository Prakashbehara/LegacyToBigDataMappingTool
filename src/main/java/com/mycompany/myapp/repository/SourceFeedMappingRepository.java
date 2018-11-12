package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SourceFeedMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SourceFeedMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceFeedMappingRepository extends JpaRepository<SourceFeedMapping, Long>, JpaSpecificationExecutor<SourceFeedMapping> {

    @Query(value = "select distinct source_feed_mapping from SourceFeedMapping source_feed_mapping left join fetch source_feed_mapping.dataAssets",
        countQuery = "select count(distinct source_feed_mapping) from SourceFeedMapping source_feed_mapping")
    Page<SourceFeedMapping> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct source_feed_mapping from SourceFeedMapping source_feed_mapping left join fetch source_feed_mapping.dataAssets")
    List<SourceFeedMapping> findAllWithEagerRelationships();

    @Query("select source_feed_mapping from SourceFeedMapping source_feed_mapping left join fetch source_feed_mapping.dataAssets where source_feed_mapping.id =:id")
    Optional<SourceFeedMapping> findOneWithEagerRelationships(@Param("id") Long id);

}
