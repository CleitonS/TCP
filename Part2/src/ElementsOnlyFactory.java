import nu.xom.NodeFactory;
import nu.xom.Nodes;

/**
 * Created by Vinícius Chagas.
 */
public class ElementsOnlyFactory extends NodeFactory {
    private Nodes empty = new Nodes();

    @Override
    public Nodes makeComment(String s) {
        return empty;
    }

    @Override
    public Nodes makeDocType(String s, String s1, String s2) {
        return empty;
    }

    @Override
    public Nodes makeText(String s) {
        return empty;
    }

    @Override
    public Nodes makeProcessingInstruction(String s, String s1) {
        return empty;
    }
}
