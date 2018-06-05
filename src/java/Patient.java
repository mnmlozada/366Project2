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
import javax.faces.component.UIInput;
import org.omnifaces.util.Faces;

@javax.faces.bean.ManagedBean(name="patient")
@SessionScoped
public class Patient implements Serializable {

    @ManagedProperty(value = "#{logins}")
    private Login logins;

    public Login getLogins() {
        return logins;
    }

    public void setLogins(Login logins) {
        this.logins = logins;
    }

    private final DBConnect dbConnect = new DBConnect();
    private Integer patientID;
    private String name;
    private String address;
    private Date created_date;
    private String username;
    private String password;
    private Room room;
    private Date dob;
    private HealthInfo hi;
    
    // Variables to handling password change
    private String curPass;
    private String newPass1;
    private String newPass2;
    private UIInput newPass1UI;
    
    public HealthInfo getHi() {
        return hi;
    }

    public void setHi(HealthInfo hi) {
        this.hi = hi;
    }
    
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public UIInput getNewPass1UI() {
        return newPass1UI;
    }

    public void setNewPass1UI(UIInput newPass1UI) {
        this.newPass1UI = newPass1UI;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getPatientID() {
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
    
    public void validatePatientUsername(
        FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        
        if (patientUsernameExists(value.toString())) {
            FacesMessage errorMessage = new FacesMessage("Username already in use");
            throw new ValidatorException(errorMessage);
        }
    }
    
    private boolean patientUsernameExists(String username) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from patient where username = '" + username + "'"
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

    public String createPatient() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        PreparedStatement ps = con.prepareStatement(
            "Insert into Patient (name, address, created_date, username, " +
                "password, dob) " +
                "values(?,?,current_date,?,?,?)");
        //preparedStatement.setInt(1, customerID);
        ps.setString(1, name);
        ps.setString(2, address);
        //preparedStatement.setDate(4, new java.sql.Date());
        ps.setString(3, username);
        ps.setString(4, password);
        ps.setDate(5, new java.sql.Date(dob.getTime()));
        ps.executeUpdate();
        
        ps.close();
        
        ps = con.prepareStatement(
            "select * from patient where username = '" + username + "'"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            patientID = result.getInt("patient_id");
        }
        
        con.commit();
        con.close();
        //Util.invalidateUserSession();
        Faces.setSessionAttribute("patient", this);
        return "/newHealthInfo.xhtml?faces-redirect=true";
    }
    
    public String release() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Reservation res = new Reservation().getByPatientID(patientID);
        res.checkOut();
        Faces.setSessionAttribute("reservation", res);
        //Util.invalidateUserSession();
        return "/releaseConfirmation.xhtml?faces-redirect=true";
    }
    
    public String updateInfo(HealthInfo hi) throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
       
        PreparedStatement ps = con.prepareStatement(
            "Insert into healthInfo (patient_id, exam_date, gender, height_inch, weight, allergies, conditions, medicine, " +
                "insurance) " +
                "values(?,current_date,?,?,?,?,?,?,?)");
        //preparedStatement.setInt(1, customerID);
        System.out.println(patientID);
        ps.setInt(1, patientID);
        ps.setString(2, hi.getGender());
        //preparedStatement.setDate(4, new java.sql.Date());
        ps.setInt(3, hi.getHeight());
        ps.setInt(4, hi.getWeight());
        ps.setString(5, hi.getAllergies());
        ps.setString(6, hi.getConditions());
        ps.setString(7, hi.getMedicine());
        ps.setString(8, hi.getInsurance());
        ps.executeUpdate();
        
        ps.close();
        con.commit();
        con.close();
        //Util.invalidateUserSession();
        return "/selectRoom.xhtml?faces-redirect=true";
    }

    public void deletePatient() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        statement.executeUpdate(
            "Delete from healthInfo where patient_id = " + patientID
        );
        statement.close();
        
        statement = con.createStatement();
        statement.executeUpdate(
            "Delete from transactions where patient_id = " + patientID
        );
        statement.close();
        
        statement = con.createStatement();
        statement.executeUpdate(
            "Delete from prescription where patient_id = " + patientID
        );
        statement.close();
        
        statement = con.createStatement();
        statement.executeUpdate(
            "Delete from reservation where patient_id = " + patientID
        );
        statement.close();
        
        statement = con.createStatement();
        statement.executeUpdate(
            "Delete from patient where patient_id = " + patientID
        );
        statement.close();
        
        con.commit();
        con.close();
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
            dob = result.getDate("dob");
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
            dob = result.getDate("dob");
        }
        return this;
    }
        
    public List<Patient> getPatientList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        /*PreparedStatement ps = con.prepareStatement(
            "select patient_id, name, address, created_date " +
            "from patient " +
            "order by patient_id"
        );*/
        
        PreparedStatement ps
                = con.prepareStatement(
                        "select * from patient left join (select room_num, patient_id from reservation where checked_out is null) "
                                + "as X on X.patient_id = patient.patient_id order by room_num desc, patient.name");

        //get patient data from database
        ResultSet result = ps.executeQuery();
        List<Patient> list = new ArrayList<>();

        while (result.next()) {
            Patient pat = new Patient();

            pat.setPatientID(result.getInt("patient_id"));
            pat.setName(result.getString("name"));
            pat.setAddress(result.getString("address"));
            pat.setCreated_date(result.getDate("created_date"));
            pat.setUsername(result.getString("username"));
            pat.setDob(result.getDate("dob"));
            
            int roomNum = result.getInt("room_num");
            if (roomNum != 0) {
                Room r = new Room();
                r.setRoomNumber(roomNum);
                pat.setRoom(r.getRoom());
            }
            
            //store all data into a List
            list.add(pat);
        }
        result.close();
        con.close();
        return list;
    }
    
    public List<Patient> getOutPatientList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps
                = con.prepareStatement(
                        "select * from patient left join (select room_num, patient_id from reservation where checked_out is null) "
                                + "as X on X.patient_id = patient.patient_id order by room_num desc, patient.name");

        //get patient data from database
        ResultSet result = ps.executeQuery();
        List<Patient> list = new ArrayList<>();

        while (result.next()) {
            Patient pat = new Patient();

            pat.setPatientID(result.getInt("patient_id"));
            pat.setName(result.getString("name"));
            pat.setAddress(result.getString("address"));
            pat.setCreated_date(result.getDate("created_date"));
            pat.setUsername(result.getString("username"));
            pat.setDob(result.getDate("dob"));
            
            int roomNum = result.getInt("room_num");
            if (roomNum == 0) {
                list.add(pat);
            }
        }
        result.close();
        con.close();
        return list;
    }
    
    public List<Patient> getInPatientList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps
                = con.prepareStatement(
                        "select * from patient join (select room_num, patient_id from reservation where checked_out is null) "
                                + "as X on X.patient_id = patient.patient_id order by room_num desc, patient.name");

        //get patient data from database
        ResultSet result = ps.executeQuery();
        List<Patient> list = new ArrayList<>();

        while (result.next()) {
            Patient pat = new Patient();

            pat.setPatientID(result.getInt("patient_id"));
            pat.setName(result.getString("name"));
            pat.setAddress(result.getString("address"));
            pat.setCreated_date(result.getDate("created_date"));
            pat.setUsername(result.getString("username"));
            pat.setDob(result.getDate("dob"));
            
            int roomNum = result.getInt("room_num");
            Room r = new Room();
            r.setRoomNumber(roomNum);
            pat.setRoom(r.getRoom());
            list.add(pat);
        }
        result.close();
        con.close();
        return list;
    }

    public String go() {
        return "newUser";
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
        if (!curPass.equals(logins.getPassword())) {
            FacesMessage errorMessage = new FacesMessage("Current password is not correct.");
            throw new ValidatorException(errorMessage);
        }
        
    }
    
    public String changePassword() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement("update patient set password = '" + newPass1 + "' where patient_id = " + logins.getUserId());
        ps.executeUpdate();
        
        logins.setPassword(newPass1);
        
        if (logins.getType() == Login.ADMIN) {
            return "/index.xhtml?faces-redirect=true";
        }
        return "/index.xhtml?faces-redirect=true";
    }
}
