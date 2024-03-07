create table if not exists rental
(
    id            bigint auto_increment,
    inst_tag_number varchar(45) not null,
    cust_dni       varchar(45) not null,
    start_date     date        not null,
    end_date       date        not null,
    instrument_rating smallint,
    review varchar(255),
    rental_charge decimal,
    constraint rental_pk
        primary key (id),
    constraint checDates
        check (start_date < end_date)
);
