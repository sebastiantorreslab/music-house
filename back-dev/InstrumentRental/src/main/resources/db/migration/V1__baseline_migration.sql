CREATE TABLE address
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    cust_address VARCHAR(255)          NOT NULL,
    city_id      BIGINT                NOT NULL,
    CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE city
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    city_code  ENUM('BOGOTA', 'BUENOS_AIRES', 'CALI', 'CORDOBA', 'MEDELLIN', 'MENDOZA')           NOT NULL,
    country_id BIGINT                NOT NULL,
    CONSTRAINT pk_city PRIMARY KEY (id)
);

CREATE TABLE country
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    country_code ENUM('COL', 'ARG')           NOT NULL,
    CONSTRAINT pk_country PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    cust_dni       VARCHAR(45)           NOT NULL,
    cust_firs_name VARCHAR(45)           NOT NULL,
    cust_last_name VARCHAR(45)           NOT NULL,
    cust_email     VARCHAR(255)          NOT NULL,
    cust_password  VARCHAR(255)          NOT NULL,
    cust_phone     VARCHAR(45),
    address_id     BIGINT                NOT NULL,
    role         ENUM('ROLE_USER','ROLE_ADMINI')          NOT NULL,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE instrument
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    inst_name        VARCHAR(40)           NOT NULL,
    inst_brand       VARCHAR(45)           NOT NULL,
    inst_tag_number  VARCHAR(45)           NOT NULL,
    inst_acq_date    DATE                  NOT NULL,
    inst_is_active   TINYINT               NOT NULL,
    category_id         BIGINT          NOT NULL,
    inst_description VARCHAR(255)          NOT NULL,
    inst_photo       VARCHAR(255)          NOT NULL,
    inst_price       DOUBLE                NOT NULL,
    ranking_prom     DECIMAL(2,1),
    ranking_count    INT,
    CONSTRAINT pk_instrument PRIMARY KEY (id)
);

create table category
(
    id                   bigint auto_increment,
    category_title       varchar(45)  not null,
    category_description varchar(255),
    category_image       varchar(255),
    constraint category_pk
        primary key (id)
);

ALTER TABLE city
    ADD CONSTRAINT uc_city_country UNIQUE (country_id);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_cust_dni UNIQUE (cust_dni);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_cust_email UNIQUE (cust_email);

ALTER TABLE instrument
    ADD CONSTRAINT uc_instrument_inst_tag_number UNIQUE (inst_tag_number);

alter table instrument
    add constraint fk_instrument_on_category
        foreign key (category_id) references category (id);

ALTER TABLE address
    ADD CONSTRAINT FK_ADDRESS_ON_CITY FOREIGN KEY (city_id) REFERENCES city (id);

ALTER TABLE city
    ADD CONSTRAINT FK_CITY_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

