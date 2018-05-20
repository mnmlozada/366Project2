/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author stanchev
 */
@Named(value = "selector_customer")
@ManagedBean
@SessionScoped
public class SelectorCustomer implements Serializable {

    private String[] choices = {"List My Reservations", "Cancel Reservation", "Create Reservation"};
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
            case "List My Reservations":
                return "listMyReservations";
            case "Cancel Reservation":
                return "cancelReservation";
            case "Create Reservation":
                return "createReservation";
            default:
                return null;
        }
    }

}
