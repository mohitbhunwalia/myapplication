package com.mettl.disha.aadhar.repository;

import com.mettl.disha.aadhar.domain.EncryptedAadharInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EncryptedAadharInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncryptedAadharInfoRepository extends JpaRepository<EncryptedAadharInfo, Long> {

}
