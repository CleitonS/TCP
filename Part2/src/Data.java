public class Data {
    private String id;
    private String name;
    private String comment;

    public Data(String id, String name) {
        this.id = id;
        this.name = name;
        this.comment = null;
    }

    public Data(String id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "\tid='" + id + "'\n" +
                "\tname='" + name + "'\n" +
                "\tcomment='" + comment + "'\n";
    }
}
