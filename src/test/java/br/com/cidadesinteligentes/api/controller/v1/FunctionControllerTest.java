//package br.com.cidadesinteligentes.api.controller.v1;
//
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.controller.FunctionController;
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.dto.request.FunctionRequestDTO;
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.dto.request.FunctionUpdateRequestDTO;
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.dto.response.FunctionResponseDTO;
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.model.Function;
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.repository.projection.FunctionProjection;
//import br.com.cidadesinteligentes.modules.solicitacaoservicomunicipal.funcaoservidorpublico.service.FunctionService;
//import br.com.cidadesinteligentes.infraestructure.util.ObjectMapperUtil;
//import br.com.cidadesinteligentes.infraestructure.util.dto.PageableDTO;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//import java.util.HashSet;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Unit test class for {@link FunctionController}.
// * <p>
// * This class uses {@link MockMvc}, Mockito, and JUnit 5 to validate the behavior of the
// * {@link FunctionController} REST endpoints, ensuring that:
// * <ul>
// *   <li>Functions can be created successfully.</li>
// *   <li>Functions can be updated and return the proper HTTP status.</li>
// *   <li>Functions can be deleted and return the correct response code.</li>
// *   <li>Paginated function data can be retrieved correctly.</li>
// * </ul>
// *
// * <p>Mocks are used for the service layer ({@link FunctionService}) and utility mapper
// * ({@link ObjectMapperUtil}), isolating the controller logic.</p>
// *
// * @author Jorge Roberto
// */
////TODO : Implementar testes em casos de erro, quando a classe exceptionHandler for criada
//@ExtendWith(MockitoExtension.class)
//class FunctionControllerTest {
//    @Mock
//    private FunctionService functionService;
//
//    @Mock
//    private ObjectMapperUtil objectMapperUtil;
//
//    @InjectMocks
//    private FunctionController functionController;
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(functionController)
//                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                .build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    @DisplayName("Should create a new function successfully")
//    void saveFunction_Success() throws Exception {
//        FunctionRequestDTO requestDTO = new FunctionRequestDTO("Test Function", "Description");
//        Function function = new Function("Test Function", "Description", new HashSet<>());
//        FunctionResponseDTO responseDTO = new FunctionResponseDTO(1L, "Test Function", "Description");
//
//        when(objectMapperUtil.map(any(FunctionRequestDTO.class), any(Class.class))).thenReturn(function);
//
//        when(functionService.save(any(Function.class))).thenReturn(responseDTO);
//
//        mockMvc.perform(post("/api/v1/functions/function")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.id").value(1L))
//                        .andExpect(jsonPath("$.name").value("Test Function"))
//                        .andExpect(jsonPath("$.description").value("Description"));
//    }
//
//    @Test
//    @DisplayName("Should update an existing function successfully")
//    void updateFunction_Success() throws Exception {
//        FunctionUpdateRequestDTO requestDTO =
//                new FunctionUpdateRequestDTO(1L, "Updated Function", "Updated Description");
//
//        Function function =
//                new Function("Updated Function", "Updated Description", new HashSet<>());
//
//        when(objectMapperUtil.map(any(FunctionUpdateRequestDTO.class), any(Class.class)))
//                .thenReturn(function);
//
//        when(functionService.update(any(Function.class)))
//                .thenReturn(new FunctionResponseDTO(1L, "Updated Function", "Updated Description"));
//
//        mockMvc.perform(put("/api/v1/functions/function")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                        .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("Should delete a function successfully")
//    void deleteFunction_Success() throws Exception {
//        Long functionId = 1L;
//        doNothing().when(functionService).delete(functionId);
//
//        mockMvc.perform(delete("/api/v1/functions/function/{id}", functionId))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @DisplayName("Should return a paginated list of functions")
//    void findAllFunctions_Success() throws Exception {
//        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());
//        Page<FunctionProjection> projectionPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
//        PageableDTO pageableDTO = new PageableDTO();
//
//        when(functionService.findAllProjectedBy(any(Pageable.class))).thenReturn(projectionPage);
//        when(objectMapperUtil.map(any(Page.class), any(Class.class))).thenReturn(pageableDTO);
//
//        mockMvc.perform(get("/api/v1/functions")
//                        .param("page", "0")
//                        .param("size", "5")
//                        .param("sort", "name,asc"))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.content").isArray());
//    }
//}