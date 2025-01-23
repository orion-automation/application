package com.eorion.bo.enhancement.application.domain.dto.inbound;

import lombok.Data;

import java.util.List;

@Data
public class QueryApplicationDTO {

    private String tenant;

    private String coeCode;

    private String userId;

    private List<String> groups;

    private List<String> editGroups;

    private String type;

    private String category;

    private String nameLike;

    private String tagsLike;

    private String appKey;

    private String status;

    private String versionTag;

    private List<String> appKeysIn;

    private String accessUser;

    private List<String> accessUsersIn;
}
