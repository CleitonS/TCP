import java.io.File;
import java.util.List;

public interface IReceiver {
    /**
     * Carrega um arquivo e extrai dele seus dados.
     * A implementação chamará o método apropriado
     * do Reader utilizado para tal.
     * @param file Arquivo de entrada contendo os dados.
     * @return Lista bruta com todos os dados lidos.
     */
    List<Data> load(File file);

    /**
     * Salva os dados solicitados no arquivo especificado,
     * no formato implementado pelo Reader.
     * @param data Lista de dados a serem escritos.
     * @param file Arquivo de saída a ser criado.
     */
    void save(List<Data> data, File file);
}
