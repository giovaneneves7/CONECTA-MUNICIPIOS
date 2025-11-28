//package br.edu.ifba.conectairece.api.controller.v1;
//
//import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.request.GeneralEvaluationItemRequestDTO;
//import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.request.GeneralEvaluationItemUpdateRequestDTO;
//import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.dto.response.GeneralEvaluationItemResponseDTO;
//import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.enums.GeneralEvaluationItemStatus;
//import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.model.GeneralEvaluationItem;
//import br.edu.ifba.conectairece.api.features.generalEvaluationItem.domain.service.IGeneralEvaluationItemService;
//import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//class GeneralEvaluationItemControllerTest {
//
//    private MockMvc mockMvc;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Mock
//    private IGeneralEvaluationItemService generalEvaluationItemService;
//
//    @Mock
//    private ObjectMapperUtil objectMapperUtil;
//
//    @InjectMocks
//    private GeneralEvaluationItemController generalEvaluationItemController;
//
//    // Test's IDs
//    private final UUID REQUEST_ID = UUID.fromString("56adcb43-b7fd-4cb4-8139-83749f5aeeb5");
//    private final Long ITEM_ID = 1L;
//    private final String BASE_URL = "/api/v1/general-evaluation-items/general-evaluation-item";
//
//    @BeforeEach
//    void setUp() {
//        PageableHandlerMethodArgumentResolver pageable = new PageableHandlerMethodArgumentResolver();
//        mockMvc = MockMvcBuilders.standaloneSetup(generalEvaluationItemController)
//                .setCustomArgumentResolvers(pageable)
//                .build();
//    }
//
//    // 1. Tests to save
//    @Test
//    void save_ShouldReturn200AndSavedItem_WhenSuccessful() throws Exception {
//        GeneralEvaluationItemRequestDTO requestDto = new GeneralEvaluationItemRequestDTO(REQUEST_ID, "Note test", GeneralEvaluationItemStatus.COMPLETED);
//        GeneralEvaluationItemResponseDTO responseDto = new GeneralEvaluationItemResponseDTO("Note test", GeneralEvaluationItemStatus.COMPLETED);
//
//        when(objectMapperUtil.map(any(GeneralEvaluationItemRequestDTO.class), eq(GeneralEvaluationItem.class))).thenReturn(new GeneralEvaluationItem());
//        when(generalEvaluationItemService.save(any(GeneralEvaluationItem.class), eq(REQUEST_ID))).thenReturn(responseDto);
//
//        mockMvc.perform(post(BASE_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.note").value("Note test"));
//
//        verify(generalEvaluationItemService, times(1)).save(any(GeneralEvaluationItem.class), eq(REQUEST_ID));
//    }
//
//    @Test
//    void save_ShouldReturn422_WhenValidationFails() throws Exception {
//        GeneralEvaluationItemRequestDTO invalidDto = new GeneralEvaluationItemRequestDTO(REQUEST_ID, "", GeneralEvaluationItemStatus.COMPLETED);
//
//        mockMvc.perform(post(BASE_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isUnprocessableEntity());
//
//        verify(generalEvaluationItemService, never()).save(any(), any());
//    }
//
//    // --- 2. Tests for findAllByRequestId ---
//    @Test
//    void findAllByRequestId_ShouldReturn200AndItems() throws Exception {
//        GeneralEvaluationItemResponseDTO item1 = new GeneralEvaluationItemResponseDTO("Note A", GeneralEvaluationItemStatus.COMPLETED);
//        List<GeneralEvaluationItemResponseDTO> expectedList = List.of(item1);
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
//
//        when(generalEvaluationItemService.findAllByRequestId(eq(REQUEST_ID), any(Pageable.class)))
//                .thenReturn(expectedList);
//
//        mockMvc.perform(get(BASE_URL + "/request/{requestId}", REQUEST_ID)
//                        .param("page", "0")
//                        .param("size", "10")
//                        .param("sort", "id,asc")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].note").value("Note A"))
//                .andExpect(jsonPath("$").isArray());
//
//        verify(generalEvaluationItemService, times(1)).findAllByRequestId(eq(REQUEST_ID), any(Pageable.class));
//    }
//
//    // 3. Tests for update
//    @Test
//    void update_ShouldReturn204_WhenSuccessful() throws Exception {
//        GeneralEvaluationItemUpdateRequestDTO updateDto = new GeneralEvaluationItemUpdateRequestDTO("New Note", GeneralEvaluationItemStatus.INCOMPLETE);
//
//        when(objectMapperUtil.map(any(GeneralEvaluationItemUpdateRequestDTO.class), eq(GeneralEvaluationItem.class)))
//                .thenReturn(new GeneralEvaluationItem());
//
//        doNothing().when(generalEvaluationItemService).update(any(GeneralEvaluationItem.class), eq(ITEM_ID));
//
//        mockMvc.perform(put(BASE_URL + "/{generalEvaluationItemId}", ITEM_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateDto)))
//                .andExpect(status().isNoContent());
//
//        verify(generalEvaluationItemService, times(1)).update(any(GeneralEvaluationItem.class), eq(ITEM_ID));
//    }
//
//    @Test
//    void update_ShouldReturn422_WhenValidationFails() throws Exception {
//        GeneralEvaluationItemUpdateRequestDTO invalidDto = new GeneralEvaluationItemUpdateRequestDTO("Valid note", null);
//
//        mockMvc.perform(put(BASE_URL + "/{generalEvaluationItemId}", ITEM_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidDto)))
//                .andExpect(status().isUnprocessableEntity());
//
//        verify(generalEvaluationItemService, never()).update(any(), any());
//    }
//}