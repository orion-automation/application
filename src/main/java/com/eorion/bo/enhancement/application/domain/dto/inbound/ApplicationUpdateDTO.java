package com.eorion.bo.enhancement.application.domain.dto.inbound;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Map;

@Data
public class ApplicationUpdateDTO {
    @NotBlank(message = "name 不能为空")
    @Size(max = 255)
    private String name;
    @Size(max = 128)
    private String tags;
    @Size(max = 2000)
    private String icon;
    @Size(max = 128)
    private String editGroup;
    @Size(max = 128)
    private String userGroup;
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
    private String accessUsers;
}
