import java.io.Serializable;
import java.util.List;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedProperty;

@javax.faces.bean.ManagedBean(name="medicationFilterView")
@ViewScoped
public class MedicationFilterView implements Serializable {

    @ManagedProperty(value = "#{medication}")
    private Medication medication;

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
    
    private List<Medication> medicationList;
    private List<Medication> filteredMedication;
    
    @PostConstruct
    public void init() {
       try {
            medicationList = medication.getMedicationList();
       }
       catch (SQLException ex) {}
    }
    
    public void delete(Medication m) {
       try {
            m.deleteMedication();
            medicationList = medication.getMedicationList();
       }
       catch (Exception ex) {}
    }

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }

    public List<Medication> getFilteredMedication() {
        return filteredMedication;
    }

    public void setFilteredMedication(List<Medication> filteredMedication) {
        this.filteredMedication = filteredMedication;
    }    
}
