package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Domain;

/**
 * A DataModel.
 */
@Entity
@Table(name = "data_model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entitiy_name")
    private String entitiyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "domain")
    private Domain domain;

    @OneToMany(mappedBy = "dataModel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DataModelMapping> dataModelMappings = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntitiyName() {
        return entitiyName;
    }

    public DataModel entitiyName(String entitiyName) {
        this.entitiyName = entitiyName;
        return this;
    }

    public void setEntitiyName(String entitiyName) {
        this.entitiyName = entitiyName;
    }

    public Domain getDomain() {
        return domain;
    }

    public DataModel domain(Domain domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<DataModelMapping> getDataModelMappings() {
        return dataModelMappings;
    }

    public DataModel dataModelMappings(Set<DataModelMapping> dataModelMappings) {
        this.dataModelMappings = dataModelMappings;
        return this;
    }

    public DataModel addDataModelMapping(DataModelMapping dataModelMapping) {
        this.dataModelMappings.add(dataModelMapping);
        dataModelMapping.setDataModel(this);
        return this;
    }

    public DataModel removeDataModelMapping(DataModelMapping dataModelMapping) {
        this.dataModelMappings.remove(dataModelMapping);
        dataModelMapping.setDataModel(null);
        return this;
    }

    public void setDataModelMappings(Set<DataModelMapping> dataModelMappings) {
        this.dataModelMappings = dataModelMappings;
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
        DataModel dataModel = (DataModel) o;
        if (dataModel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataModel{" +
            "id=" + getId() +
            ", entitiyName='" + getEntitiyName() + "'" +
            ", domain='" + getDomain() + "'" +
            "}";
    }
}
