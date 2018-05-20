

CREATE TABLE reservation (
    reservation_id INTEGER,
    room_id INTEGER,
    customer_id INTEGER,
    start_date DATE,
    end_date DATE,
    PRIMARY KEY(reservation_id),
    FOREIGN KEY(room_num) REFERENCES rooms(id),
    FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

