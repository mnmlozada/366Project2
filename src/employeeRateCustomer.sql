CREATE TABLE employee (
	id  		serial,
	name 		varchar(30) NOT NULL,
	username    varchar(20) NOT NULL,
	password 	varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE rate (
	room_num 	integer,
	start_date 	date,
	rate 		decimal(8, 2) NOT NULL,
	PRIMARY KEY (room_num, start_date),
	FOREIGN KEY (room_num) REFERENCES room(room_num)
);

ALTER TABLE customer ADD COLUMN username varchar(20) NOT NULL;
ALTER TABLE customer ADD COLUMN password varchar(20) NOT NULL;
ALTER TABLE customer ADD COLUMN email varchar(40) NOT NULL;
ALTER TABLE customer ADD COLUMN cc_num varchar(16) NOT NULL;
ALTER TABLE customer ADD COLUMN cc_ex date NOT NULL;
ALTER TABLE customer ADD COLUMN cc_crc varchar(3) NOT NULL;
ALTER TABLE customer ALTER COLUMN name SET NOT NULL;
ALTER TABLE customer ALTER COLUMN address SET NOT NULL;
ALTER TABLE customer ALTER COLUMN created_date SET NOT NULL;