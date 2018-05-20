
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
import javax.faces.component.UIInput;

@Named(value = "rates")
@SessionScoped
@ManagedBean
public class Rates implements Serializable {
    
    private static final long ONE_DAY_MILLISECONDS = 86400000;

    private DBConnect dbConnect = new DBConnect();
    private Integer roomNum;
    private Date date;
    private Double rate;
    private Date endDate;
    private UIInput endDateUI;
    private UIInput startDateUI;

    public UIInput getEndDateUI() {
        return endDateUI;
    }

    public void setEndDateUI(UIInput endDateUI) {
        this.endDateUI = endDateUI;
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

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
    
    public List<Rates> getRatesList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select room_number, coalesce((select rate from rate where room_number = room_num and date = ?), 100.00) as actual_rate from rooms order by room_number");
        ps.setDate(1, new java.sql.Date(date.getTime()));

        ResultSet result = ps.executeQuery();

        List<Rates> list = new ArrayList<Rates>();

        while (result.next()) {
            Rates rate = new Rates();

            rate.setRoomNum(result.getInt("room_number"));
            rate.setDate(new java.sql.Date(date.getTime()));
            rate.setRate(result.getDouble("actual_rate"));
            //store all data into a List
            list.add(rate);
        }
        result.close();
        con.close();
        return list;
    }
    
    public Rates getRates() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps =
            con.prepareStatement("select * from rates where room_num = " + roomNum
            + " and date =  " + date);

        //get customer data from database
        ResultSet result = ps.executeQuery();
        result.next();

        roomNum = result.getInt("room_num");
        date = result.getDate("date");
        rate = result.getDouble("rate");
        return this;
    }

    public String setRates() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
       
        PreparedStatement preparedStatement =
            con.prepareStatement("delete from rate where room_num = ? and date between ? and ?");

        preparedStatement.setInt(1, roomNum);
        preparedStatement.setDate(2, new java.sql.Date(date.getTime()));
        preparedStatement.setDate(3, new java.sql.Date(endDate.getTime()));
        
        preparedStatement.executeUpdate();
        
        Date temp = new java.sql.Date(date.getTime());
        
        while (temp.getTime() < endDate.getTime()) {
                 preparedStatement =
                    con.prepareStatement("Insert into rate values(?,?,?)");
                preparedStatement.setInt(1, roomNum);
                preparedStatement.setDate(2, new java.sql.Date(temp.getTime()));
                preparedStatement.setDouble(3, rate);

                preparedStatement.executeUpdate();
                temp.setTime(temp.getTime() + ONE_DAY_MILLISECONDS);
                preparedStatement.close();
        }
        
        con.commit();
        
        return "main";
    }
    
    public String setRatesAll() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
       
        PreparedStatement preparedStatement =
            con.prepareStatement("delete from rate where date between ? and ?");

        preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
        preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
        
        preparedStatement.executeUpdate();
        
        Date temp = new java.sql.Date(date.getTime());
        
        PreparedStatement ps =
            con.prepareStatement("select * from rooms");

        //get customer data from database
        ResultSet result = ps.executeQuery();

        while (result.next()) {
            int roomNumber = result.getInt("room_number");
            temp = new java.sql.Date(date.getTime());

            while (temp.getTime() < endDate.getTime()) {
                preparedStatement =
                   con.prepareStatement("Insert into rate values(?,?,?)");
                preparedStatement.setInt(1, roomNumber);
                preparedStatement.setDate(2, new java.sql.Date(temp.getTime()));
                preparedStatement.setDouble(3, rate);

                preparedStatement.executeUpdate();
                temp.setTime(temp.getTime() + ONE_DAY_MILLISECONDS);
                preparedStatement.close();
            }
        
        }
        
        con.commit();
        
        return "main";
    }
    
    public void validateNewRate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        date = new Date(startDateUI.getLocalValue().toString());
        endDate = new Date(endDateUI.getLocalValue().toString());
        rate = Double.parseDouble(value.toString());
        
      
        if (endDate.getTime() <= date.getTime() || rate <= 0) {
            FacesMessage errorMessage = new FacesMessage("New rate is invalid.");
            throw new ValidatorException(errorMessage);
        }
                        
    }
    
    public String searchRates () {
        return "search";
    }
}
