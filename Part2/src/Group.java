import java.util.ArrayList;
import java.util.List;

public class Group extends Data {
    private String teacher;
    private int numStudents;
    private List<Session> sessions;

    public List<Session> listSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        this.sessions.add(session);
    }

    public Group(String id, String name) {
        super(id, name);
        this.sessions = new ArrayList<>();
    }

    public Group(String id, String name, String teacher, int numStudents) {
        super(id, name);
        this.teacher = teacher;
        this.numStudents = numStudents;
        this.sessions = new ArrayList<>();
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    @Override
    public String toString() {
        return "Group{\n" +
                "\tteacher='" + teacher + "'\n" +
                "\tnumStudents=" + numStudents + "\n" +
                "\tsessions=" + sessions + "\n" +
                "}\n" + super.toString();
    }
}
