create schema if not exists "customer_management";

create table if not exists "customer_management"."customers"
(
    "id"           uuid                        not null default gen_random_uuid() primary key,
    "deleted"      boolean                     not null default false,
    "version"      bigint                      not null,
    "created_at"   timestamp without time zone null,
    "created_by"   varchar(255)                null,
    "updated_at"   timestamp without time zone null,
    "updated_by"   varchar(255)                null,
    "name"         varchar(500)                not null,
    "email"        varchar(500)                not null,
    "cpf"          varchar(11)                 not null,
    "phone_number" varchar(50)                 not null,
    "street"       varchar(255)                null,
    "city"         varchar(100)                null,
    "state"        varchar(2)                  null,
    "country"      varchar(100)                null,
    "postal_code"  varchar(8)                  null
);