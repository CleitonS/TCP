import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Allocator implements IAllocator {

    private Database database;
    public Allocator (Database database){
        this.database = database;
    }

    @Override
    public void loadRequests(File file) {
        database.initialize();
        database.populate(file);
    }

    @Override
    public void writeResults(File file) {

    }

    public Booking createBook(Session session, Room room, Building building) {

        Booking newBook = new Booking(session.getGroup().getId(), session.getGroup().getName());
        newBook.setBuilding(building);
        newBook.setRoom(room);
        //List features tem que ser de features
//        newBook.setFeatures(room.getFeatures());
        newBook.setWeekday(session.getDayOfWeek());
        newBook.setTime(session.getTime());
        newBook.setDuration(session.getDuration());
        newBook.setGroup(session.getGroup());

        return newBook;
    }


    @Override
    public void allocate(Session session) throws Exception {

        List<Building> listBuildings = database.getBuildings();
        Building building;
        Room room;
        List<Booking> listBooking;
        boolean sessionAllocated = false;
        //String[] stringFeaturesSession = session.getRequiredFeatures().split(", ");
        //List<String> listFeaturesSession = Arrays.asList(stringFeaturesSession);
        Booking preBook = new Booking(null, null);
        int buildingIterator = 0;

        while (buildingIterator < listBuildings.size() && !sessionAllocated) { //percorrendo todos predios
            //for(int buildingIterator = 0; buildingIterator <= listBuildings.size(); buildingIterator++){
            building = listBuildings.get(buildingIterator);
            int roomIterator = 0;
            while (roomIterator < building.listRooms().size() && !sessionAllocated) { //percorrendo todas salas de um predio
                //for(int roomIterator = 0; roomIterator <= building.listRooms().size(); roomIterator++){
                room = building.getRoom(roomIterator);
                if ((room.getCapacity() >= session.getGroup().getNumStudents()) && (room.isAvailable())) {
                    //CERTIFICAR QUE AS DUAS LISTAS SERAO DE STRINGS
                    //String[] StringFeaturesSession =session.getRequiredFeatures.split(", ");

//                    System.out.print(room.getId());
//                    System.out.print(" sala: " + room.getFeatures().toString());
//                    System.out.println(" req: "+session.getRequiredFeatures().toString());

                    if (room.getFeatures().equals(session.getRequiredFeatures())) { //se a sala conter os recursos solicitados
                        //Verificando se a sala esta disponivel no horario solicitado
//                        System.out.print("room");
                        listBooking = database.getBookings();
                        int bookingIterator = 0;
                        while (!sessionAllocated && (bookingIterator <= listBooking.size())) { //percorro todas as reservas já efetuadas
                            //for(int i_booking = 0; i_booking <= listBooking.size(); i_booking++ ){
                            if (
                                    listBooking.get(bookingIterator).getBuilding() == building &&
                                            listBooking.get(bookingIterator).getRoom() == room &&
                                            listBooking.get(bookingIterator).getWeekday() == session.getDayOfWeek() &&
                                            listBooking.get(bookingIterator).getTime() == session.getTime()
                                    ) {/*sala está ocupada*/
                                sessionAllocated = false;
                            } else {
                                //sala está desocupada
                                Booking newBook = createBook(session, room, building);
                                database.setBooking(newBook);
                                sessionAllocated = true;
                            }
                            bookingIterator++;
                        }
                        //fim while
                    } else {  /*list festures diferentes*/
//                        System.out.println(this.enoughFeatures(session.getRequiredFeatures(), room.getFeatures().toString()));
                        if (this.enoughFeatures(session.getRequiredFeatures(), room.getFeatures().toString())) {
                            /*
                            * Aqui, é realizada uma pré reserva, ou seja, a sala contem todos os recursos solicitados e outros.
                            * Assim, não é a sala ideal(sala ideal é aquela que possui somente os recursos solicitados e nada mais).
                            * Porém, caso não haja sala ideal, é feita a reserva usando esta pre reservada.
                            * */
                            preBook = createBook(session, room, building);
                            /*
                            System.out.print("sao suf");
                            preBook = new Booking(session.getGroup().getId(), session.getGroup().getName());
                            preBook.setBuilding(building);
                            preBook.setRoom(room);
                            //List features tem que ser de features
                            preBook.setFeatures(room.getFeatures());
                            preBook.setWeekday(session.getDayOfWeek());
                            preBook.setTime(session.getTime());
                            preBook.setDuration(session.getDuration());
                            preBook.setGroup(session.getGroup());*/
                       }
                    }
                }
                roomIterator++;
            }
            buildingIterator++;
        }
        if (!sessionAllocated && preBook.getId() != null) {
            database.setBooking(preBook);
            sessionAllocated = true;
        } else {
            throw new Exception("Features Unavailable");
        }
    }

    @Override
    public void unbook(Booking booking) throws Exception {
        List<Booking> listBookings = new ArrayList<Booking>();
        boolean bookRemoved = false;
        int bookIterator = 0;
       while (bookIterator < listBookings.size() && !bookRemoved){
        if(booking.equals(listBookings.get(bookIterator))){
              //listBookings.remove(bookIterator);
                database.removeBooking(bookIterator);
                bookRemoved = true;
            bookIterator++;
        }
           if(!bookRemoved){  throw new Exception("Reserva não encontrada");  }
       }
    }


    private boolean enoughFeatures(String stringFeaturesSession, String stringFeaturesRoom){
        boolean nextFeatureSession = false;
        boolean enoughtFeatures = true;
        int roomIterator = 0;
        int sessionIterator = 0;
        List<String> featuresRoom;
        List<String> featuresSession;

        if(stringFeaturesRoom.isEmpty()){
            return false;
        }
        else{
            String[] convfeaturesRoom = stringFeaturesRoom.split(", ");
            featuresRoom = Arrays.asList(convfeaturesRoom);
        }

        if(stringFeaturesSession.isEmpty()){
            return true;
        }
        else{
            String[] convfeaturesSession = stringFeaturesSession.split(", ");
            featuresSession = Arrays.asList(convfeaturesSession);
        }



        for (sessionIterator = 0; sessionIterator < featuresSession.size(); sessionIterator++){
            for(roomIterator = 0; roomIterator<featuresRoom.size(); roomIterator++){
                if(featuresRoom.get(roomIterator).equals(featuresSession.get(sessionIterator))){
                    nextFeatureSession = true;
                }
            }
            if(!nextFeatureSession) enoughtFeatures = false;
        }


        if(!enoughtFeatures)
        {return false;}
        else
        {return true;}
    }

    public void createBook(Booking booking) throws Exception {
        List<Booking> listBookings = new ArrayList<Booking>();
        boolean bookRemoved = false;
        int bookIterator = 0;

        while (bookIterator < listBookings.size() && !bookRemoved){
            if(booking.equals(listBookings.get(bookIterator))){
                //listBookings.remove(bookIterator);
                database.removeBooking(bookIterator);
                bookRemoved = true;
            }
            bookIterator++;
        }

        if(!bookRemoved){  throw new Exception("Reserva não encontrada");  }
    }
}
