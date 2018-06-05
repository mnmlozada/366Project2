import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;
import org.omnifaces.util.Faces;

@ManagedBean(name="prescriptionView")
@ViewScoped
public class PrescriptionView implements Serializable {

    @ManagedProperty(value = "#{prescription}")
    private Prescription prescription;

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }
        
    public String selectForPrescription(Patient p) {
        Faces.setSessionAttribute("patient", p);
        return "/selectMedicationForPrescription.xhtml?faces-redirect=true";
    }
    
    public String prescribe(Medication m) throws SQLException {
        Faces.setSessionAttribute("medication", m);
        Prescription p = new Prescription();
        
        Integer patientID = ((Patient)Faces.getSessionAttribute("patient")).getPatientID();
        p.setPatient_id(patientID);
        
        p.setStaff_id(((Staff)Faces.getSessionAttribute("staff")).getStaffID());
        
        Reservation r = new Reservation().getByPatientID(patientID);
        p.setReservation_id(r.getReservationId());
        
        p.setMedication_id(m.getDrugID());
        
        p.createPrescription();
        Faces.setSessionAttribute("prescription", p);
        
        return "/prescriptionConfirmation.xhtml?faces-redirect=true";
    }
}
