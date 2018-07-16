public class Feature extends Data {
    private boolean hidden;

    public Feature(String id, String name) {
        super(id, name);
        this.hidden = false;
    }

    public Feature(String id, String name, boolean hidden){
        super(id, name);
        this.hidden = hidden;
    }

    public boolean isHidden(){
        return hidden;
    }

    @Override
    public String toString() {
        return "Feature{\n" +
                "\thidden=" + hidden + "\n" +
                "}\n" + super.toString();
    }
}
