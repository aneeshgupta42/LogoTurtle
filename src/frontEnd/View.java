package frontEnd;

import Controller.Control;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.Dimension;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;


public class View extends Application {
  private Scene myScene;
  private Group display;
  private Stage myStage;

  private Turtle myTurtle;
  private Pen myPen;
  private Control control;
  private Rectangle rectangle;
  private Line myLine;

  public static final String TITLE = "JavaFX Animation Example";
  public static final Dimension DEFAULT_SIZE = new Dimension(1200, 1200);
  private static final int DISPLAY_WIDTH = 1000;
  private static final int DISPLAY_HEIGHT = 500;
  private Node myActor;
  private TextArea myCommander;
  private static final String TURTLE = "turtle.png";
  private String myText;
  private static final String RESOURCES = "resources.languages.";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "" + RESOURCES + "/";
  public static final String STYLE_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
  private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
  private static final String STYLESHEET = "default.css";
  private ResourceBundle styleResources;
  private Node display_window;
  private double display_height;
  private double display_width;
  private static final String LANGUAGE_PROMPT  = "Language";
  private static final String[] languages = { "English", "Chinese", "French",
          "German", "Italian","Portuguese","Russian","Spanish","Urdu" };
  private static final String BACKGROUND_PROMPT  = "Background Color";
  private static final String PEN_PROMPT  = "Pen Color";
  private static final String[] colorNames = {"red", "yellow", "blue"};
  public static final Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
  private static final HashMap<String, Color> map= new HashMap<>();
  private BorderPane root;
  private Color lineColor = Color.BLACK;
  private ImageView turtleimage;


  public View() {
    myStage = new Stage();
    myTurtle = new Turtle(this);
    //control = new Control(myTurtle);
    control = new Control();
    myLine = new Line();
    display = new Group();
    //pass in turtle to control (THis was changed, change also in control)
    for (int i=0; i< colors.length; i++){
      map.put(colorNames[i], colors[i]);
    }

  }

  @Override
  public void start(Stage primaryStage){
    myStage.setTitle(TITLE);
    View view = new View();
    myStage.setX(0);
    makeDisplayWindow();
    makeCommandWindow();
    myScene = makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height);
    myScene.getStylesheets().add("default.css");
    myStage.setScene(myScene);
    myStage.show();
    myStage.setOnCloseRequest(t->stopEverything());
    Animation animation = makeAnimation(myActor);
    //animation.play();
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene (int width, int height) {
    root = new BorderPane();
    HBox hbox = addHBox();
    root.setTop(hbox);
    display_window = makeDisplayWindow();
    root.setLeft(display_window);
    root.setRight(makeSideWindow());
    root.setBottom(makeCommandWindow());
    return new Scene(root);
  }

  // create something to animate
  private Node makeActor () {
    Shape result = new Rectangle(50, 50, 50, 50);
    result.setFill(Color.PLUM);
    return result;
  }


  public void closeWindow(){
    myStage.close();
  }

  private Node makeDisplayWindow(){
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    //rectangle = new Rectangle();
    rectangle.getStyleClass().add("rectangle");
    turtleimage = (ImageView) myTurtle.displayTurtle();
    setTurtlePosition(turtleimage);
    //turtleimage.setX(750);
    display.getChildren().addAll(rectangle, turtleimage);
    return display;
  }

  public void setTurtlePosition(ImageView image) {
    image.setX(DISPLAY_WIDTH/2-image.getBoundsInLocal().getWidth()/2);
    image.setY(DISPLAY_HEIGHT/2-image.getBoundsInLocal().getHeight()/2);
    image.setRotate(0);
    myTurtle.initializeLinePosition(image.getX(), image.getY(), image.getRotate());
  }

  public void setLine(Line line) {
    myLine = line;
    //myLine.getStyleClass().add(getLineColor());
  }

  private Node makeSideWindow() {
    GridPane gridPane = createGridPane();
   // gridPane.setMinHeight(800);
    gridPane.setMinWidth(400);
    return gridPane;
  }

  public void addLineToRoot(Line line){
    display.getChildren().add(line);
  }

  private Node makeCommandWindow(){
    HBox hbox = new HBox();
    TextArea inputArea = new TextArea();
    myCommander = inputArea;
    inputArea.setPromptText("Enter Command");
    inputArea.setPrefColumnCount(10);
    inputArea.getText();
    GridPane.setConstraints(inputArea, 0, 0);
    inputArea.setMinWidth(1100);
    inputArea.setMaxHeight(200);
    VBox vbox = new VBox();
    Button runButton = new Button("Run");
    runButton.setPrefSize(100, 20);


    runButton.setOnAction(action -> {
      myText = inputArea.getText();

      control.passCommand(myText);
      control.passTurtle(myTurtle);
      control.parseCommand();
      //myTurtle.move(50.5,50.666,0);
    });
    Button clearButton = new Button("Clear Text");
    clearButton.setPrefSize(100, 20);

    clearButton.setOnAction(action -> {
      inputArea.setText("");

    });
    vbox.getChildren().addAll(runButton, clearButton);
    hbox.getChildren().addAll(inputArea, vbox);

    return hbox;
  }


  private void stopEverything(){
    System.exit(1);
  }

  private Animation makeAnimation (Node agent) {
    // create something to follow
    Path path = new Path();
    path.getElements().addAll(new MoveTo(50, 50), new HLineTo(350));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(4), path, agent);
    // create an animation that rotates the shape
    RotateTransition rt = new RotateTransition(Duration.seconds(3));
    rt.setByAngle(90);
    // put them together in order
    return new SequentialTransition(agent, pt, rt);
  }

  public HBox addHBox() {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
//    hbox.setStyle("-fx-background-color: #336699");

    Button buttonCurrent = new Button("Help");
    buttonCurrent.setPrefSize(100, 20);

    Button reset_display = new Button("Reset Display");
    reset_display.setPrefSize(100, 20);
    EventHandler<ActionEvent> event2 =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            resetScreen();
            //rectangle.setFill(map.get(reset_display.getValue().toString()));
          }
        };
    // Set on action
    reset_display.setOnAction(event2);

    FileChooser fileChooser = new FileChooser();
    Button button = new Button("Select File");
    button.setOnAction(e -> {
      fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
      File selectedFile = fileChooser.showOpenDialog(myStage);
      if(selectedFile!= null){
        try {
          scanFile(selectedFile);
        } catch (FileNotFoundException ex) {
          ///****fix this******
          ex.printStackTrace();
        }
      }
    });
    // Create a combo box
    ComboBox language_box =
        new ComboBox(FXCollections
            .observableArrayList(languages));
    language_box.setPromptText(LANGUAGE_PROMPT);

    //Create action event
    EventHandler<ActionEvent> event =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            control.passLanguage(language_box.getValue().toString());
          }
        };
    // Set on action
    language_box.setOnAction(event);
    // Create a tile pane

    control.passLanguage("English");
    ComboBox background_box =
        new ComboBox(FXCollections
            .observableArrayList(colorNames));
    background_box.setPromptText(BACKGROUND_PROMPT);

    //Create action event
    EventHandler<ActionEvent> event1 =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            rectangle.setFill(map.get(background_box.getValue().toString()));
          }
        };
    // Set on action
    background_box.setOnAction(event1);
    // Create a tile pane

    ComboBox pen_box =
        new ComboBox(FXCollections
            .observableArrayList(colorNames));
    pen_box.setPromptText(PEN_PROMPT);

    //Create action event
    EventHandler<ActionEvent> event3 =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            myLine.setStroke(map.get(pen_box.getValue().toString()));
            myLine.getStyleClass().add(pen_box.getValue().toString());
            //lineColor = pen_box.getValue().toString();
            lineColor = map.get(pen_box.getValue());
          }
        };
    // Set on action
    pen_box.setOnAction(event3);

    hbox.getChildren().addAll(buttonCurrent, reset_display, button, language_box, background_box, pen_box);
    return hbox;
  }

  private void resetScreen() {
    setTurtlePosition(turtleimage);
    display.getChildren().removeIf(object -> object != turtleimage && object != rectangle);
  }

  private void scanFile(File file) throws FileNotFoundException {
    Scanner scnr = new Scanner(file);
    //Reading each line of file using Scanner class
    int lineNumber = 0;
    while(scnr.hasNextLine()){
      String line = scnr.nextLine();
      myCommander.setText(myCommander.getText()+line+"\n");
      lineNumber++;
    }
  }

  public String getText(){
    return myText;
  }

  private GridPane createGridPane() {
    GridPane grid = new GridPane();
    grid.getStyleClass().add("grid-pane");
    return grid ;
  }

  public static void main(String[] args) {
    launch(args);
  }

  public Color getLineColor() {
    return lineColor;
  }
}
