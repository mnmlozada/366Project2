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

@Named(value = "patient")
@SessionScoped
@ManagedBean
public class Patient implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private final DBConnect dbConnect = new DBConnect();
    private Integer patientID;
    private String name;
    private String address;
    private Date created_date;
    private String username;
    private String password;
    private String email;
    private String cc_num;
    private Date cc_exp;
    private Integer cc_crc;

    public Integer getPatientID() throws SQLException {
        if (patientID == null) {
            Connection con = dbConnect.getConnection();
            if (con == null) {
                throw new SQLException("Can't get database connection");
            }

            PreparedStatement ps = con.prepareStatement(
                "select max(patient_id) + 1 from customer"
            );
            
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            patientID = result.getInt(1);
            
            result.close();
            con.close();
        }
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public void validatePatientID(
        FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        int id = (Integer) value;
        if (id < 0) {
            FacesMessage errorMessage = new FacesMessage("ID must be positive");
            throw new ValidatorException(errorMessage);
        }
        if (patientIDExists((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID already exists");
            throw new ValidatorException(errorMessage);
        }
    }
    
    public void patientIDExists(FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        
        patientID = Integer.parseInt(value.toString());
        
        if (!patientIDExists(patientID)) {
            FacesMessage errorMessage = new FacesMessage("Patient ID does not exist.");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean patientIDExists(int id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from patient where patient_id = " + id
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

    public void validatePatientName(
        FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        
        if (!patientNameExists(value.toString())) {
            FacesMessage errorMessage = new FacesMessage("Name does not exist");
            throw new ValidatorException(errorMessage);
        }
    }
    
    private boolean patientNameExists(String name) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from patient where name = '" + name + "'"
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

    public String createPatient() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        if (patientID == null) {
            patientID = getPatientID();
        }
        PreparedStatement ps = con.prepareStatement(
            "Insert into Patient (name, address, created_date, username, " +
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

    public String deletePatient() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate(
            "Delete from Patient where patient_id = " + patientID
        );
        statement.close();
        
        con.commit();
        con.close();
        
        return "main";
    }

    public String showPatient() {
        return "showPatient";
    }
    
    public Patient getByID(Integer cID) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from patient where patient_id = " + cID
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            patientID = result.getInt("patient_id");
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
    
    public Patient getByName(String cName) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from patient where name = '" + cName + "'"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            patientID = result.getInt("patient_id");
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
        
    public List<Patient> getPatientList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select patient_id, name, address, created_date " +
            "from patient " +
            "order by patient_id"
        );

        //get patient data from database
        ResultSet result = ps.executeQuery();
        List<Patient> list = new ArrayList<>();

        while (result.next()) {
            Patient pat = new Patient();

            pat.setPatientID(result.getInt("patient_id"));
            pat.setName(result.getString("name"));
            pat.setAddress(result.getString("address"));
            pat.setCreated_date(result.getDate("created_date"));

            //store all data into a List
            list.add(pat);
        }
        result.close();
        con.close();
        return list;
    }

    public String go() {
        return "newUser";
    }
}
