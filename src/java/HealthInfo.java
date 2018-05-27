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

public class HealthInfo {
    
    private DBConnect dbConnect = new DBConnect();
    private Integer patient_id;
    private Integer age;
    private String gender;
    private Integer height;
    private Integer weight;
    private String allergies;
    private String conditions;
    private String medicine;
    private String insurance;
    
    
    public Integer getPatientID(){
        return patient_id;
    }
    
    public void setPatientID(Integer patient_id){
        this.patient_id = patient_id;
    }
    
    public Integer getAge(){
        return age;
    }
    
    public void setAge(Integer age){
        this.age = age;
    } 
    
    public String getGender(){
        return gender;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
}
