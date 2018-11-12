package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.Domain;
import com.mycompany.myapp.domain.enumeration.ProjectType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Application entity. This class is used in ApplicationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /applications?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ApplicationCriteria implements Serializable {
    /**
     * Class for filtering Domain
     */
    public static class DomainFilter extends Filter<Domain> {
    }
    /**
     * Class for filtering ProjectType
     */
    public static class ProjectTypeFilter extends Filter<ProjectType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter applicationCode;

    private DomainFilter domain;

    private ProjectTypeFilter projectType;

    private StringFilter description;

    private StringFilter owner;

    private LongFilter sourceFeedId;

    private LongFilter dataAssetId;

    private LongFilter sourceDatabaseId;

    public ApplicationCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(StringFilter applicationCode) {
        this.applicationCode = applicationCode;
    }

    public DomainFilter getDomain() {
        return domain;
    }

    public void setDomain(DomainFilter domain) {
        this.domain = domain;
    }

    public ProjectTypeFilter getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectTypeFilter projectType) {
        this.projectType = projectType;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getOwner() {
        return owner;
    }

    public void setOwner(StringFilter owner) {
        this.owner = owner;
    }

    public LongFilter getSourceFeedId() {
        return sourceFeedId;
    }

    public void setSourceFeedId(LongFilter sourceFeedId) {
        this.sourceFeedId = sourceFeedId;
    }

    public LongFilter getDataAssetId() {
        return dataAssetId;
    }

    public void setDataAssetId(LongFilter dataAssetId) {
        this.dataAssetId = dataAssetId;
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
        final ApplicationCriteria that = (ApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(applicationCode, that.applicationCode) &&
            Objects.equals(domain, that.domain) &&
            Objects.equals(projectType, that.projectType) &&
            Objects.equals(description, that.description) &&
            Objects.equals(owner, that.owner) &&
            Objects.equals(sourceFeedId, that.sourceFeedId) &&
            Objects.equals(dataAssetId, that.dataAssetId) &&
            Objects.equals(sourceDatabaseId, that.sourceDatabaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        applicationCode,
        domain,
        projectType,
        description,
        owner,
        sourceFeedId,
        dataAssetId,
        sourceDatabaseId
        );
    }

    @Override
    public String toString() {
        return "ApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (applicationCode != null ? "applicationCode=" + applicationCode + ", " : "") +
                (domain != null ? "domain=" + domain + ", " : "") +
                (projectType != null ? "projectType=" + projectType + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (owner != null ? "owner=" + owner + ", " : "") +
                (sourceFeedId != null ? "sourceFeedId=" + sourceFeedId + ", " : "") +
                (dataAssetId != null ? "dataAssetId=" + dataAssetId + ", " : "") +
                (sourceDatabaseId != null ? "sourceDatabaseId=" + sourceDatabaseId + ", " : "") +
            "}";
    }

}
