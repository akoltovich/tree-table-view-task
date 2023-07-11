create database if not exists TreeTableView;

create table if not exists human (
    id bigint primary key not null auto_increment,
    name varchar(255) not null ,
    age int not null ,
    birthdate date not null
    );