
CREATE TRANSACTIONS (
	id INTEGER,
	customer_id INTEGER,
	reservation_id INTEGER,
	cc_num VARCHAR(16),
	cc_ex DATE,
	cc_crc VARCHAR(3),
	tran_date DATE,
	total DECIMAL(7,2),
	primary key(id),
	foreign key(customer_id) references customer(customer_id),
	foreign key (reservation_id) references reservation (reservation_id)
);


CREATE TABLE ROOMS (
	id INTEGER,
	room_number INTEGER, 
	window VARCHAR(6), 
	bed VARCHAR(3),
	primary key (id)
);

/* FLOOR 1 */
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (1,101,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (2,102,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (3,103,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (4,104,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (5,105,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (6,106,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (7,107,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (8,108,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (9,109,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (10,110,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (11,111,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (12,112,'Pool','Queen');

/* FLOOR 2 */
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (13,201,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (14,202,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (15,203,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (16,204,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (17,205,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (18,206,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (19,207,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (20,208,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (21,209,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (22,210,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (23,211,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (24,212,'Pool','Queen');


/* FLOOR 3 */
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (25,301,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (26,302,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (27,303,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (28,304,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (29,305,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (30,306,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (31,307,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (32,308,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (33,309,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (34,310,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (35,311,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (36,312,'Pool','Queen');


/* FLOOR 4 */
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (37,401,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (38,402,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (39,403,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (40,404,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (41,405,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (42,406,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (43,407,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (44,408,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (45,409,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (46,410,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (47,411,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (48,412,'Pool','Queen');

/* FLOOR 5 */
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (49,501,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (50,502,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (51,503,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (52,504,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (53,505,'Ocean','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (54,506,'Ocean','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (55,507,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (56,508,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (57,509,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (58,510,'Pool','Queen');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (59,511,'Pool','King');
INSERT INTO ROOMS (Id,RoomNumber,Window,Bed) VALUES (60,512,'Pool','Queen');









