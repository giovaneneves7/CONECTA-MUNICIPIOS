package br.edu.ifba.conectairece.api.controller.v1;

import br.edu.ifba.conectairece.api.features.admin.domain.dto.request.AdminProfileRequestDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.dto.response.AdminResponseDTO;
import br.edu.ifba.conectairece.api.features.admin.domain.model.AdminProfile;
import br.edu.ifba.conectairece.api.features.admin.domain.service.IAdminService;
import br.edu.ifba.conectairece.api.infraestructure.util.ObjectMapperUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IAdminService adminService;

    @Mock
    private ObjectMapperUtil objectMapperUtil;

    @InjectMocks
    private AdminController adminController;

    private final UUID USER_ID = UUID.fromString("965572d4-c214-4f36-8bcf-b4222340f1a8");
    private final UUID PROFILE_ID = UUID.fromString("a5c45cd9-fcfa-43fd-95b1-ac67dcdf9f1f");
    private final String BASE_URL = "/api/v1/admin-profiles/admin-profile";

    @BeforeEach
    void setUp() {
        PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setCustomArgumentResolvers(pageableResolver)
                .build();
    }

    // 1. POST /admin-profile (createAdmin)
    @Test
    void createAdmin_ShouldReturn200AndAdminProfile_WhenSuccessful() throws Exception {
        AdminProfileRequestDTO requestDto = new AdminProfileRequestDTO("Admin", "url_img", USER_ID);
        AdminResponseDTO responseDto = new AdminResponseDTO(PROFILE_ID, "Admin", "url_img");

        when(objectMapperUtil.map(any(AdminProfileRequestDTO.class), eq(AdminProfile.class))).thenReturn(new AdminProfile());
        when(adminService.createAdmin(eq(USER_ID), any(AdminProfile.class))).thenReturn(responseDto);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Admin"));

        verify(adminService, times(1)).createAdmin(eq(USER_ID), any(AdminProfile.class));
    }

    @Test
    void createAdmin_ShouldReturn422_WhenUserIdIsMissing() throws Exception {
        AdminProfileRequestDTO invalidDto = new AdminProfileRequestDTO("Admin", "url_img", null);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isUnprocessableEntity()); // Status 422

        verify(adminService, never()).createAdmin(any(), any());
    }

    // 2. PUT /admin-profile (update)

    @Test
    void update_ShouldReturn204_WhenSuccessful() throws Exception {
        AdminProfileRequestDTO requestDto = new AdminProfileRequestDTO("Super Admin", "new_url", USER_ID);

        when(objectMapperUtil.map(any(AdminProfileRequestDTO.class), eq(AdminProfile.class))).thenReturn(new AdminProfile());
        doNothing().when(adminService).update(eq(USER_ID), any(AdminProfile.class));

        mockMvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNoContent()); // Status 204

        verify(adminService, times(1)).update(eq(USER_ID), any(AdminProfile.class));
    }

    @Test
    void update_ShouldReturn422_WhenTypeIsBlank() throws Exception {
        AdminProfileRequestDTO invalidDto = new AdminProfileRequestDTO("", "new_url", USER_ID);

        mockMvc.perform(put(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isUnprocessableEntity());

        verify(adminService, never()).update(any(), any());
    }

    // 3. DELETE /admin-profile/{id} (delete)

    @Test
    void delete_ShouldReturn204_WhenSuccessful() throws Exception {
        doNothing().when(adminService).delete(eq(PROFILE_ID));

        mockMvc.perform(delete(BASE_URL + "/{id}", PROFILE_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // Status 204

        verify(adminService, times(1)).delete(eq(PROFILE_ID));
    }

    // 4. GET /admin-profiles (findAll)

    @Test
    void findAll_ShouldReturn200AndListOfProfiles() throws Exception {
        AdminResponseDTO admin1 = new AdminResponseDTO(PROFILE_ID, "Admin", "url1");
        List<AdminResponseDTO> expectedList = List.of(admin1);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());

        when(adminService.findAll(any(Pageable.class))).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/admin-profiles")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "id,asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Admin"));

        verify(adminService, times(1)).findAll(any(Pageable.class));
    }
}