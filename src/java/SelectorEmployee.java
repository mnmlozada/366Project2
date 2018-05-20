import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Named(value = "selector_employee")
@ManagedBean
@SessionScoped
public class SelectorEmployee implements Serializable {

    private String[] choices = 
    {
        "Change Password",
        "Create New Customer",
        "Delete Customer",
        "Find Customer",
        "List All Customers",
        "View Room Prices",
        "Check In Customer",
        "Check Out Customer",
        "Add Charges to Customer",
        "Find Reservation",
        "Cancel Reservation",
        "Create Reservation",
        "List All Reservations"
    };
    
    private String choice;

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String transition() {
        switch (choice) {
            case "Change Password":
                return "changePassword";
            case "View Room Prices":
                return "listPrices";
            case "Check In Customer":
                return "checkInCustomer";
            case "Check Out Customer":
                return "checkOutCustomer";
            case "Add Charges to Customer":
                return "addCharges";
            case "List All Reservations":
                return "listReservations";
            case "Find Reservation":
                return "findReservation";
            case "Cancel Reservation":
                return "cancelReservation";
            case "Create Reservation":
                return "createReservation";
            case "Create New Customer":
                return "newCustomer";
            case "List All Customers":
                return "listCustomers";
            case "Find Customer":
                return "findCustomer";
            case "Delete Customer":
                return "deleteCustomer";
            default:
                return null;
        }
    }
    
    public String backToStart() {
        return "startEmployee";
    } 
}
