package frontEnd;

import Controller.Control;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.Dimension;
import javafx.scene.shape.Rectangle;


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
  private static final String LANGUAGE_PROMPT  = "Language";
  private static final String[] languages = { "English", "Chinese", "French",
          "German", "Italian","Portuguese","Russian","Spanish","Urdu" };
  private static final String BACKGROUND_PROMPT  = "Background Color";
  private static final String PEN_PROMPT  = "Pen Color";
  private static final String[] colorNames = {"red", "yellow", "blue"};
  public static final Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
  private static final HashMap<String, Color> map = new HashMap<>();
  private BorderPane root;
  private Color lineColor = Color.BLACK;
  private ImageView turtleimage;
  private HBox hbox;
  private ScrollPane scrollPane;
  private TextArea inputArea;

  final WebView browser = new WebView();
  final WebEngine webEngine = browser.getEngine();


  public View() {
    myStage = new Stage();
    myTurtle = new Turtle(this);
    control = new Control();
    myLine = new Line();
    display = new Group();
    for (int i=0; i< colors.length; i++){
      map.put(colorNames[i], colors[i]);
    }

  }

  public BorderPane getRoot() {
    return root;
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
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene (int width, int height) {
    root = new BorderPane();
    hbox = addHBox();
    root.setTop(hbox);
    display_window = makeDisplayWindow();
    root.setLeft(display_window);
    root.setRight(makeSideWindow());
    //root.setBottom(makeCommandWindow());
    turtleimage = (ImageView) myTurtle.displayTurtle();
    setTurtlePosition(turtleimage);
    root.getChildren().addAll(turtleimage, browser);
    return new Scene(root);
  }



  public void closeWindow(){
    myStage.close();
  }

  private Node makeDisplayWindow(){
    VBox vbox = new VBox();
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    //rectangle = new Rectangle();
    rectangle.getStyleClass().add("rectangle");
    vbox.getChildren().addAll(rectangle, makeCommandWindow());
    //turtleimage.setX(750);
    display.getChildren().addAll(vbox);
    return display;
  }

  public void setTurtlePosition(ImageView image) {
    image.setX(DISPLAY_WIDTH/2-image.getBoundsInLocal().getWidth()/2);
    image.setY(70 + DISPLAY_HEIGHT/2-image.getBoundsInLocal().getHeight()/2);
    image.setRotate(0);
    System.out.println(display.getLayoutY());
    myTurtle.initializeLinePosition(image.getX(), image.getY(), image.getRotate());
    myTurtle.setTurtleInitialCords(image.getX(), image.getY());
  }

  private Node makeSideWindow() {
    TabPane tabPane = new TabPane();
    tabPane.setMinWidth(300);
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    scrollPane = new ScrollPane();
    Tab tab1 = new Tab("History", scrollPane);

    Tab tab2 = new Tab("Variables"  , new Label("Show all cars available"));
    Tab tab3 = new Tab("Commands" , new Label("Show all boats available"));

    tabPane.getTabs().add(tab1);
    tabPane.getTabs().add(tab2);
    tabPane.getTabs().add(tab3);

    VBox vBox = new VBox(tabPane);
    Scene scene = new Scene(vBox);

    return tabPane;
  }

  public void addNodeToRoot(Node object){
    root.getChildren().add(object);
  }


  private Node makeCommandWindow(){
    HBox hbox = new HBox();
    inputArea = new TextArea();
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
    VBox box = new VBox();


    runButton.setOnAction(action -> {
      myText = inputArea.getText();
      String thistext = myText;

      control.passCommand(myText);
      control.passTurtle(myTurtle);
      control.parseCommand();
      TextArea text = new TextArea();
      Hyperlink link = new Hyperlink();
      link.getStyleClass().add("hyper-link");
      link.setText(myText);
      link.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          inputArea.setText(thistext);
        }
      });
      text.setText(myText);
      text.setEditable(false);
      text.setDisable(true);
      //setAction(text);
      box.getChildren().add(0, link);
     /* text.setOnMouseClicked(e -> {
            inputArea.setText(text.getText());
            System.out.println(text.getText());
          });*/
      scrollPane.setContent(box);
      inputArea.setText("");

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

  private void setAction(TextArea text){
    EventHandler<ActionEvent> event =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            inputArea.setText(text.getText());
            System.out.println("hi");
          }
        };
  }
  private void stopEverything(){
    System.exit(1);
  }

  public HBox addHBox() {
    HBox hbox = new HBox();
    hbox.getStyleClass().add("hbox");
    hbox.setPrefHeight(70);
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
//    hbox.setStyle("-fx-background-color: #336699");

    Button help = new Button("Help");
    help.setPrefSize(100, 20);
    Stage stage2 = new Stage();
    TextArea textArea = new TextArea();
    EventHandler<ActionEvent> event4 =
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e)
          {
            textArea.setText("HELPPPPPPPPPPP");
            stage2.setScene(new Scene(textArea, 400, 400));
            stage2.show();

            //webEngine.load("www.cs.duke.edu/courses/spring20/compsci308/assign/03_parser/commands.php");
            //rectangle.setFill(map.get(reset_display.getValue().toString()));
          }
        };
    // Set on action
    help.setOnAction(event4);

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

    final ColorPicker colorBackgroundPicker = new ColorPicker();
    colorBackgroundPicker.setValue(Color.GREY);

    Text backgrouondText = new Text(BACKGROUND_PROMPT + ":");

    //penText.setFont(Font.font ("Verdana", 20));
    //backgrouondText.setFill(colorBackgroundPicker.getValue());

    colorBackgroundPicker.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        //penText.setFill(colorPenPicker.getValue());
        rectangle.setFill(colorBackgroundPicker.getValue());
      }
    });

    final ColorPicker colorPenPicker = new ColorPicker();
    colorPenPicker.setValue(Color.BLACK);

    Text penText = new Text(PEN_PROMPT + ":");

    //penText.setFont(Font.font ("Verdana", 20));
    //penText.setFill(colorPenPicker.getValue());

    colorPenPicker.setOnAction(new EventHandler() {
      @Override
      public void handle(Event event) {
        //penText.setFill(colorPenPicker.getValue());
        myLine.setStroke(colorPenPicker.getValue());
        lineColor = colorPenPicker.getValue();
      }
    });


    hbox.getChildren().addAll(help, reset_display, button, language_box, browser, backgrouondText, colorBackgroundPicker, penText, colorPenPicker);
    HBox.setHgrow(browser, Priority.ALWAYS);
    return hbox;
  }

  private void resetScreen() {
    root.getChildren().removeIf(object -> object instanceof Line);
    setTurtlePosition(turtleimage);
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
