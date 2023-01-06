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

-- SELECT CUSTOMER DATA
SELECT * FROM customers;

-- SQL_SELECT_CUSTOMER_BY_NAME
SELECT * from customers where name = 'Yizhun';

-- c. Create a Order table --
-- CREATE Table order_status
create table order_status (
	order_id varchar(128) not null,
    delivery_id varchar(128) not null,
    status enum ('pending', 'dispatched'),
    status_update datetime default current_timestamp ON UPDATE current_timestamp,
    primary key (order_id)
);

desc order_status;

SELECT * FROM order_status;

create table line (
	id int auto_increment not null,
    primary key(id)
);

desc line;

select * FROM line;

create table line_items (
	id int auto_increment not null,
	item varchar(128) not null,
    quantity int not null,
    primary key(id),
    foreign key (id) references line (id)
);

desc line_items;

SELECT * FROM line_items;

-- CREATE TABLE order
create table orders (
	id int auto_increment not null,
    customer_id int not null,
    order_id varchar(128) not null,
    line_items_id int not null,
    order_date datetime default current_timestamp ON UPDATE current_timestamp,
    primary key (id),
    foreign key (customer_id) references customers (customer_id),
    foreign key (order_id) references order_status (order_id),
    foreign key (line_items_id) references line (id)
);

desc orders;

SELECT * FROM orders;

-- Save the order to the database
-- 1. INSERT Order Status *order_id [PRE-REQUISITE]
INSERT INTO order_status (order_id, delivery_id, status, status_update) VALUES ('O123456D', 'D0', 'PENDING', NOW());

-- 2. INSERT line *line_items_id [PRE-REQUISITE]
INSERT INTO line (id) VALUES (null);

-- 3. INSERT line_items 
INSERT INTO line_items (item, quantity) VALUES ('IPHONE', 5);

-- 4. INSERT Orders 
INSERT INTO orders (customer_id, order_id, line_items_id, order_date) VALUES (1, 'O123456D', 1, NOW());

SELECT * FROM customers;
SELECT * FROM order_status order by status_update desc;
SELECT * FROM line;
SELECT * FROM line_items;
SELECT * FROM orders;

-- DROPPING OF TABLES TO BE IN SEQUENCE
-- drop table customers;
drop table orders;
drop table order_status;
drop table line_items;
drop table line;