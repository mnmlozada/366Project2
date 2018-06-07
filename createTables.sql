drop table healthInfo;
drop table prescription;
drop table transactions;
drop table reservation;
drop table rooms;
drop table staff;
drop table medication;
drop table patient;

create table patient
(
  patient_id serial not null,
  name text not null,
  address text not null,
  created_date date not null,
  username text not null unique,
  password text not null,
  dob date not null,
  primary key (patient_id)
);

create table healthInfo
(
  hi_id serial,
  patient_id integer,
  exam_date date,
  gender text,
  height_inch integer,
  weight integer,
  allergies text,
  conditions text,
  medicine text,
  insurance text,
  primary key (hi_id),
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

create table staff (
  staff_id serial,
  position text, /* nurse or doctor */
  name text not null, 
  username text not null unique,
  password text not null,
  adminFlag boolean,
  primary key (staff_id)
);

create table rooms (
  room_number integer,
  department text,
  price real,
  wing text,
  primary key (room_number)
);

create table reservation (
  reservation_id serial,
  room_num integer,
  patient_id integer,
  total real,
  checked_in date,
  checked_out date,
  primary key(reservation_id),
  foreign key(room_num) references rooms(room_number),
  foreign key(patient_id) references patient(patient_id)
);

create table prescription 
(
  prescription_id serial,
  patient_id integer,
  staff_id integer,
  reservation_id integer,
  medication_id integer,
  primary key (prescription_id),
  foreign key (patient_id) references patient,
  foreign key (staff_id) references staff,
  foreign key (reservation_id) references reservation,
  foreign key (medication_id) references medication
);

create table transactions (
  transaction_id serial,
  patient_id integer,
  reservation_id integer,
  charge_type text,
  charge_description text,
  tran_date date,
  total real,
  primary key(transaction_id),
  foreign key(patient_id) references patient(patient_id),
  foreign key (reservation_id) references reservation (reservation_id)
);

-- rooms, floor 1 --
insert into rooms (room_number, department, price, wing) values (101, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (103, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (105, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (107, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (109, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (111, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (113, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (115, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (117, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (119, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (121, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (123, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (125, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (127, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (129, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (131, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (133, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (135, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (137, 'medical', 100, 'left');
insert into rooms (room_number, department, price, wing) values (139, 'medical', 100, 'left');

insert into rooms (room_number, department, price, wing) values (102, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (104, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (106, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (108, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (110, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (112, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (114, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (116, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (118, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (120, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (122, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (124, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (126, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (128, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (130, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (132, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (134, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (136, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (138, 'respiratory', 100, 'right');
insert into rooms (room_number, department, price, wing) values (140, 'respiratory', 100, 'right');

-- floor 2 --
insert into rooms (room_number, department, price, wing) values (201, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (203, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (205, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (207, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (209, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (211, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (213, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (215, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (217, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (219, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (221, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (223, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (225, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (227, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (229, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (231, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (233, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (235, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (237, 'womens', 200, 'left');
insert into rooms (room_number, department, price, wing) values (239, 'womens', 200, 'left');

insert into rooms (room_number, department, price, wing) values (202, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (204, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (206, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (208, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (210, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (212, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (214, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (216, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (218, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (220, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (222, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (224, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (226, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (228, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (230, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (232, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (234, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (236, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (238, 'post-womens', 200, 'right');
insert into rooms (room_number, department, price, wing) values (240, 'post-womens', 200, 'right');

-- floor 3 -- 
insert into rooms (room_number, department, price, wing) values (301, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (303, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (305, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (307, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (309, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (311, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (313, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (315, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (317, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (319, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (321, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (323, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (325, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (327, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (329, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (331, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (333, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (335, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (337, 'orthopedic', 300, 'left');
insert into rooms (room_number, department, price, wing) values (339, 'orthopedic', 300, 'left');

insert into rooms (room_number, department, price, wing) values (302, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (304, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (306, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (308, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (310, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (312, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (314, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (316, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (318, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (320, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (322, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (324, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (326, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (328, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (330, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (332, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (334, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (336, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (338, 'neurology', 300, 'right');
insert into rooms (room_number, department, price, wing) values (340, 'neurology', 300, 'right');

-- floor 4 --
insert into rooms (room_number, department, price, wing) values (401, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (403, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (405, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (407, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (409, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (411, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (413, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (415, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (417, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (419, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (421, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (423, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (425, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (427, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (429, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (431, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (433, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (435, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (437, 'surgery', 400, 'left');
insert into rooms (room_number, department, price, wing) values (439, 'surgery', 400, 'left');

insert into rooms (room_number, department, price, wing) values (402, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (404, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (406, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (408, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (410, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (412, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (414, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (416, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (418, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (420, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (422, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (424, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (426, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (428, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (430, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (432, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (434, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (436, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (438, 'oncology', 400, 'right');
insert into rooms (room_number, department, price, wing) values (440, 'oncology', 400, 'right');

-- floor 5 --
insert into rooms (room_number, department, price, wing) values (501, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (503, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (505, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (507, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (509, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (511, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (513, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (515, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (517, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (519, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (521, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (523, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (525, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (527, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (529, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (531, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (533, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (535, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (537, 'ICU', 500, 'left');
insert into rooms (room_number, department, price, wing) values (539, 'ICU', 500, 'left');

insert into rooms (room_number, department, price, wing) values (502, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (504, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (506, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (508, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (510, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (512, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (514, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (516, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (518, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (520, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (522, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (524, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (526, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (528, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (530, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (532, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (534, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (536, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (538, 'CCU', 500, 'right');
insert into rooms (room_number, department, price, wing) values (540, 'CCU', 500, 'right');

-- admin -- 
insert into staff values (0, 'admin', 'Admin', 'admin', 'admin', true);

