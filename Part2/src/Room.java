import java.util.ArrayList;
import java.util.List;

public class Room extends Data {
    private boolean available;
    private int capacity;
    private String features;
    private String note;

    public Room(String id, String name) {
        super(id, name);
    }

    public Room(String id, String name, int capacity, String features){
        super(id, name);    //???
        this.available = true;  //se "available" não for fornecido, suponha true
        this.capacity = capacity;
        this.features = features;
        this.note = "";
    }

    public Room(String id, String name, boolean available, int capacity, String features){
        super(id, name);    //???
        this.available = available;
        this.capacity = capacity;
        this.features = features;
        this.note = "";
    }

    public Room(String id, String name, boolean available, int capacity, String features, String note){
        super(id, name);    //???
        this.available = available;
        this.capacity = capacity;
        this.features = features;
        this.note = note;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable(){
        return available;
    }

//    public void addFeature(Feature feature){
//        this.features.add(feature);
//    }

//    public List<Feature> getFeatures (){
//        List<Feature> features = new ArrayList<>();
//        String[] splitStrings = this.features.split(", ");
//
//
//        return features;
//    }

    public String getFeatures(){
        return features;
    }

    @Override
    public String toString() {
        return "Room{\n" +
                "\tavailable=" + available + "\n" +
                "\tcapacity=" + capacity + "\n" +
                "\tfeatures='" + features + "'\n" +
                "\tnote='" + note + '\'' +
                "}\n" + super.toString();
    }

//    public List<String> getFeaturesString(){
//        List<String> listFeatures = new ArrayList<String>();
//        for (int i = 0; i <= features.size(); i++){
//            listFeatures.add(features.get(i).getId());
//        }
//        return listFeatures;
//    }
}
