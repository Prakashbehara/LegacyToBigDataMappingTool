package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.FieldDataType;

/**
 * A SourceDatabaseMapping.
 */
@Entity
@Table(name = "source_database_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SourceDatabaseMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "db_column_name", nullable = false)
    private String dbColumnName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "db_data_type", nullable = false)
    private FieldDataType dbDataType;

    @Column(name = "db_field_scale")
    private Integer dbFieldScale;

    @Column(name = "db_field_precision")
    private Integer dbFieldPrecision;

    @ManyToOne
    @JsonIgnoreProperties("sourceDatabaseMappings")
    private SourceDatabase sourceDatabase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public SourceDatabaseMapping dbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
        return this;
    }

    public void setDbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public FieldDataType getDbDataType() {
        return dbDataType;
    }

    public SourceDatabaseMapping dbDataType(FieldDataType dbDataType) {
        this.dbDataType = dbDataType;
        return this;
    }

    public void setDbDataType(FieldDataType dbDataType) {
        this.dbDataType = dbDataType;
    }

    public Integer getDbFieldScale() {
        return dbFieldScale;
    }

    public SourceDatabaseMapping dbFieldScale(Integer dbFieldScale) {
        this.dbFieldScale = dbFieldScale;
        return this;
    }

    public void setDbFieldScale(Integer dbFieldScale) {
        this.dbFieldScale = dbFieldScale;
    }

    public Integer getDbFieldPrecision() {
        return dbFieldPrecision;
    }

    public SourceDatabaseMapping dbFieldPrecision(Integer dbFieldPrecision) {
        this.dbFieldPrecision = dbFieldPrecision;
        return this;
    }

    public void setDbFieldPrecision(Integer dbFieldPrecision) {
        this.dbFieldPrecision = dbFieldPrecision;
    }

    public SourceDatabase getSourceDatabase() {
        return sourceDatabase;
    }

    public SourceDatabaseMapping sourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
        return this;
    }

    public void setSourceDatabase(SourceDatabase sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
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
        SourceDatabaseMapping sourceDatabaseMapping = (SourceDatabaseMapping) o;
        if (sourceDatabaseMapping.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceDatabaseMapping.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceDatabaseMapping{" +
            "id=" + getId() +
            ", dbColumnName='" + getDbColumnName() + "'" +
            ", dbDataType='" + getDbDataType() + "'" +
            ", dbFieldScale=" + getDbFieldScale() +
            ", dbFieldPrecision=" + getDbFieldPrecision() +
            "}";
    }
}
