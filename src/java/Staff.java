
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
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIInput;

@Named(value = "staff")
@SessionScoped
@ManagedBean
public class Staff implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    private Integer staffID;
    private String position;
    private String name;
    private String username;
    private String password;
    
    // Variables to handling password change
    private String curPass;
    private String newPass1;
    private String newPass2;
    private UIInput newPass1UI;
    
    private List filteredStaff;

    public List getFilteredStaff() {
        return filteredStaff;
    }

    public void setFilteredStaff(List filteredStaff) {
        this.filteredStaff = filteredStaff;
    }

    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public UIInput getNewPass1UI() {
        return newPass1UI;
    }

    public void setNewPass1UI(UIInput newPass1UI) {
        this.newPass1UI = newPass1UI;
    }

    public String getCurPass() {
        return curPass;
    }

    public void setCurPass(String curPass) {
        this.curPass = curPass;
    }

    public String getNewPass1() {
        return newPass1;
    }

    public void setNewPass1(String newPass1) {
        this.newPass1 = newPass1;
    }

    public String getNewPass2() {
        return newPass2;
    }

    public void setNewPass2(String newPass2) {
        this.newPass2 = newPass2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStaffID() throws SQLException {
        if (staffID == null) {
            Connection con = dbConnect.getConnection();

            if (con == null) {
                throw new SQLException("Can't get database connection");
            }

            PreparedStatement ps
                    = con.prepareStatement(
                            "select max(staff_id)+1 from staff");
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            staffID = result.getInt(1);
            result.close();
            con.close();
        }
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public String getName() {
        // ELContext elContext = FacesContext.getCurrentInstance().getELContext();
    //Login login = (Login) elContext.getELResolver().getValue(elContext, null, "login");
    
      //  return login.getLogin();
           return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String createStaff() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        PreparedStatement preparedStatement = con.prepareStatement("Insert into staff (name, position, username, password) values(?,?,?, ?)");
        //preparedStatement.setInt(1, staffID);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, position);
        preparedStatement.setString(3, username);
        preparedStatement.setString(4, password);
        preparedStatement.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        //Util.invalidateUserSession();
        return "main";
    }

    public void deleteStaff() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate("Delete from staff where staff_id = " + staffID);
        statement.close();
        con.commit();
        con.close();
        //Util.invalidateUserSession();
        //return "main";
    }

    public String showStaff() {
        return "showStaff";
    }
    
    // Admin Access Only
    public Staff AdminGetStaff() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from staff where staff_id = " + staffID);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        result.next();
        
        staffID = result.getInt("staffID");
        position = result.getString("position");
        name = result.getString("name");
        username = result.getString("username");
        password = result.getString("password");
        return this;
    }
    
    // Employee Access Only
    public Staff getStaff() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from staff where staff_id = " + staffID);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        result.next();
        
        staffID = result.getInt("staffID");
        name = result.getString("name");
        position = result.getString("position");
        return this;
    }
    
    // Admin Access Only
    public List<Staff> adminGetStaffList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select staff_id, name, position, username, password from staff order by staff_id");

        //get employee data from database
        ResultSet result = ps.executeQuery();

        List<Staff> list = new ArrayList<Staff>();

        while (result.next()) {
            Staff staff = new Staff();

            staff.setStaffID(result.getInt("staff_id"));
            staff.setName(result.getString("name"));
            staff.setPosition(result.getString("position"));
            staff.setUsername(result.getString("username"));
            staff.setPassword(result.getString("password"));

            //store all data into a List
            list.add(staff);
        }
        result.close();
        con.close();
        return list;
    }
    
    // Employee Access Only
    public List<Staff> getStaffList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select staff_id, name, position, username, password from staff order by staff_id");

        //get employee data from database
        ResultSet result = ps.executeQuery();

        List<Staff> list = new ArrayList<Staff>();

        while (result.next()) {
            Staff staff = new Staff();

            staff.setStaffID(result.getInt("staff_id"));
            staff.setName(result.getString("name"));
            staff.setPosition(result.getString("position"));

            //store all data into a List
            list.add(staff);
        }
        result.close();
        con.close();
        return list;
    }

    public void StaffIDExists(FacesContext context, UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {

        if (!existsStaffID((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID does not exist");
            throw new ValidatorException(errorMessage);
        }
    }

    public void validateStaffID(FacesContext context, UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {
        int id = (Integer) value;
        if (id < 0) {
            FacesMessage errorMessage = new FacesMessage("ID must be positive");
            throw new ValidatorException(errorMessage);
        }
        if (existsStaffID((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID already exists");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean existsStaffID(int id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement("select * from staff where staff_id = " + id);

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            result.close();
            con.close();
            return true;
        }
        result.close();
        con.close();
        return false;
    }
    
    public void validateNewPass(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        newPass1 = newPass1UI.getLocalValue().toString();
        newPass2 = value.toString();
        if (!newPass1.equals(newPass2)) {
            FacesMessage errorMessage = new FacesMessage("New passwords must match.");
            throw new ValidatorException(errorMessage);
        }
        
    }
    
    public void validateCurPass(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException { 
        curPass = value.toString();
        if (!curPass.equals(login.getPassword())) {
            FacesMessage errorMessage = new FacesMessage("Current password is not correct.");
            throw new ValidatorException(errorMessage);
        }
        
    }
    
    public String changePassword() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement("update staff set password = '" + newPass1 + "' where staff_id = " + login.getUserId());
        ps.executeUpdate();
        
        login.setPassword(newPass1);
        
        if (login.getType() == Login.ADMIN) {
            return "successAdmin";
        }
        return "success";
    }
}
