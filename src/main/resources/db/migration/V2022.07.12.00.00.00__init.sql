drop table if exists barcode_incrementer;
drop table if exists point_history;
drop table if exists store;
drop table if exists user_barcode;
drop table if exists user_point;
drop table if exists user_point_aud;
drop table if exists revinfo;


create table if not exists barcode_incrementer (id bigint not null auto_increment, primary key (id)) engine=InnoDB;
create table if not exists point_history (id bigint not null auto_increment, approved_at datetime(6) not null, barcode varchar(10) not null, changed_point bigint not null, current_point bigint not null, store_category varchar(8) not null, store_name varchar(32) not null, type varchar(8) not null, primary key (id)) engine=InnoDB;
create table if not exists revinfo (rev integer not null auto_increment, revtstmp bigint, primary key (rev)) engine=InnoDB;
create table if not exists store (id bigint not null, category varchar(3) not null, name varchar(32) not null, primary key (id)) engine=InnoDB;
create table if not exists user_barcode (user_id integer not null, barcode varchar(10) not null, created_at datetime(6) not null, primary key (user_id)) engine=InnoDB;
create table if not exists user_point (id bigint not null, barcode varchar(12) not null, category varchar(3) not null, created_at datetime(6) not null, point bigint not null, version bigint not null, primary key (id)) engine=InnoDB;
create table if not exists user_point_aud (id bigint not null, rev integer not null, revtype tinyint, barcode varchar(12), category varchar(3), created_at datetime(6), point bigint, primary key (id, rev)) engine=InnoDB;
create index point_history_ix_barcode_approved_at on point_history (barcode, approved_at desc);
alter table user_barcode add constraint user_barcode_uk_barcode unique (barcode);
alter table user_point add constraint user_point_uk_barcode_category unique (barcode, category);
alter table user_point_aud add constraint FKobtfsn8bklgguud25e511eolf foreign key (rev) references revinfo (rev);

ALTER TABLE barcode_incrementer AUTO_INCREMENT=5000000001;

insert into store(id, category, name) values(1, 'A', '카오 식품');
insert into store(id, category, name) values(2, 'B', '카페 화장품');
insert into store(id, category, name) values(3, 'C', '카카 식당');