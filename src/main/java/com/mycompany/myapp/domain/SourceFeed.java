package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Frequency;

/**
 * A SourceFeed.
 */
@Entity
@Table(name = "source_feed")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SourceFeed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "feed_code", nullable = false)
    private String feedCode;

    @NotNull
    @Column(name = "file_name_pattern", nullable = false)
    private String fileNamePattern;

    @NotNull
    @Column(name = "header_count", nullable = false)
    private Integer headerCount;

    @NotNull
    @Column(name = "trailer_count", nullable = false)
    private Integer trailerCount;

    @Column(name = "trailer_record_starts_with")
    private String trailerRecordStartsWith;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "feed_frequency", nullable = false)
    private Frequency feedFrequency;

    @Column(name = "sla")
    private String sla;

    @ManyToOne
    @JsonIgnoreProperties("sourceFeeds")
    private Application application;

    @OneToOne    @JoinColumn(unique = true)
    private SourceDatabase sourceDatabase;

    @OneToMany(mappedBy = "sourceFeed")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SourceFeedMapping> sourceFeedMappings = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedCode() {
        return feedCode;
    }

    public SourceFeed feedCode(String feedCode) {
        this.feedCode = feedCode;
        return this;
    }

    public void setFeedCode(String feedCode) {
        this.feedCode = feedCode;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public SourceFeed fileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
        return this;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public Integer getHeaderCount() {
        return headerCount;
    }

    public SourceFeed headerCount(Integer headerCount) {
        this.headerCount = headerCount;
        return this;
    }

    public void setHeaderCount(Integer headerCount) {
        this.headerCount = headerCount;
    }

    public Integer getTrailerCount() {
        return trailerCount;
    }

    public SourceFeed trailerCount(Integer trailerCount) {
        this.trailerCount = trailerCount;
        return this;
    }

    public void setTrailerCount(Integer trailerCount) {
        this.trailerCount = trailerCount;
    }

    public String getTrailerRecordStartsWith() {
        return trailerRecordStartsWith;
    }

    public SourceFeed trailerRecordStartsWith(String trailerRecordStartsWith) {
        this.trailerRecordStartsWith = trailerRecordStartsWith;
        return this;
    }

    public void setTrailerRecordStartsWith(String trailerRecordStartsWith) {
        this.trailerRecordStartsWith = trailerRecordStartsWith;
    }

    public Frequency getFeedFrequency() {
        return feedFrequency;
    }

    public SourceFeed feedFrequency(Frequency feedFrequency) {
        this.feedFrequency = feedFrequency;
        return this;
    }

    public void setFeedFrequency(Frequency feedFrequency) {
        this.feedFrequency = feedFrequency;
    }

    public String getSla() {
        return sla;
    }

    public SourceFeed sla(String sla) {
        this.sla = sla;
        return this;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public Application getApplication() {
        return application;
    }

    public SourceFeed application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public SourceDatabase getSourceDatabase() {
        return sourceDatabase;
    }

    public SourceFeed sourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
        return this;
    }

    public void setSourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public Set<SourceFeedMapping> getSourceFeedMappings() {
        return sourceFeedMappings;
    }

    public SourceFeed sourceFeedMappings(Set<SourceFeedMapping> sourceFeedMappings) {
        this.sourceFeedMappings = sourceFeedMappings;
        return this;
    }

    public SourceFeed addSourceFeedMapping(SourceFeedMapping sourceFeedMapping) {
        this.sourceFeedMappings.add(sourceFeedMapping);
        sourceFeedMapping.setSourceFeed(this);
        return this;
    }

    public SourceFeed removeSourceFeedMapping(SourceFeedMapping sourceFeedMapping) {
        this.sourceFeedMappings.remove(sourceFeedMapping);
        sourceFeedMapping.setSourceFeed(null);
        return this;
    }

    public void setSourceFeedMappings(Set<SourceFeedMapping> sourceFeedMappings) {
        this.sourceFeedMappings = sourceFeedMappings;
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
        SourceFeed sourceFeed = (SourceFeed) o;
        if (sourceFeed.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceFeed.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceFeed{" +
            "id=" + getId() +
            ", feedCode='" + getFeedCode() + "'" +
            ", fileNamePattern='" + getFileNamePattern() + "'" +
            ", headerCount=" + getHeaderCount() +
            ", trailerCount=" + getTrailerCount() +
            ", trailerRecordStartsWith='" + getTrailerRecordStartsWith() + "'" +
            ", feedFrequency='" + getFeedFrequency() + "'" +
            ", sla='" + getSla() + "'" +
            "}";
    }
}
