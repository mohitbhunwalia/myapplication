package com.mettl.disha.aadhar.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the EncryptedAadharInfo entity.
 */
public class EncryptedAadharInfoDTO implements Serializable {

    private Long id;

    private String encryptedAadhar;

    private String referenceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncryptedAadhar() {
        return encryptedAadhar;
    }

    public void setEncryptedAadhar(String encryptedAadhar) {
        this.encryptedAadhar = encryptedAadhar;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EncryptedAadharInfoDTO encryptedAadharInfoDTO = (EncryptedAadharInfoDTO) o;
        if(encryptedAadharInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), encryptedAadharInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EncryptedAadharInfoDTO{" +
            "id=" + getId() +
            ", encryptedAadhar='" + getEncryptedAadhar() + "'" +
            ", referenceId='" + getReferenceId() + "'" +
            "}";
    }
}
