package com.mettl.disha.aadhar.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A EncryptedAadharInfo.
 */
@Entity
@Table(name = "encrypted_aadhar_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EncryptedAadharInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "encrypted_aadhar")
    private String encryptedAadhar;

    @Column(name = "reference_id")
    private String referenceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncryptedAadhar() {
        return encryptedAadhar;
    }

    public EncryptedAadharInfo encryptedAadhar(String encryptedAadhar) {
        this.encryptedAadhar = encryptedAadhar;
        return this;
    }

    public void setEncryptedAadhar(String encryptedAadhar) {
        this.encryptedAadhar = encryptedAadhar;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public EncryptedAadharInfo referenceId(String referenceId) {
        this.referenceId = referenceId;
        return this;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
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
        EncryptedAadharInfo encryptedAadharInfo = (EncryptedAadharInfo) o;
        if (encryptedAadharInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), encryptedAadharInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EncryptedAadharInfo{" +
            "id=" + getId() +
            ", encryptedAadhar='" + getEncryptedAadhar() + "'" +
            ", referenceId='" + getReferenceId() + "'" +
            "}";
    }
}
