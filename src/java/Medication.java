import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Faces;

@ManagedBean(name="medication")
@SessionScoped
public class Medication implements Serializable{
    
    private DBConnect dbConnect = new DBConnect();
    private Integer drug_id;
    private String name;
    private Double price;
    private Integer duration;

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
    
    public String createMedication() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        PreparedStatement ps = con.prepareStatement(
            "Insert into medication (name, price, duration) " +
            "values(?, ?, ?)"
        );
        ps.setString(1, name);
        ps.setDouble(2, price);
        ps.setInt(3, duration);
        ps.executeUpdate();
        
        ps = con.prepareStatement(
            "select * from medication where name = '" + name + "'"
        );

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            drug_id = result.getInt("drug_id");
        }
        con.commit();
        
        ps.close();
        con.close();
        Faces.setSessionAttribute("medication", this);
        return "/showAllMedications.xhtml?faces-redirect=true";
    }
    
    public Medication getMedication() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from medication where drug_id = " + drug_id
        );
        ResultSet result = ps.executeQuery();
        result.next();

        drug_id = result.getInt("drug_id");
        name = result.getString("name");
        price = result.getDouble("price");
        duration = result.getInt("duration");
        return this;
    }

    public List<Medication> getMedicationList() throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        
        PreparedStatement ps = con.prepareStatement(
            "select * from medication " +
            "order by drug_id"
        );
        
        ResultSet result = ps.executeQuery();
        List<Medication> list = new ArrayList<>();

        while (result.next()) {
            Medication med = new Medication();

            med.setDrugID(result.getInt("drug_id"));
            med.setName(result.getString("name"));
            med.setPrice(result.getDouble("price"));
            med.setDuration(result.getInt("duration"));
            
            list.add(med);
        }
        
        result.close();
        con.close();
        return list;
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

    public void deleteMedication() throws SQLException, ParseException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        con.setAutoCommit(false);
        
        Statement statement = con.createStatement();
        statement.executeUpdate(
            "Delete from medication where drug_id = " + drug_id
        );
        statement.close();
        
        con.commit();
        con.close();
    }
}
