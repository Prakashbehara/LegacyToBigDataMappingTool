package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.FieldDataType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the SourceDatabaseMapping entity. This class is used in SourceDatabaseMappingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /source-database-mappings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceDatabaseMappingCriteria implements Serializable {
    /**
     * Class for filtering FieldDataType
     */
    public static class FieldDataTypeFilter extends Filter<FieldDataType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dbColumnName;

    private FieldDataTypeFilter dbDataType;

    private IntegerFilter dbFieldScale;

    private IntegerFilter dbFieldPrecision;

    private LongFilter sourceDatabaseId;

    public SourceDatabaseMappingCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDbColumnName() {
        return dbColumnName;
    }

    public void setDbColumnName(StringFilter dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public FieldDataTypeFilter getDbDataType() {
        return dbDataType;
    }

    public void setDbDataType(FieldDataTypeFilter dbDataType) {
        this.dbDataType = dbDataType;
    }

    public IntegerFilter getDbFieldScale() {
        return dbFieldScale;
    }

    public void setDbFieldScale(IntegerFilter dbFieldScale) {
        this.dbFieldScale = dbFieldScale;
    }

    public IntegerFilter getDbFieldPrecision() {
        return dbFieldPrecision;
    }

    public void setDbFieldPrecision(IntegerFilter dbFieldPrecision) {
        this.dbFieldPrecision = dbFieldPrecision;
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
        final SourceDatabaseMappingCriteria that = (SourceDatabaseMappingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dbColumnName, that.dbColumnName) &&
            Objects.equals(dbDataType, that.dbDataType) &&
            Objects.equals(dbFieldScale, that.dbFieldScale) &&
            Objects.equals(dbFieldPrecision, that.dbFieldPrecision) &&
            Objects.equals(sourceDatabaseId, that.sourceDatabaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dbColumnName,
        dbDataType,
        dbFieldScale,
        dbFieldPrecision,
        sourceDatabaseId
        );
    }

    @Override
    public String toString() {
        return "SourceDatabaseMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dbColumnName != null ? "dbColumnName=" + dbColumnName + ", " : "") +
                (dbDataType != null ? "dbDataType=" + dbDataType + ", " : "") +
                (dbFieldScale != null ? "dbFieldScale=" + dbFieldScale + ", " : "") +
                (dbFieldPrecision != null ? "dbFieldPrecision=" + dbFieldPrecision + ", " : "") +
                (sourceDatabaseId != null ? "sourceDatabaseId=" + sourceDatabaseId + ", " : "") +
            "}";
    }

}
