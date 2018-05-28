package com.mettl.disha.aadhar.service.mapper;

import com.mettl.disha.aadhar.domain.*;
import com.mettl.disha.aadhar.service.dto.EncryptedAadharInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EncryptedAadharInfo and its DTO EncryptedAadharInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EncryptedAadharInfoMapper extends EntityMapper<EncryptedAadharInfoDTO, EncryptedAadharInfo> {



    default EncryptedAadharInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        EncryptedAadharInfo encryptedAadharInfo = new EncryptedAadharInfo();
        encryptedAadharInfo.setId(id);
        return encryptedAadharInfo;
    }
}
