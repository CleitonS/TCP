import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

public class Session extends Data {
    private Group group;
    private DayOfWeek dayOfWeek;
    private LocalTime time;
    private Duration duration;
    private String requiredRoom;
    private String requiredBuilding;
    private String requiredFeatures;

    public Session(String id, String name) {
        super(id, name);
    }

    public Session(String id, String name, DayOfWeek dayOfWeek, LocalTime time, Duration duration){
        super(id, name);
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.duration = duration;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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

    public String getRequiredRoom() {
        return requiredRoom;
    }

    public void setRequiredRoom(String requiredRoom) {
        this.requiredRoom = requiredRoom;
    }

    public String getRequiredBuilding() {
        return requiredBuilding;
    }

    public void setRequiredBuilding(String requiredBuilding) {
        this.requiredBuilding = requiredBuilding;
    }

    public String getRequiredFeatures() {
        return requiredFeatures;
    }

    public void setRequiredFeatures(String requiredFeatures) {
        this.requiredFeatures = requiredFeatures;
    }

    @Override
    public String toString() {
        return "Session{\n" +
                "\tdayOfWeek=" + dayOfWeek + "\n" +
                "\ttime=" + time + "\n" +
                "\tduration=" + duration + "\n" +
                "\trequiredRoom='" + requiredRoom + "'\n" +
                "\trequiredBuilding='" + requiredBuilding + "'\n" +
                "\trequiredFeatures='" + requiredFeatures + "'\n" +
                "}\n" + super.toString();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
