package com.eorion.bo.enhancement.application.domain.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class ApplicationSaveDTO {
    @NotBlank(message = "name 不能为空")
    @Size(max = 255)
    private String name;
    @Size(max = 128)
    private String coeCode;
    @Size(max = 128)
    private String tags;
    @Size(max = 2000)
    private String icon;
    @Size(max = 128)
    private String editGroup;
    @Size(max = 128)
    private String userGroup;
    @NotBlank(message = "owner 不能为空")
    @Size(max = 64)
    private String owner;
    @Size(max = 64)
    private String tenant;
    @Size(max = 128)
    private String type;
    @Size(max = 255)
    private String category;

    private Map<String, Object> configJson;
    @Size(max = 128)
    private String appKey;
    @Size(max = 4000)
    private String description;
    @Size(max = 64)
    private String versionTag;
    @Size(max = 255)
    private String target;
    @Size(max = 255)
    private String targetApplicationId;
    @Size(max = 255)
    private String accessUsers;
}
