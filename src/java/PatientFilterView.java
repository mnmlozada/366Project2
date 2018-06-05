
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import java.sql.SQLException;
import org.omnifaces.util.Faces;

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
    private List<Patient> outPatientList;
    private List<Patient> inPatientList;
    private List<Patient> filteredPatient;
    
    @PostConstruct
    public void init() {
       try {
            patientList = patient.getPatientList();
            outPatientList = patient.getOutPatientList();
            inPatientList = patient.getInPatientList();
       }
       catch (SQLException ex) {
           
       }
    }
    
    public void delete(Patient p) {
       try {
            p.deletePatient();
            patientList = patient.getPatientList();
       }
       catch (Exception ex) {
           
       }
    }

    public List<Patient> getInPatientList() {
        return inPatientList;
    }

    public void setInPatientList(List<Patient> inPatientList) {
        this.inPatientList = inPatientList;
    }

    public List<Patient> getOutPatientList() {
        return outPatientList;
    }

    public void setOutPatientList(List<Patient> outPatientList) {
        this.outPatientList = outPatientList;
    }
    
    public String admit(Patient p) {
        Faces.setSessionAttribute("patient", p);
        return "/newHealthInfo.xhtml?faces-redirect=true";
    }
    
    public String release(Patient p) {
        try {
            p.release();
            //Faces.setSessionAttribute("patient", p);
       }
       catch (Exception ex) {
           System.out.println(ex);
       }
       Faces.setSessionAttribute("patient", new Patient());
       return "/releaseConfirmation.xhtml?faces-redirect=true";
    }
    
    public String view(Patient p) {
        Faces.setSessionAttribute("patient", p);
        return "/viewPatient.xhtml?faces-redirect=true";
    }
    
    public String addCharges(Patient p) throws SQLException {
        Faces.setSessionAttribute("patient", p);
        Reservation res = new Reservation().getByPatientID(p.getPatientID());
        Faces.setSessionAttribute("reservation", res);
        Transaction t = new Transaction();
        Faces.setSessionAttribute("transaction", t);
        ((Transaction)(Faces.getSessionAttribute("transaction"))).setPatient_id(p.getPatientID());
        ((Transaction)(Faces.getSessionAttribute("transaction"))).setReservation_id(res.getReservationId());
        ((Transaction)(Faces.getSessionAttribute("transaction"))).setTran_date(new java.util.Date());
        return "/addCharges2.xhtml?faces-redirect=true";
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
