import java.io.File;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinícius Chagas.
 */
public class Database {
    private IReceiver receiver;
    private List<Building> buildings;
    private List<Course> courses;
    private List<Feature> features;
    private List<Booking> bookings;
    private List<Session> requests;

    public Database(IReceiver receiver) {
        this.receiver = receiver;
    }

    public void initialize(){
        buildings = new ArrayList<>();
        courses = new ArrayList<>();
        features = new ArrayList<>();
        bookings = new ArrayList<>();
        requests = new ArrayList<>();
    }
    
    public void populate(File file){
        List<Data> rawData = receiver.load(file);
        for (Data data : rawData) {
            if (data instanceof Course){
                Course course = (Course) data;
                courses.add(course);
                requests.addAll(course.listAllSessions());
            } else if (data instanceof Feature){
                Feature feature = (Feature) data;
                features.add(feature);
            } else if (data instanceof Building){
                Building building = (Building) data;
                buildings.add(building);
            }
        }
    }

    
    public List<Building> getBuildings() {  return buildings;   }

    public List<Booking> getBookings() {    return bookings;    }

    public void removeBooking(int index){   bookings.remove(index); }

    public void setBooking (Booking book){   this.bookings.add(book);    }

    public List<Session> getRequests(){return requests;}




}



