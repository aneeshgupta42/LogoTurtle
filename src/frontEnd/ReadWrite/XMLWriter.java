package frontEnd.ReadWrite;

import frontEnd.UserInterface;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/***
 * Class for writing out or "Downloading" XML files
 * for the View being run
 * Dependencies: Pass in the current UI being run
 * for the view you want to write out.
 *
 * Welll designed because:
 *  - Small and meaningful methods
 *  - No magic values
 *  - factors in exceptions
 *
 * @author Aneesh Gupta
 */
public class XMLWriter {

    private static final String xmlFilePath = "./data/savedfiles/";
    private static final String BACKGROUNDCOLOR = "backgroundcolor";
    private static final String NUMTURTLES = "numturtles";
    private static final String AUTH_ELEMENT = "author";
    private static final String PENCOLOR = "pencolor";
    private static final String TURTLEIMAGE = "turtleimage";
    private static final String LOGOFILE = "logofile";

    private String backgroundColor, numTurtles, penColor, turtleImage;

    private DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
    private Document document = documentBuilder.newDocument();
    private Element root;

    /***
     * Constructor for the writer object
     * Intiailizes the writing out mechanism,
     * @param UI : view to be written out
     * @throws Exception: writing or parsing exceptions
     */
    public XMLWriter(UserInterface UI) throws ParserConfigurationException {
        backgroundColor = UI.getDisplayWindow().getBackgroundColor();
        int temp = UI.getNumTurtles();
        numTurtles = Integer.toString(temp);
        penColor = UI.getMover().getLineColor().toString();
        turtleImage = UI.getMover().getImage().getImage().getUrl();
    }

    private void createRoot(){
        root = document.createElement("data");
        document.appendChild(root);
        Attr attr = document.createAttribute("media");
        attr.setValue("slogo");
        root.setAttributeNode(attr);

    }

    private void addInformation(){
        Element name = document.createElement(BACKGROUNDCOLOR);
        name.appendChild(document.createTextNode(backgroundColor));
        root.appendChild(name);

        Element author = document.createElement(AUTH_ELEMENT);
        author.appendChild(document.createTextNode("Team 17"));
        root.appendChild(author);

        Element numturts = document.createElement(NUMTURTLES);
        numturts.appendChild(document.createTextNode(numTurtles));
        root.appendChild(numturts);

        Element pencolor = document.createElement(PENCOLOR);
        pencolor.appendChild(document.createTextNode(penColor));
        root.appendChild(pencolor);

        Element turtImage = document.createElement(TURTLEIMAGE);
        turtImage.appendChild(document.createTextNode(turtleImage));
        root.appendChild(turtImage);
    }


    private void addElements(){
        createRoot();
        addInformation();
    }


    private void writeFile(){
        try {
            System.out.println("This is reached");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath+"turtle_saved_recent.xml"));
            transformer.transform(domSource, streamResult);
        }
        catch (TransformerException tfe) {
            System.out.println("Error function");
            throw new XMLException("Transformation fault");
        }
    }

    /***
     * Call both the above methods to, first initialize
     * the xml tree structure, and then write it out to a file
     * @throws ParserConfigurationException: exceptions
     */
    public void outputFile() throws ParserConfigurationException {
        addElements();
        writeFile();
    }
}
