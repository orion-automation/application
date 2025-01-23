package com.eorion.bo.enhancement.application.adapter.outbound;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eorion.bo.enhancement.application.domain.dto.inbound.QueryApplicationDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationRepository extends ServiceImpl<ApplicationMapper, Application> {
    private final ApplicationMapper mapper;

    public ApplicationRepository(ApplicationMapper mapper) {
        this.mapper = mapper;
    }

    public List<Application> selectApplicationPageList(QueryApplicationDTO query, Integer firstResult, Integer maxResults) {
        return mapper.selectApplicationPageList(query, firstResult, maxResults);
    }

    public Integer selectApplicationCount(QueryApplicationDTO query) {
        return mapper.selectApplicationCount(query);
    }

    public List<Application> selectPdaApplications(String userId, List<String> groups, String tenant, String nameLike) {

        return mapper.selectPdaApplications(userId, groups, tenant, nameLike);
    }
}
