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
 * Criteria class for the SourceFeedMapping entity. This class is used in SourceFeedMappingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /source-feed-mappings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceFeedMappingCriteria implements Serializable {
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

    private IntegerFilter fieldOrderNumber;

    private FieldDataTypeFilter fieldDataType;

    private IntegerFilter fieldScale;

    private IntegerFilter fieldPrecision;

    private BooleanFilter cde;

    private BooleanFilter pii;

    private DataCategoryFilter dataCategory;

    private StringFilter dataQualityRule;

    private LongFilter sourceFeedId;

    private LongFilter dataModelMappingId;

    private LongFilter sourceDatabaseMappingId;

    private LongFilter dataAssetId;

    public SourceFeedMappingCriteria() {
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

    public IntegerFilter getFieldOrderNumber() {
        return fieldOrderNumber;
    }

    public void setFieldOrderNumber(IntegerFilter fieldOrderNumber) {
        this.fieldOrderNumber = fieldOrderNumber;
    }

    public FieldDataTypeFilter getFieldDataType() {
        return fieldDataType;
    }

    public void setFieldDataType(FieldDataTypeFilter fieldDataType) {
        this.fieldDataType = fieldDataType;
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

    public BooleanFilter getCde() {
        return cde;
    }

    public void setCde(BooleanFilter cde) {
        this.cde = cde;
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

    public StringFilter getDataQualityRule() {
        return dataQualityRule;
    }

    public void setDataQualityRule(StringFilter dataQualityRule) {
        this.dataQualityRule = dataQualityRule;
    }

    public LongFilter getSourceFeedId() {
        return sourceFeedId;
    }

    public void setSourceFeedId(LongFilter sourceFeedId) {
        this.sourceFeedId = sourceFeedId;
    }

    public LongFilter getDataModelMappingId() {
        return dataModelMappingId;
    }

    public void setDataModelMappingId(LongFilter dataModelMappingId) {
        this.dataModelMappingId = dataModelMappingId;
    }

    public LongFilter getSourceDatabaseMappingId() {
        return sourceDatabaseMappingId;
    }

    public void setSourceDatabaseMappingId(LongFilter sourceDatabaseMappingId) {
        this.sourceDatabaseMappingId = sourceDatabaseMappingId;
    }

    public LongFilter getDataAssetId() {
        return dataAssetId;
    }

    public void setDataAssetId(LongFilter dataAssetId) {
        this.dataAssetId = dataAssetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourceFeedMappingCriteria that = (SourceFeedMappingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldName, that.fieldName) &&
            Objects.equals(fieldOrderNumber, that.fieldOrderNumber) &&
            Objects.equals(fieldDataType, that.fieldDataType) &&
            Objects.equals(fieldScale, that.fieldScale) &&
            Objects.equals(fieldPrecision, that.fieldPrecision) &&
            Objects.equals(cde, that.cde) &&
            Objects.equals(pii, that.pii) &&
            Objects.equals(dataCategory, that.dataCategory) &&
            Objects.equals(dataQualityRule, that.dataQualityRule) &&
            Objects.equals(sourceFeedId, that.sourceFeedId) &&
            Objects.equals(dataModelMappingId, that.dataModelMappingId) &&
            Objects.equals(sourceDatabaseMappingId, that.sourceDatabaseMappingId) &&
            Objects.equals(dataAssetId, that.dataAssetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldName,
        fieldOrderNumber,
        fieldDataType,
        fieldScale,
        fieldPrecision,
        cde,
        pii,
        dataCategory,
        dataQualityRule,
        sourceFeedId,
        dataModelMappingId,
        sourceDatabaseMappingId,
        dataAssetId
        );
    }

    @Override
    public String toString() {
        return "SourceFeedMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldName != null ? "fieldName=" + fieldName + ", " : "") +
                (fieldOrderNumber != null ? "fieldOrderNumber=" + fieldOrderNumber + ", " : "") +
                (fieldDataType != null ? "fieldDataType=" + fieldDataType + ", " : "") +
                (fieldScale != null ? "fieldScale=" + fieldScale + ", " : "") +
                (fieldPrecision != null ? "fieldPrecision=" + fieldPrecision + ", " : "") +
                (cde != null ? "cde=" + cde + ", " : "") +
                (pii != null ? "pii=" + pii + ", " : "") +
                (dataCategory != null ? "dataCategory=" + dataCategory + ", " : "") +
                (dataQualityRule != null ? "dataQualityRule=" + dataQualityRule + ", " : "") +
                (sourceFeedId != null ? "sourceFeedId=" + sourceFeedId + ", " : "") +
                (dataModelMappingId != null ? "dataModelMappingId=" + dataModelMappingId + ", " : "") +
                (sourceDatabaseMappingId != null ? "sourceDatabaseMappingId=" + sourceDatabaseMappingId + ", " : "") +
                (dataAssetId != null ? "dataAssetId=" + dataAssetId + ", " : "") +
            "}";
    }

}
