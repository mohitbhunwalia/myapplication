package com.mettl.disha.aadhar.service;

import com.mettl.disha.aadhar.service.dto.EncryptedAadharInfoDTO;
import java.util.List;

/**
 * Service Interface for managing EncryptedAadharInfo.
 */
public interface EncryptedAadharInfoService {

    /**
     * Save a encryptedAadharInfo.
     *
     * @param encryptedAadharInfoDTO the entity to save
     * @return the persisted entity
     */
    EncryptedAadharInfoDTO save(EncryptedAadharInfoDTO encryptedAadharInfoDTO);

    /**
     * Get all the encryptedAadharInfos.
     *
     * @return the list of entities
     */
    List<EncryptedAadharInfoDTO> findAll();

    /**
     * Get the "id" encryptedAadharInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EncryptedAadharInfoDTO findOne(Long id);

    /**
     * Delete the "id" encryptedAadharInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
