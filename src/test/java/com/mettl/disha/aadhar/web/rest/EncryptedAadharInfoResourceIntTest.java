package com.mettl.disha.aadhar.web.rest;

import com.mettl.disha.aadhar.MyapplicationApp;

import com.mettl.disha.aadhar.domain.EncryptedAadharInfo;
import com.mettl.disha.aadhar.repository.EncryptedAadharInfoRepository;
import com.mettl.disha.aadhar.service.EncryptedAadharInfoService;
import com.mettl.disha.aadhar.service.dto.EncryptedAadharInfoDTO;
import com.mettl.disha.aadhar.service.mapper.EncryptedAadharInfoMapper;
import com.mettl.disha.aadhar.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mettl.disha.aadhar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EncryptedAadharInfoResource REST controller.
 *
 * @see EncryptedAadharInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyapplicationApp.class)
public class EncryptedAadharInfoResourceIntTest {

    private static final String DEFAULT_ENCRYPTED_AADHAR = "AAAAAAAAAA";
    private static final String UPDATED_ENCRYPTED_AADHAR = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_ID = "BBBBBBBBBB";

    @Autowired
    private EncryptedAadharInfoRepository encryptedAadharInfoRepository;

    @Autowired
    private EncryptedAadharInfoMapper encryptedAadharInfoMapper;

    @Autowired
    private EncryptedAadharInfoService encryptedAadharInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEncryptedAadharInfoMockMvc;

    private EncryptedAadharInfo encryptedAadharInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EncryptedAadharInfoResource encryptedAadharInfoResource = new EncryptedAadharInfoResource(encryptedAadharInfoService);
        this.restEncryptedAadharInfoMockMvc = MockMvcBuilders.standaloneSetup(encryptedAadharInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EncryptedAadharInfo createEntity(EntityManager em) {
        EncryptedAadharInfo encryptedAadharInfo = new EncryptedAadharInfo()
            .encryptedAadhar(DEFAULT_ENCRYPTED_AADHAR)
            .referenceId(DEFAULT_REFERENCE_ID);
        return encryptedAadharInfo;
    }

    @Before
    public void initTest() {
        encryptedAadharInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncryptedAadharInfo() throws Exception {
        int databaseSizeBeforeCreate = encryptedAadharInfoRepository.findAll().size();

        // Create the EncryptedAadharInfo
        EncryptedAadharInfoDTO encryptedAadharInfoDTO = encryptedAadharInfoMapper.toDto(encryptedAadharInfo);
        restEncryptedAadharInfoMockMvc.perform(post("/api/encrypted-aadhar-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encryptedAadharInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the EncryptedAadharInfo in the database
        List<EncryptedAadharInfo> encryptedAadharInfoList = encryptedAadharInfoRepository.findAll();
        assertThat(encryptedAadharInfoList).hasSize(databaseSizeBeforeCreate + 1);
        EncryptedAadharInfo testEncryptedAadharInfo = encryptedAadharInfoList.get(encryptedAadharInfoList.size() - 1);
        assertThat(testEncryptedAadharInfo.getEncryptedAadhar()).isEqualTo(DEFAULT_ENCRYPTED_AADHAR);
        assertThat(testEncryptedAadharInfo.getReferenceId()).isEqualTo(DEFAULT_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void createEncryptedAadharInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encryptedAadharInfoRepository.findAll().size();

        // Create the EncryptedAadharInfo with an existing ID
        encryptedAadharInfo.setId(1L);
        EncryptedAadharInfoDTO encryptedAadharInfoDTO = encryptedAadharInfoMapper.toDto(encryptedAadharInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncryptedAadharInfoMockMvc.perform(post("/api/encrypted-aadhar-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encryptedAadharInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EncryptedAadharInfo in the database
        List<EncryptedAadharInfo> encryptedAadharInfoList = encryptedAadharInfoRepository.findAll();
        assertThat(encryptedAadharInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEncryptedAadharInfos() throws Exception {
        // Initialize the database
        encryptedAadharInfoRepository.saveAndFlush(encryptedAadharInfo);

        // Get all the encryptedAadharInfoList
        restEncryptedAadharInfoMockMvc.perform(get("/api/encrypted-aadhar-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encryptedAadharInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].encryptedAadhar").value(hasItem(DEFAULT_ENCRYPTED_AADHAR.toString())))
            .andExpect(jsonPath("$.[*].referenceId").value(hasItem(DEFAULT_REFERENCE_ID.toString())));
    }

    @Test
    @Transactional
    public void getEncryptedAadharInfo() throws Exception {
        // Initialize the database
        encryptedAadharInfoRepository.saveAndFlush(encryptedAadharInfo);

        // Get the encryptedAadharInfo
        restEncryptedAadharInfoMockMvc.perform(get("/api/encrypted-aadhar-infos/{id}", encryptedAadharInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(encryptedAadharInfo.getId().intValue()))
            .andExpect(jsonPath("$.encryptedAadhar").value(DEFAULT_ENCRYPTED_AADHAR.toString()))
            .andExpect(jsonPath("$.referenceId").value(DEFAULT_REFERENCE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEncryptedAadharInfo() throws Exception {
        // Get the encryptedAadharInfo
        restEncryptedAadharInfoMockMvc.perform(get("/api/encrypted-aadhar-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncryptedAadharInfo() throws Exception {
        // Initialize the database
        encryptedAadharInfoRepository.saveAndFlush(encryptedAadharInfo);
        int databaseSizeBeforeUpdate = encryptedAadharInfoRepository.findAll().size();

        // Update the encryptedAadharInfo
        EncryptedAadharInfo updatedEncryptedAadharInfo = encryptedAadharInfoRepository.findOne(encryptedAadharInfo.getId());
        // Disconnect from session so that the updates on updatedEncryptedAadharInfo are not directly saved in db
        em.detach(updatedEncryptedAadharInfo);
        updatedEncryptedAadharInfo
            .encryptedAadhar(UPDATED_ENCRYPTED_AADHAR)
            .referenceId(UPDATED_REFERENCE_ID);
        EncryptedAadharInfoDTO encryptedAadharInfoDTO = encryptedAadharInfoMapper.toDto(updatedEncryptedAadharInfo);

        restEncryptedAadharInfoMockMvc.perform(put("/api/encrypted-aadhar-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encryptedAadharInfoDTO)))
            .andExpect(status().isOk());

        // Validate the EncryptedAadharInfo in the database
        List<EncryptedAadharInfo> encryptedAadharInfoList = encryptedAadharInfoRepository.findAll();
        assertThat(encryptedAadharInfoList).hasSize(databaseSizeBeforeUpdate);
        EncryptedAadharInfo testEncryptedAadharInfo = encryptedAadharInfoList.get(encryptedAadharInfoList.size() - 1);
        assertThat(testEncryptedAadharInfo.getEncryptedAadhar()).isEqualTo(UPDATED_ENCRYPTED_AADHAR);
        assertThat(testEncryptedAadharInfo.getReferenceId()).isEqualTo(UPDATED_REFERENCE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingEncryptedAadharInfo() throws Exception {
        int databaseSizeBeforeUpdate = encryptedAadharInfoRepository.findAll().size();

        // Create the EncryptedAadharInfo
        EncryptedAadharInfoDTO encryptedAadharInfoDTO = encryptedAadharInfoMapper.toDto(encryptedAadharInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEncryptedAadharInfoMockMvc.perform(put("/api/encrypted-aadhar-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(encryptedAadharInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the EncryptedAadharInfo in the database
        List<EncryptedAadharInfo> encryptedAadharInfoList = encryptedAadharInfoRepository.findAll();
        assertThat(encryptedAadharInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEncryptedAadharInfo() throws Exception {
        // Initialize the database
        encryptedAadharInfoRepository.saveAndFlush(encryptedAadharInfo);
        int databaseSizeBeforeDelete = encryptedAadharInfoRepository.findAll().size();

        // Get the encryptedAadharInfo
        restEncryptedAadharInfoMockMvc.perform(delete("/api/encrypted-aadhar-infos/{id}", encryptedAadharInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EncryptedAadharInfo> encryptedAadharInfoList = encryptedAadharInfoRepository.findAll();
        assertThat(encryptedAadharInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncryptedAadharInfo.class);
        EncryptedAadharInfo encryptedAadharInfo1 = new EncryptedAadharInfo();
        encryptedAadharInfo1.setId(1L);
        EncryptedAadharInfo encryptedAadharInfo2 = new EncryptedAadharInfo();
        encryptedAadharInfo2.setId(encryptedAadharInfo1.getId());
        assertThat(encryptedAadharInfo1).isEqualTo(encryptedAadharInfo2);
        encryptedAadharInfo2.setId(2L);
        assertThat(encryptedAadharInfo1).isNotEqualTo(encryptedAadharInfo2);
        encryptedAadharInfo1.setId(null);
        assertThat(encryptedAadharInfo1).isNotEqualTo(encryptedAadharInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EncryptedAadharInfoDTO.class);
        EncryptedAadharInfoDTO encryptedAadharInfoDTO1 = new EncryptedAadharInfoDTO();
        encryptedAadharInfoDTO1.setId(1L);
        EncryptedAadharInfoDTO encryptedAadharInfoDTO2 = new EncryptedAadharInfoDTO();
        assertThat(encryptedAadharInfoDTO1).isNotEqualTo(encryptedAadharInfoDTO2);
        encryptedAadharInfoDTO2.setId(encryptedAadharInfoDTO1.getId());
        assertThat(encryptedAadharInfoDTO1).isEqualTo(encryptedAadharInfoDTO2);
        encryptedAadharInfoDTO2.setId(2L);
        assertThat(encryptedAadharInfoDTO1).isNotEqualTo(encryptedAadharInfoDTO2);
        encryptedAadharInfoDTO1.setId(null);
        assertThat(encryptedAadharInfoDTO1).isNotEqualTo(encryptedAadharInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(encryptedAadharInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(encryptedAadharInfoMapper.fromId(null)).isNull();
    }
}
