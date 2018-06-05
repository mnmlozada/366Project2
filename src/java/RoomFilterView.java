
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import java.sql.SQLException;
import org.omnifaces.util.Faces;

@ManagedBean(name="roomFilterView")
@ViewScoped
public class RoomFilterView implements Serializable {

    @ManagedProperty(value = "#{room}")
    private Room room;

    public Room  getRoom () {
        return room;
    }

    public void setRoom(Room  room) {
        this.room = room;
    }
    
    private List<Room> roomList;
    private List<Room> freeRoomList;
    private List<Room> filteredRoom;
    
    @PostConstruct
    public void init() {
       try {
            roomList = room.getRoomList();
            freeRoomList = room.getFreeRoomList();
       }
       catch (SQLException ex) {
           
       }
    }

    public List<Room> getFreeRoomList() {
        return freeRoomList;
    }

    public void setFreeRoomList(List<Room> freeRoomList) {
        this.freeRoomList = freeRoomList;
    }
    
    public String admitTo(Room r) {
        Reservation res = new Reservation();
        res.setRoomId(r.getRoomNumber());
        res.setPatientId(((Patient)Faces.getSessionAttribute("patient")).getPatientID());
        Faces.setSessionAttribute("reservation", res);
        try {
            res.create();
        }
        catch (Exception ex) {       
        }
        Faces.setSessionAttribute("patient", new Patient());
        Faces.setSessionAttribute("health_info", new HealthInfo());
        return "/admitConfirmation.xhtml?faces-redirect=true";
    }

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Room> getFilteredRoom() {
        return filteredRoom;
    }

    public void setFilteredRoom(List<Room> filteredRoom) {
        this.filteredRoom = filteredRoom;
    }

}
