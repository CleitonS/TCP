import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinícius Chagas.
 */
public class Receiver implements IReceiver {
    private IReader reader;

    public Receiver(IReader reader) {
        this.reader = reader;
    }

    /**
     * Carrega o arquivo especificado no leitor e processa
     * seus dados.
     *
     * @param file Arquivo de entrada contendo os dados.
     * @return Lista com os dados lidos.
     */
    @Override
    public List<Data> load(File file) {
        List<Data> processedData;

        reader.open(file);
        processedData = reader.parse();

        return processedData;
    }

    /**
     * Salva os dados solicitados em um arquivo,
     * de acordo com o formato definido pelo Reader.
     *
     * @param data Lista de dados a serem escritos.
     * @param file Arquivo de saída a ser criado.
     */
    @Override
    public void save(List<Data> data, File file) {

    }

    public List<String> write(Booking book){
        /*
        Gera a lista
        <book.name, book.id, book.goup.numStudents,  book.group.teacher, book.group.id, book.room.id,
        book.duration, book.building.id, book.dayOfWeek, book.time>
        */

        List<String> listData = new ArrayList<String>();
        listData.add(book.getName());
        listData.add(book.getId());
        listData.add(String.valueOf(book.getGroup().getNumStudents()));
        listData.add(book.getGroup().getTeacher());
        listData.add(book.getGroup().getId());
        listData.add(book.getRoom().getId());
        listData.add(book.getDuration().toString());
        listData.add(book.getBuilding().getId());
        listData.add(book.getWeekday().toString());
        listData.add(book.getTime().toString());
        return listData;
    }
}
