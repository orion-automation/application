package com.eorion.bo.enhancement.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eorion.bo.enhancement.application.domain.dto.inbound.QueryApplicationDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
    List<Application> selectApplicationPageList(@Param("query") QueryApplicationDTO query, @Param("firstResult") Integer firstResult, @Param("maxResults") Integer maxResults);

    Integer selectApplicationCount(@Param("query") QueryApplicationDTO query);

    List<Application> selectPdaApplications(@Param("userId") String userId, @Param("groups") List<String> groups,
                                            @Param("tenant") String tenant, @Param("nameLike") String nameLike);
}
