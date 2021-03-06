
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
import org.omnifaces.util.Faces;

@javax.faces.bean.ManagedBean(name="login")
@SessionScoped
public class Login implements Serializable {

    public static final int ADMIN = 0;
    public static final int EMPLOYEE = 1;
    public static final int CUSTOMER = 2;
    
    private String login;
    private String password;
    private UIComponent loginUI;
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

    public UIComponent getLoginUI() {
        return loginUI;
    }

    public void setLoginUI(UIComponent loginUI) {
        this.loginUI = loginUI;
    }

    public String getLogins() {
        return login;
    }

    public void setLogins(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void logout() {
        Faces.setSessionAttribute("patient", new Patient());
        Faces.setSessionAttribute("reservation", new Reservation());
        login = "";
        password = "";
        type = -1;
        //return "logout";
    }

    public void validate(FacesContext context, UIComponent component, Object value)
            throws ValidatorException, SQLException { 
        UIInput temp = (UIInput)loginUI;
        login = temp.getLocalValue().toString();
        password = value.toString();

        type = processLogin(login, password);

        if (type == -1) {
            FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Wrong login/password");
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

        PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM patient WHERE username = ? AND password = ?");
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            userId = rs.getInt("patient_id");
            Faces.setSessionAttribute("patient", (new Patient()).getByID(userId));
            result = CUSTOMER;
        }
        statement.close();
        con.commit();
        
        if (result == -1) {
            statement = con.createStatement();

            preparedStatement = con.prepareStatement("SELECT * FROM staff WHERE username = ? AND password = ?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            Staff s = new Staff();
            while (rs.next()) {
                boolean adminFlag = rs.getBoolean("adminFlag");
                userId = rs.getInt("staff_id");
                if (adminFlag) {
                    result = ADMIN;
                } else {
                    result = EMPLOYEE;
                }
                s.setStaffID(userId);
                Faces.setSessionAttribute("staff", s.getStaff());
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
    
     public String getMenu() {
      if (type == ADMIN) {
          return "_admin";
      } else if (type == EMPLOYEE){
        return "_staff";
      } else if (type == CUSTOMER) {
          return "_patient";
      }
      return "";
    }

}
