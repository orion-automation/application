package com.eorion.bo.enhancement.application.adapter.inbound;

import com.eorion.bo.enhancement.application.adapter.outbound.ApplicationRepository;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.utils.BatchSQLExecutor;
import org.camunda.bpm.engine.IdentityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PdaApplicationControllerTest extends BaseControllerTest {

    @Autowired
    private ApplicationRepository repository;
    @Autowired
    private IdentityService identityService;

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
    public void getPdaListApplicationReturn200() throws Exception {

        for (int i = 0; i < 5; i++) {
            Application application = new Application();
            application.setType("duo");
            application.setName("name");
            application.setOwner("owner");
            application.setCategory("combination");
            application.setConfigJson(Map.of("key", "value"));
            application.setUserGroup(i % 2 == 0 ? "admin,test" : "group");
            application.setAccessUsers(i % 2 == 0 ? "demo" : "camunda");
            repository.save(application);
        }

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/enhancement/pda-application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andDo(print());

    }

    @Test
    public void getPdaListApplicationNameLikeReturn200() throws Exception {

        for (int i = 0; i < 5; i++) {
            Application application = new Application();
            application.setType("duo");
            application.setName("name" + i);
            application.setOwner("owner");
            application.setCategory("combination");
            application.setConfigJson(Map.of("key", "value"));
            application.setUserGroup(i % 2 == 0 ? "admin,test" : "group");
            application.setAccessUsers(i % 2 == 0 ? "demo" : "camunda");
            repository.save(application);
        }

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/enhancement/pda-application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                                .param("nameLike", "name")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andDo(print());

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/enhancement/pda-application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                                .param("nameLike", "yiuwyeiwer")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
                .andDo(print());

    }

    @Test
    public void getPdaListApplicationWithEmptyReturn200() throws Exception {
        for (int i = 0; i < 5; i++) {
            Application application = new Application();
            application.setType("duo");
            application.setName("name");
            application.setOwner("owner");
            application.setCategory("combination");
            application.setConfigJson(Map.of("key", "value"));
            application.setUserGroup("group");
            application.setAccessUsers("camunda");
            repository.save(application);
        }
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/enhancement/pda-application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
                .andDo(print());
    }

    @Test
    public void getPdaListApplicationWithAccessUserReturn200() throws Exception {
        for (int i = 0; i < 5; i++) {
            Application application = new Application();
            application.setType("duo");
            application.setName("name");
            application.setCategory("combination");
            application.setOwner("owner");
            application.setConfigJson(Map.of("key", "value"));
            application.setUserGroup(i % 2 == 0 ? "admin,test" : "group");
            application.setAccessUsers("demo");
            repository.save(application);
        }
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/enhancement/pda-application")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(headers)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(5))
                .andDo(print());
    }
}
