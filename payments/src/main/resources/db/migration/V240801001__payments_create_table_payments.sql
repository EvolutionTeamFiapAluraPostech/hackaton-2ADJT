create schema if not exists "payments_management";

create table if not exists "payments_management"."payments"
(
    "id"              uuid                        not null default gen_random_uuid() primary key,
    "deleted"         boolean                     not null default false,
    "version"         bigint                      not null,
    "created_at"      timestamp without time zone null,
    "created_by"      varchar(255)                null,
    "updated_at"      timestamp without time zone null,
    "updated_by"      varchar(255)                null,
    "cpf"             varchar(11)                 not null,
    "number"          varchar(16)                 not null,
    "expiration_date" varchar(5)                  not null,
    "cvv"             varchar(3)                  not null,
    "value"           numeric(16, 2)              not null,
    "status"          varchar(20)                 null
);