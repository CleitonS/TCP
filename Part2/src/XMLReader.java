import nu.xom.*;

import javax.management.AttributeNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
//import java.io.InputStream;

/**
 * Created by Vinícius Chagas.
 */
public class XMLReader implements IReader {
    private Document document;
    private Builder builder;
    private File file;
    private Element root;

    /**
     * Inicializa um novo XOM Builder, que
     * receberá o XML a ser lido posteriormente.
     */
    public XMLReader() {
        //ElementsNodeFactory é um "override" do NodeFactory padrão
        //usado pelo XOM. Criei pra poder descartar absolutamente
        //toda e qualquer tag do XML que não seja um Element.
        this.builder = new Builder(new ElementsOnlyFactory());
    }

    /**
     * Abre o arquivo especificado e constrói
     * sua representação interna dentro do
     * XOM Builder criado anteriormente.
     *
     * @param file Arquivo a ser aberto
     */
    @Override
    public void open(File file) {
        try {
            this.file = file;
            this.document = builder.build(file);
        } catch (ParsingException e) {
            System.out.println("Erro de parsing!\n" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro de I/O!\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Libera da memória o documento lido
     * e o caminho de arquivo.
     */
    @Override
    public void close() {
        this.document = null;
        this.file = null;
    }

    /**
     * Analiza o documento lido e o converte
     * para um formato padronizado, compatível
     * com os outros módulos do programa.
     *
     * Assim é possível implementar leitores
     * para qualquer formato de arquivo, já
     * que o resultado final estará sempre
     * padronizado.
     *
     * @return Lista de dados lidos
     */
    @Override
    public List<Data> parse() {
        Element curElement = document.getRootElement();
        List<Data> parsedXML = new ArrayList<>();

        parseAux(curElement, parsedXML);

        return parsedXML;
    }

    @Override
    public void write(File file) {

    }

    /**
     * Função auxiliar para a função parse(),
     * é aqui que o processamento é feito de
     * fato. Optou-se por ter uma função
     * auxiliar, já que assim esconde-se do
     * usuário a necessidade de passar
     * parâmetros.
     *
     * @param element nodo a ser analizado
     * @param parsedXML a lista de dados a ser preenchida
     *
     * @see #parse()
     */
    private void parseAux(Element element, List<Data> parsedXML){
        Data data = null;
        String tag = element.getQualifiedName();

        /*
        disclaimer:
        o XOM possibilita que eu faça isso de uma forma BEM mais elegante
        criando listas somente com os nodos-filhos que contenham determinada tag.

        como não é o que acontece aqui, se ele ler algo que não é uma das tags
        predefinidas, ele vai deixar o data contendo null. Pra que isso não
        vá pra lista final, eu descarto na cara dura mesmo.

        eu usei isso lá nas funções auxiliares e acabei não usando aqui por pura falta
        de tempo. Acontece.

        todo: Implementar usando métodos do XOM
        */
        switch (tag) {
            case "course":
                try {
                    data = processCourse(element);
                } catch (AttributeNotFoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "feature":
                try {
                    data = processFeature(element);
                } catch (AttributeNotFoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                break;
            case "building":
                try {
                    data = processBuilding(element);
                } catch (AttributeNotFoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

        //descarta os dados que forem nulos
        if (!(data == null)) parsedXML.add(data);

        for (int i = 0; i < element.getChildCount(); i++) {
            parseAux((Element)element.getChild(i),parsedXML);
        }
    }

    private Course processCourse(Element element) throws AttributeNotFoundException {
        String name = null;
        String id = null;

        for (int i = 0; i < element.getAttributeCount(); i++) {
            Attribute attribute = element.getAttribute(i);
            String attributeName = attribute.getQualifiedName();
            String attributeValue = attribute.getValue();

            switch (attributeName) {
                case "name":
                    name = attributeValue;
                    break;
                case "id":
                    id = attributeValue;
                    break;
                default:
                    throw new AttributeNotFoundException("Atributo inválido: " + attributeName);
            }
        }

        assert id != null;
        assert name != null;
        Course course = new Course(id,name);

        Elements groups = element.getChildElements("group");
        for (int i = 0; i < groups.size(); i++) {
            Group group;
            try {
                group = processGroup(groups.get(i));
                group.setName(name);
                course.addGroup(group);
            } catch (AttributeNotFoundException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        return course;
    }

    private Group processGroup(Element element) throws AttributeNotFoundException {
        String id = null;
        String teacher = null;
        int numStudents = 0;

        for (int i = 0; i < element.getAttributeCount(); i++) {
            Attribute attribute = element.getAttribute(i);
            String attributeName = attribute.getQualifiedName();
            String attributeValue = attribute.getValue();

            switch (attributeName) {
                case "number_of_students":
                    numStudents = Integer.parseInt(attributeValue);
                    break;
                case "teacher":
                    teacher = attributeValue;
                    break;
                case "id":
                    id = attributeValue;
                    break;
                default:
                    throw new AttributeNotFoundException("Atributo inválido: " + attributeName);
            }
        }

        assert id != null;
        assert teacher != null;
        assert numStudents > 0;
        Group group = new Group(id, "", teacher, numStudents);

        Elements sessions = element.getChildElements("session");
        for (int i = 0; i < sessions.size(); i++) {
            Session session = processSession(sessions.get(i));
            session.setGroup(group);
            group.addSession(session);
        }

        return group;
    }

    private Session processSession(Element element) throws AttributeNotFoundException {
        DayOfWeek dayOfWeek = null;
        LocalTime time = null;
        Duration duration = null;
        String requiredRoom = "";
        String requiredBuilding = "";
        String requiredFeatures = "";

        for (int i = 0; i < element.getAttributeCount(); i++) {
            Attribute attribute = element.getAttribute(i);
            String attributeName = attribute.getQualifiedName();
            String attributeValue = attribute.getValue();

            switch (attributeName){
                case "weekday":
                    dayOfWeek = DayOfWeek.of(Integer.parseInt(attributeValue));
                    break;
                case "start_time":
                    time = LocalTime.parse(attributeValue);
                    break;
                case "requires_room_id":
                    requiredRoom = attributeValue;
                    break;
                case "requires_building_id":
                    requiredBuilding = attributeValue;
                    break;
                case "duration":
                    if (!attributeValue.isEmpty())
                        duration = Duration.ofMinutes(Long.parseLong(attributeValue));
                    else
                        duration = Duration.ZERO;   //isso vai dar uma banana no alocador...
                    break;
                case "feature_ids":
                    requiredFeatures = attributeValue;
                    break;
                case "features_ids":
                    requiredFeatures = attributeValue;
                    break;
                case "room_id":
                    break;
                case "building_id":
                    break;
                default:
                    throw new AttributeNotFoundException("Atributo inválido: " + attributeName);
            }
        }

        assert dayOfWeek != null;
        assert time != null;
        assert duration != null;

        Session session = new Session("", "", dayOfWeek, time, duration);
        session.setRequiredBuilding(requiredBuilding);
        session.setRequiredFeatures(requiredFeatures);
        session.setRequiredRoom(requiredRoom);

        return session;
    }

    private Feature processFeature(Element element) throws AttributeNotFoundException {
        String name = null;
        String id = null;
        boolean hidden = false;

        for (int i = 0; i < element.getAttributeCount(); i++) {
            Attribute attribute = element.getAttribute(i);
            String attributeName = attribute.getQualifiedName();
            String attributeValue = attribute.getValue();

            switch (attributeName){
                case "name":
                    name = attributeValue;
                    break;
                case "id":
                    id = attributeValue;
                    break;
                case "hidden":
                    hidden = Boolean.parseBoolean(attributeValue);
                    break;
                default:
                    throw new AttributeNotFoundException("Atributo inválido: " + attributeName);
            }
        }

        assert name != null;
        assert id != null;
        Feature feature = new Feature(id, name, hidden);

        return feature;
    }

    private Building processBuilding(Element element) throws AttributeNotFoundException {
        String id = null;

        for (int i = 0; i < element.getAttributeCount(); i++) {
            Attribute attribute = element.getAttribute(i);
            String attributeName = attribute.getQualifiedName();
            String attributeValue = attribute.getValue();

            if (attributeName.equals("id"))
                id = attributeValue;
            else throw new AttributeNotFoundException("Atributo inválido: " + attributeName);
        }

        assert id != null;
        Building building = new Building(id, "");

        Elements rooms = element.getChildElements("room");
        for (int i = 0; i < rooms.size(); i++) {
            Room room;
            room = processRoom(rooms.get(i));
            building.addRoom(room);
        }

        return building;
    }

    private Room processRoom(Element element) throws AttributeNotFoundException {
        boolean available = true;
        int capacity = 0;
        String id = null;
        String note = "";
        String features = null;

        for (int i = 0; i < element.getAttributeCount(); i++) {
            Attribute attribute = element.getAttribute(i);
            String attributeName = attribute.getQualifiedName();
            String attributeValue = attribute.getValue();

            switch (attributeName){
                case "id":
                    id = attributeValue;
                    break;
                case "number_of_places":
                    capacity = Integer.parseInt(attributeValue);
                    break;
                case "note":
                    note = attributeValue;
                    break;
                case "available_for_allocation":
                    available = Boolean.parseBoolean(attributeValue);
                    break;
                case "feature_ids":
                    features = attributeValue;
//                    System.out.println("teste:" + features);
                    break;
                default:
                    throw new AttributeNotFoundException("Atributo inválido: " + attributeName);
            }
        }

        assert id != null;
        assert capacity > 0;
        Room room = new Room(id,"",available,capacity,features,note);

        return room;
    }

    public String readLine() {
        return null;
    }

    public char readChar() {
        return 0;
    }


    public void addBookToWrite(List<String> listData){
        /*
        Receeb a lista:
        <book.name, book.id, book.goup.numStudents,
        book.group.teacher, book.group.id, book.room.id,
        book.duration, book.building.id, book.dayOfWeek, book.time>
        e gera:

        <course name='' id=''>
		- <group number_of_students='' teacher='' id=''>
		  <session room_id='' duration='' building_id='' weekday='' start_time=''/>
		  </group>
		  </course>
        */

        Element course = new Element("course");
        Attribute atCourseName = new Attribute("name", listData.get(0));
        Attribute atCourseId = new Attribute("id", listData.get(1));
        course.addAttribute(atCourseName);
        course.addAttribute(atCourseId);

        Element group = new Element("group");
        Attribute atGroupNumStudents = new Attribute("number_of_students", listData.get(2));
        Attribute atGroupTeacher = new Attribute("teacher", listData.get(3));
        Attribute atGroupId = new Attribute("id", listData.get(4));
        group.addAttribute(atGroupNumStudents);
        group.addAttribute(atGroupTeacher);
        group.addAttribute(atGroupId);

        Element session = new Element("session");
        Attribute atSessionRoom = new Attribute("room_id", listData.get(5));
        Attribute atSessionDuration = new Attribute("duration", listData.get(6));
        Attribute atSessionBuilding = new Attribute("building_id", listData.get(7));
        Attribute atSessionWeekDay = new Attribute("weekday", listData.get(8));
        Attribute atSessionTime = new Attribute("start_time", listData.get(9));
        session.addAttribute(atSessionRoom);
        session.addAttribute(atSessionDuration);
        session.addAttribute(atSessionBuilding);
        session.addAttribute(atSessionWeekDay);
        session.addAttribute(atSessionTime);

        group.appendChild(session);
        course.appendChild(group);
        this.root.appendChild(course);

    }

    /*INACABADO!!!!*/
    public void generate() throws IOException {
        /*Depois de passar todos os elementos que desejamos colocar no arquivo de saida utilizando a função addBookToWrite,
        * geramos o arquivo xml */
        Document doc = new Document(root);
        //System.out.println(doc.toXML());
        FileWriter file = new FileWriter("booking.xml");
        PrintWriter writerFile = new PrintWriter(file);
        writerFile.printf(doc.toXML());
        file.close();
    }




}
