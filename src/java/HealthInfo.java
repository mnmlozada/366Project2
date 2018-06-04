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

@javax.faces.bean.ManagedBean(name="health_info")
@SessionScoped
public class HealthInfo implements Serializable{
    
    private DBConnect dbConnect = new DBConnect();
    private Integer patient_id;
    private String gender;
    private Integer height;
    private Integer weight;
    private String allergies;
    private String conditions;
    private String medicine;
    private String insurance;
    private Date exam_date;
    private Integer hi_id;

    public Integer getHi_id() {
        return hi_id;
    }

    public void setHi_id(Integer hi_id) {
        this.hi_id = hi_id;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public Date getExam_date() {
        return exam_date;
    }

    public void setExam_date(Date exam_date) {
        this.exam_date = exam_date;
    }
    
    public String getGender(){
        return gender;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
    
    public Integer getHeight(){
        return height;
    }
    
    public void setHeight(Integer height){
        this.height = height;
    }
    
    public Integer getWeight(){
        return weight;
    }
    
    public void setWeight(Integer weight){
        this.weight = weight;
    }
    
    public String getAllergies(){
        return allergies;
    }
    
    public void setAllergies(String allergies){
        this.allergies = allergies;
    }
    
    public String getConditions(){
        return conditions;
    }
    
    public void setConditions(String conditions){
        this.conditions = conditions;
    }
    
    public String getMedicine(){
        return medicine;
    }
    
    public void setMedicine(String medicine){
        this.medicine = medicine;
    }
    
    public String getInsurance(){
        return insurance;
    }
    
    public void setInsurance(String insurance){
        this.insurance = insurance;
    }
}
