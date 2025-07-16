package com.eorion.bo.enhancement.application.adapter.inbound;

import com.eorion.bo.enhancement.application.adapter.outbound.ApplicationRepository;
import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationUpdateDTO;
import com.eorion.bo.enhancement.application.domain.dto.inbound.QueryApplicationDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.domain.enums.ApplicationStatus;
import com.eorion.bo.enhancement.application.utils.BatchSQLExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.IdentityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApplicationControllerTest extends BaseControllerTest {

    @Autowired
    private ApplicationRepository repository;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected BatchSQLExecutor executor;

    private static final HttpHeaders headers = new HttpHeaders();

    static {
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString("demo:demo".getBytes(StandardCharsets.UTF_8)));
    }

    @BeforeEach
    public void clearUp() throws SQLException {
        executor.batchExecuteSqlFromFile(getApplicationDeleteReader());
        identityService.setAuthenticatedUserId("demo");
    }

    @Test
    public void createApplicationReturn200() throws Exception {

        var file = ResourceUtils.getFile("classpath:data/normal-save.json");
        var requestBody = new String(Files.readAllBytes(file.toPath()));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                                .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
        var application = repository.getById(1);
        Assertions.assertNotNull(application);
        assertEquals("demo,test", application.getAccessUsers());
    }


    @Test
    public void updateApplicationReturn204() throws Exception {

        Application application = new Application();
        application.setName("name");
        application.setOwner("demo");
        repository.save(application);

        var file = ResourceUtils.getFile("classpath:data/normal-update.json");
        var requestBody = new String(Files.readAllBytes(file.toPath()));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/enhancement/application/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                                .content(requestBody)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
        var dbApplication = repository.getById(1);

        assertEquals("editGroup", dbApplication.getEditGroup());
        assertEquals("tags", dbApplication.getTags());
        assertEquals(dbApplication.getConfigJson(), Map.of("var1", "var1"));
        assertEquals("demo,test", dbApplication.getAccessUsers());

    }

    @Test
    @Disabled("Business logic has been changed")
    public void updateApplicationWithErrorOwnerReturn500() throws Exception {

        Application application = new Application();
        application.setName("name");
        application.setOwner("owner");
        repository.save(application);

        ApplicationUpdateDTO updateDto = new ApplicationUpdateDTO();
        updateDto.setName("name2");
        updateDto.setTags("tags");
        updateDto.setEditGroup("editGroup");
        updateDto.setUserGroup("userGroup");
        updateDto.setIcon("icon");

        var json = mapper.writeValueAsString(updateDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/enhancement/application/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                                .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andDo(print());

    }

    @Test
    public void getDetailApplicationReturn200() throws Exception {

        Application application = new Application();
        Map<String, Object> params = new HashMap<>();
        params.put("var1", "var1");
        application.setName("name");
        application.setOwner("owner");
        application.setConfigJson(params);
        application.setType("type");
        application.setCategory("category");
        application.setAppKey("key");
        application.setDescription("description");
        application.setVersionTag("versionTag");
        repository.save(application);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/enhancement/application/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"))
                .andDo(print());
    }

    @Test
    public void getListApplicationReturn200() throws Exception {

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("sql/insert-application.sql")));
        executor.batchExecuteSqlFromFile(inputStreamReader);

        var file = ResourceUtils.getFile("classpath:data/normal-query.json");
        var queryBody = new String(Files.readAllBytes(file.toPath()));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application/list")
                                .content(queryBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andDo(print());

        QueryApplicationDTO query = new QueryApplicationDTO();
        query.setTenant("tenant");
        query.setCoeCode("coe");
        query.setGroups(List.of("user1", "user2", "user3"));
        query.setType("type");
        query.setCategory("category");
        query.setNameLike("name");
        query.setAppKeysIn(List.of("key1", "key2", "key3"));
        query.setStatus("0");
        query.setVersionTag("versionTag");
        query.setAccessUsersIn(List.of("demo", "test", "test1"));

        var request = mapper.writeValueAsString(query);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application/list")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andDo(print());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application/list")
                                .content(queryBody)
                                .param("firstResult", "0")
                                .param("maxResults", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));
    }

    @Test
    public void deleteApplicationReturn204() throws Exception {

        Application application = new Application();
        application.setName("name");
        application.setOwner("demo");
        repository.save(application);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/enhancement/application/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void Return204() throws Exception {

        Application application = new Application();
        application.setName("name");
        application.setOwner("demo");
        repository.save(application);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/enhancement/application/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void applicationPublishReturn204() throws Exception {

        Application application = new Application();
        application.setName("name");
        application.setOwner("demo");
        repository.save(application);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application/{id}/publish", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var dbApplication = repository.getById(1);
        assertEquals(dbApplication.getStatus(), ApplicationStatus.PUBLISH);
    }

    @Test
    public void applicationTakeDownReturn204() throws Exception {

        Application application = new Application();
        application.setName("name");
        application.setOwner("demo");
        application.setStatus(ApplicationStatus.PUBLISH);
        repository.save(application);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application/{id}/off", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var dbApplication = repository.getById(1);
        assertEquals(ApplicationStatus.DRAFT, dbApplication.getStatus());
    }

    @Test
    public void getCountApplicationReturn200() throws Exception {

        InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("sql/insert-application.sql")));
        executor.batchExecuteSqlFromFile(inputStreamReader);

        var file = ResourceUtils.getFile("classpath:data/normal-query.json");
        var queryBody = new String(Files.readAllBytes(file.toPath()));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/enhancement/application/count")
                                .content(queryBody)
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(2))
                .andDo(print());
    }
}
