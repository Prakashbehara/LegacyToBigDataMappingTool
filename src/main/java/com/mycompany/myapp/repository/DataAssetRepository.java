package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DataAsset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the DataAsset entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataAssetRepository extends JpaRepository<DataAsset, Long>, JpaSpecificationExecutor<DataAsset> {

    @Query(value = "select distinct data_asset from DataAsset data_asset left join fetch data_asset.sourceFeeds left join fetch data_asset.sourceDatabases",
        countQuery = "select count(distinct data_asset) from DataAsset data_asset")
    Page<DataAsset> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct data_asset from DataAsset data_asset left join fetch data_asset.sourceFeeds left join fetch data_asset.sourceDatabases")
    List<DataAsset> findAllWithEagerRelationships();

    @Query("select data_asset from DataAsset data_asset left join fetch data_asset.sourceFeeds left join fetch data_asset.sourceDatabases where data_asset.id =:id")
    Optional<DataAsset> findOneWithEagerRelationships(@Param("id") Long id);

}
