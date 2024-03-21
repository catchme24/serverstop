drop sequence if exists server_seq;
drop sequence if exists usr_seq;
create sequence server_seq start with 1 increment by 50;
create sequence usr_seq start with 1 increment by 50;

create table server (
    created_at   timestamp(6),
    id           bigint not null,
    owner_id     bigint,
    server_start timestamp(6),
    updated_at   timestamp(6),
    chronicle    varchar(255),
    domain       varchar(255),
    server_rate  varchar(255),
    primary key (id)
);

create table user_role (
    user_id bigint not null,
    roles varchar(255) check (roles in ('USER','ADMIN'))
);

create table usr (
     created_at timestamp(6),
     id bigint not null,
     updated_at timestamp(6),
     password varchar(255),
     username varchar(255),
     primary key (id)
);

alter table if exists server
    add constraint server_user_fk
    foreign key (owner_id) references usr;

alter table if exists user_role
    add constraint user_role_user_fk
    foreign key (user_id) references usr
    on delete cascade
    on update cascade;