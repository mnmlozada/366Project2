
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import java.sql.SQLException;

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
    private List<Room> filteredRoom;
    
    @PostConstruct
    public void init() {
       try {
            roomList = room.getRoomList();
       }
       catch (SQLException ex) {
           
       }
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
