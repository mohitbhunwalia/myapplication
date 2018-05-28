package com.mettl.disha.aadhar.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mettl.disha.aadhar.service.EncryptedAadharInfoService;
import com.mettl.disha.aadhar.web.rest.errors.BadRequestAlertException;
import com.mettl.disha.aadhar.web.rest.util.HeaderUtil;
import com.mettl.disha.aadhar.service.dto.EncryptedAadharInfoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EncryptedAadharInfo.
 */
@RestController
@RequestMapping("/api")
public class EncryptedAadharInfoResource {

    private final Logger log = LoggerFactory.getLogger(EncryptedAadharInfoResource.class);

    private static final String ENTITY_NAME = "encryptedAadharInfo";

    private final EncryptedAadharInfoService encryptedAadharInfoService;

    public EncryptedAadharInfoResource(EncryptedAadharInfoService encryptedAadharInfoService) {
        this.encryptedAadharInfoService = encryptedAadharInfoService;
    }

    /**
     * POST  /encrypted-aadhar-infos : Create a new encryptedAadharInfo.
     *
     * @param encryptedAadharInfoDTO the encryptedAadharInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new encryptedAadharInfoDTO, or with status 400 (Bad Request) if the encryptedAadharInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/encrypted-aadhar-infos")
    @Timed
    public ResponseEntity<EncryptedAadharInfoDTO> createEncryptedAadharInfo(@RequestBody EncryptedAadharInfoDTO encryptedAadharInfoDTO) throws URISyntaxException {
        log.debug("REST request to save EncryptedAadharInfo : {}", encryptedAadharInfoDTO);
        if (encryptedAadharInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new encryptedAadharInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncryptedAadharInfoDTO result = encryptedAadharInfoService.save(encryptedAadharInfoDTO);
        return ResponseEntity.created(new URI("/api/encrypted-aadhar-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /encrypted-aadhar-infos : Updates an existing encryptedAadharInfo.
     *
     * @param encryptedAadharInfoDTO the encryptedAadharInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated encryptedAadharInfoDTO,
     * or with status 400 (Bad Request) if the encryptedAadharInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the encryptedAadharInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/encrypted-aadhar-infos")
    @Timed
    public ResponseEntity<EncryptedAadharInfoDTO> updateEncryptedAadharInfo(@RequestBody EncryptedAadharInfoDTO encryptedAadharInfoDTO) throws URISyntaxException {
        log.debug("REST request to update EncryptedAadharInfo : {}", encryptedAadharInfoDTO);
        if (encryptedAadharInfoDTO.getId() == null) {
            return createEncryptedAadharInfo(encryptedAadharInfoDTO);
        }
        EncryptedAadharInfoDTO result = encryptedAadharInfoService.save(encryptedAadharInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, encryptedAadharInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /encrypted-aadhar-infos : get all the encryptedAadharInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of encryptedAadharInfos in body
     */
    @GetMapping("/encrypted-aadhar-infos")
    @Timed
    public List<EncryptedAadharInfoDTO> getAllEncryptedAadharInfos() {
        log.debug("REST request to get all EncryptedAadharInfos");
        return encryptedAadharInfoService.findAll();
        }

    /**
     * GET  /encrypted-aadhar-infos/:id : get the "id" encryptedAadharInfo.
     *
     * @param id the id of the encryptedAadharInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the encryptedAadharInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/encrypted-aadhar-infos/{id}")
    @Timed
    public ResponseEntity<EncryptedAadharInfoDTO> getEncryptedAadharInfo(@PathVariable Long id) {
        log.debug("REST request to get EncryptedAadharInfo : {}", id);
        EncryptedAadharInfoDTO encryptedAadharInfoDTO = encryptedAadharInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(encryptedAadharInfoDTO));
    }

    /**
     * DELETE  /encrypted-aadhar-infos/:id : delete the "id" encryptedAadharInfo.
     *
     * @param id the id of the encryptedAadharInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/encrypted-aadhar-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEncryptedAadharInfo(@PathVariable Long id) {
        log.debug("REST request to delete EncryptedAadharInfo : {}", id);
        encryptedAadharInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
