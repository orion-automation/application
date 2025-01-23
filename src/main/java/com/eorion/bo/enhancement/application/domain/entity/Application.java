package com.eorion.bo.enhancement.application.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.eorion.bo.enhancement.application.domain.enums.ApplicationStatus;
import com.eorion.bo.enhancement.application.domain.handler.JsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@TableName(value = "ENHANCEMENT_APPLICATION", autoResultMap = true)
@EqualsAndHashCode
public class Application {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "NAME_TXT")
    private String name;

    @TableField(value = "ICON_URL")
    private String icon;

    @TableField(value = "COE_CODE_TXT")
    private String coeCode;

    @TableField(value = "USER_GROUP_TXT")
    private String userGroup;

    @TableField(value = "EDIT_GROUP_TXT")
    private String editGroup;

    @TableField(value = "TAGS_TXT")
    private String tags;

    @TableField(value = "OWNER_TXT")
    private String owner;

    @TableField(value = "TENANT_TXT")
    private String tenant;

    @TableField(value = "TYPE_TXT")
    private String type;

    @TableField(value = "CATEGORY_TXT")
    private String category;

    @TableField(value = "CONFIG_TXT", typeHandler = JsonTypeHandler.class)
    private Map<String, Object> configJson;

    @TableField(value = "KEY_TXT")
    private String appKey;

    @TableField(value = "STATUS_FG")
    private ApplicationStatus status;

    @TableField(value = "DESCRIPTION_TXT")
    private String description;

    @TableField(value = "VERSION_TAG_TXT")
    private String versionTag;

    @TableField(value = "TARGET_TXT")
    private String target;

    @TableField(value = "TARGET_APPLICATION_ID")
    private String targetApplicationId;

    @TableField(value = "ACCESS_USERS_TXT")
    private String accessUsers;

    @TableField(value = "CREATED_TS", fill = FieldFill.INSERT)
    private Long createdTs;

    @TableField(value = "UPDATED_TS", fill = FieldFill.INSERT_UPDATE)
    private Long updatedTs;

    @TableField(value = "CREATE_BY_TXT", fill = FieldFill.INSERT)
    private String createdBy;

    @TableField(value = "UPDATED_BY_TXT", fill = FieldFill.UPDATE)
    private String updatedBy;

    @TableField(value = "DELETE_FG")
    @TableLogic(value = "0", delval = "1")
    @JsonIgnore
    private short deleteFlag;

}
