import java.util.ArrayList;
import java.util.List;

public class Building extends Data {
    private List<Room> rooms;

    public Building(String id, String name) {
        super(id, name);
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room){
        this.rooms.add(room);
    }

    public Room getRoom(int index){
        return this.rooms.get(index);
    }

    public Room getRoom(String query){
        //itera todas as salas da lista de salas
        //retorna a que tiver ID ou nome iguais à string solicitada
        for (Room room : this.rooms) {
            if (room.getId().equalsIgnoreCase(query) || room.getName().equalsIgnoreCase(query))
                return room;
        }
        return null;
    }

    public List<Room> listRooms(){
        return rooms;
    }

    public List<Room> listAvailableRooms(){
        List<Room> availableRooms = new ArrayList<Room>();

        for (Room room : rooms) {
            if (room.isAvailable())
                availableRooms.add(room);
        }

        return availableRooms;
    }

    @Override
    public String toString() {
        return "Building{\n" +
                "\trooms=" + rooms + "\n" +
                "}\n" + super.toString();
    }
}
