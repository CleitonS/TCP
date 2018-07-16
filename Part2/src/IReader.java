import java.io.File;
import java.util.List;

public interface IReader {
    /**
     * Abre o arquivo contendo as demandas e recursos.
     * @param file Arquivo a ser lido - o formato depende
     *             do leitor implementado.
     */
    void open(File file);

    /**
     * Fecha o arquivo lido e limpa as referências a ele.
     */
    void close();

    /**
     * Analisa o arquivo aberto e retorna uma representação
     * padronizada do seu conteúdo, a fim de transmití-la
     * para os outros módulos do programa.
     *
     * @return Lista de dados contendo o conteúdo do arquivo
     */
    List<Data> parse();

    void write(File file);
}
