package com.eorion.bo.enhancement.application.domain.dto;

import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationSaveDTO;
import com.eorion.bo.enhancement.application.domain.dto.inbound.ApplicationUpdateDTO;
import com.eorion.bo.enhancement.application.domain.entity.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationStructureMapper {

    Application saveDtoToEntity(ApplicationSaveDTO dto);

    Application updateDtoToEntity(ApplicationUpdateDTO dto);

}
