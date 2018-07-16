import java.util.ArrayList;
import java.util.List;

public class Course extends Data {
    private List<Group> groups;

    public Course(String id, String name) {
        super(id, name);
        this.groups = new ArrayList<>();
    }

    public void addGroup(Group group){
        this.groups.add(group);
    }

    public List<Session> listAllSessions(){
        List<Session> sessions = new ArrayList<>();

        for (Group group : groups) {
            List<Session> groupSessions = group.listSessions();
            sessions.addAll(groupSessions);
        }

        return sessions;
    }

    public List<Group> listGroups(){
        return groups;
    }

    @Override
    public String toString() {
        return "Course{\n" +
                "\tgroups=" + groups + "\n" +
                "}\n" + super.toString();
    }
}
