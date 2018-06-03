
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import java.sql.SQLException;

@ManagedBean(name="patientFilterView")
@ViewScoped
public class PatientFilterView implements Serializable {

    @ManagedProperty(value = "#{patient}")
    private Patient patient;

    public Patient  getPatient () {
        return patient;
    }

    public void setPatient(Patient  patient) {
        this.patient = patient;
    }
    
    private List<Patient> patientList;
    private List<Patient> filteredPatient;
    
    @PostConstruct
    public void init() {
       try {
            patientList = patient.getPatientList();
       }
       catch (SQLException ex) {
           
       }
    }
    
    public void delete(Patient p) {
        System.out.println("deleting");
       try {
            p.deletePatient();
            patientList = patient.getPatientList();
       }
       catch (Exception ex) {
           
       }
    }
    
    public String view(Patient p) {
        return "/viewPatient.xhtml?faces-redirect=true";
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Patient> getFilteredPatient() {
        return filteredPatient;
    }

    public void setFilteredPatient(List<Patient> filteredPatient) {
        this.filteredPatient = filteredPatient;
    }

}
