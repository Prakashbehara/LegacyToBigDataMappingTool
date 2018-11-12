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

import com.mycompany.myapp.domain.enumeration.TableType;

/**
 * A SourceDatabase.
 */
@Entity
@Table(name = "source_database")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SourceDatabase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "jhi_schema")
    private String schema;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_type")
    private TableType tableType;

    @ManyToOne
    @JsonIgnoreProperties("sourceDatabases")
    private Application application;

    @OneToMany(mappedBy = "sourceDatabase")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SourceDatabaseMapping> sourceDatabaseMappings = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public SourceDatabase tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchema() {
        return schema;
    }

    public SourceDatabase schema(String schema) {
        this.schema = schema;
        return this;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public TableType getTableType() {
        return tableType;
    }

    public SourceDatabase tableType(TableType tableType) {
        this.tableType = tableType;
        return this;
    }

    public void setTableType(TableType tableType) {
        this.tableType = tableType;
    }

    public Application getApplication() {
        return application;
    }

    public SourceDatabase application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Set<SourceDatabaseMapping> getSourceDatabaseMappings() {
        return sourceDatabaseMappings;
    }

    public SourceDatabase sourceDatabaseMappings(Set<SourceDatabaseMapping> sourceDatabaseMappings) {
        this.sourceDatabaseMappings = sourceDatabaseMappings;
        return this;
    }

    public SourceDatabase addSourceDatabaseMapping(SourceDatabaseMapping sourceDatabaseMapping) {
        this.sourceDatabaseMappings.add(sourceDatabaseMapping);
        sourceDatabaseMapping.setSourceDatabase(this);
        return this;
    }

    public SourceDatabase removeSourceDatabaseMapping(SourceDatabaseMapping sourceDatabaseMapping) {
        this.sourceDatabaseMappings.remove(sourceDatabaseMapping);
        sourceDatabaseMapping.setSourceDatabase(null);
        return this;
    }

    public void setSourceDatabaseMappings(Set<SourceDatabaseMapping> sourceDatabaseMappings) {
        this.sourceDatabaseMappings = sourceDatabaseMappings;
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
        SourceDatabase sourceDatabase = (SourceDatabase) o;
        if (sourceDatabase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sourceDatabase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SourceDatabase{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            ", schema='" + getSchema() + "'" +
            ", tableType='" + getTableType() + "'" +
            "}";
    }
}
