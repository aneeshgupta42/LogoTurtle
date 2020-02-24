package frontEnd;

import Controller.Control;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
  private GridPane grid;
  private Stage myStage;

  private Turtle myTurtle;
  private Pen myPen;
  private Control control;

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
  private static final String[] backgroundColor = {"red", "yellow"};
  private static final String PEN_PROMPT  = "Pen Color";
  private static final String[] penColor = {"red", "yellow", "blue"};


  public View() {
    control = new Control();
    myStage = new Stage();
    myTurtle = new Turtle();
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
    BorderPane root = new BorderPane();
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
    /*Group gridPane = new Group();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(0, 10, 0, 10));
    //gridPane.setMaxSize(1200, 400);
    gridPane.setMinHeight(400);
    gridPane.setMinWidth(1000);
    gridPane.setAlignment(Pos.CENTER);
    display_height = gridPane.getHeight();
    Node turtleimage = myTurtle.displayTurtle();
    Button button2 = new Button("Clear");
    GridPane.setConstraints(turtleimage, 50, 6);
    gridPane.getChildren().add(turtleimage);
    return gridPane;*/
    Group gridPane = new Group();
    Rectangle rect = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rect.getStyleClass().add("group");
    ImageView turtleimage = (ImageView) myTurtle.displayTurtle();
    setTurtlePosition(turtleimage);
    //turtleimage.setX(750);
    gridPane.getChildren().addAll(rect, turtleimage);
    return gridPane;
  }

  private void setTurtlePosition(ImageView image) {
    image.setX(DISPLAY_WIDTH/2-image.getBoundsInLocal().getWidth()/2);
    image.setY(DISPLAY_HEIGHT/2-image.getBoundsInLocal().getHeight()/2);
  }


  private Node makeSideWindow() {
    GridPane gridPane = createGridPane();
   // gridPane.setMinHeight(800);
    gridPane.setMinWidth(400);
    return gridPane;
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
      control.parseCommand();
      myTurtle.move(50.5,50.666,0);
    });
    Button clearButton = new Button("Clear");
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

    Button buttonCurrent = new Button("Current");
    buttonCurrent.setPrefSize(100, 20);

    Button buttonProjected = new Button("Projected");
    buttonProjected.setPrefSize(100, 20);
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

    ComboBox background_box =
        new ComboBox(FXCollections
            .observableArrayList(backgroundColor));
    background_box.setPromptText(BACKGROUND_PROMPT);

    //Create action event
    EventHandler<ActionEvent> event1 =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            // add something here
          }
        };
    // Set on action
    background_box.setOnAction(event1);
    // Create a tile pane

    ComboBox pen_box =
        new ComboBox(FXCollections
            .observableArrayList(penColor));
    pen_box.setPromptText(PEN_PROMPT);

    //Create action event
    EventHandler<ActionEvent> event2 =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            // add something here
          }
        };
    // Set on action
    pen_box.setOnAction(event2);

    hbox.getChildren().addAll(buttonCurrent, buttonProjected, button, language_box, background_box, pen_box);
    return hbox;
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
}
