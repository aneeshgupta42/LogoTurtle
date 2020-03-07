package frontEnd.ReadWrite.test;


/**
 * Testing out the XML writer, by reading in a file.
 * And then writing it out.
 */
public class TestXMLWriter {
    /**
     * Start of the program.
     */
    private static final String DATADIR = "data/percolationLayout.xml";
    private static final String DISP_MSG = "Testing out XML writing";

    public static void main (String[] args) throws Exception {
        System.out.println(DISP_MSG);
//        XMLReader reader = new XMLReader("media");
//        Game game = reader.getGame(DATADIR);
//        GridCreator creator = new GridCreator();
//        Grid myGrid = creator.newGridSelector(game);
//        XMLWriter writer = new XMLWriter(myGrid,game);
//        writer.outputFile();
    }
}
