package com.eorion.bo.enhancement.application.adapter.inbound;

import com.eorion.bo.enhancement.application.domain.dto.ApplicationStructureMapper;
import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationSaveDTO;
import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationUpdateDTO;
import com.eorion.bo.enhancement.application.domain.dto.inbound.QueryApplicationDTO;
import com.eorion.bo.enhancement.application.domain.dto.outbound.CountDTO;
import com.eorion.bo.enhancement.application.domain.dto.outbound.IdDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.domain.exception.DataNotExistException;
import com.eorion.bo.enhancement.application.domain.exception.InsertFailedException;
import com.eorion.bo.enhancement.application.domain.exception.UpdateFailedException;
import com.eorion.bo.enhancement.application.service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enhancement/application")
public class ApplicationController {

    private final ApplicationService service;
    private final ApplicationStructureMapper structureMapper;

    public ApplicationController(ApplicationService service, ApplicationStructureMapper structureMapper) {
        this.service = service;
        this.structureMapper = structureMapper;
    }

    @PostMapping()
    public IdDTO<?> saveApplication(@Validated @RequestBody ApplicationSaveDTO saveDto) throws InsertFailedException {
        return service.save(structureMapper.saveDtoToEntity(saveDto));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateApplication(@PathVariable("id") Integer id, @RequestBody ApplicationUpdateDTO updateDto) throws UpdateFailedException {
        service.updateApplication(id, structureMapper.updateDtoToEntity(updateDto));
    }

    @GetMapping("/{id}")
    public Application getDetail(@PathVariable("id") Integer id) throws DataNotExistException {
        return service.getDetailById(id);
    }

    @PostMapping("/list")
    public List<Application> getApplicationList(
            @RequestBody(required = false) QueryApplicationDTO query,
            @RequestParam(value = "firstResult", defaultValue = "0") Integer firstResult,
            @RequestParam(value = "maxResults", defaultValue = "2147483647") Integer maxResults
    ) {
        return service.getApplicationList(query, firstResult, maxResults);
    }

    @PostMapping("/count")
    public CountDTO getApplicationCount(
            @RequestBody(required = false) QueryApplicationDTO query
    ) {
        return service.getApplicationCount(query);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Integer id) throws UpdateFailedException {
        service.deleteById(id);
    }

    /**
     * 修改应用状态，0 -> 1
     *
     * @param id 应用ID
     */
    @PostMapping("/{id}/publish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void applicationPublish(@PathVariable("id") Long id) throws DataNotExistException, UpdateFailedException {
        service.applicationPublish(id);
    }

    /**
     * 修改应用状态，1 -> 0
     *
     * @param id 应用ID
     */
    @PostMapping("/{id}/off")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void applicationTakeDown(@PathVariable("id") Long id) throws DataNotExistException, UpdateFailedException {
        service.applicationTakeDown(id);
    }
}
