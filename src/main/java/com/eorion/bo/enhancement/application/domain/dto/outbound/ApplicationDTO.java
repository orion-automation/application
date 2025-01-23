package com.eorion.bo.enhancement.application.domain.dto.outbound;

import com.eorion.bo.enhancement.application.domain.entity.Application;
import com.eorion.bo.enhancement.application.domain.enums.ApplicationStatus;

import java.util.Map;

public class ApplicationDTO{

    private Integer id;

    private String name;

    private String icon;

    private String coeCode;

    private String tags;

    private String owner;

    private String tenant;

    private String userGroup;

    private String editGroup;

    private String assessUsers;

    private String  type;

    private String category;

    private Map<String, Object> configJson;

    private String appKey;

    private ApplicationStatus status;

    private String description;

    private String versionTag;

    private String target;

    private String targetApplicationId;

    private Long createdTs;

    private Long updatedTs;

    private String createdBy;

    private String updatedBy;

    public String getUserGroup() {
        return userGroup;
    }

    public String getEditGroup() {
        return editGroup;
    }

    public String getAssessUsers() {
        return assessUsers;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getCoeCode() {
        return coeCode;
    }

    public String getTags() {
        return tags;
    }

    public String getOwner() {
        return owner;
    }

    public String getTenant() {
        return tenant;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public Map<String, Object> getConfigJson() {
        return configJson;
    }

    public String getAppKey() {
        return appKey;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getVersionTag() {
        return versionTag;
    }

    public String getTarget() {
        return target;
    }

    public String getTargetApplicationId() {
        return targetApplicationId;
    }

    public Long getCreatedTs() {
        return createdTs;
    }

    public Long getUpdatedTs() {
        return updatedTs;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public static ApplicationDTO formEntity(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.name = application.getName();
        applicationDTO.appKey = application.getAppKey();
        applicationDTO.createdBy = application.getCreatedBy();
        applicationDTO.category = application.getCategory();
        applicationDTO.createdTs = application.getCreatedTs();
        applicationDTO.coeCode  = application.getCoeCode();
        applicationDTO.configJson = application.getConfigJson();
        applicationDTO.description = application.getDescription();
        applicationDTO.id = application.getId();
        applicationDTO.icon = application.getIcon();
        applicationDTO.owner = application.getOwner();
        applicationDTO.status = application.getStatus();
        applicationDTO.tags = application.getTarget();
        applicationDTO.target = application.getTarget();
        applicationDTO.targetApplicationId = application.getTargetApplicationId();
        if (application.getType() != null)
            applicationDTO.type = application.getType();
        applicationDTO.versionTag = application.getVersionTag();
        applicationDTO.updatedTs = application.getUpdatedTs();
        applicationDTO.updatedBy = application.getUpdatedBy();
        applicationDTO.tenant = application.getTenant();
        applicationDTO.editGroup = application.getEditGroup();
        applicationDTO.userGroup = application.getUserGroup();
        applicationDTO.assessUsers = application.getAccessUsers();
        return  applicationDTO;
    }
    
}