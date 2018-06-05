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

@javax.faces.bean.ManagedBean(name="medication")
@SessionScoped
public class Medication implements Serializable{
    
    private DBConnect dbConnect = new DBConnect();
    private Integer drug_id;
    private String name;
    private Double price;
    private Integer duration;

    public Integer getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(Integer drug_id) {
        this.drug_id = drug_id;
    }
    
    
    public Integer getDrugID(){
        return drug_id;
    }
    
    public void setDrugID(Integer drug_id){
        this.drug_id = drug_id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Double getPrice(){
        return price;
    }
    
     public void setPrice(Double price){
        this.price = price;
    }
    
    public Integer getDuration(){
        return duration;
    }
    
    public void setDuration(Integer duration){
        this.duration = duration;
    }
    
    public Medication getMedication() throws SQLException {
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps =
            con.prepareStatement("select * from medication where drug_id = " + drug_id);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        result.next();

        drug_id = result.getInt("drug_id");
        name = result.getString("name");
        price = result.getDouble("price");
        duration = result.getInt("duration");
        return this;
    }
    
    public void medicationExists(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        drug_id = Integer.parseInt(value.toString());
        
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps =
            con.prepareStatement("select * from medication where drug_id = " + drug_id);

        //get customer data from database
        ResultSet result = ps.executeQuery();

        if (!result.next()) {
             FacesMessage errorMessage = new FacesMessage("Medication does not exist.");
                throw new ValidatorException(errorMessage);
        }
    }
}
