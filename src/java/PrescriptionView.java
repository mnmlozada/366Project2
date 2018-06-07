import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;
import org.omnifaces.util.Faces;

@javax.faces.bean.ManagedBean(name="prescriptionView")
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
    
    public String prescribe(Medication m) throws SQLException, ParseException {
        Faces.setSessionAttribute("medication", m);
        
        Integer patientID = ((Patient)Faces.getSessionAttribute("patient")).getPatientID();
        Reservation r = new Reservation().getByPatientID(patientID);
        
        Prescription p = new Prescription();
        p.setPatient_id(patientID);
        p.setStaff_id(((Staff)Faces.getSessionAttribute("staff")).getStaffID());
        p.setReservation_id(r.getReservationId());
        p.setMedication_id(m.getDrugID());
        p.createPrescription();
        Faces.setSessionAttribute("prescription", p);
        
        Transaction t = new Transaction();
        t.setPatient_id(patientID);
        t.setReservation_id(r.getReservationId());
        t.setCharge_type("prescription");
        t.setCharge_description("Patient has been prescribed " + m.getName());
        t.setTran_date(new java.util.Date());
        t.setTotal(m.getPrice());
        t.addCharge();
        Faces.setSessionAttribute("transaction", t);
        
        return "/prescriptionConfirmation.xhtml?faces-redirect=true";
    }
}
