import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;

import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@ManagedBean
@Named(value = "reservation")
@SessionScoped
public class Reservation implements Serializable {
    
    private static final long ONE_DAY_MILLISECONDS = 86400000;
    private static final double DEFAULT_RATE = 100.0;
    
    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    
    private final DBConnect dbConnect = new DBConnect();
    private Integer reservationId;
    private Integer roomId;
    private Integer customerId;
    
    private Date startDate;
    private UIInput startDateUI;
    
    private Date endDate;
    private UIInput endDateUI;
    
    /*
     * Total is stored to account for possible changes in room rate when a
     * reservation is already made.
     */
    private Double total;
    
    private Date checkedIn;
    private Date checkedOut;
    
    private String bedType;
    private String viewType;
    private UIInput viewTypeUI;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
   
    public UIInput getStartDateUI() {
        return startDateUI;
    }

    public void setStartDateUI(UIInput startDateUI) {
        this.startDateUI = startDateUI;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
     
    public UIInput getEndDateUI() {
        return endDateUI;
    }

    public void setEndDateUI(UIInput endDateUI) {
        this.endDateUI = endDateUI;
    }
    
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    public Date getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Date checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Date getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Date checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }
    
    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public UIInput getViewTypeUI() {
        return viewTypeUI;
    }

    public void setViewTypeUI(UIInput viewTypeUI) {
        this.viewTypeUI = viewTypeUI;
    }

    public void validateNewReservation(
        FacesContext context,
        UIComponent component,
        Object value
    ) throws ValidatorException, SQLException {
        startDate = new Date(startDateUI.getLocalValue().toString());
        endDate = new Date(endDateUI.getLocalValue().toString());
        viewType = viewTypeUI.getLocalValue().toString();
        bedType = value.toString();
        
         try {
            Connection con = dbConnect.getConnection();
            if (con == null) {
                throw new SQLException("Can't get database connection");
            }
            con.setAutoCommit(false);
            
            if (endDate.getTime() <= startDate.getTime()) {
                FacesMessage errorMessage = new FacesMessage(
                    "No rooms available matching given criteria."
                );
                throw new ValidatorException(errorMessage);
            }
                        
            PreparedStatement ps = con.prepareStatement(
                "select * from rooms " +
                    "room1 left outer join reservation res1 " +
                    "on res1.room_num = room1.room_number " +
                "where window_type like ? and bed like ? and " +
                    "(" +
                        "select count(*) from reservation res2 " +
                        "where res2.room_num = res1.room_num and " +
                        "start_date < ?::date and end_date > ?::date" +
                    ") = 0"
            );

            ps.setString(1, viewType);
            ps.setString(2, bedType);
            ps.setDate(3, new java.sql.Date(endDate.getTime()));
            ps.setDate(4, new java.sql.Date(startDate.getTime()));

            ResultSet result = ps.executeQuery();
            if (result.next()) {
                roomId = result.getInt("room_number");
                if (login.getType() == Login.CUSTOMER) {
                    customerId = login.getUserId();
                }
            }
            else {
                FacesMessage errorMessage = new FacesMessage(
                    "No rooms available matching given criteria."
                );
                throw new ValidatorException(errorMessage);
            }
            result.close();
            con.commit();
            ps.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String create() throws SQLException, ParseException {
        try {
            Connection con = dbConnect.getConnection();
            if (con == null) {
                throw new SQLException("Can't get database connection");
            }
            con.setAutoCommit(false);
            
            Date temp = new java.sql.Date(startDate.getTime());

            Double rate;
            PreparedStatement ps;
            total = 0.0;
            
            while (temp.getTime() < endDate.getTime()) {
                rate = DEFAULT_RATE;
                ps = con.prepareStatement(
                    "select * from rate " +
                    "where room_num = " + roomId + " and " +
                        "date = '" + temp.toString() + "'::date"
                );

                ResultSet result = ps.executeQuery();
                while (result.next()) {
                    rate = result.getDouble("rate");
                }
                total += rate;
                temp.setTime(temp.getTime() + ONE_DAY_MILLISECONDS);
                ps.close();
                System.out.println(temp.getTime());
                System.out.println(endDate.getTime());
            }
            
            ps = con.prepareStatement(
                "Insert into reservation (room_num, customer_id, start_date," +
                    " end_date, total) values(?,?,?,?,?)"
            );
            
            //ps.setInt(1, reservationId);
            ps.setInt(1, roomId);
            ps.setInt(2, customerId);
            ps.setDate(3, new java.sql.Date(startDate.getTime()));
            ps.setDate(4, new java.sql.Date(endDate.getTime()));
            ps.setDouble(5, total);
            ps.executeUpdate();
            con.commit();
            ps.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return "main";
    }
    
    public String delete() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate(
            "Delete from reservation " +
            "where reservation_id = " + reservationId
        );
        statement.close();
        con.commit();
        con.close();
        
        return "main";
    }
    
    public void canCancelReservation(
        FacesContext context,
        UIComponent component,
        Object value
    ) throws ValidatorException, SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        if (login.getType() == Login.CUSTOMER) {
            reservationId = Integer.parseInt(value.toString());
            PreparedStatement ps = con.prepareStatement(
                "select * from reservation " +
                "where reservation_id = " + reservationId
            );

            ResultSet result = ps.executeQuery();
            customerId = -1;
            if (result.next()) {
                customerId = result.getInt("customer_id");
            }
            
            ps.close();
            con.close();

            if (login.getUserId() != customerId) {
                FacesMessage errorMessage = new FacesMessage(
                    "Reservation does not exist or is not your own reservation."
                );
                throw new ValidatorException(errorMessage);
            }
        }
    } 
    
    public Reservation getNextToCheckIn(String customerName) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * " +
            "from reservation " +
            "where customer_id = " +
            "(" +
                "select customer_id " +
                "from customer " +
                "where name = '" + customerName + "'" +
            ") and checked_in is null " +
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
        else {
            return null;
        }
        return this;
    }
    
    public Reservation getByName(String customerName) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * " +
            "from reservation " +
            "where customer_id = " +
            "(" +
                "select customer_id " +
                "from customer " +
                "where name = '" + customerName + "'" +
            ") and checked_in is not null and checked_out is null " +
            "order by start_date " +
            "limit 1"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            Reservation r = new Reservation();
            r.reservationId = result.getInt("reservation_id");
            r.roomId = result.getInt("room_num");
            r.customerId = result.getInt("customer_id");
            r.startDate = result.getDate("start_date");
            r.endDate = result.getDate("end_date");
            r.total = result.getDouble("total");
            r.checkedIn = result.getDate("checked_in");
            r.checkedOut = result.getDate("checked_out");
            
            return r;
        }
        else {
            return null;
        }
    }
        
    public List<Reservation> getMyReservationList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from reservation " +
            "where customer_id = " + login.getUserId() + " order by start_date"
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

    public String goToCheckIn() {
        return "checkInCustomer";
    }

    public boolean isCheckedIn(Integer rID) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement(
            "select reservation_id " +
            "from reservation " +
            "where (reservation_id = " + rID +
                ") and checked_in is not null"
        );
        
        ResultSet result = ps.executeQuery();
        if (result.next()) {
            ps.close();
            con.close();
            return true;    
        }
        ps.close();
        con.close();
        return false;
    }
    
    public Integer checkIn(Integer rID, Date checkInDate) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement(
            "update reservation " +
            "set checked_in = ? " +
            "where reservation_id = " + rID
        );
        ps.setDate(1, new java.sql.Date(checkInDate.getTime()));
        ps.executeUpdate();

        ps.close();
        con.close();
        
        return roomId;
    }

    public String goToCheckOut() {
        return "checkOutCustomer";
    }
    
    public Double checkOut(Integer rID, Date checkOutDate) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "update reservation " +
            "set checked_out = ? " +
            "where reservation_id = " + rID
        );
        ps.setDate(1, new java.sql.Date(checkOutDate.getTime()));
        ps.executeUpdate();
        
        con.close();
        
        return total;
    }    

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
    
    public String goToViewReservation() {
        return "view";
    }
	
    public Reservation viewReservation() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * " +
            "from reservation " +
            "where reservation_id = " + reservationId +
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
    
    public void reservationIDExists(
        FacesContext context,
        UIComponent component,
        Object value
    ) throws ValidatorException, SQLException  {
        Connection con = dbConnect.getConnection();
        
        int id = Integer.parseInt(value.toString());
        
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from reservation where reservation_id = " + id
        );

        ResultSet result = ps.executeQuery();
        
        con.close();
        
        if (!result.next()) {
            FacesMessage errorMessage = new FacesMessage("Reservation does not exist.");
            throw new ValidatorException(errorMessage);
        }
    }
}
