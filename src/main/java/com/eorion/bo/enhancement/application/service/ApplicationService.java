package com.eorion.bo.enhancement.application.service;

import com.eorion.bo.enhancement.application.adapter.outbound.ApplicationRepository;
import com.eorion.bo.enhancement.application.domain.dto.inbound.QueryApplicationDTO;
import com.eorion.bo.enhancement.application.domain.dto.outbound.CountDTO;
import com.eorion.bo.enhancement.application.domain.dto.outbound.IdDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.domain.enums.ApplicationStatus;
import com.eorion.bo.enhancement.application.domain.exception.DataNotExistException;
import com.eorion.bo.enhancement.application.domain.exception.InsertFailedException;
import com.eorion.bo.enhancement.application.domain.exception.UpdateFailedException;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ApplicationService {

    private final ApplicationRepository repository;
    private final IdentityService identityService;

    public ApplicationService(ApplicationRepository repository, IdentityService identityService) {
        this.repository = repository;
        this.identityService = identityService;
    }

    public IdDTO<Integer> save(Application application) throws InsertFailedException {
        if (!repository.save(application)) {
            throw new InsertFailedException();
        }
        return new IdDTO<>(application.getId());
    }

    public void updateApplication(Integer id, Application application) throws UpdateFailedException {
        var userId = identityService.getCurrentAuthentication().getUserId();
        var dbApplication = repository.getById(id);
        if (Objects.nonNull(dbApplication)) {
            application.setId(id);
            if (!repository.updateById(application)) {
                throw new UpdateFailedException();
            }
        } else {
            throw new UpdateFailedException("更新失败，请检查是资源是否存在或是否具有更新资源的权限！");
        }
    }

    public void deleteById(Integer id) throws UpdateFailedException {
        var userId = identityService.getCurrentAuthentication().getUserId();
        Application application = repository.getById(id);
        if (Objects.nonNull(application) && application.getOwner().equals(userId)) {
            if (!repository.removeById(id)) {
                throw new UpdateFailedException();
            }
        } else {
            throw new UpdateFailedException("删除失败，请检查对应资源是否存在或是否具有删除资源的权限！");
        }


    }

    public Application getDetailById(Integer id) throws DataNotExistException {
        Application application = repository.getById(id);
        if (Objects.isNull(application)) {
            throw new DataNotExistException();
        }
        return application;
    }

    public List<Application> getApplicationList(QueryApplicationDTO query, Integer firstResult, Integer maxResults) {
        return repository.selectApplicationPageList(query, firstResult, maxResults);
    }

    public void applicationPublish(Long id) throws DataNotExistException, UpdateFailedException {
        var dbApplication = repository.getById(id);
        if (Objects.isNull(dbApplication)) {
            throw new DataNotExistException("对应ID信息不存在！");
        }
        dbApplication.setStatus(ApplicationStatus.PUBLISH);
        if (!repository.updateById(dbApplication)) {
            throw new UpdateFailedException("更新失败");
        }
    }

    public void applicationTakeDown(Long id) throws DataNotExistException, UpdateFailedException {
        var dbApplication = repository.getById(id);
        if (Objects.isNull(dbApplication)) {
            throw new DataNotExistException("对应ID信息不存在！");
        }
        dbApplication.setStatus(ApplicationStatus.DRAFT);
        if (!repository.updateById(dbApplication)) {
            throw new UpdateFailedException("更新失败");
        }
    }

    public CountDTO getApplicationCount(QueryApplicationDTO query) {
        return new CountDTO(repository.selectApplicationCount(query));
    }
}
