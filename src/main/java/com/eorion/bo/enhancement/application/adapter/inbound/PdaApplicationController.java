package com.eorion.bo.enhancement.application.adapter.inbound;

import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.service.PdaApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enhancement/pda-application")
public class PdaApplicationController {

    private final PdaApplicationService service;

    public PdaApplicationController(PdaApplicationService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Application> getPdaApplications(@RequestParam(value = "tenant", required = false) String tenant,
                                                @RequestParam(value = "nameLike", required = false) String nameLike) {
        return service.getPdaApplications(tenant, nameLike);
    }
}
