package com.eorion.bo.enhancement.application.service;

import com.eorion.bo.enhancement.application.adapter.outbound.ApplicationRepository;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdaApplicationService {

    private final ApplicationRepository repository;
    private final IdentityService identityService;

    public PdaApplicationService(ApplicationRepository repository, IdentityService identityService) {
        this.repository = repository;
        this.identityService = identityService;
    }


    public List<Application> getPdaApplications(String tenant, String nameLike) {
        String loginUserId = identityService.getCurrentAuthentication().getUserId();
        List<String> groups = identityService.getCurrentAuthentication().getGroupIds();
        return repository.selectPdaApplications(loginUserId, groups, tenant, nameLike);
    }
}
