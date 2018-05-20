 // used for employee to view list
 public List<Reservation> getReservationList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from reservation order by start_date"
        );

        //get employee data from database
        ResultSet result = ps.executeQuery();
        List<Reservation> list = new ArrayList<>();

        while (result.next()) {
            Reservation r = new Reservation();

            r.setReservationId(result.getInt("reservation_id"));
            r.setRoomId(result.getInt("room_num"));
            r.setCustomerId(result.getInt("customer_id"));
            r.setStartDate(result.getDate("start_date"));
            r.setEndDate(result.getDate("end_date"));
            r.setTotal(result.getDouble("total"));
            r.setCheckedIn(result.getDate("checked_in"));
            r.setCheckedOut(result.getDate("checked_out"));

            //store all data into a List
            list.add(r);
        }
        
        result.close();
        con.close();
        return list;
    }
	
public Reservation viewReservation() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * " +
            "from reservation " +
            "where reservation_id = " + login.getReservationId
            "order by start_date " +
            "limit 1"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            reservationId = result.getInt("reservation_id");
            roomId = result.getInt("room_num");
            customerId = result.getInt("customer_id");
            startDate = result.getDate("start_date");
            endDate = result.getDate("end_date");
            total = result.getDouble("total");
            checkedIn = result.getDate("checked_in");
            checkedOut = result.getDate("checked_out");
        }
        
        return this;
    }