
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import java.sql.SQLException;

@ManagedBean(name="staffFilterView")
@ViewScoped
public class StaffFilterView implements Serializable {

    @ManagedProperty(value = "#{staff}")
    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    
    private List<Staff> staffList;
    private List<Staff> filteredStaff;
    
    @PostConstruct
    public void init() {
       try {
            staffList = staff.adminGetStaffList();
       }
       catch (SQLException ex) {
           
       }
    }
    
    public void delete(Staff s) {
       try {
            s.deleteStaff();
            staffList = staff.adminGetStaffList();
       }
       catch (Exception ex) {
           
       }
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public List<Staff> getFilteredStaff() {
        return filteredStaff;
    }

    public void setFilteredStaff(List<Staff> filteredStaff) {
        this.filteredStaff = filteredStaff;
    }

}
