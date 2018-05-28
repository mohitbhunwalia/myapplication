package com.mettl.disha.aadhar.service.impl;

import com.mettl.disha.aadhar.service.EncryptedAadharInfoService;
import com.mettl.disha.aadhar.domain.EncryptedAadharInfo;
import com.mettl.disha.aadhar.repository.EncryptedAadharInfoRepository;
import com.mettl.disha.aadhar.service.dto.EncryptedAadharInfoDTO;
import com.mettl.disha.aadhar.service.mapper.EncryptedAadharInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EncryptedAadharInfo.
 */
@Service
@Transactional
public class EncryptedAadharInfoServiceImpl implements EncryptedAadharInfoService {

    private final Logger log = LoggerFactory.getLogger(EncryptedAadharInfoServiceImpl.class);

    private final EncryptedAadharInfoRepository encryptedAadharInfoRepository;

    private final EncryptedAadharInfoMapper encryptedAadharInfoMapper;

    public EncryptedAadharInfoServiceImpl(EncryptedAadharInfoRepository encryptedAadharInfoRepository, EncryptedAadharInfoMapper encryptedAadharInfoMapper) {
        this.encryptedAadharInfoRepository = encryptedAadharInfoRepository;
        this.encryptedAadharInfoMapper = encryptedAadharInfoMapper;
    }

    /**
     * Save a encryptedAadharInfo.
     *
     * @param encryptedAadharInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EncryptedAadharInfoDTO save(EncryptedAadharInfoDTO encryptedAadharInfoDTO) {
        log.debug("Request to save EncryptedAadharInfo : {}", encryptedAadharInfoDTO);
        EncryptedAadharInfo encryptedAadharInfo = encryptedAadharInfoMapper.toEntity(encryptedAadharInfoDTO);
        encryptedAadharInfo = encryptedAadharInfoRepository.save(encryptedAadharInfo);
        return encryptedAadharInfoMapper.toDto(encryptedAadharInfo);
    }

    /**
     * Get all the encryptedAadharInfos.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EncryptedAadharInfoDTO> findAll() {
        log.debug("Request to get all EncryptedAadharInfos");
        return encryptedAadharInfoRepository.findAll().stream()
            .map(encryptedAadharInfoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one encryptedAadharInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EncryptedAadharInfoDTO findOne(Long id) {
        log.debug("Request to get EncryptedAadharInfo : {}", id);
        EncryptedAadharInfo encryptedAadharInfo = encryptedAadharInfoRepository.findOne(id);
        return encryptedAadharInfoMapper.toDto(encryptedAadharInfo);
    }

    /**
     * Delete the encryptedAadharInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EncryptedAadharInfo : {}", id);
        encryptedAadharInfoRepository.delete(id);
    }
}
