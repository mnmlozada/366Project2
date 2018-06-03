
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

@javax.faces.bean.ManagedBean(name="room")
@SessionScoped
public class Room implements Serializable {

    private DBConnect dbConnect = new DBConnect();
    private Integer roomID;
    private Integer roomNumber;
    private String department;
    private String wing;
    private Double price;
    private Boolean occupied;
    private Patient patient;

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }
    
    public Room getRoom() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps =
            con.prepareStatement("select * from rooms where room_number = " + roomNumber);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        result.next();

        roomNumber = result.getInt("room_number");
        department = result.getString("department");
        wing = result.getString("wing");
        price = result.getDouble("price");
        return this;
    }
    
    public void roomExists(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        roomNumber = Integer.parseInt(value.toString());
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps =
            con.prepareStatement("select * from rooms where room_number = " + roomNumber);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        if (!result.next()) {
             FacesMessage errorMessage = new FacesMessage("Room does not exist.");
                throw new ValidatorException(errorMessage);
        }
    }
    
     public List<Room> getRoomList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from rooms left join ((select room_num, patient_id from reservation where checked_out = null) "
                                + "as X join patient on X.patient_id = patient.patient_id) on X.room_num = room_number order by department, room_number");

        ResultSet result = ps.executeQuery();

        List<Room> list = new ArrayList<Room>();

        while (result.next()) {
            Room room = new Room();

            room.setRoomNumber(result.getInt("room_number"));
            room.setWing(result.getString("wing"));
            room.setDepartment(result.getString("department"));
            room.setPrice(result.getDouble("price"));
            
            room.setOccupied(result.getString("name") != null);
            
            int pid = result.getInt("patient_id");
            if (room.occupied) {
                room.setPatient(new Patient().getByID(pid));
            }
            //store all data into a List
            list.add(room);
        }
        result.close();
        con.close();
        return list;
    }
     
     public List<String> getDepartments() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select distinct department from rooms order by department");

        ResultSet result = ps.executeQuery();

        List<String> list = new ArrayList<String>();

        while (result.next()) {
            list.add(result.getString("department"));
        }
        result.close();
        con.close();
        return list;
    }
     
      public List<String> getWings() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select distinct wing from rooms order by wing");

        ResultSet result = ps.executeQuery();

        List<String> list = new ArrayList<String>();

        while (result.next()) {
            list.add(result.getString("wing"));
        }
        result.close();
        con.close();
        return list;
    }
     
    public String setRate() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
       
        PreparedStatement preparedStatement =
            con.prepareStatement("update rooms set price = round(cast(? as numeric), 2) where room_number = ?");

        preparedStatement.setDouble(1, price);
        preparedStatement.setInt(2, roomNumber);
        
        preparedStatement.executeUpdate();
        
        con.commit();
        
        return "/listPrices.xhtml?faces-redirect=true";
    }
    
    public String setDepartmentRate() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
       
        PreparedStatement preparedStatement =
            con.prepareStatement("update rooms set price = round(cast(? as numeric), 2) where department = ?");

        preparedStatement.setDouble(1, price);
        preparedStatement.setString(2, department);
        
        preparedStatement.executeUpdate();
        
        con.commit();
        
        return "/listPrices.xhtml?faces-redirect=true";
    }
    
    public String setAllRates() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
       
        PreparedStatement preparedStatement =
            con.prepareStatement("update rooms set price = round(cast(? as numeric), 2)");

        preparedStatement.setDouble(1, price);
        
        preparedStatement.executeUpdate();
        
        con.commit();
        
        return "/listPrices.xhtml?faces-redirect=true";
    }
}
