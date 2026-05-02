create extension if not exists vector;
create extension if not exists hstore;
create extension if not exists "uuid-ossp";

create table learner
(
    id integer primary key generated always as identity,
    auth0_id text unique not null,
    username text unique not null
);

create table topic
(
    id                integer primary key generated always as identity,
    name              text not null,
    description       text,
    learner_id        integer,
    source_language   text,
    target_language   text,
    proficiency_level text,
    constraint fk_learner foreign key (learner_id) references learner (id) on delete cascade
);

create table word
(
    id          integer primary key generated always as identity,
    text        text not null,
    translation text not null,
    type        text,
    topic_id    integer,
    attributes  jsonb,
    constraint fk_topic foreign key (topic_id) references topic (id) on delete cascade
);

insert into learner (auth0_id, username)
values ('auth0|69d24846542f0db1a5791498', 'marek');
