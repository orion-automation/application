package com.eorion.bo.enhancement.application.adapter.inbound;

import com.eorion.bo.enhancement.application.domain.dto.ApplicationStructureMapper;
import com.eorion.bo.enhancement.application.domain.dto.common.ErrorMessage;
import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationSaveDTO;
import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationUpdateDTO;
import com.eorion.bo.enhancement.application.domain.dto.inbound.QueryApplicationDTO;
import com.eorion.bo.enhancement.application.domain.dto.openapi.IdDTOInteger;
import com.eorion.bo.enhancement.application.domain.dto.outbound.CountDTO;
import com.eorion.bo.enhancement.application.domain.dto.outbound.IdDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.domain.exception.DataNotExistException;
import com.eorion.bo.enhancement.application.domain.exception.InsertFailedException;
import com.eorion.bo.enhancement.application.domain.exception.UpdateFailedException;
import com.eorion.bo.enhancement.application.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enhancement/application")
@Tag(name = "Application Management")
public class ApplicationController {

    private final ApplicationService service;
    private final ApplicationStructureMapper structureMapper;

    public ApplicationController(ApplicationService service, ApplicationStructureMapper structureMapper) {
        this.service = service;
        this.structureMapper = structureMapper;
    }

    @Operation(summary = "Create one application")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = IdDTOInteger.class))),
            @ApiResponse(responseCode = "400", description = "Request body format validation failed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error when creating",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping
    public IdDTO<?> saveApplication(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Application to create", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApplicationUpdateDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "name",
                                      "coeCode": "coeCode",
                                      "tags": "tags",
                                      "icon": "icon",
                                      "editGroup": "editGroup",
                                      "userGroup": "userGroup",
                                      "owner": "owner",
                                      "tenant": "tenant",
                                      "type": "type",
                                      "category": "category",
                                      "configJson": {
                                        "var1": "var1"
                                      },
                                      "appKey": "key",
                                      "description": "description",
                                      "versionTag": "versionTag",
                                      "target": null,
                                      "targetApplicationId": null,
                                      "accessUsers": "demo,test"
                                    }""")))
            @Validated @RequestBody ApplicationSaveDTO saveDto) throws InsertFailedException {
        return service.save(structureMapper.saveDtoToEntity(saveDto));
    }

    @Operation(summary = "Update one application by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Request body format validation failed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Error when updating",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateApplication(
            @Parameter(
                    description = "application id",
                    example = "1", required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable("id") Integer id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Application information to update", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApplicationUpdateDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "name2",
                                      "tags": "tags",
                                      "icon": "icon",
                                      "editGroup": "editGroup",
                                      "userGroup": "userGroup",
                                      "type": "type",
                                      "category": "category",
                                      "configJson": {
                                        "var1": "var1"
                                      },
                                      "appKey": "key",
                                      "description": "description",
                                      "versionTag": "versionTag",
                                      "accessUsers": "demo,test"
                                    }""")))
            @Valid @RequestBody ApplicationUpdateDTO updateDto) throws UpdateFailedException {
        service.updateApplication(id, structureMapper.updateDtoToEntity(updateDto));
    }

    @Operation(summary = "Get one application by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Application.class))),
            @ApiResponse(responseCode = "404", description = "Data with given id not exist", content = @Content)
    })
    @GetMapping("/{id}")
    public Application getDetail(
            @Parameter(
                    description = "application id",
                    example = "1", required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable("id") Integer id) throws DataNotExistException {
        return service.getDetailById(id);
    }

    @Operation(summary = "Query list of application information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of applications",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Application.class))
                    )),
            @ApiResponse(responseCode = "400", description = "Request parameters format validation failed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping("/list")
    public List<Application> getApplicationList(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Query conditions of application",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = QueryApplicationDTO.class),
                            examples = @ExampleObject(value = """
                                    {"userId": "userId", "tenant": "tenant"}""")))
            @RequestBody(required = false) QueryApplicationDTO query,
            @Parameter(
                    description = "Zero-based page index (0..N)",
                    example = "0",
                    schema = @Schema(type = "integer", format = "int32", defaultValue = "0")
            )
            @RequestParam(value = "firstResult", defaultValue = "0") Integer firstResult,
            @Parameter(
                    description = "The size of the page to be returned",
                    example = "10",
                    schema = @Schema(type = "integer", format = "int32", defaultValue = "2147483647")
            )
            @RequestParam(value = "maxResults", defaultValue = "2147483647") Integer maxResults
    ) {
        return service.getApplicationList(query, firstResult, maxResults);
    }

    @Operation(summary = "Query count of application information")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Count of applications",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CountDTO.class)))
    )
    @PostMapping("/count")
    public CountDTO getApplicationCount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Query conditions of application",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = QueryApplicationDTO.class),
                            examples = @ExampleObject(value = """
                                    {"userId": "userId", "tenant": "tenant"}""")))
            @RequestBody(required = false) QueryApplicationDTO query
    ) {
        return service.getApplicationCount(query);
    }

    @Operation(summary = "Delete one application by id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Error when deleting",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(
                    description = "application id",
                    example = "1", required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable("id") Integer id) throws UpdateFailedException {
        service.deleteById(id);
    }

    /**
     * 修改应用状态，0 -> 1
     *
     * @param id 应用ID
     */
    @Operation(summary = "Publish one application")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Data with given id not exist", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error when publishing",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping("/{id}/publish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void applicationPublish(
            @Parameter(
                    description = "application id",
                    example = "1", required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable("id") Long id) throws DataNotExistException, UpdateFailedException {
        service.applicationPublish(id);
    }

    /**
     * 修改应用状态，1 -> 0
     *
     * @param id 应用ID
     */
    @Operation(summary = "Unpublish one application")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Data with given id not exist", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error when unpublishing",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping("/{id}/off")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void applicationTakeDown(
            @Parameter(
                    description = "application id",
                    example = "1", required = true,
                    schema = @Schema(type = "integer", format = "int32")
            )
            @PathVariable("id") Long id) throws DataNotExistException, UpdateFailedException {
        service.applicationTakeDown(id);
    }
}
