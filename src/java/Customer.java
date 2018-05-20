import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.ParseException;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;

@Named(value = "customer")
@SessionScoped
@ManagedBean
public class Customer implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private final DBConnect dbConnect = new DBConnect();
    private Integer customerID;
    private String name;
    private String address;
    private Date created_date;
    private String username;
    private String password;
    private String email;
    private String cc_num;
    private Date cc_exp;
    private Integer cc_crc;

    public Integer getCustomerID() throws SQLException {
        if (customerID == null) {
            Connection con = dbConnect.getConnection();
            if (con == null) {
                throw new SQLException("Can't get database connection");
            }

            PreparedStatement ps = con.prepareStatement(
                "select max(customer_id) + 1 from customer"
            );
            
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            customerID = result.getInt(1);
            
            result.close();
            con.close();
        }
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public void validateCustomerID(
        FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        int id = (Integer) value;
        if (id < 0) {
            FacesMessage errorMessage = new FacesMessage("ID must be positive");
            throw new ValidatorException(errorMessage);
        }
        if (customerIDExists((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID already exists");
            throw new ValidatorException(errorMessage);
        }
    }
    
    public void customerIDExists(FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        
        customerID = Integer.parseInt(value.toString());
        
        if (!customerIDExists(customerID)) {
            FacesMessage errorMessage = new FacesMessage("Customer ID does not exist.");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean customerIDExists(int id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from customer where customer_id = " + id
        );

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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void validateCustomerName(
        FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        
        if (!customerNameExists(value.toString())) {
            FacesMessage errorMessage = new FacesMessage("Name does not exist");
            throw new ValidatorException(errorMessage);
        }
    }
    
    private boolean customerNameExists(String name) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from customer where name = '" + name + "'"
        );

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
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        this.created_date = created_date;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCc_num() {
        return cc_num;
    }

    public void setCc_num(String cc_num) {
        this.cc_num = cc_num;
    }

    public Date getCc_exp() {
        return cc_exp;
    }

    public void setCc_exp(Date cc_exp) {
        this.cc_exp = cc_exp;
    }

    public Integer getCc_crc() {
        return cc_crc;
    }

    public void setCc_crc(Integer cc_crc) {
        this.cc_crc = cc_crc;
    }

    public String createCustomer() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        if (customerID == null) {
            customerID = getCustomerID();
        }
        PreparedStatement ps = con.prepareStatement(
            "Insert into Customer (name, address, created_date, username, " +
                "password, email, cc_num, cc_ex, cc_crc) " +
                "values(?,?,current_date,?,?,?,?,?,?)");
        //preparedStatement.setInt(1, customerID);
        ps.setString(1, name);
        ps.setString(2, address);
        //preparedStatement.setDate(4, new java.sql.Date());
        ps.setString(3, username);
        ps.setString(4, password);
        ps.setString(5, email);
        ps.setString(6, cc_num);
        ps.setDate(7, new java.sql.Date(cc_exp.getTime()));
        ps.setInt(8, cc_crc);
        ps.executeUpdate();
        
        ps.close();
        con.commit();
        con.close();
        //Util.invalidateUserSession();
        return "main";
    }

    public String deleteCustomer() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate(
            "Delete from Customer where customer_id = " + customerID
        );
        statement.close();
        
        con.commit();
        con.close();
        
        return "main";
    }

    public String showCustomer() {
        return "showCustomer";
    }
    
    public Customer getByID(Integer cID) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from customer where customer_id = " + cID
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            customerID = result.getInt("customer_id");
            name = result.getString("name");
            address = result.getString("address");
            created_date = result.getDate("created_date");
            username = result.getString("username");
            password = result.getString("password");
            email = result.getString("email");
            cc_num = result.getString("cc_num");
            cc_exp = result.getDate("cc_ex");
            cc_crc = result.getInt("cc_crc");
        }
        return this;
    }
    
    public Customer getByName(String cName) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from customer where name = '" + cName + "'"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            customerID = result.getInt("customer_id");
            name = result.getString("name");
            address = result.getString("address");
            created_date = result.getDate("created_date");
            username = result.getString("username");
            password = result.getString("password");
            email = result.getString("email");
            cc_num = result.getString("cc_num");
            cc_exp = result.getDate("cc_ex");
            cc_crc = result.getInt("cc_crc");
        }
        return this;
    }
        
    public List<Customer> getCustomerList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select customer_id, name, address, created_date " +
            "from customer " +
            "order by customer_id"
        );

        //get customer data from database
        ResultSet result = ps.executeQuery();
        List<Customer> list = new ArrayList<>();

        while (result.next()) {
            Customer cust = new Customer();

            cust.setCustomerID(result.getInt("customer_id"));
            cust.setName(result.getString("name"));
            cust.setAddress(result.getString("address"));
            cust.setCreated_date(result.getDate("created_date"));
//            cust.setUsername(result.getString("username"));
//            cust.setPassword(result.getString("password"));
//            cust.setEmail(result.getString("email"));
//            cust.setCc_num(result.getString("cc_num"));
//            cust.setCc_exp(result.getDate("cc_exp"));
//            cust.setCc_crc(result.getInt("cc_crc"));

            //store all data into a List
            list.add(cust);
        }
        result.close();
        con.close();
        return list;
    }

    public String go() {
        return "newUser";
    }
}
