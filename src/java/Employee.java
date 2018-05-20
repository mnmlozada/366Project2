
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

@Named(value = "employee")
@SessionScoped
@ManagedBean
public class Employee implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    private Integer employeeID;
    private String name;
    private String username;
    private String password;
    
    // Variables to handling password change
    private String curPass;
    private String newPass1;
    private String newPass2;
    private UIInput newPass1UI;

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

    public Integer getEmployeeID() throws SQLException {
        if (employeeID == null) {
            Connection con = dbConnect.getConnection();

            if (con == null) {
                throw new SQLException("Can't get database connection");
            }

            PreparedStatement ps
                    = con.prepareStatement(
                            "select max(employee_id)+1 from employee");
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            employeeID = result.getInt(1);
            result.close();
            con.close();
        }
        return employeeID;
    }

    public void setEmployeeID(Integer employeeID) {
        this.employeeID = employeeID;
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

    public String createEmployee() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        PreparedStatement preparedStatement = con.prepareStatement("Insert into employee (name, username, password) values(?,?,?)");
        //preparedStatement.setInt(1, employeeID);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, username);
        preparedStatement.setString(3, password);
        preparedStatement.executeUpdate();
        statement.close();
        con.commit();
        con.close();
        //Util.invalidateUserSession();
        return "main";
    }

    public String deleteEmployee() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate("Delete from employee where employee_id = " + employeeID);
        statement.close();
        con.commit();
        con.close();
        Util.invalidateUserSession();
        return "main";
    }

    public String showEmployee() {
        return "showEmployee";
    }
    
    // Admin Access Only
    public Employee AdminGetEmployee() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from employee where employee_id = " + employeeID);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        result.next();
        
        employeeID = result.getInt("employeeID");
        name = result.getString("name");
        username = result.getString("username");
        password = result.getString("password");
        return this;
    }
    
    // Employee Access Only
    public Employee getEmployee() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select * from employee where employee_id = " + employeeID);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        result.next();
        
        employeeID = result.getInt("employeeID");
        name = result.getString("name");
        return this;
    }
    
    // Admin Access Only
    public List<Employee> adminGetEmployeeList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select employee_id, name, username, password from employee order by employee_id");

        //get employee data from database
        ResultSet result = ps.executeQuery();

        List<Employee> list = new ArrayList<Employee>();

        while (result.next()) {
            Employee employee = new Employee();

            employee.setEmployeeID(result.getInt("employee_id"));
            employee.setName(result.getString("name"));
            employee.setUsername(result.getString("username"));
            employee.setPassword(result.getString("password"));

            //store all data into a List
            list.add(employee);
        }
        result.close();
        con.close();
        return list;
    }
    
    // Employee Access Only
    public List<Employee> getEmployeeList() throws SQLException {

        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps
                = con.prepareStatement(
                        "select employee_id, name, username, password from employee order by employee_id");

        //get employee data from database
        ResultSet result = ps.executeQuery();

        List<Employee> list = new ArrayList<Employee>();

        while (result.next()) {
            Employee employee = new Employee();

            employee.setEmployeeID(result.getInt("employee_id"));
            employee.setName(result.getString("name"));

            //store all data into a List
            list.add(employee);
        }
        result.close();
        con.close();
        return list;
    }

    public void employeeIDExists(FacesContext context, UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {

        if (!existsEmployeeID((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID does not exist");
            throw new ValidatorException(errorMessage);
        }
    }

    public void validateEmployeeID(FacesContext context, UIComponent componentToValidate, Object value)
            throws ValidatorException, SQLException {
        int id = (Integer) value;
        if (id < 0) {
            FacesMessage errorMessage = new FacesMessage("ID must be positive");
            throw new ValidatorException(errorMessage);
        }
        if (existsEmployeeID((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID already exists");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean existsEmployeeID(int id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement("select * from employee where employee_id = " + id);

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

        PreparedStatement ps = con.prepareStatement("update employee set password = '" + newPass1 + "' where employee_id = " + login.getUserId());
        ps.executeUpdate();
        
        login.setPassword(newPass1);
        
        if (login.getType() == Login.ADMIN) {
            return "successAdmin";
        }
        return "success";
    }
}
