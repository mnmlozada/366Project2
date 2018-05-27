CREATE TABLE patient
(
  patient_id serial NOT NULL,
  name text not null,
  address text not null,
  created_date date not null,
  username text not null,
  password text not null,
  PRIMARY KEY (patient_id)
);


CREATE TABLE healthInfo
(
  patient_id integer,
  age integer,
  gender text,
  height_inch integer,
  weight integer,
  allergies text,
  conditions text,
  medicine text,
  insurance text,
  primary key (patient_id),
  foreign key (patient_id) references patient
);

-- -- 
create table prescription 
(
  patient_id integer,
  staff_id integer,
  reservation_id integer,
  medication_id integer,
  primary key (patient_id, staff_id, reservation_id, medication_id),
  foreign key (patient_id) references patient,
  foreign key (staff_id) references staff,
  foreign key (reservation_id) references reservation,
  foreign key (medication_id) references medication
);

-- medication prescribed by doctor to patient -- 
create table medication
(
  drug_id serial not null,
  name text,
  price real,
  duration integer
  primary key (drug_id)
);

CREATE TABLE staff (
	staff_id  serial,
	position text, /* nurse or doctor */
	name text NOT NULL, 
	username   text NOT NULL,
	password text NOT NULL,
	adminFlag   BOOLEAN,
	PRIMARY KEY (staff_id)
);

CREATE TABLE rooms (
  room_number INTEGER,
  department text,
  price real,
  wing text,
  primary key (room_number)
);

CREATE TABLE reservation (
    reservation_id SERIAL,
    room_num INTEGER,
    patient_id INTEGER,
    total real,
    checked_in DATE,
    checked_out DATE,
    PRIMARY KEY(reservation_id),
    FOREIGN KEY(room_num) REFERENCES rooms(room_number),
    FOREIGN KEY(patient_id) REFERENCES patient(patient_id)
);

CREATE TABLE transactions (
	transaction_id SERIAL,
	patient_id INTEGER,
	reservation_id INTEGER,
	charge_type text,
	charge_description text,
	tran_date DATE,
	total real,
	primary key(transaction_id),
	foreign key(patient_id) references patient(patient_id),
	foreign key (reservation_id) references reservation (reservation_id)
);

-- rooms, floor 1 --
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('medical', 100, 'left');

INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('respiratory', 100, 'right');

-- floor 2 --
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('womens', 200, 'left');

INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('post-womens', 200, 'right');

-- floor 3 -- 
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('orthopedic', 300, 'left');

INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('neurology', 300, 'right');

-- floor 4 --
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('surgery', 400, 'left');

INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('oncology', 400, 'right');

-- floor 5 --
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');
INSERT INTO rooms (department, price, wing) VALUES ('ICU', 500, 'left');

INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');
INSERT INTO rooms (department, price, wing) VALUES ('CCU', 500, 'right');

-- admin -- 
INSERT INTO staff VALUES (0, 'Admin', 'admin', 'admin', TRUE);