-- begin PERSACC_STAFF
create table PERSACC_STAFF (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    LAST_NAME varchar(255),
    FIRST_NAME varchar(255),
    PATRONYMIC varchar(255),
    EMAIL varchar(255) not null,
    POST varchar(255),
    COMPANY varchar(255),
    PHONE varchar(255),
    --
    primary key (ID)
)^
-- end PERSACC_STAFF
