create extension if not exists vector;
create extension if not exists hstore;
create extension if not exists "uuid-ossp";

create table topic
(
    id   integer primary key generated always as identity,
    name text not null
);

create table word
(
    id       integer primary key generated always as identity,
    text     text not null,
    topic_id integer,
    constraint fk_topic foreign key (topic_id) references topic (id) on delete cascade
)
