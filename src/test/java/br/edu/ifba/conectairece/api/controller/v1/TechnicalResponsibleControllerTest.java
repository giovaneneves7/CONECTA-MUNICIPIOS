//package br.edu.ifba.conectairece.api.controller.v1;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.AssociationActionRequestDTO;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service.ConstructionLicenseRequirementIService;
//import br.edu.ifba.conectairece.api.features.document.domain.dto.request.DocumentCorrectionSuggestionDTO;
//import br.edu.ifba.conectairece.api.features.document.domain.dto.response.DocumentDetailResponseDTO;
//import br.edu.ifba.conectairece.api.features.document.domain.enums.DocumentStatus;
//import br.edu.ifba.conectairece.api.features.document.domain.service.IDocumentService;
//import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.request.TechnicalResponsibleRequestDto;
//import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.dto.response.TechnicalResponsibleResponseDto;
//import br.edu.ifba.conectairece.api.features.technicalResponsible.domain.service.ITechnicalResponsibleService;
//
//@ExtendWith(MockitoExtension.class)
//public class TechnicalResponsibleControllerTest {
//
//    private MockMvc mockMvc;
//    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//
//    @Mock
//    private ITechnicalResponsibleService service;
//
//    @Mock
//    private ConstructionLicenseRequirementIService requirementService;
//
//    @Mock
//    private IDocumentService documentService;
//
//    @InjectMocks
//    private TechnicalResponsibleController controller;
//
//private final String BASE_URL = "/api/v1/technical-responsibles";
//    private final UUID SAMPLE_RESPONSIBLE_ID = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
//    private final UUID SAMPLE_USER_ID = UUID.fromString("b1b2c3d4-e5f6-7890-1234-567890abcde0");
//    private final String SAMPLE_REGISTRATION_ID = "12345-D";
//    private final Long SAMPLE_REQUIREMENT_ID = 1L;
//    private final UUID SAMPLE_DOCUMENT_ID = UUID.fromString("c1b2c3d4-e5f6-7890-1234-567890abcde1");
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(controller)
//                .build();
//    }
//
//    @Test
//    void create_ShouldReturn201AndDto_WhenDtoIsValid() throws Exception {
//        TechnicalResponsibleRequestDto requestDto = new TechnicalResponsibleRequestDto(SAMPLE_REGISTRATION_ID, "Engenheiro Civil", "http://image.url/img.png", SAMPLE_USER_ID);
//        TechnicalResponsibleResponseDto responseDto = new TechnicalResponsibleResponseDto(SAMPLE_RESPONSIBLE_ID, SAMPLE_REGISTRATION_ID, "Engenheiro Civil", "http://image.url/img.png", "John Doe", "12345678901", "john.doe@email.com", "987654321");
//
//        when(service.save(any(TechnicalResponsibleRequestDto.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post(BASE_URL + "/technical-responsible")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(SAMPLE_RESPONSIBLE_ID.toString()))
//              .andExpect(jsonPath("$.responsibleName").value("John Doe"))
//                .andExpect(jsonPath("$.registrationId").value("12345-D"));
//
//        verify(service, times(1)).save(any(TechnicalResponsibleRequestDto.class));
//    }
//
//    @Test
//    void create_ShouldReturn400_WhenDtoIsInvalid() throws Exception {
//        TechnicalResponsibleRequestDto invalidDto = new TechnicalResponsibleRequestDto(null, "", "http://image.url/img.png", null);
//
//        mockMvc.perform(post(BASE_URL + "/technical-responsible")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest()); // O controller retorna 400 para falha de validação
//
//        verify(service, never()).save(any());
//    }
//
//    @Test
//    void getAll_ShouldReturn200AndListOfDtos() throws Exception {
//        TechnicalResponsibleResponseDto dto1 = new TechnicalResponsibleResponseDto(SAMPLE_RESPONSIBLE_ID, SAMPLE_REGISTRATION_ID, "Engenheiro Civil", "http://image.url/img.png", "John Doe", "12345678901", "john.doe@email.com", "987654321");
//        List<TechnicalResponsibleResponseDto> list = List.of(dto1);
//
//        when(service.findAll()).thenReturn(list);
//
//        mockMvc.perform(get(BASE_URL)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].responsibleName").value("John Doe"));
//
//        verify(service, times(1)).findAll();
//    }
//
//    @Test
//    void delete_ShouldReturn204_WhenSuccessful() throws Exception {
//        doNothing().when(service).delete(eq(SAMPLE_RESPONSIBLE_ID));
//
//        mockMvc.perform(delete(BASE_URL + "/technical-responsible/{id}", SAMPLE_RESPONSIBLE_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(service, times(1)).delete(eq(SAMPLE_RESPONSIBLE_ID));
//    }
//
//    @Test
//    void acceptRequirement_ShouldReturn204_WhenSuccessful() throws Exception {
//        AssociationActionRequestDTO requestDto = new AssociationActionRequestDTO(SAMPLE_REGISTRATION_ID, SAMPLE_REQUIREMENT_ID);
//
//        doNothing().when(requirementService).approveAssociation(any(AssociationActionRequestDTO.class));
//
//        mockMvc.perform(post(BASE_URL + "/accept-requirement")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isNoContent());
//
//        verify(requirementService, times(1)).approveAssociation(any(AssociationActionRequestDTO.class));
//    }
//
//    @Test
//    void refuseRequirement_ShouldReturn204_WhenSuccessful() throws Exception {
//        RejectionRequestDTO requestDto = new RejectionRequestDTO(SAMPLE_REGISTRATION_ID, SAMPLE_REQUIREMENT_ID, "Justificativa válida.");
//
//        doNothing().when(requirementService).rejectAssociation(any(RejectionRequestDTO.class));
//
//        mockMvc.perform(post(BASE_URL + "/refuse-requirement")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isNoContent());
//
//        verify(requirementService, times(1)).rejectAssociation(any(RejectionRequestDTO.class));
//    }
//
//    @Test
//    void refuseRequirement_ShouldReturn400_WhenDtoIsInvalid() throws Exception {
//        RejectionRequestDTO invalidDto = new RejectionRequestDTO(SAMPLE_REGISTRATION_ID, SAMPLE_REQUIREMENT_ID, "");
//
//        mockMvc.perform(post(BASE_URL + "/refuse-requirement")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(requirementService, never()).rejectAssociation(any());
//    }
//
//
//    @Test
//    void getByRegistrationId_ShouldReturn200AndDto_WhenFound() throws Exception {
//        TechnicalResponsibleResponseDto responseDto = new TechnicalResponsibleResponseDto(SAMPLE_RESPONSIBLE_ID, SAMPLE_REGISTRATION_ID, "Engenheiro Civil", "http://image.url/img.png", "John Doe", "12345678901", "john.doe@email.com", "987654321");
//
//        when(service.findByRegistrationId(eq(SAMPLE_REGISTRATION_ID))).thenReturn(Optional.of(responseDto));
//
//        mockMvc.perform(get(BASE_URL + "/technical-responsible/{registrationId}", SAMPLE_REGISTRATION_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SAMPLE_RESPONSIBLE_ID.toString()))
//                .andExpect(jsonPath("$.registrationId").value(SAMPLE_REGISTRATION_ID));
//
//        verify(service, times(1)).findByRegistrationId(eq(SAMPLE_REGISTRATION_ID));
//    }
//
//    @Test
//    void getByRegistrationId_ShouldReturn404_WhenNotFound() throws Exception {
//        when(service.findByRegistrationId(eq(SAMPLE_REGISTRATION_ID))).thenReturn(Optional.empty());
//
//        mockMvc.perform(get(BASE_URL + "/technical-responsible/{registrationId}", SAMPLE_REGISTRATION_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//
//        verify(service, times(1)).findByRegistrationId(eq(SAMPLE_REGISTRATION_ID));
//    }
//
//    @Test
//    void getRequirementsByResponsibleId_ShouldReturn200AndListOfRequirements() throws Exception {
//        ConstructionLicenseRequirementResponseDTO reqDto = new ConstructionLicenseRequirementResponseDTO(SAMPLE_REQUIREMENT_ID, LocalDateTime.now(), "Owner", "Phone", "CEP", "CPF", "Addr", 100f, "Resp Name", AssociationStatus.APPROVED);
//        List<ConstructionLicenseRequirementResponseDTO> list = List.of(reqDto);
//
//        when(requirementService.findAllByTechnicalResponsible(eq(SAMPLE_RESPONSIBLE_ID))).thenReturn(list);
//
//        mockMvc.perform(get(BASE_URL + "/{responsibleId}/requirements", SAMPLE_RESPONSIBLE_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].id").value(SAMPLE_REQUIREMENT_ID));
//
//        verify(requirementService, times(1)).findAllByTechnicalResponsible(eq(SAMPLE_RESPONSIBLE_ID));
//    }
//
//    @Test
//    void getRequirementsByRegistrationId_ShouldReturn200AndListOfRequirements() throws Exception {
//        ConstructionLicenseRequirementResponseDTO reqDto = new ConstructionLicenseRequirementResponseDTO(SAMPLE_REQUIREMENT_ID, LocalDateTime.now(), "Owner", "Phone", "CEP", "CPF", "Addr", 100f, "Resp Name", AssociationStatus.APPROVED);
//        List<ConstructionLicenseRequirementResponseDTO> list = List.of(reqDto);
//
//        when(requirementService.findAllByTechnicalResponsibleRegistrationId(eq(SAMPLE_REGISTRATION_ID))).thenReturn(list);
//
//        mockMvc.perform(get(BASE_URL + "/registration/{registrationId}/requirements", SAMPLE_REGISTRATION_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].id").value(SAMPLE_REQUIREMENT_ID));
//
//        verify(requirementService, times(1)).findAllByTechnicalResponsibleRegistrationId(eq(SAMPLE_REGISTRATION_ID));
//    }
//
//@Test
//    void suggestDocumentCorrection_ShouldReturn200AndUpdatedDocument_WhenDtoIsValid() throws Exception {
//        DocumentCorrectionSuggestionDTO requestDto = new DocumentCorrectionSuggestionDTO(SAMPLE_DOCUMENT_ID, SAMPLE_REGISTRATION_ID, "Corrigir a planta baixa.");
//
//        DocumentDetailResponseDTO responseDto = new DocumentDetailResponseDTO(SAMPLE_DOCUMENT_ID, "Planta Baixa", ".pdf", "http://url/doc.pdf", DocumentStatus.CORRECTION_SUGGESTED, "Corrigir a planta baixa.", null);
//
//        when(documentService.suggestCorrection(any(DocumentCorrectionSuggestionDTO.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post(BASE_URL + "/documents/suggest-correction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SAMPLE_DOCUMENT_ID.toString()))
//                .andExpect(jsonPath("$.status").value("CORRECTION_SUGGESTED"))
//                .andExpect(jsonPath("$.reviewNote").value("Corrigir a planta baixa."));
//
//        verify(documentService, times(1)).suggestCorrection(any(DocumentCorrectionSuggestionDTO.class));
//    }
//
//    @Test
//    void suggestDocumentCorrection_ShouldReturn422_WhenDtoIsInvalid() throws Exception {
//        DocumentCorrectionSuggestionDTO invalidDto = new DocumentCorrectionSuggestionDTO(SAMPLE_DOCUMENT_ID, SAMPLE_REGISTRATION_ID, null);
//
//        mockMvc.perform(post(BASE_URL + "/documents/suggest-correction")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isUnprocessableEntity());
//        verify(documentService, never()).suggestCorrection(any());
//    }
//}
