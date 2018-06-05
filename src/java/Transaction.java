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
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;

@javax.faces.bean.ManagedBean(name="transaction")
@SessionScoped
public class Transaction implements Serializable {

    @ManagedProperty(value = "#{logins}")
    private Login logins;

    public Login getLogins() {
        return logins;
    }

    public void setLogins(Login logins) {
        this.logins = logins;
    }

    private DBConnect dbConnect = new DBConnect();
    
    private Integer transaction_id;
    private Integer patient_id;
    private Integer reservation_id;
    private String charge_type;
    private String charge_description;
    private Date tran_date;
    private Double total;

    public Integer getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getCharge_description() {
        return charge_description;
    }

    public void setCharge_description(String charge_description) {
        this.charge_description = charge_description;
    }

    public Date getTran_date() {
        return tran_date;
    }

    public void setTran_date(Date tran_date) {
        this.tran_date = tran_date;
    }
    
    

    public Integer getTransactionID() throws SQLException {
        if (transaction_id == null) {
            Connection con = dbConnect.getConnection();
            if (con == null) {
                throw new SQLException("Can't get database connection");
            }

            PreparedStatement ps = con.prepareStatement(
                "select max(transaction_id) + 1 from transactions");
            ResultSet result = ps.executeQuery();
            if (!result.next()) {
                return null;
            }
            
            transaction_id = result.getInt(1);
            result.close();
            con.close();
        }
        return transaction_id;
    }

    public void setTransactionID(Integer transaction_id) {
        this.transaction_id = transaction_id;
    }

    public void validateTransactionID(
        FacesContext context,
        UIComponent componentToValidate,
        Object value
    ) throws ValidatorException, SQLException {
        int id = (Integer) value;
        if (id < 0) {
            FacesMessage errorMessage = new FacesMessage("ID must be positive");
            throw new ValidatorException(errorMessage);
        }
        if (transactionIdExists((Integer) value)) {
            FacesMessage errorMessage = new FacesMessage("ID already exists");
            throw new ValidatorException(errorMessage);
        }
    }

    private boolean transactionIdExists(int id) throws SQLException {
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
    
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String create(Patient c, Integer rID, Date transDate, Double total)
        throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(
            "Insert into transactions (" +
                "patient_id," +
                "reservation_id," +
                "charge_type," +
                "charge_description," +
                "tran_date, " +
                "total" +
            ") values (?, ?, ?, ?, ?, round(cast(? as numeric), 2))"
        );
        ps.setInt(1, c.getPatientID());
        ps.setInt(2, rID);
        
        ps.setString(3, charge_type);
        ps.setString(4, charge_description);
        
        ps.setDate(5, new java.sql.Date(transDate.getTime()));
        ps.setDouble(6, total);
        
        ps.executeUpdate();
        
        ps.close();
        con.commit();
        con.close();
        
        return "/viewReservation.xhtml?faces-redirect=true";
    }
    
   public String addCharge()
        throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(
            "Insert into transactions (" +
                "patient_id," +
                "reservation_id," +
                "charge_type," +
                "charge_description," +
                "tran_date, " +
                "total" +
            ") values (?, ?, ?, ?, ?, round(cast(? as numeric), 2))"
        );
        ps.setInt(1, patient_id);
        ps.setInt(2, reservation_id);
        
        ps.setString(3, charge_type);
        ps.setString(4, charge_description);
        
        ps.setDate(5, new java.sql.Date(tran_date.getTime()));
        ps.setDouble(6, total);
        
        ps.executeUpdate();
        
        ps.close();
        con.commit();
        con.close();
   
        return "/viewReservation.xhtml?faces-redirect=true";
    }
    
     public void insert()
        throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(
            "Insert into transactions (" +
                "patient_id," +
                "reservation_id," +
                "charge_type," +
                "charge_description," +
                "tran_date, " +
                "total" +
            ") values (?, ?, ?, ?, ?, round(cast(? as numeric), 2))"
        );
        ps.setInt(1, patient_id);
        ps.setInt(2, reservation_id);
        
        ps.setString(3, charge_type);
        ps.setString(4, charge_description);
        
        ps.setDate(5, new java.sql.Date(tran_date.getTime()));
        ps.setDouble(6, total);
        
        ps.executeUpdate();
        
        ps.close();
        con.commit();
        con.close();
    }

    public String delete() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        Statement statement = con.createStatement();
        statement.executeUpdate(
            "Delete from transactions where transaction_id = " + transaction_id
        );
        
        statement.close();
        con.commit();
        con.close();
        
        return "main";
    }

    public String showTransaction() {
        return "showTransaction";
    }
    
    public boolean exists(Integer rID) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from transactions " +
            "where reservation_id = " + rID
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
    
    public List<Transaction> getTransactionList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from transactions order by transaction_id"
        );
        ResultSet result = ps.executeQuery();
        
        List<Transaction> list = new ArrayList<>();
        while (result.next()) {
            Transaction tran = new Transaction();

            tran.setTransactionID(result.getInt("transaction_id"));
            tran.setPatient_id(result.getInt("patient_id"));
            tran.setReservation_id(result.getInt("reservation_id"));
            
            tran.setCharge_type(result.getString("charge_type"));
            tran.setCharge_description(result.getString("charge_description"));
            
            tran.setTran_date(result.getDate("tran_date"));
            tran.setTotal(result.getDouble("total"));
            
            list.add(tran);
        }
        
        result.close();
        con.close();
        return list;
    }
    
    public List<Transaction> getResTransactionList(int res_id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        

        PreparedStatement ps = con.prepareStatement(
            "select * from transactions where reservation_id = " + res_id + " order by transaction_id"
        );
        ResultSet result = ps.executeQuery();
        
        List<Transaction> list = new ArrayList<>();
        while (result.next()) {
            Transaction tran = new Transaction();

            tran.setTransactionID(result.getInt("transaction_id"));
            tran.setPatient_id(result.getInt("patient_id"));
            tran.setReservation_id(result.getInt("reservation_id"));
            
            tran.setCharge_type(result.getString("charge_type"));
            tran.setCharge_description(result.getString("charge_description"));
            
            tran.setTran_date(result.getDate("tran_date"));
            tran.setTotal(result.getDouble("total"));
            
            list.add(tran);
        }
           
        result.close();
        con.close();
        return list;
    }
}
