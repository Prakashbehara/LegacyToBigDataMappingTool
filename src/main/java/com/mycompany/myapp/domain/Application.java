package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Domain;

import com.mycompany.myapp.domain.enumeration.ProjectType;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 6)
    @Column(name = "application_code", length = 6, nullable = false)
    private String applicationCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "domain", nullable = false)
    private Domain domain;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "project_type", nullable = false)
    private ProjectType projectType;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "[a-z]+")
    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @Column(name = "owner")
    private String owner;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SourceFeed> sourceFeeds = new HashSet<>();
    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataAsset> dataAssets = new HashSet<>();
    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SourceDatabase> sourceDatabases = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public Application applicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public Domain getDomain() {
        return domain;
    }

    public Application domain(Domain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public Application projectType(ProjectType projectType) {
        this.projectType = projectType;
        return this;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public String getDescription() {
        return description;
    }

    public Application description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public Application owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<SourceFeed> getSourceFeeds() {
        return sourceFeeds;
    }

    public Application sourceFeeds(Set<SourceFeed> sourceFeeds) {
        this.sourceFeeds = sourceFeeds;
        return this;
    }

    public Application addSourceFeed(SourceFeed sourceFeed) {
        this.sourceFeeds.add(sourceFeed);
        sourceFeed.setApplication(this);
        return this;
    }

    public Application removeSourceFeed(SourceFeed sourceFeed) {
        this.sourceFeeds.remove(sourceFeed);
        sourceFeed.setApplication(null);
        return this;
    }

    public void setSourceFeeds(Set<SourceFeed> sourceFeeds) {
        this.sourceFeeds = sourceFeeds;
    }

    public Set<DataAsset> getDataAssets() {
        return dataAssets;
    }

    public Application dataAssets(Set<DataAsset> dataAssets) {
        this.dataAssets = dataAssets;
        return this;
    }

    public Application addDataAsset(DataAsset dataAsset) {
        this.dataAssets.add(dataAsset);
        dataAsset.setApplication(this);
        return this;
    }

    public Application removeDataAsset(DataAsset dataAsset) {
        this.dataAssets.remove(dataAsset);
        dataAsset.setApplication(null);
        return this;
    }

    public void setDataAssets(Set<DataAsset> dataAssets) {
        this.dataAssets = dataAssets;
    }

    public Set<SourceDatabase> getSourceDatabases() {
        return sourceDatabases;
    }

    public Application sourceDatabases(Set<SourceDatabase> sourceDatabases) {
        this.sourceDatabases = sourceDatabases;
        return this;
    }

    public Application addSourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabases.add(sourceDatabase);
        sourceDatabase.setApplication(this);
        return this;
    }

    public Application removeSourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabases.remove(sourceDatabase);
        sourceDatabase.setApplication(null);
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
        Application application = (Application) o;
        if (application.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), application.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", applicationCode='" + getApplicationCode() + "'" +
            ", domain='" + getDomain() + "'" +
            ", projectType='" + getProjectType() + "'" +
            ", description='" + getDescription() + "'" +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
