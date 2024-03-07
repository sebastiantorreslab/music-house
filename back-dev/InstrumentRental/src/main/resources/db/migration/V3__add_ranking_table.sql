#alter table instrument
    #add ranking_prom decimal;

#alter table instrument
    #add ranking_count int;

create table if not exists ranking
(
    id            bigint auto_increment,
    score         smallint null,
    instrument_id bigint   null,
    constraint ranking_pk
        primary key (id),
    constraint instrument_id
        foreign key (instrument_id) references instrument (id)
);
