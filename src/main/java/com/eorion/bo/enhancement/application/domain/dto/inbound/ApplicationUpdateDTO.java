package com.eorion.bo.enhancement.application.domain.dto.inbound;

import lombok.Data;

import java.util.Map;

@Data
public class ApplicationUpdateDTO {
    private String name;

    private String tags;

    private String icon;

    private String editGroup;

    private String userGroup;

    private String type;

    private String category;

    private Map<String, Object> configJson;

    private String appKey;

    private String description;

    private String versionTag;

    private String accessUsers;
}
