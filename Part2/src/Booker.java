import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cleiton Souza, Lucas Tansini, Mateus Bianchi and Vinícius Chagas.
 */
public class Booker {
    public static void main(String[] args){
        IReader reader = new XMLReader();
        IReceiver receiver = new Receiver(reader);
        Database database = new Database(receiver);
        IAllocator allocator = new Allocator(database);
        allocator.loadRequests(new java.io.File(args[0]));



        for(int i = 0; i<15;i++) {
            try {
                allocator.allocate(database.getRequests().get(i));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(
                    "NameGroup: " + database.getBookings().get(i).getGroup().getName() +
                            "\nIdGroup: " + database.getBookings().get(i).getGroup().getId() +
                            "\nTeacher: " + database.getBookings().get(i).getGroup().getTeacher() +
                            "\nDuration: " + database.getBookings().get(i).getDuration().toString() +
                            "\nweekday: " + database.getBookings().get(i).getWeekday().toString() +
                            "\ntime: " + database.getBookings().get(i).getTime().toString() +
                            "\ncapacity: " + database.getBookings().get(i).getRoom().getCapacity() +
                            "\nRoomName: " + database.getBookings().get(i).getRoom().getName() +
                            "\nRoomId: " + database.getBookings().get(i).getRoom().getId() +
                            "\nRoomFeatures: " + database.getBookings().get(i).getRoom().getFeatures().toString() +
                            "\nBuildingName: " + database.getBookings().get(i).getBuilding().getName() +
                            "\nBuildingId: " + database.getBookings().get(i).getBuilding().getId()
            );
        }


    }

}
