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

@Named(value = "prescription")
@SessionScoped
@ManagedBean
public class Prescription {
    
    private DBConnect dbConnect = new DBConnect();
    private Integer patient_id;
    private Integer staff_id;
    private Integer medication_id;
    
    
    public Integer getPatientID(){
        return patient_id;
    }
    
    public void setPatientID(Integer patient_id){
        this.patient_id = patient_id;
    } 
    
    public Integer getStaffID(){
        return patient_id;
    }
    
    public void setStaffID(Integer staff_id){
        this.staff_id = staff_id;
    } 
    
    public Integer getMedicationID(){
        return medication_id;
    }
    
    public void setMedicationID(Integer medication_id){
        this.medication_id = medication_id;
    } 
}
