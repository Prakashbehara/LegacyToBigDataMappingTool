package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.TableType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the SourceDatabase entity. This class is used in SourceDatabaseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /source-databases?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceDatabaseCriteria implements Serializable {
    /**
     * Class for filtering TableType
     */
    public static class TableTypeFilter extends Filter<TableType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tableName;

    private StringFilter schema;

    private TableTypeFilter tableType;

    private LongFilter applicationId;

    private LongFilter sourceDatabaseMappingId;

    public SourceDatabaseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTableName() {
        return tableName;
    }

    public void setTableName(StringFilter tableName) {
        this.tableName = tableName;
    }

    public StringFilter getSchema() {
        return schema;
    }

    public void setSchema(StringFilter schema) {
        this.schema = schema;
    }

    public TableTypeFilter getTableType() {
        return tableType;
    }

    public void setTableType(TableTypeFilter tableType) {
        this.tableType = tableType;
    }

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
    }

    public LongFilter getSourceDatabaseMappingId() {
        return sourceDatabaseMappingId;
    }

    public void setSourceDatabaseMappingId(LongFilter sourceDatabaseMappingId) {
        this.sourceDatabaseMappingId = sourceDatabaseMappingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourceDatabaseCriteria that = (SourceDatabaseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(tableName, that.tableName) &&
            Objects.equals(schema, that.schema) &&
            Objects.equals(tableType, that.tableType) &&
            Objects.equals(applicationId, that.applicationId) &&
            Objects.equals(sourceDatabaseMappingId, that.sourceDatabaseMappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        tableName,
        schema,
        tableType,
        applicationId,
        sourceDatabaseMappingId
        );
    }

    @Override
    public String toString() {
        return "SourceDatabaseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (tableName != null ? "tableName=" + tableName + ", " : "") +
                (schema != null ? "schema=" + schema + ", " : "") +
                (tableType != null ? "tableType=" + tableType + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                (sourceDatabaseMappingId != null ? "sourceDatabaseMappingId=" + sourceDatabaseMappingId + ", " : "") +
            "}";
    }

}
