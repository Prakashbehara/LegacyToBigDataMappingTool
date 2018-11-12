package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.FieldDataType;
import com.mycompany.myapp.domain.enumeration.DataCategory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the DataModelMapping entity. This class is used in DataModelMappingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /data-model-mappings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataModelMappingCriteria implements Serializable {
    /**
     * Class for filtering FieldDataType
     */
    public static class FieldDataTypeFilter extends Filter<FieldDataType> {
    }
    /**
     * Class for filtering DataCategory
     */
    public static class DataCategoryFilter extends Filter<DataCategory> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fieldName;

    private FieldDataTypeFilter fieldDataType;

    private IntegerFilter fieldOrderNumber;

    private IntegerFilter fieldScale;

    private IntegerFilter fieldPrecision;

    private BooleanFilter pii;

    private DataCategoryFilter dataCategory;

    private LongFilter dataModelId;

    public DataModelMappingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFieldName() {
        return fieldName;
    }

    public void setFieldName(StringFilter fieldName) {
        this.fieldName = fieldName;
    }

    public FieldDataTypeFilter getFieldDataType() {
        return fieldDataType;
    }

    public void setFieldDataType(FieldDataTypeFilter fieldDataType) {
        this.fieldDataType = fieldDataType;
    }

    public IntegerFilter getFieldOrderNumber() {
        return fieldOrderNumber;
    }

    public void setFieldOrderNumber(IntegerFilter fieldOrderNumber) {
        this.fieldOrderNumber = fieldOrderNumber;
    }

    public IntegerFilter getFieldScale() {
        return fieldScale;
    }

    public void setFieldScale(IntegerFilter fieldScale) {
        this.fieldScale = fieldScale;
    }

    public IntegerFilter getFieldPrecision() {
        return fieldPrecision;
    }

    public void setFieldPrecision(IntegerFilter fieldPrecision) {
        this.fieldPrecision = fieldPrecision;
    }

    public BooleanFilter getPii() {
        return pii;
    }

    public void setPii(BooleanFilter pii) {
        this.pii = pii;
    }

    public DataCategoryFilter getDataCategory() {
        return dataCategory;
    }

    public void setDataCategory(DataCategoryFilter dataCategory) {
        this.dataCategory = dataCategory;
    }

    public LongFilter getDataModelId() {
        return dataModelId;
    }

    public void setDataModelId(LongFilter dataModelId) {
        this.dataModelId = dataModelId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataModelMappingCriteria that = (DataModelMappingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(fieldDataType, that.fieldDataType) &&
            Objects.equals(fieldOrderNumber, that.fieldOrderNumber) &&
            Objects.equals(fieldScale, that.fieldScale) &&
            Objects.equals(fieldPrecision, that.fieldPrecision) &&
            Objects.equals(pii, that.pii) &&
            Objects.equals(dataCategory, that.dataCategory) &&
            Objects.equals(dataModelId, that.dataModelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldName,
        fieldDataType,
        fieldOrderNumber,
        fieldScale,
        fieldPrecision,
        pii,
        dataCategory,
        dataModelId
        );
    }

    @Override
    public String toString() {
        return "DataModelMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
                (fieldDataType != null ? "fieldDataType=" + fieldDataType + ", " : "") +
                (fieldOrderNumber != null ? "fieldOrderNumber=" + fieldOrderNumber + ", " : "") +
                (fieldScale != null ? "fieldScale=" + fieldScale + ", " : "") +
                (fieldPrecision != null ? "fieldPrecision=" + fieldPrecision + ", " : "") +
                (pii != null ? "pii=" + pii + ", " : "") +
                (dataCategory != null ? "dataCategory=" + dataCategory + ", " : "") +
                (dataModelId != null ? "dataModelId=" + dataModelId + ", " : "") +
            "}";
    }

}
