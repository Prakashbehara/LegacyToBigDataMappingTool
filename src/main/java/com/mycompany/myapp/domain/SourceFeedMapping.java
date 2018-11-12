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

import com.mycompany.myapp.domain.enumeration.FieldDataType;

import com.mycompany.myapp.domain.enumeration.DataCategory;

/**
 * A SourceFeedMapping.
 */
@Entity
@Table(name = "source_feed_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SourceFeedMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @NotNull
    @Column(name = "field_order_number", nullable = false)
    private Integer fieldOrderNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "field_data_type", nullable = false)
    private FieldDataType fieldDataType;

    @Column(name = "field_scale")
    private Integer fieldScale;

    @Column(name = "field_precision")
    private Integer fieldPrecision;

    @NotNull
    @Column(name = "cde", nullable = false)
    private Boolean cde;

    @NotNull
    @Column(name = "pii", nullable = false)
    private Boolean pii;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_category", nullable = false)
    private DataCategory dataCategory;

    @Column(name = "data_quality_rule")
    private String dataQualityRule;

    @ManyToOne
    @JsonIgnoreProperties("sourceFeedMappings")
    private SourceFeed sourceFeed;

    @OneToOne    @JoinColumn(unique = true)
    private DataModelMapping dataModelMapping;

    @OneToOne    @JoinColumn(unique = true)
    private SourceDatabaseMapping sourceDatabaseMapping;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "source_feed_mapping_data_asset",
               joinColumns = @JoinColumn(name = "source_feed_mappings_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "data_assets_id", referencedColumnName = "id"))
    private Set<DataAsset> dataAssets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public SourceFeedMapping fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldOrderNumber() {
        return fieldOrderNumber;
    }

    public SourceFeedMapping fieldOrderNumber(Integer fieldOrderNumber) {
        this.fieldOrderNumber = fieldOrderNumber;
        return this;
    }

    public void setFieldOrderNumber(Integer fieldOrderNumber) {
        this.fieldOrderNumber = fieldOrderNumber;
    }

    public FieldDataType getFieldDataType() {
        return fieldDataType;
    }

    public SourceFeedMapping fieldDataType(FieldDataType fieldDataType) {
        this.fieldDataType = fieldDataType;
        return this;
    }

    public void setFieldDataType(FieldDataType fieldDataType) {
        this.fieldDataType = fieldDataType;
    }

    public Integer getFieldScale() {
        return fieldScale;
    }

    public SourceFeedMapping fieldScale(Integer fieldScale) {
        this.fieldScale = fieldScale;
        return this;
    }

    public void setFieldScale(Integer fieldScale) {
        this.fieldScale = fieldScale;
    }

    public Integer getFieldPrecision() {
        return fieldPrecision;
    }

    public SourceFeedMapping fieldPrecision(Integer fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
        return this;
    }

    public void setFieldPrecision(Integer fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
    }

    public Boolean isCde() {
        return cde;
    }

    public SourceFeedMapping cde(Boolean cde) {
        this.cde = cde;
        return this;
    }

    public void setCde(Boolean cde) {
        this.cde = cde;
    }

    public Boolean isPii() {
        return pii;
    }

    public SourceFeedMapping pii(Boolean pii) {
        this.pii = pii;
        return this;
    }

    public void setPii(Boolean pii) {
        this.pii = pii;
    }

    public DataCategory getDataCategory() {
        return dataCategory;
    }

    public SourceFeedMapping dataCategory(DataCategory dataCategory) {
        this.dataCategory = dataCategory;
        return this;
    }

    public void setDataCategory(DataCategory dataCategory) {
        this.dataCategory = dataCategory;
    }

    public String getDataQualityRule() {
        return dataQualityRule;
    }

    public SourceFeedMapping dataQualityRule(String dataQualityRule) {
        this.dataQualityRule = dataQualityRule;
        return this;
    }

    public void setDataQualityRule(String dataQualityRule) {
        this.dataQualityRule = dataQualityRule;
    }

    public SourceFeed getSourceFeed() {
        return sourceFeed;
    }

    public SourceFeedMapping sourceFeed(SourceFeed sourceFeed) {
        this.sourceFeed = sourceFeed;
        return this;
    }

    public void setSourceFeed(SourceFeed sourceFeed) {
        this.sourceFeed = sourceFeed;
    }

    public DataModelMapping getDataModelMapping() {
        return dataModelMapping;
    }

    public SourceFeedMapping dataModelMapping(DataModelMapping dataModelMapping) {
        this.dataModelMapping = dataModelMapping;
        return this;
    }

    public void setDataModelMapping(DataModelMapping dataModelMapping) {
        this.dataModelMapping = dataModelMapping;
    }

    public SourceDatabaseMapping getSourceDatabaseMapping() {
        return sourceDatabaseMapping;
    }

    public SourceFeedMapping sourceDatabaseMapping(SourceDatabaseMapping sourceDatabaseMapping) {
        this.sourceDatabaseMapping = sourceDatabaseMapping;
        return this;
    }

    public void setSourceDatabaseMapping(SourceDatabaseMapping sourceDatabaseMapping) {
        this.sourceDatabaseMapping = sourceDatabaseMapping;
    }

    public Set<DataAsset> getDataAssets() {
        return dataAssets;
    }

    public SourceFeedMapping dataAssets(Set<DataAsset> dataAssets) {
        this.dataAssets = dataAssets;
        return this;
    }

    public SourceFeedMapping addDataAsset(DataAsset dataAsset) {
        this.dataAssets.add(dataAsset);
        return this;
    }

    public SourceFeedMapping removeDataAsset(DataAsset dataAsset) {
        this.dataAssets.remove(dataAsset);
        return this;
    }

    public void setDataAssets(Set<DataAsset> dataAssets) {
        this.dataAssets = dataAssets;
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
        SourceFeedMapping sourceFeedMapping = (SourceFeedMapping) o;
        if (sourceFeedMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceFeedMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceFeedMapping{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldOrderNumber=" + getFieldOrderNumber() +
            ", fieldDataType='" + getFieldDataType() + "'" +
            ", fieldScale=" + getFieldScale() +
            ", fieldPrecision=" + getFieldPrecision() +
            ", cde='" + isCde() + "'" +
            ", pii='" + isPii() + "'" +
            ", dataCategory='" + getDataCategory() + "'" +
            ", dataQualityRule='" + getDataQualityRule() + "'" +
            "}";
    }
}
