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

@javax.faces.bean.ManagedBean(name="prescription")
@SessionScoped
public class Prescription implements Serializable{
    
    private DBConnect dbConnect = new DBConnect();
    private Integer patient_id;
    private Integer staff_id;
    private Integer medication_id;
    
    private Medication med;
    private String staff_name;

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public Integer getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(Integer staff_id) {
        this.staff_id = staff_id;
    }

    public Integer getMedication_id() {
        return medication_id;
    }

    public void setMedication_id(Integer medication_id) {
        this.medication_id = medication_id;
    }

    public Medication getMed() {
        return med;
    }

    public void setMed(Medication med) {
        this.med = med;
    }
    
    public List<Prescription> getResPrescriptions(int res_id) throws SQLException {
        Connection con = dbConnect.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
            "select * from prescription join medication on drug_id = medication_id join staff on prescription.staff_id = staff.staff_id where reservation_id = " + res_id
        );

        //get employee data from database
        ResultSet result = ps.executeQuery();
        List<Prescription> list = new ArrayList<>();

        while (result.next()) {
            Prescription p = new Prescription();
            Medication m = new Medication();

            p.setMedication_id(result.getInt("medication_id"));
            p.setPatient_id(result.getInt("patient_id"));
            p.setStaff_id(result.getInt("staff_id"));
            m.setDrugID(result.getInt("drug_id"));
            m.setDuration(result.getInt("duration"));
            m.setName(result.getString("medication.name"));
            m.setPrice(result.getDouble("price"));
            p.setStaff_name(result.getString("staff.name"));
            p.setMed(m);
            //store all data into a List
            list.add(p);
        }
        
        result.close();
        con.close();
        return list;
    }
}
