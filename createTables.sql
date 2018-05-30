drop table healthInfo;
drop table prescription;
drop table transactions;
drop table reservation;
drop table rooms;
drop table staff;
drop table medication;
drop table patient;

CREATE TABLE patient
(
  patient_id serial NOT NULL,
  name text not null,
  address text not null,
  created_date date not null,
  username text not null unique,
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


-- medication prescribed by doctor to patient -- 
create table medication
(
  drug_id serial not null,
  name text,
  price real,
  duration integer,
  primary key (drug_id)
);

CREATE TABLE staff (
	staff_id  serial,
	position text, /* nurse or doctor */
	name text NOT NULL, 
	username   text NOT NULL unique,
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
INSERT INTO rooms (room_number, department, price, wing) VALUES (101, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (103, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (105, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (107, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (109, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (111, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (113, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (115, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (117, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (119, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (121, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (123, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (125, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (127, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (129, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (131, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (133, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (135, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (137, 'medical', 100, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (139, 'medical', 100, 'left');

INSERT INTO rooms (room_number, department, price, wing) VALUES (102, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (104, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (106, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (108, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (110, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (112, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (114, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (116, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (118, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (120, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (122, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (124, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (126, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (128, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (130, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (132, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (134, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (136, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (138, 'respiratory', 100, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (140, 'respiratory', 100, 'right');


-- floor 2 --
INSERT INTO rooms (room_number, department, price, wing) VALUES (201, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (203, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (205, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (207, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (209, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (211, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (213, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (215, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (217, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (219, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (221, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (223, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (225, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (227, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (229, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (231, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (233, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (235, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (237, 'womens', 200, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (239, 'womens', 200, 'left');

INSERT INTO rooms (room_number, department, price, wing) VALUES (202, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (204, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (206, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (208, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (210, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (212, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (214, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (216, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (218, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (220, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (222, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (224, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (226, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (228, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (230, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (232, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (234, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (236, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (238, 'post-womens', 200, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (240, 'post-womens', 200, 'right');

-- floor 3 -- 
INSERT INTO rooms (room_number, department, price, wing) VALUES (301, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (303, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (305, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (307, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (309, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (311, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (313, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (315, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (317, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (319, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (321, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (323, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (325, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (327, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (329, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (331, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (333, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (335, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (337, 'orthopedic', 300, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (339, 'orthopedic', 300, 'left');

INSERT INTO rooms (room_number, department, price, wing) VALUES (302, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (304, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (306, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (308, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (310, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (312, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (314, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (316, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (318, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (320, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (322, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (324, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (326, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (328, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (330, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (332, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (334, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (336, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (338, 'neurology', 300, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (340, 'neurology', 300, 'right');

-- floor 4 --
INSERT INTO rooms (room_number, department, price, wing) VALUES (401, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (403, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (405, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (407, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (409, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (411, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (413, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (415, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (417, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (419, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (421, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (423, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (425, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (427, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (429, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (431, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (433, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (435, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (437, 'surgery', 400, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (439, 'surgery', 400, 'left');

INSERT INTO rooms (room_number, department, price, wing) VALUES (402, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (404, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (406, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (408, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (410, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (412, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (414, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (416, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (418, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (420, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (422, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (424, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (426, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (428, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (430, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (432, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (434, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (436, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (438, 'oncology', 400, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (440, 'oncology', 400, 'right');

-- floor 5 --
INSERT INTO rooms (room_number, department, price, wing) VALUES (501, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (503, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (505, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (507, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (509, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (511, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (513, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (515, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (517, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (519, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (521, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (523, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (525, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (527, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (529, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (531, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (533, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (535, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (537, 'ICU', 500, 'left');
INSERT INTO rooms (room_number, department, price, wing) VALUES (539, 'ICU', 500, 'left');

INSERT INTO rooms (room_number, department, price, wing) VALUES (502, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (504, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (506, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (508, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (510, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (512, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (514, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (516, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (518, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (520, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (522, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (524, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (526, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (528, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (530, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (532, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (534, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (536, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (538, 'CCU', 500, 'right');
INSERT INTO rooms (room_number, department, price, wing) VALUES (540, 'CCU', 500, 'right');

-- admin -- 
INSERT INTO staff VALUES (0, 'admin', 'Admin', 'admin', 'admin', TRUE);