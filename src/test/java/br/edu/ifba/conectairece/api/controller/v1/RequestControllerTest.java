//package br.edu.ifba.conectairece.api.controller.v1;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.request.RejectionRequestDTO;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.dto.response.ConstructionLicenseRequirementResponseDTO;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.enums.AssociationStatus;
//import br.edu.ifba.conectairece.api.features.constructionLicenseRequirement.domain.service.ConstructionLicenseRequirementService;
//import br.edu.ifba.conectairece.api.features.monitoring.domain.dto.response.MonitoringResponseDTO;
//import br.edu.ifba.conectairece.api.features.monitoring.domain.enums.MonitoringStatus;
//import br.edu.ifba.conectairece.api.features.request.domain.dto.reposnse.RequestResponseDto;
//import br.edu.ifba.conectairece.api.features.request.domain.dto.request.RequestPostRequestDto;
//import br.edu.ifba.conectairece.api.features.request.domain.service.RequestIService;
//import br.edu.ifba.conectairece.api.features.update.domain.dto.response.UpdateResponseDTO;
//import br.edu.ifba.conectairece.api.infraestructure.exception.BusinessException;
//import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//
//@ExtendWith(MockitoExtension.class)
//class RequestControllerTest {
//
//    private MockMvc mockMvc;
//    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//
//    @Mock
//    private RequestIService requestService;
//
//    @Mock
//    private ConstructionLicenseRequirementService constructionLicenseRequirementService;
//
//    @Mock
//    private ObjectMapperUtil objectMapperUtil;
//
//    @InjectMocks
//    private RequestController requestController;
//
//    private final String BASE_URL = "/api/v1/requests";
//    private final UUID SAMPLE_REQUEST_ID = UUID.fromString("a1b2c3d4-e5f6-7890-1234-567890abcdef");
//    private final Long SAMPLE_CLR_ID = 1L;
//    private final UUID SAMPLE_PROFILE_ID = UUID.fromString("f0e9d8c7-b6a5-4321-fedc-ba9876543210");
//    private final Long SAMPLE_MUNICIPAL_SERVICE_ID = 10L;
//    private final String SAMPLE_REGISTRATION_ID = "12345-D";
//
//    @BeforeEach
//    void setUp() {
//        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
//        mockMvc = MockMvcBuilders.standaloneSetup(requestController)
//                .setCustomArgumentResolvers(pageableResolver)
//                .build();
//    }
//
//    @Test
//    void create_ShouldReturn200AndRequestDto_WhenSuccessful() throws Exception {
//        RequestPostRequestDto requestDto = new RequestPostRequestDto("PROTO-001", LocalDateTime.now().plusDays(10), "Type A", "Note A", SAMPLE_PROFILE_ID, SAMPLE_MUNICIPAL_SERVICE_ID);
//        RequestResponseDto responseDto = new RequestResponseDto(SAMPLE_REQUEST_ID, "PROTO-001", LocalDateTime.now(), LocalDateTime.now().plusDays(10), LocalDateTime.now(), "Type A", "Note A", SAMPLE_MUNICIPAL_SERVICE_ID);
//
//        when(requestService.save(any(RequestPostRequestDto.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post(BASE_URL + "/request")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SAMPLE_REQUEST_ID.toString()))
//                .andExpect(jsonPath("$.protocolNumber").value("PROTO-001"));
//
//        verify(requestService, times(1)).save(any(RequestPostRequestDto.class));
//    }
//
//    @Test
//    void create_ShouldReturn400_WhenDtoIsInvalid() throws Exception {
//        RequestPostRequestDto invalidDto = new RequestPostRequestDto(null, null, "", null, null, null);
//
//        mockMvc.perform(post(BASE_URL + "/request")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(requestService, never()).save(any());
//    }
//
//    @Test
//    void getAll_ShouldReturn200AndListOfRequests() throws Exception {
//        RequestResponseDto dto1 = new RequestResponseDto(UUID.randomUUID(), "P1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), "T1", "N1", 1L);
//        List<RequestResponseDto> expectedList = List.of(dto1);
//
//        when(requestService.findAll()).thenReturn(expectedList);
//
//        mockMvc.perform(get(BASE_URL)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].protocolNumber").value("P1"));
//
//        verify(requestService, times(1)).findAll();
//    }
//
//    @Test
//    void getById_ShouldReturn200AndRequestDto_WhenFound() throws Exception {
//        RequestResponseDto responseDto = new RequestResponseDto(SAMPLE_REQUEST_ID, "P1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), "T1", "N1", 1L);
//        when(requestService.findById(eq(SAMPLE_REQUEST_ID))).thenReturn(responseDto);
//
//        mockMvc.perform(get(BASE_URL + "/request/{id}", SAMPLE_REQUEST_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SAMPLE_REQUEST_ID.toString()));
//
//        verify(requestService, times(1)).findById(eq(SAMPLE_REQUEST_ID));
//    }
//
//    @Test
//    void getById_ShouldReturn404_WhenNotFound() throws Exception {
//        when(requestService.findById(eq(SAMPLE_REQUEST_ID))).thenThrow(new BusinessException("Request not found"));
//
//        mockMvc.perform(get(BASE_URL + "/request/{id}", SAMPLE_REQUEST_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//        verify(requestService, times(1)).findById(eq(SAMPLE_REQUEST_ID));
//    }
//
//    @Test
//    void update_ShouldReturn200AndUpdatedDto_WhenSuccessful() throws Exception {
//        RequestPostRequestDto requestDto = new RequestPostRequestDto("PROTO-UPD", LocalDateTime.now().plusDays(20), "Type B", "Note B", SAMPLE_PROFILE_ID, SAMPLE_MUNICIPAL_SERVICE_ID);
//        RequestResponseDto responseDto = new RequestResponseDto(SAMPLE_REQUEST_ID, "PROTO-UPD", LocalDateTime.now(), LocalDateTime.now().plusDays(20), LocalDateTime.now(), "Type B", "Note B", SAMPLE_MUNICIPAL_SERVICE_ID);
//
//        when(requestService.update(eq(SAMPLE_REQUEST_ID), any(RequestPostRequestDto.class))).thenReturn(responseDto);
//
//        mockMvc.perform(put(BASE_URL + "/request/{id}", SAMPLE_REQUEST_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                        .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.protocolNumber").value("PROTO-UPD"));
//
//        verify(requestService, times(1)).update(eq(SAMPLE_REQUEST_ID), any(RequestPostRequestDto.class));
//    }
//
//    @Test
//    void update_ShouldReturn400_WhenDtoIsInvalid() throws Exception {
//        RequestPostRequestDto invalidDto = new RequestPostRequestDto(null, null, "", null, null, null);
//
//        mockMvc.perform(put(BASE_URL + "/request/{id}", SAMPLE_REQUEST_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(requestService, never()).update(any(), any());
//    }
//
//    @Test
//    void delete_ShouldReturn204_WhenSuccessful() throws Exception {
//        doNothing().when(requestService).delete(eq(SAMPLE_REQUEST_ID));
//
//        mockMvc.perform(delete(BASE_URL + "/request/{id}", SAMPLE_REQUEST_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//
//        verify(requestService, times(1)).delete(eq(SAMPLE_REQUEST_ID));
//    }
//
//    @Test
//    void getMonitorings_ShouldReturn200AndPaginatedMonitorings() throws Exception {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").descending());
//        MonitoringResponseDTO monitoring1 = new MonitoringResponseDTO(UUID.randomUUID(),           // id
//    "CODE-001",                  // code
//    UUID.randomUUID(),           // stepId
//    MonitoringStatus.PENDING); // monitoringStatus (Use an actual value from your enum);
//        Page<MonitoringResponseDTO> expectedPage = new PageImpl<>(List.of(monitoring1), pageable, 1);
//
//        when(requestService.getMonitoringListByRequestId(eq(SAMPLE_REQUEST_ID), any(Pageable.class))).thenReturn(expectedPage);
//
//        mockMvc.perform(get(BASE_URL + "/request/{id}/monitorings", SAMPLE_REQUEST_ID)
//                        .param("page", "0").param("size", "5").param("sort", "name,desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.totalElements").value(1));
//
//        verify(requestService, times(1)).getMonitoringListByRequestId(eq(SAMPLE_REQUEST_ID), any(Pageable.class));
//    }
//
//    @Test
//    void getUpdates_ShouldReturn200AndPaginatedUpdates() throws Exception {
//        Pageable pageable = PageRequest.of(0, 20, Sort.by("id").descending());
//        UpdateResponseDTO update1 = new UpdateResponseDTO(LocalDateTime.now(),
//    "Sample update note" );
//        Page<UpdateResponseDTO> expectedPage = new PageImpl<>(List.of(update1), pageable, 1);
//
//        when(requestService.getUpdateListByRequestId(eq(SAMPLE_REQUEST_ID), any(Pageable.class))).thenReturn(expectedPage);
//
//        mockMvc.perform(get(BASE_URL + "/request/{id}/updates", SAMPLE_REQUEST_ID)
//                        .param("page", "0").param("size", "20").param("sort", "id,desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.pageable.pageSize").value(20));
//
//        verify(requestService, times(1)).getUpdateListByRequestId(eq(SAMPLE_REQUEST_ID), any(Pageable.class));
//    }
//
//    @Test
//    void acceptRequest_ShouldReturn200AndUpdatedDto_WhenSuccessful() throws Exception {
//        ConstructionLicenseRequirementResponseDTO responseDto = new ConstructionLicenseRequirementResponseDTO(SAMPLE_CLR_ID, LocalDateTime.now(), "Owner", "Phone", "CEP", "CPF", "Addr", 100f, "Resp Name", AssociationStatus.APPROVED);
//
//        when(constructionLicenseRequirementService.acceptRequest(eq(SAMPLE_CLR_ID))).thenReturn(responseDto);
//
//        mockMvc.perform(post(BASE_URL + "/request/{id}/review/accept", SAMPLE_CLR_ID)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SAMPLE_CLR_ID))
//                .andExpect(jsonPath("$.technicalResponsibleStatus").value("APPROVED")); // Exemplo de check
//
//        verify(constructionLicenseRequirementService, times(1)).acceptRequest(eq(SAMPLE_CLR_ID));
//    }
//
//    @Test
//    void rejectRequest_ShouldReturn200AndUpdatedDto_WhenSuccessful() throws Exception {
//        RejectionRequestDTO rejectionDto = new RejectionRequestDTO(SAMPLE_REGISTRATION_ID, SAMPLE_CLR_ID, "Documentação incompleta.");
//        ConstructionLicenseRequirementResponseDTO responseDto = new ConstructionLicenseRequirementResponseDTO(SAMPLE_CLR_ID, LocalDateTime.now(), "Owner", "Phone", "CEP", "CPF", "Addr", 100f, "Resp Name", AssociationStatus.REJECTED);
//
//        when(constructionLicenseRequirementService.rejectRequest(eq(SAMPLE_CLR_ID), any(RejectionRequestDTO.class))).thenReturn(responseDto);
//
//        mockMvc.perform(post(BASE_URL + "/request/{id}/review/reject", SAMPLE_CLR_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(rejectionDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(SAMPLE_CLR_ID))
//                .andExpect(jsonPath("$.technicalResponsibleStatus").value("REJECTED")); // Exemplo de check
//
//        verify(constructionLicenseRequirementService, times(1)).rejectRequest(eq(SAMPLE_CLR_ID), any(RejectionRequestDTO.class));
//    }
//
//    @Test
//    void rejectRequest_ShouldReturn400_WhenDtoIsInvalid() throws Exception {
//        RejectionRequestDTO invalidDto = new RejectionRequestDTO(null, SAMPLE_CLR_ID, "");
//
//        mockMvc.perform(post(BASE_URL + "/request/{id}/review/reject", SAMPLE_CLR_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isBadRequest());
//
//        verify(constructionLicenseRequirementService, never()).rejectRequest(anyLong(), any());
//    }
//
//
//    @Test
//    void getRequestsByType_ShouldReturn200AndPaginatedRequests_WhenTypeProvided() throws Exception {
//        String requestType = "Alvará de Construção";
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
//        RequestResponseDto request1 = new RequestResponseDto(UUID.randomUUID(), "P1", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), requestType, "N1", 1L);
//        Page<RequestResponseDto> expectedPage = new PageImpl<>(List.of(request1), pageable, 1);
//
//        when(requestService.findByType(eq(requestType), any(Pageable.class))).thenReturn(expectedPage);
//
//        mockMvc.perform(get(BASE_URL)
//                        .param("type", requestType)
//                        .param("page", "0").param("size", "10").param("sort", "createdAt,desc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.content[0].type").value(requestType))
//                .andExpect(jsonPath("$.totalElements").value(1));
//
//        verify(requestService, times(1)).findByType(eq(requestType), any(Pageable.class));
//    }
//}