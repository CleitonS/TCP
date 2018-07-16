import java.io.File;

public interface IAllocator {
    void loadRequests(File file);
    void writeResults(File file);
    void allocate(Session session) throws Exception;
    void unbook(Booking booking)throws Exception;
}
