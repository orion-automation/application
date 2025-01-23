package com.eorion.bo.enhancement.application.domain.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class ApplicationSaveDTO {

    @NotBlank(message = "name 不能为空")
    private String name;

    private String coeCode;

    private String tags;

    private String icon;

    private String editGroup;

    private String userGroup;

    @NotBlank(message = "owner 不能为空")
    private String owner;

    private String tenant;

    private String type;

    private String category;

    private Map<String, Object> configJson;

    private String appKey;

    private String description;

    private String versionTag;

    private String target;

    private String targetApplicationId;

    private String accessUsers;
}
