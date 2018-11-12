package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.DataAssetType;

import com.mycompany.myapp.domain.enumeration.Frequency;

/**
 * A DataAsset.
 */
@Entity
@Table(name = "data_asset")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataAsset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "asset_file_name", nullable = false)
    private String assetFileName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private DataAssetType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "frequency", nullable = false)
    private Frequency frequency;

    @Column(name = "stored_procedure_name")
    private String storedProcedureName;

    @Lob
    @Column(name = "query_logic")
    private String queryLogic;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "edh_asset_name")
    private String edhAssetName;

    @Column(name = "email_distribution")
    private String emailDistribution;

    @ManyToOne
    @JsonIgnoreProperties("dataAssets")
    private Application application;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "data_asset_source_feed",
               joinColumns = @JoinColumn(name = "data_assets_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "source_feeds_id", referencedColumnName = "id"))
    private Set<SourceFeed> sourceFeeds = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "data_asset_source_database",
               joinColumns = @JoinColumn(name = "data_assets_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "source_databases_id", referencedColumnName = "id"))
    private Set<SourceDatabase> sourceDatabases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DataAsset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssetFileName() {
        return assetFileName;
    }

    public DataAsset assetFileName(String assetFileName) {
        this.assetFileName = assetFileName;
        return this;
    }

    public void setAssetFileName(String assetFileName) {
        this.assetFileName = assetFileName;
    }

    public DataAssetType getType() {
        return type;
    }

    public DataAsset type(DataAssetType type) {
        this.type = type;
        return this;
    }

    public void setType(DataAssetType type) {
        this.type = type;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public DataAsset frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public String getStoredProcedureName() {
        return storedProcedureName;
    }

    public DataAsset storedProcedureName(String storedProcedureName) {
        this.storedProcedureName = storedProcedureName;
        return this;
    }

    public void setStoredProcedureName(String storedProcedureName) {
        this.storedProcedureName = storedProcedureName;
    }

    public String getQueryLogic() {
        return queryLogic;
    }

    public DataAsset queryLogic(String queryLogic) {
        this.queryLogic = queryLogic;
        return this;
    }

    public void setQueryLogic(String queryLogic) {
        this.queryLogic = queryLogic;
    }

    public String getRemarks() {
        return remarks;
    }

    public DataAsset remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEdhAssetName() {
        return edhAssetName;
    }

    public DataAsset edhAssetName(String edhAssetName) {
        this.edhAssetName = edhAssetName;
        return this;
    }

    public void setEdhAssetName(String edhAssetName) {
        this.edhAssetName = edhAssetName;
    }

    public String getEmailDistribution() {
        return emailDistribution;
    }

    public DataAsset emailDistribution(String emailDistribution) {
        this.emailDistribution = emailDistribution;
        return this;
    }

    public void setEmailDistribution(String emailDistribution) {
        this.emailDistribution = emailDistribution;
    }

    public Application getApplication() {
        return application;
    }

    public DataAsset application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<SourceFeed> getSourceFeeds() {
        return sourceFeeds;
    }

    public DataAsset sourceFeeds(Set<SourceFeed> sourceFeeds) {
        this.sourceFeeds = sourceFeeds;
        return this;
    }

    public DataAsset addSourceFeed(SourceFeed sourceFeed) {
        this.sourceFeeds.add(sourceFeed);
        return this;
    }

    public DataAsset removeSourceFeed(SourceFeed sourceFeed) {
        this.sourceFeeds.remove(sourceFeed);
        return this;
    }

    public void setSourceFeeds(Set<SourceFeed> sourceFeeds) {
        this.sourceFeeds = sourceFeeds;
    }

    public Set<SourceDatabase> getSourceDatabases() {
        return sourceDatabases;
    }

    public DataAsset sourceDatabases(Set<SourceDatabase> sourceDatabases) {
        this.sourceDatabases = sourceDatabases;
        return this;
    }

    public DataAsset addSourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabases.add(sourceDatabase);
        return this;
    }

    public DataAsset removeSourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabases.remove(sourceDatabase);
        return this;
    }

    public void setSourceDatabases(Set<SourceDatabase> sourceDatabases) {
        this.sourceDatabases = sourceDatabases;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataAsset dataAsset = (DataAsset) o;
        if (dataAsset.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataAsset.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataAsset{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", assetFileName='" + getAssetFileName() + "'" +
            ", type='" + getType() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", storedProcedureName='" + getStoredProcedureName() + "'" +
            ", queryLogic='" + getQueryLogic() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", edhAssetName='" + getEdhAssetName() + "'" +
            ", emailDistribution='" + getEmailDistribution() + "'" +
            "}";
    }
}
