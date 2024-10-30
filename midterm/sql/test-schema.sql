drop database if exists dwmh_test;
create database dwmh_test;
use dwmh_test;
 
create table state (
    state_id int primary key auto_increment,
    name varchar(50) not null unique,
    usps_code varchar(2) not null unique
);
 
create table `user` (
    user_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(512) not null unique,
    phone varchar(50) not null
);
 
 
create table location (
    location_id int primary key auto_increment,
    host_user_id int not null,
    address varchar(100) not null,
    city varchar(100) not null,
    postal_code varchar(20) not null,
    state_id int not null,
    standard_rate decimal(8, 2) not null,
    weekend_rate decimal(8, 2) not null,
    foreign key (host_user_id) references `user`(user_id),
    foreign key (state_id) references state(state_id)
);
 
create table reservation (
    reservation_id int primary key auto_increment,
    location_id int not null,
    guest_user_id int not null,
    start_date date not null,
    end_date date not null,
    total decimal(10, 2) not null,
    foreign key (location_id) references location(location_id),
    foreign key (guest_user_id) references `user`(user_id)
);
 
delimiter //
 
create procedure set_known_good_state()
begin

	set foreign_key_checks = 0;
    truncate table reservation;
    truncate table location;
    truncate table `user`;
    truncate table state;
 
    set foreign_key_checks = 1;
 
    insert into state (`name`, usps_code) values
    ('california', 'ca'),
    ('new york', 'ny'),
    ('texas', 'tx');
 
    insert into `user` (first_name, last_name, email, phone) values
        ('llywellyn', 'vondra', 'lvondra0@vkontakte.ru', '(940) 3612277'),
        ('roanna', 'klimpt', 'rklimpt1@paginegialle.it', '(277) 2824355'),
        ('teresina', 'honnan', 'thonnan2@berkeley.edu', '(353) 2288123');

 
    insert into location (host_user_id, address, city, postal_code, state_id, standard_rate, weekend_rate) values
        (1, '123 elm st', 'los angeles', '90001', (select state_id from state where usps_code = 'ca'), 200.00, 250.00),
        (2, '456 maple ave', 'new york', '10001', (select state_id from state where usps_code = 'ny'), 300.00, 350.00),
        (3, '789 oak dr', 'houston', '77001', (select state_id from state where usps_code = 'tx'), 150.00, 200.00);
 
    insert into reservation (location_id, guest_user_id, start_date, end_date, total) values
        (1, 2, '2024-08-01', '2024-08-05', 1000.00),
        (2, 3, '2024-09-10', '2024-09-15', 1750.00),
        (3, 1, '2024-10-01', '2024-10-07', 1050.00);
end//
 
delimiter ;

