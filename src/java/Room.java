
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.util.Date;
import java.util.TimeZone;
import javax.el.ELContext;
import javax.faces.bean.ManagedProperty;

@Named(value = "room")
@SessionScoped
@ManagedBean
public class Room implements Serializable {

    private DBConnect dbConnect = new DBConnect();
    private Integer roomID;
    private Integer roomNumber;
    private String windowType;
    private String bed;
    
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

    public String getWindowType() {
        return windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
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
        windowType = result.getString("window_type");
        bed = result.getString("bed");
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
}
