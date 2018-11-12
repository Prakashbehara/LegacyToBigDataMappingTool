package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.FieldDataType;

import com.mycompany.myapp.domain.enumeration.DataCategory;

/**
 * A DataModelMapping.
 */
@Entity
@Table(name = "data_model_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataModelMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "field_name", nullable = false)
    private String fieldName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "field_data_type", nullable = false)
    private FieldDataType fieldDataType;

    @NotNull
    @Column(name = "field_order_number", nullable = false)
    private Integer fieldOrderNumber;

    @Column(name = "field_scale")
    private Integer fieldScale;

    @Column(name = "field_precision")
    private Integer fieldPrecision;

    @NotNull
    @Column(name = "pii", nullable = false)
    private Boolean pii;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "data_category", nullable = false)
    private DataCategory dataCategory;

    @ManyToOne
    @JsonIgnoreProperties("dataModelMappings")
    private DataModel dataModel;

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

    public DataModelMapping fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldDataType getFieldDataType() {
        return fieldDataType;
    }

    public DataModelMapping fieldDataType(FieldDataType fieldDataType) {
        this.fieldDataType = fieldDataType;
        return this;
    }

    public void setFieldDataType(FieldDataType fieldDataType) {
        this.fieldDataType = fieldDataType;
    }

    public Integer getFieldOrderNumber() {
        return fieldOrderNumber;
    }

    public DataModelMapping fieldOrderNumber(Integer fieldOrderNumber) {
        this.fieldOrderNumber = fieldOrderNumber;
        return this;
    }

    public void setFieldOrderNumber(Integer fieldOrderNumber) {
        this.fieldOrderNumber = fieldOrderNumber;
    }

    public Integer getFieldScale() {
        return fieldScale;
    }

    public DataModelMapping fieldScale(Integer fieldScale) {
        this.fieldScale = fieldScale;
        return this;
    }

    public void setFieldScale(Integer fieldScale) {
        this.fieldScale = fieldScale;
    }

    public Integer getFieldPrecision() {
        return fieldPrecision;
    }

    public DataModelMapping fieldPrecision(Integer fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
        return this;
    }

    public void setFieldPrecision(Integer fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
    }

    public Boolean isPii() {
        return pii;
    }

    public DataModelMapping pii(Boolean pii) {
        this.pii = pii;
        return this;
    }

    public void setPii(Boolean pii) {
        this.pii = pii;
    }

    public DataCategory getDataCategory() {
        return dataCategory;
    }

    public DataModelMapping dataCategory(DataCategory dataCategory) {
        this.dataCategory = dataCategory;
        return this;
    }

    public void setDataCategory(DataCategory dataCategory) {
        this.dataCategory = dataCategory;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public DataModelMapping dataModel(DataModel dataModel) {
        this.dataModel = dataModel;
        return this;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
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
        DataModelMapping dataModelMapping = (DataModelMapping) o;
        if (dataModelMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataModelMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataModelMapping{" +
            "id=" + getId() +
            ", fieldName='" + getFieldName() + "'" +
            ", fieldDataType='" + getFieldDataType() + "'" +
            ", fieldOrderNumber=" + getFieldOrderNumber() +
            ", fieldScale=" + getFieldScale() +
            ", fieldPrecision=" + getFieldPrecision() +
            ", pii='" + isPii() + "'" +
            ", dataCategory='" + getDataCategory() + "'" +
            "}";
    }
}
