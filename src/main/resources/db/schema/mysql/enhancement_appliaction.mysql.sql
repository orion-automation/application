-- liquibase formatted sql

-- changeset eorion:1 dbms:mysql
create table if not exists ENHANCEMENT_APPLICATION
(
    ID              int unsigned auto_increment primary key,
    NAME_TXT        varchar(255) not null,
    ICON_URL        varchar(2000)         default '',
    COE_CODE_TXT    varchar(128)          default '',
    USER_GROUP_TXT  varchar(128)          default '' comment '用户权限组',
    EDIT_GROUP_TXT  varchar(128)          default '' comment '编辑权限组',
    TAGS_TXT        varchar(128)          default '',
    OWNER_TXT       varchar(64) comment '拥有者',
    TENANT_TXT      varchar(64),
    TYPE_TXT        varchar(128),
    CATEGORY_TXT    longtext,
    CONFIG_TXT      varchar(4000)         default '',
    KEY_TXT         varchar(128),
    STATUS_FG       char(1)      NOT NULL DEFAULT '0' comment '0-草稿，1-上架，默认0',
    DESCRIPTION_TXT varchar(4000)         default '',
    VERSION_TAG_TXT varchar(64),
    TARGET_TXT varchar(255) default 'local',
    TARGET_APPLICATION_ID varchar(255) default '',
    ACCESS_USERS_TXT varchar(255),
    CREATED_TS      bigint,
    UPDATED_TS      bigint,
    CREATE_BY_TXT   varchar(20)           default null,
    UPDATED_BY_TXT  varchar(20)           default null,
    DELETE_FG       smallint              default 0
) comment = '应用表';