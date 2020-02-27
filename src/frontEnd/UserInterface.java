package frontEnd;

import Controller.Control;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.Dimension;
import javafx.scene.shape.Rectangle;


public class UserInterface extends Application {
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
  private static final String COMMAND_ONE = "viewboc.png";
  private static final String COMMAND_TWO = "viewbox.png";
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
  private ScrollPane history;
  private ScrollPane variables;
  private ScrollPane userCommands;
  private TextArea inputArea;
  private static final String ButtonResources = "resources.UIActions.ButtonActions";
  private static final String ComboBoxResources = "resources.UIActions.ComboBoxActions";
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String InitialColorResources = "resources.UIActions.InitialColors";
  private ResourceBundle myButtonResources;
  private ResourceBundle myComboBoxResources;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myInitialColorResources;


  public UserInterface() {
    myStage = new Stage();
    myTurtle = new Turtle(this);
    control = new Control();
    myLine = new Line();
    display = new Group();
    for (int i=0; i< colors.length; i++){
      map.put(colorNames[i], colors[i]);
    }
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources= ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources= ResourceBundle.getBundle(InitialColorResources);
    control.setLanguage("English");

  }

  public BorderPane getRoot() {
    return root;
  }

  @Override
  public void start(Stage primaryStage){
    myStage.setTitle(TITLE);
    UserInterface view = new UserInterface();
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
    root.getChildren().addAll(turtleimage);
    return new Scene(root);
  }

  public Pane getPane(){
    return root;
  }

  public ImageView getImage(){
    return turtleimage;
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
    history = new ScrollPane();
    variables = new ScrollPane();
    userCommands = new ScrollPane();
    Tab tab1 = new Tab("History", history);

    Tab tab2 = new Tab("Variables"  , variables);
    Tab tab3 = new Tab("Commands" , userCommands);

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
    VBox historyBox = new VBox();
    VBox variablesBox = new VBox();
    VBox userCommandsBox = new VBox();


    runButton.setOnAction(action -> {
      myText = inputArea.getText();
      String thistext = myText;
      control.setCommand(myText);
      control.passTurtle(myTurtle);
      control.parseCommand();
      System.out.println("variables" + control.getVariables().keySet());
      setHistoryTab(historyBox, thistext);
      setVariablesTab(variablesBox);
      setUserCommandsTab(userCommandsBox);
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

  private void setUserCommandsTab(VBox userCommandsBox) {
    userCommandsBox.getChildren().clear();
    for(Object variable: control.getUserCommands().keySet()) {
      Hyperlink linkVariable = new Hyperlink(variable.toString());
      linkVariable.getStyleClass().add("hyper-link");
      userCommandsBox.getChildren().add(linkVariable);
      ;
      linkVariable.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          inputArea.setText(linkVariable.getText());
        }
      });
    }
    //variablesBox.getChildren().addAll(control.getVariables().keySet());
    userCommands.setContent(userCommandsBox);
  }

  private void setVariablesTab(VBox variablesBox) {
    variablesBox.getChildren().clear();
    for(Object variable: control.getVariables().keySet()) {
      Hyperlink linkVariable = new Hyperlink(variable.toString());
      linkVariable.getStyleClass().add("hyper-link");
      variablesBox.getChildren().add(linkVariable);
      ;
      linkVariable.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          inputArea.setText(linkVariable.getText());
        }
      });
    }
    //variablesBox.getChildren().addAll(control.getVariables().keySet());
    variables.setContent(variablesBox);
  }

  private void setHistoryTab(VBox historyBox, String thistext) {
    Hyperlink link = new Hyperlink();
    link.getStyleClass().add("hyper-link");
    link.setText(myText);
    link.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        inputArea.setText(thistext);
      }
    });
    historyBox.getChildren().add(0, link);
    history.setContent(historyBox);
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
    addButtons(hbox);
    return hbox;
  }

  private void addButtons(HBox hbox) {
    for (String key : Collections.list(myButtonResources.getKeys())) {
      hbox.getChildren().add(new OurButtons(myButtonResources.getString(key), key, this));
    }
    for (String key : Collections.list(myComboBoxResources.getKeys())) {
      hbox.getChildren().add(new OurComboBox(myComboBoxResources.getString(key), key, this, FXCollections
          .observableArrayList(languages)));
    }
    for (String key : Collections.list(myColorPickerResources.getKeys())) {
      hbox.getChildren().add(new OurLabeledColorPickers(myColorPickerResources.getString(key), key, this, myInitialColorResources.getString(key + "Initial")));
    }
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

  private void createErrorDialog(Exception e){
    ErrorBoxes ep = new ErrorBoxes(e);
  }


  private void displayHelpScreen() {
    Stage stage2 = new Stage();
    stage2.setTitle("Table View Sample");
    ScrollPane pane = new ScrollPane();
    ScrollBar bar = new ScrollBar();
    bar.setOrientation(Orientation.VERTICAL);
    bar.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        bar.setLayoutX(-new_val.doubleValue());
        pane.hvalueProperty().bindBidirectional(bar.valueProperty());
      }
    });
    stage2.setWidth(300);
    stage2.setHeight(500);

    Image command= new Image(getClass().getClassLoader().getResourceAsStream(COMMAND_ONE));
    ImageView commandOneIm = new ImageView(command);
    Image commandTwo= new Image(getClass().getClassLoader().getResourceAsStream(COMMAND_TWO));
    ImageView commandTwoIm = new ImageView(commandTwo);

    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(commandOneIm,commandTwoIm);
    pane.setContent(vbox);
    Scene scene = new Scene(pane);
    stage2.setScene(scene);
    stage2.show();
  }


  public void resetDisplay() {
    root.getChildren().removeIf(object -> object instanceof Line);
    setTurtlePosition(turtleimage);
  }

  public void selectFileScreen() {
    FileChooser fileChooser = new FileChooser();
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
  }

  public void setLanguage(String language) {
    control.setLanguage(language);
  }

  public void setBackgroundColor(Color color){
    rectangle.setFill(color);
  }
  public void setPenColor(Color color){
    myLine.setStroke(color);
    lineColor = color;
  }
}
