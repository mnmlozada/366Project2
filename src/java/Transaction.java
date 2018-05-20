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

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import javax.inject.Named;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;

@Named(value = "transaction")
@SessionScoped
@ManagedBean
public class Transaction implements Serializable {

    @ManagedProperty(value = "#{login}")
    private Login login;

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    private DBConnect dbConnect = new DBConnect();
    
    private Integer transaction_id;
    private Integer customer_id;
    private Integer reservation_id;
    
    private String cc_num;
    private Date cc_exp;
    private Integer cc_crc;
    
    private Date tran_date;
    private Double total;

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
    
    public Integer getCustomerID() {
        return customer_id;
    }

    public void setCustomerID(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public Integer getReservationID() {
        return reservation_id;
    }

    public void setReservationID(Integer reservation_id) {
        this.reservation_id = reservation_id;
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
    
    public Date getTranDate() {
        return tran_date;
    }

    public void setTranDate(Date tran_date) {
        this.tran_date = tran_date;
    }
    
    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void create(Customer c, Integer rID, Date transDate, Double total)
        throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);

        PreparedStatement ps = con.prepareStatement(
            "Insert into transactions (" +
                "customer_id," +
                "reservation_id," +
                "cc_num," +
                "cc_ex," +
                "cc_crc," +
                "tran_date, " +
                "total" +
            ") values (?, ?, ?, ?, ?, ?, ?)"
        );
        ps.setInt(1, c.getCustomerID());
        ps.setInt(2, rID);
        
        ps.setString(3, c.getCc_num());
        ps.setDate(4, new java.sql.Date(c.getCc_exp().getTime()));
        ps.setInt(5, c.getCc_crc());
        
        ps.setDate(6, new java.sql.Date(transDate.getTime()));
        ps.setDouble(7, total);
        
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
            tran.setCustomerID(result.getInt("customer_id"));
            tran.setReservationID(result.getInt("reservation_id"));
            
            tran.setCc_num(result.getString("cc_num"));
            tran.setCc_exp(result.getDate("cc_exp"));
            tran.setCc_crc(result.getInt("cc_crc"));
            
            tran.setTranDate(result.getDate("tran_date"));
            tran.setTotal(result.getDouble("total"));
            
            list.add(tran);
        }
        
        result.close();
        con.close();
        return list;
    }
}
