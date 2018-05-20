CREATE TABLE customer
(
  customer_id serial NOT NULL,
  name text not null,
  address text not null,
  created_date date not null,
  username text not null,
  password text not null,
  email text not null,
  cc_num varchar(16) NOT NULL,
  cc_ex date NOT NULL,
  cc_crc varchar(3) NOT NULL,
  PRIMARY KEY (customer_id)
);


CREATE TABLE employee (
	employee_id  serial,
	name text NOT NULL,
	username   text NOT NULL,
	password text NOT NULL,
	adminFlag   BOOLEAN,
	PRIMARY KEY (employee_id)
);

CREATE TABLE rooms (
  room_number INTEGER,
  window_type VARCHAR(5),
  bed VARCHAR(5),
  primary key (room_number)
);

CREATE TABLE rate (
	room_num 	integer,
	date 	date,
	rate 		decimal(8, 2) NOT NULL,
	PRIMARY KEY (room_num, date),
	FOREIGN KEY (room_num) REFERENCES rooms(room_number)
);

CREATE TABLE reservation (
    reservation_id SERIAL,
    room_num INTEGER,
    customer_id INTEGER,
    start_date DATE,
    end_date DATE,
    total DECIMAL(7,2),
    checked_in DATE,
    checked_out DATE,
    PRIMARY KEY(reservation_id),
    FOREIGN KEY(room_num) REFERENCES rooms(room_number),
    FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE transactions (
	transaction_id SERIAL,
	customer_id INTEGER,
	reservation_id INTEGER,
	cc_num VARCHAR(16),
	cc_ex DATE,
	cc_crc INTEGER,
	tran_date DATE,
	total DECIMAL(7,2),
	primary key(transaction_id),
	foreign key(customer_id) references customer(customer_id),
	foreign key (reservation_id) references reservation (reservation_id)
);

INSERT INTO rooms (room_number,window_type,Bed) VALUES (101,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (102,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (103,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (104,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (105,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (106,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (107,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (108,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (109,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (110,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (111,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (112,'Pool','Queen');

INSERT INTO rooms (room_number,window_type,Bed) VALUES (201,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (202,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (203,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (204,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (205,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (206,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (207,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (208,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (209,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (210,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (211,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (212,'Pool','Queen');

INSERT INTO rooms (room_number,window_type,Bed) VALUES (301,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (302,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (303,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (304,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (305,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (306,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (307,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (308,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (309,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (310,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (311,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (312,'Pool','Queen');

INSERT INTO rooms (room_number,window_type,Bed) VALUES (401,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (402,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (403,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (404,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (405,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (406,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (407,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (408,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (409,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (410,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (411,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (412,'Pool','Queen');

INSERT INTO rooms (room_number,window_type,Bed) VALUES (501,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (502,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (503,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (504,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (505,'Ocean','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (506,'Ocean','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (507,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (508,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (509,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (510,'Pool','Queen');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (511,'Pool','King');
INSERT INTO rooms (room_number,window_type,Bed) VALUES (512,'Pool','Queen');

INSERT INTO employee VALUES (0, 'Admin', 'admin', 'admin', TRUE);