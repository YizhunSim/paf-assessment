-- CREATE NEW DATABASE

drop database if exists eshop;

create database eshop;

show databases;

use eshop;

-- CREATE NEW TABLE

create table customers (
    customer_id int auto_increment not null,
	name varchar(32) not null,
    address varchar(128) not null,
    email varchar(128) not null,
    primary key (customer_id)
);

-- INSERT DATA INTO TABLE

insert into customers (name, address, email) values ('Yizhun', 'abc123', 'yizhunabc123@gmail.com');
insert into customers (name, address, email) values ('Zhun', 'bcd123', 'zhun123@gmail.com');
insert into customers (name, address, email) values ('ZhunSim', 'cdf123', 'zhunsim123@gmail.com');
insert into customers (name, address, email) values ('SimZhun', 'efg123', 'simzhun123@gmail.com');
insert into customers (name, address, email) values ('AhZhun', 'hij123', 'ahzhun123@gmail.com');

