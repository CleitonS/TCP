import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class Booking extends Data {
    private Building building;
    private Room room;
    private String features;
    private Group group;
    private DayOfWeek weekday;
    private LocalTime time;
    private Duration duration;

    public Booking(String id, String name) {
        super(id, name);
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public DayOfWeek getWeekday() {
        return weekday;
    }

    public void setWeekday(DayOfWeek weekday) {
        this.weekday = weekday;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
