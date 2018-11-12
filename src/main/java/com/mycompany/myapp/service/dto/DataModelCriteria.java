package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mycompany.myapp.domain.enumeration.Domain;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the DataModel entity. This class is used in DataModelResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /data-models?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DataModelCriteria implements Serializable {
    /**
     * Class for filtering Domain
     */
    public static class DomainFilter extends Filter<Domain> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter entitiyName;

    private DomainFilter domain;

    private LongFilter dataModelMappingId;

    public DataModelCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEntitiyName() {
        return entitiyName;
    }

    public void setEntitiyName(StringFilter entitiyName) {
        this.entitiyName = entitiyName;
    }

    public DomainFilter getDomain() {
        return domain;
    }

    public void setDomain(DomainFilter domain) {
        this.domain = domain;
    }

    public LongFilter getDataModelMappingId() {
        return dataModelMappingId;
    }

    public void setDataModelMappingId(LongFilter dataModelMappingId) {
        this.dataModelMappingId = dataModelMappingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DataModelCriteria that = (DataModelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(entitiyName, that.entitiyName) &&
            Objects.equals(domain, that.domain) &&
            Objects.equals(dataModelMappingId, that.dataModelMappingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        entitiyName,
        domain,
        dataModelMappingId
        );
    }

    @Override
    public String toString() {
        return "DataModelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entitiyName != null ? "entitiyName=" + entitiyName + ", " : "") +
                (domain != null ? "domain=" + domain + ", " : "") +
                (dataModelMappingId != null ? "dataModelMappingId=" + dataModelMappingId + ", " : "") +
            "}";
    }

}
