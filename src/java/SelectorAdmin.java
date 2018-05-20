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
@Named(value = "selector_admin")
@ManagedBean
@SessionScoped
public class SelectorAdmin implements Serializable {

    private String[] choices = {"Change Password", "View Room Prices", "Change Single Room Price", "Change All Room Prices", "Show All Employees", "Add Employee", "Delete Employee"};
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
            case "Change Single Room Price":
                return "changePrices";
            case "Change All Room Prices":
                return "changePricesAll";
            case "Add Employee":
                return "addEmployee";
            case "Delete Employee":
                return "deleteEmployee";
            case "Show All Employees":
                return "listEmployees";
            default:
                return null;
        }
    }

}
