package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.DataAssetType;
import com.mycompany.myapp.domain.enumeration.Frequency;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the DataAsset entity. This class is used in DataAssetResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /data-assets?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataAssetCriteria implements Serializable {
    /**
     * Class for filtering DataAssetType
     */
    public static class DataAssetTypeFilter extends Filter<DataAssetType> {
    }
    /**
     * Class for filtering Frequency
     */
    public static class FrequencyFilter extends Filter<Frequency> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter assetFileName;

    private DataAssetTypeFilter type;

    private FrequencyFilter frequency;

    private StringFilter storedProcedureName;

    private StringFilter remarks;

    private StringFilter edhAssetName;

    private StringFilter emailDistribution;

    private LongFilter applicationId;

    private LongFilter sourceFeedId;

    private LongFilter sourceDatabaseId;

    public DataAssetCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAssetFileName() {
        return assetFileName;
    }

    public void setAssetFileName(StringFilter assetFileName) {
        this.assetFileName = assetFileName;
    }

    public DataAssetTypeFilter getType() {
        return type;
    }

    public void setType(DataAssetTypeFilter type) {
        this.type = type;
    }

    public FrequencyFilter getFrequency() {
        return frequency;
    }

    public void setFrequency(FrequencyFilter frequency) {
        this.frequency = frequency;
    }

    public StringFilter getStoredProcedureName() {
        return storedProcedureName;
    }

    public void setStoredProcedureName(StringFilter storedProcedureName) {
        this.storedProcedureName = storedProcedureName;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public StringFilter getEdhAssetName() {
        return edhAssetName;
    }

    public void setEdhAssetName(StringFilter edhAssetName) {
        this.edhAssetName = edhAssetName;
    }

    public StringFilter getEmailDistribution() {
        return emailDistribution;
    }

    public void setEmailDistribution(StringFilter emailDistribution) {
        this.emailDistribution = emailDistribution;
    }

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
    }

    public LongFilter getSourceFeedId() {
        return sourceFeedId;
    }

    public void setSourceFeedId(LongFilter sourceFeedId) {
        this.sourceFeedId = sourceFeedId;
    }

    public LongFilter getSourceDatabaseId() {
        return sourceDatabaseId;
    }

    public void setSourceDatabaseId(LongFilter sourceDatabaseId) {
        this.sourceDatabaseId = sourceDatabaseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataAssetCriteria that = (DataAssetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(assetFileName, that.assetFileName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(storedProcedureName, that.storedProcedureName) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(edhAssetName, that.edhAssetName) &&
            Objects.equals(emailDistribution, that.emailDistribution) &&
            Objects.equals(applicationId, that.applicationId) &&
            Objects.equals(sourceFeedId, that.sourceFeedId) &&
            Objects.equals(sourceDatabaseId, that.sourceDatabaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        assetFileName,
        type,
        frequency,
        storedProcedureName,
        remarks,
        edhAssetName,
        emailDistribution,
        applicationId,
        sourceFeedId,
        sourceDatabaseId
        );
    }

    @Override
    public String toString() {
        return "DataAssetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (assetFileName != null ? "assetFileName=" + assetFileName + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (frequency != null ? "frequency=" + frequency + ", " : "") +
                (storedProcedureName != null ? "storedProcedureName=" + storedProcedureName + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (edhAssetName != null ? "edhAssetName=" + edhAssetName + ", " : "") +
                (emailDistribution != null ? "emailDistribution=" + emailDistribution + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                (sourceFeedId != null ? "sourceFeedId=" + sourceFeedId + ", " : "") +
                (sourceDatabaseId != null ? "sourceDatabaseId=" + sourceDatabaseId + ", " : "") +
            "}";
    }

}
