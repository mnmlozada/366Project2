import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Faces;

@javax.faces.bean.ManagedBean(name="reservation")
@SessionScoped
public class Reservation implements Serializable {
    
    private static final long ONE_DAY_MILLISECONDS = 86400000;
    private static final double DEFAULT_RATE = 100.0;
    
    @ManagedProperty(value = "#{logins}")
    private Login logins;

    public Login getLogins() {
        return logins;
    }

    public void setLogins(Login logins) {
        this.logins = logins;
    }
    
    private final DBConnect dbConnect = new DBConnect();
    private Integer reservationId;
    private Integer roomId;
    private Integer patientId;
    
    private Double total;
    
    private Date checkedIn;
    private Date checkedOut;

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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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
    
    public String view() {
        Faces.setSessionAttribute("reservation", this);
        return "/viewReservation.xhtml?faces-redirect=true";
    }
    
    public String viewMy() {
        Faces.setSessionAttribute("reservation", this);
        return "/viewMyReservation.xhtml?faces-redirect=true";
    }
    
    public String viewPatient() throws SQLException {
        Faces.setSessionAttribute("patient", (new Patient()).getByID(patientId));
        return "/viewPatient.xhtml?faces-redirect=true";
    }
    
    public String getPatientName() throws SQLException {
        Patient p = new Patient();
        p.getByID(patientId);
        return p.getName();
    }
    
    public String create() throws SQLException, ParseException {
        try {
            Connection con = dbConnect.getConnection();
            if (con == null) {
                throw new SQLException("Can't get database connection");
            }
            con.setAutoCommit(false);
            
            PreparedStatement ps;
            ps = con.prepareStatement(
                "Insert into reservation (room_num, patient_id, checked_in) values(?,?,current_date)"
            );
            
            //ps.setInt(1, reservationId);
            ps.setInt(1, roomId);
            ps.setInt(2, patientId);
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
        
        if (logins.getType() == Login.CUSTOMER) {
            reservationId = Integer.parseInt(value.toString());
            PreparedStatement ps = con.prepareStatement(
                "select * from reservation " +
                "where reservation_id = " + reservationId
            );

            ResultSet result = ps.executeQuery();
            patientId = -1;
            if (result.next()) {
                patientId = result.getInt("patient_id");
            }
            
            ps.close();
            con.close();

            if (logins.getUserId() != patientId) {
                FacesMessage errorMessage = new FacesMessage(
                    "Reservation does not exist or is not your own reservation."
                );
                throw new ValidatorException(errorMessage);
            }
        }
    } 
    
    public Reservation getByName(String patientName) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * " +
            "from reservation " +
            "where patient_id = " +
            "(" +
                "select patient_id " +
                "from patient " +
                "where name = '" + patientName + "'" +
            ") and checked_in is not null and checked_out is null " +
            "order by start_date " +
            "limit 1"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            Reservation r = new Reservation();
            r.reservationId = result.getInt("reservation_id");
            r.roomId = result.getInt("room_num");
            r.patientId = result.getInt("patient_id");
            r.total = result.getDouble("total");
            r.checkedIn = result.getDate("checked_in");
            r.checkedOut = result.getDate("checked_out");
            
            return r;
        }
        else {
            return null;
        }
    }
    
    public Reservation getByPatientID(int patientID) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from reservation " +
            "where patient_id = " + patientID + " and checked_out is null" +
            " and checked_in is not null " +
            "limit 1"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            Reservation r = new Reservation();
            r.reservationId = result.getInt("reservation_id");
            r.roomId = result.getInt("room_num");
            r.patientId = result.getInt("patient_id");
            r.total = result.getDouble("total");
            r.checkedIn = result.getDate("checked_in");
            r.checkedOut = result.getDate("checked_out");
            
            return r;
        }
        else {
            return null;
        }
    }
    
    public List<Reservation> getAllByPatientID(int patientID) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from reservation " +
            "where patient_id = " + patientID +
            " order by checked_in"
        );

        ResultSet result = ps.executeQuery();
        List<Reservation> list = new ArrayList<>();
        while (result.next()) {
            Reservation r = new Reservation();
            r.reservationId = result.getInt("reservation_id");
            r.roomId = result.getInt("room_num");
            r.patientId = result.getInt("patient_id");
            r.total = result.getDouble("total");
            r.checkedIn = result.getDate("checked_in");
            r.checkedOut = result.getDate("checked_out");
            
            list.add(r);
        }
        return list;
    }
        
    public List<Reservation> getMyReservationList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from reservation " +
            "where patient_id = " + logins.getUserId() + " order by start_date"
        );

        //get employee data from database
        ResultSet result = ps.executeQuery();
        List<Reservation> list = new ArrayList<>();

        while (result.next()) {
            Reservation r = new Reservation();

            r.setReservationId(result.getInt("reservation_id"));
            r.setRoomId(result.getInt("room_num"));
            r.setPatientId(result.getInt("patient_id"));
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
        return "checkInPatient";
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
        return "checkOutPatient";
    }
    
    public Double checkOut() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        int nights = (int)( ((new Date()).getTime() - checkedIn.getTime()) / (1000 * 60 * 60 * 24));
        
        Room room = new Room();
        room.setRoomNumber(roomId);
        room = room.getRoom();
        
        Transaction roomT = new Transaction();
        roomT.setPatient_id(patientId);
        roomT.setReservation_id(reservationId);
        roomT.setTran_date(new Date());
        roomT.setCharge_type("room");
        roomT.setCharge_description(nights + " night(s) in " + room.getDepartment() + " department room " + roomId);
        roomT.setTotal(nights * room.getPrice());
        roomT.insert();

        PreparedStatement ps = con.prepareStatement(
            "select * from transactions where reservation_id = " + reservationId + " order by transaction_id"
        );

        //get employee data from database
        ResultSet result = ps.executeQuery();
        
        total = 0.0;

        while (result.next()) {;
            total += result.getDouble("total");
        }
        
        result.close();

        ps = con.prepareStatement(
            "update reservation " +
            "set checked_out = current_date, total =  " + total +
            "where reservation_id = " + reservationId
        );
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
            r.setPatientId(result.getInt("patient_id"));
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
            patientId = result.getInt("patient_id");
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
