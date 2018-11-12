package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.Frequency;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the SourceFeed entity. This class is used in SourceFeedResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /source-feeds?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceFeedCriteria implements Serializable {
    /**
     * Class for filtering Frequency
     */
    public static class FrequencyFilter extends Filter<Frequency> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter feedCode;

    private StringFilter fileNamePattern;

    private IntegerFilter headerCount;

    private IntegerFilter trailerCount;

    private StringFilter trailerRecordStartsWith;

    private FrequencyFilter feedFrequency;

    private StringFilter sla;

    private LongFilter applicationId;

    private LongFilter sourceDatabaseId;

    private LongFilter sourceFeedMappingId;

    public SourceFeedCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFeedCode() {
        return feedCode;
    }

    public void setFeedCode(StringFilter feedCode) {
        this.feedCode = feedCode;
    }

    public StringFilter getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(StringFilter fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public IntegerFilter getHeaderCount() {
        return headerCount;
    }

    public void setHeaderCount(IntegerFilter headerCount) {
        this.headerCount = headerCount;
    }

    public IntegerFilter getTrailerCount() {
        return trailerCount;
    }

    public void setTrailerCount(IntegerFilter trailerCount) {
        this.trailerCount = trailerCount;
    }

    public StringFilter getTrailerRecordStartsWith() {
        return trailerRecordStartsWith;
    }

    public void setTrailerRecordStartsWith(StringFilter trailerRecordStartsWith) {
        this.trailerRecordStartsWith = trailerRecordStartsWith;
    }

    public FrequencyFilter getFeedFrequency() {
        return feedFrequency;
    }

    public void setFeedFrequency(FrequencyFilter feedFrequency) {
        this.feedFrequency = feedFrequency;
    }

    public StringFilter getSla() {
        return sla;
    }

    public void setSla(StringFilter sla) {
        this.sla = sla;
    }

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
    }

    public LongFilter getSourceDatabaseId() {
        return sourceDatabaseId;
    }

    public void setSourceDatabaseId(LongFilter sourceDatabaseId) {
        this.sourceDatabaseId = sourceDatabaseId;
    }

    public LongFilter getSourceFeedMappingId() {
        return sourceFeedMappingId;
    }

    public void setSourceFeedMappingId(LongFilter sourceFeedMappingId) {
        this.sourceFeedMappingId = sourceFeedMappingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourceFeedCriteria that = (SourceFeedCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(feedCode, that.feedCode) &&
            Objects.equals(fileNamePattern, that.fileNamePattern) &&
            Objects.equals(headerCount, that.headerCount) &&
            Objects.equals(trailerCount, that.trailerCount) &&
            Objects.equals(trailerRecordStartsWith, that.trailerRecordStartsWith) &&
            Objects.equals(feedFrequency, that.feedFrequency) &&
            Objects.equals(sla, that.sla) &&
            Objects.equals(applicationId, that.applicationId) &&
            Objects.equals(sourceDatabaseId, that.sourceDatabaseId) &&
            Objects.equals(sourceFeedMappingId, that.sourceFeedMappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        feedCode,
        fileNamePattern,
        headerCount,
        trailerCount,
        trailerRecordStartsWith,
        feedFrequency,
        sla,
        applicationId,
        sourceDatabaseId,
        sourceFeedMappingId
        );
    }

    @Override
    public String toString() {
        return "SourceFeedCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (feedCode != null ? "feedCode=" + feedCode + ", " : "") +
                (fileNamePattern != null ? "fileNamePattern=" + fileNamePattern + ", " : "") +
                (headerCount != null ? "headerCount=" + headerCount + ", " : "") +
                (trailerCount != null ? "trailerCount=" + trailerCount + ", " : "") +
                (trailerRecordStartsWith != null ? "trailerRecordStartsWith=" + trailerRecordStartsWith + ", " : "") +
                (feedFrequency != null ? "feedFrequency=" + feedFrequency + ", " : "") +
                (sla != null ? "sla=" + sla + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                (sourceDatabaseId != null ? "sourceDatabaseId=" + sourceDatabaseId + ", " : "") +
                (sourceFeedMappingId != null ? "sourceFeedMappingId=" + sourceFeedMappingId + ", " : "") +
            "}";
    }

}
