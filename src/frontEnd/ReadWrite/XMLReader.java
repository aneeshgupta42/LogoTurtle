package frontEnd.ReadWrite;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


/**
 * This class handles parsing XML files and returning a completed object.
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * extended by Aneesh Gupta, Shruthi Kumar, Chris Warren
 */

public class XMLReader {
    // Readable error message that can be displayed by the GUI
    public static final String ERROR_MESSAGE = "XML file does not represent %s";
    public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
    public static final String INCORRECT_DATATYPE = "XML file has incorrect data type";
    // name of root attribute that notes the type of file expecting to parse
    private static final String xmlFilePath = "./data/savedfiles/";
    private static final String BACKGROUNDCOLOR = "backgroundcolor";
    private static final String NUMTURTLES = "numturtles";
    private static final String AUTH_ELEMENT = "author";
    private static final String PENCOLOR = "pencolor";
    private static final String TURTLEIMAGE = "turtleimage";
    private static final String LOGOFILE = "logofile";

    private final String TYPE_ATTRIBUTE;
    private static final int FIRE = 4;
    private static final int SEGREGATION = 2;
    private static final int RPS = 5;
    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    private int choice, isLayout, myRows, myCols, myShape;
    private String simulationName, author, myLayout;
    private float myProb;
    private double myThreshold;


    /**
     * Create parser for XML files of given type.
     */
    public XMLReader (String type) {
        DOCUMENT_BUILDER = getDocumentBuilder();
        TYPE_ATTRIBUTE = type;
    }

    /***
     * Get the game from the xml file,
     * extracting all required information
     * @param fname: file name
     * @return: Game object
     * @throws XMLException: various hit cases
     */
    public Game getGame (String fname) throws XMLException{
        File dataFile = new File(fname);
        Element root = getRootElement(dataFile);
        if (! isValidFile(root, Game.DATA_TYPE)) {
            System.out.println("Going here");
            throw new XMLException(ERROR_MESSAGE, Game.DATA_TYPE);
        }
        // read data associated with the fields given by the object
        readBasic(root);
        if(choice == FIRE){//Fire
//            myProb = Float.parseFloat(getTextValue(root, PROBELEMENT));
            return new Game(simulationName, author, choice, isLayout, myRows, myCols, myProb, myLayout, myShape);
        }
        if(choice == SEGREGATION){//Segregation
//            myThreshold = Double.parseDouble(getTextValue(root, THRESHOLDELEMENT));
            return new Game(simulationName, author, choice, isLayout, myRows, myCols, myThreshold, myLayout, myShape);
        }
        if(choice == RPS) {
//            int myThreshold = (int) Double.parseDouble(getTextValue(root, THRESHOLDELEMENT));
            return new Game(simulationName, author, choice, isLayout, myRows, myCols, myThreshold, myLayout, myShape);
        }
        return new Game(simulationName, author, choice, isLayout, myRows, myCols, myLayout, myShape);
    }

    private void readBasic(Element root) throws XMLException{
        try{

        }
        catch (NumberFormatException e){
            throw new XMLException(INCORRECT_DATATYPE, Game.DATA_TYPE);
        }
    }

    // get root element of an XML file
    private Element getRootElement (File xmlFile) {
        try {
            DOCUMENT_BUILDER.reset();
            Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            throw new XMLException(e);
        }
    }

    // returns if this is a valid XML file for the specified object type
    private boolean isValidFile (Element root, String type) {
        return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
    }

    // get value of Element's attribute
    private String getAttribute (Element e, String attributeName) {
        return e.getAttribute(attributeName);
    }

//    private boolean missingFields(Element root,)

    // get value of Element's text
    private String getTextValue (Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0 && !nodeList.item(0).getTextContent().equals("")) {
            return nodeList.item(0).getTextContent();
        }
        else {
            throw new XMLException(CORRUPTED_FIELD + ": " + tagName, Game.DATA_TYPE);
        }
    }

    // boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            throw new XMLException(e);
        }
    }
}

