
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author stanchev
 */
@Named(value = "login")
@SessionScoped
@ManagedBean
public class Login implements Serializable {

    public static final int ADMIN = 0;
    public static final int EMPLOYEE = 1;
    public static final int CUSTOMER = 2;
    
    private String login;
    private String password;
    private UIInput loginUI;
    private int userId;
    private int type = -1;
    private DBConnect dbConnect = new DBConnect();

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public UIInput getLoginUI() {
        return loginUI;
    }

    public void setLoginUI(UIInput loginUI) {
        this.loginUI = loginUI;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String logout() {
        return "logout";
    }

    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException {
        login = loginUI.getLocalValue().toString();
        password = value.toString();
        System.out.println("hello");
        type = processLogin(login, password);

        if (type == -1) {
            FacesMessage errorMessage = new FacesMessage("Wrong login/password");
            throw new ValidatorException(errorMessage);
        }
    }
    
    private int processLogin(String login, String password) throws SQLException {
        int result = -1;
        Connection con = dbConnect.getConnection();

        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        con.setAutoCommit(false);

        Statement statement = con.createStatement();

        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM customer WHERE username = ? AND password = ?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            userId = rs.getInt("customer_id");
            result = CUSTOMER;
        }
        statement.close();
        con.commit();
        
        if (result == -1) {
            statement = con.createStatement();

            preparedStatement = con.prepareStatement("SELECT * FROM employee WHERE username = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                boolean adminFlag = rs.getBoolean("adminFlag");
                userId = rs.getInt("employee_id");
                if (adminFlag) {
                    result = ADMIN;
                } else {
                    result = EMPLOYEE;
                }
            }
            statement.close();
            con.commit();
        }
        con.close();
        
        return result;
    }

    public String go() {
      //  Util.invalidateUserSession();
      if (type == ADMIN) {
          return "successAdmin";
      } else if (type == EMPLOYEE){
        return "successEmployee";
      } else if (type == CUSTOMER) {
          return "successCustomer";
      }
      return "failure";
    }

}
