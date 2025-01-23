-- liquibase formatted sql

-- changeset eorion:1 dbms:postgresql
create table if not exists ENHANCEMENT_APPLICATION
(
    ID              SERIAL PRIMARY KEY,
    NAME_TXT        varchar(255) not null,
    ICON_URL        varchar(2000)         default '',
    COE_CODE_TXT    varchar(128)          default '',
    USER_GROUP_TXT  varchar(128)          default '',
    EDIT_GROUP_TXT  varchar(128)          default '',
    TAGS_TXT        varchar(128)          default '',
    OWNER_TXT       varchar(64),
    TENANT_TXT      varchar(64),
    TYPE_TXT        varchar(128),
    CATEGORY_TXT    varchar(255),
    CONFIG_TXT      text,
    KEY_TXT         varchar(128),
    STATUS_FG       char(1)      NOT NULL DEFAULT '0',
    DESCRIPTION_TXT varchar(4000),
    VERSION_TAG_TXT varchar(64),
    TARGET_TXT varchar(255) default 'local',
    TARGET_APPLICATION_ID varchar(255) default '',
    ACCESS_USERS_TXT varchar(255) default '',
    CREATED_TS      bigint,
    UPDATED_TS      bigint,
    CREATE_BY_TXT   varchar(20)           default null,
    UPDATED_BY_TXT  varchar(20)           default null,
    DELETE_FG       smallint              default 0
);
COMMENT ON COLUMN ENHANCEMENT_APPLICATION.OWNER_TXT IS '拥有者';
COMMENT ON COLUMN ENHANCEMENT_APPLICATION.EDIT_GROUP_TXT IS '编辑权限组';
COMMENT ON COLUMN ENHANCEMENT_APPLICATION.USER_GROUP_TXT IS '用户权限组';
COMMENT ON COLUMN ENHANCEMENT_APPLICATION.STATUS_FG IS '0-草稿，1-上架，默认0';