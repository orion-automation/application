package com.eorion.bo.enhancement.application.adapter.inbound;

import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.service.PdaApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enhancement/pda-application")
@Tag(name = "Process Driven Application Management")
public class PdaApplicationController {

    private final PdaApplicationService service;

    public PdaApplicationController(PdaApplicationService service) {
        this.service = service;
    }

    @Operation(summary = "Query list of process driven application information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = Application.class))
            ))
    })
    @GetMapping
    public List<Application> getPdaApplications(
            @Parameter(
                    description = "Optional tenant condition",
                    schema = @Schema(type = "string")
            )
            @RequestParam(value = "tenant", required = false) String tenant,
            @Parameter(
                    description = "Optional name wildcard condition",
                    schema = @Schema(type = "string")
            )
            @RequestParam(value = "nameLike", required = false) String nameLike,
            @Parameter(
                    description = "Optional category condition",
                    example = "1",
                    schema = @Schema(type = "string")
            )
            @RequestParam(value = "category", required = false) String category) {
        return service.getPdaApplications(tenant, nameLike, category);
    }
}
