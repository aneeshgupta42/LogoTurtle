package frontEnd;

import Controller.Control;
import backEnd.ErrorHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class UserInterface extends Application {
  private Scene myScene;
  private Group display;
  private Stage myStage;
  private Mover myMover;
  private Control control;
  private Rectangle rectangle;
  private Line myLine;
  //private ImageView myMover.getImage();
  private HBox hbox;
  private ScrollPane history;
  private ScrollPane variables;
  private ScrollPane userCommands;
  private TextArea inputArea;
  private HBox commandWindow;
  private TabPane tabPane;
  private String myText;
  private TextArea myCommander;
  private Node display_window;
  private BorderPane root;
  VBox historyBox = new VBox();
  VBox variablesBox = new VBox();
  VBox userCommandsBox = new VBox();
  private ResourceBundle myButtonResources;
  private ResourceBundle myComboBoxResources;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myInitialColorResources;
  private ResourceBundle myComboBoxOptionsResources;
  private ResourceBundle myTextButtonResources;
  private ResourceBundle myTurtlePropertyResources;
  private Hyperlink linkVariable;
  private Map turtleMap;


  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final String TITLE = "Slogo Team 17";
  private static final double DISPLAY_WIDTH = 900;
  private static final double DISPLAY_HEIGHT = 500;
  private static final double TEXTBOX_HEIGHT = 200;
  private static final double COMMAND_CONTROLS_WIDTH = 75;
  private static final String COMMAND_ONE = "viewboc.png";
  private static final String COMMAND_TWO = "viewbox.png";
  private static final String TURTLE = "turtle.png";
  private static final String RESOURCES = "resources.languages.";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "" + RESOURCES + "/";
  public static final String STYLE_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
  private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
  private static final String STYLESHEET = "default.css";
  private ResourceBundle styleResources;
  private Color lineColor = Color.BLACK;
  private static final String ButtonResources = "resources.UIActions.ButtonActions";
  private static final String ComboBoxResources = "resources.UIActions.ComboBoxActions";
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String InitialColorResources = "resources.UIActions.InitialColors";
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private static final String TextBoxButtonResources = "resources.UIActions.TextButtonActions";
  private static final String TurtlePropertyResources = "resources.UIActions.TurtlePropertyActions";
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String RECTANGLE_STYLE = "rectangle";
  private static final String HBOX_STYLE = "hbox";
  private static final String HYPERLINK_STYLE = "hyper-link";
  private static final String COMBO_OPTIONS = "Options";
  private static final String COLOR_INITIAL = "Initial";
  private static final String HISTORY_TAB_TITLE = "History";
  private static final String VARIABLE_TAB_TITLE = "Variables";
  private static final String COMMAND_TAB_TITLE = "User Commands";
  private static final String TEXT_INPUT_PROMPT = "Enter Command";
  private static final int BUTTON_PANE_HEIGHT = 70;
  private static final int SIDEPANE_WIDTH = 300;
  private static final int NUM_TEXT_COLUMNS= 10;
  private static final int MOVE_SIZE = 50;
  private static int numOfMovers = 1;



  public UserInterface() {
    myStage = new Stage();
    myMover = new Mover(this);
    control = new Control();
    myLine = new Line();
    display = new Group();
    turtleMap = new HashMap<Integer, Mover>();
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources= ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources= ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    myTextButtonResources = ResourceBundle.getBundle(TextBoxButtonResources);
    myTurtlePropertyResources = ResourceBundle.getBundle(TurtlePropertyResources);
    control.setLanguage(DEFAULT_LANGUAGE);
  }

  @Override
  public void start(Stage primaryStage){
    myStage.setTitle(TITLE);
    UserInterface view = new UserInterface();
    myStage.setX(0);
    myScene = makeScene();
    myScene.getStylesheets().add(STYLESHEET);
    myStage.setScene(myScene);
    myStage.show();
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step();
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    myStage.setOnCloseRequest(t->stopEverything());
  }

  private void step() {
    //checkIfTurtleMovesOutOfBounds();
  }

  private void checkIfTurtleMovesOutOfBounds() {
    if(myMover.getImage().getBoundsInParent().intersects(hbox.getBoundsInParent()) ){
      hideMoverAndLine(myMover.getLine().getStartX(), hbox.getBoundsInParent().getMaxY());
    }
    if(myMover.getImage().getBoundsInParent().intersects(commandWindow.getBoundsInParent())){
      hideMoverAndLine(myMover.getLine().getStartX(), commandWindow.getBoundsInParent().getMinY());
    }
    if(myMover.getImage().getBoundsInParent().intersects(tabPane.getBoundsInParent())){
      hideMoverAndLine(tabPane.getBoundsInParent().getMinX(), myMover.getLine().getStartY());
    }
  }

  private void hideMoverAndLine(double endX, double endY) {
    myMover.moverVisible(false);
    root.getChildren().remove(myMover.getLine());
    Line line = new Line(myMover.getLine().getStartX(), myMover.getLine().getStartY(), endX, endY);
    root.getChildren().add(line);
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene () {
    root = new BorderPane();
    hbox = addHBox();
    root.setTop(hbox);
    display_window = makeDisplayWindow();
    root.setLeft(makeTurtlePropertiesWindow());
    root.setCenter(display_window);
    root.setRight(makeSideWindow());
    for(int i=1; i<=numOfMovers; i++){
      myMover = new Mover(this);
      //moverImage = myMover.displayMover(TURTLE);
      setMoverPosition(myMover.getImage());
      turtleMap.put(i, myMover);
    }
    //root.getChildren().addAll(moverImage);
    for(Object mover: turtleMap.values()){
      Mover moverObject = (Mover) mover;
      root.getChildren().add(moverObject.getImage());


    }
    //root.getChildren().addAll(turtleMap.values());
    return new Scene(root);
  }

  private Node makeTurtlePropertiesWindow() {
    VBox box = new VBox();
    box.setPrefWidth(SIDEPANE_WIDTH);
    for (String key : Collections.list(myTurtlePropertyResources.getKeys())) {
      box.getChildren().add(new OurButtons(myTurtlePropertyResources.getString(key), key, this));
    }
    return box;
  }

  //fix numbers
  public HBox addHBox() {
    HBox buttonPane = new HBox();
    buttonPane.getStyleClass().add(HBOX_STYLE);
    buttonPane.setPrefHeight(BUTTON_PANE_HEIGHT);
    buttonPane.setPadding(new Insets(15, 12, 15, 12));
    buttonPane.setSpacing(10);
    addButtons(buttonPane);
    return buttonPane;
  }

  private void addButtons(HBox hbox) {
    for (String key : Collections.list(myButtonResources.getKeys())) {
      hbox.getChildren().add(new OurButtons(myButtonResources.getString(key), key, this));
    }
    for (String key : Collections.list(myComboBoxResources.getKeys())) {
      hbox.getChildren().add(new OurComboBox(myComboBoxResources.getString(key), key, this, FXCollections
          .observableArrayList(myComboBoxOptionsResources.getString(key+COMBO_OPTIONS).split(","))));
    }
    for (String key : Collections.list(myColorPickerResources.getKeys())) {
      hbox.getChildren().add(new OurLabeledColorPickers(myColorPickerResources.getString(key), key, this, myInitialColorResources.getString(key + COLOR_INITIAL)));
    }
  }

  private Node makeDisplayWindow(){
    VBox vbox = new VBox();
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rectangle.getStyleClass().add(RECTANGLE_STYLE);
    commandWindow = new HBox(makeCommandWindow());
    vbox.getChildren().addAll(rectangle, commandWindow);
    display.getChildren().addAll(vbox);
    return display;
  }

  public void setMoverPosition(ImageView image) {
    image.setX(DISPLAY_WIDTH/2-image.getBoundsInLocal().getWidth()/2 + SIDEPANE_WIDTH);
    image.setY(BUTTON_PANE_HEIGHT + DISPLAY_HEIGHT/2-image.getBoundsInLocal().getHeight()/2);
    image.setRotate(0);
    myMover.initializeLinePosition(image.getX(), image.getY(), image.getRotate());
    myMover.setMoverInitialCords(image.getX(), image.getY());
  }

  private Node makeSideWindow() {
    tabPane = new TabPane();
    tabPane.setMinWidth(SIDEPANE_WIDTH);
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    history = new ScrollPane(); ////here or at the top?
    variables = new ScrollPane();
    userCommands = new ScrollPane();
    Tab tab1 = new Tab(HISTORY_TAB_TITLE, history);
    Tab tab2 = new Tab(VARIABLE_TAB_TITLE , variables);
    Tab tab3 = new Tab(COMMAND_TAB_TITLE, userCommands);
    tabPane.getTabs().add(tab1);
    tabPane.getTabs().add(tab2);
    tabPane.getTabs().add(tab3);
    //VBox vBox = new VBox(tabPane);
    return tabPane;
  }

  private Node makeCommandWindow(){
    HBox hbox = new HBox();
    hbox.setPrefWidth(DISPLAY_WIDTH);
    inputArea = new TextArea();
    myCommander = inputArea;
    inputArea.setPromptText(TEXT_INPUT_PROMPT);
    inputArea.setPrefColumnCount(NUM_TEXT_COLUMNS);
    inputArea.getText();
    GridPane.setConstraints(inputArea, 0, 0);
    inputArea.setPrefWidth(DISPLAY_WIDTH);
    inputArea.setMaxHeight(TEXTBOX_HEIGHT);
    VBox vbox = new VBox();
    for (String key : Collections.list(myTextButtonResources.getKeys())) {
      vbox.getChildren().add(new OurButtons(myTextButtonResources.getString(key), key, this));
    }
    vbox.setMinWidth(COMMAND_CONTROLS_WIDTH);
    hbox.getChildren().addAll(inputArea, vbox);
    return hbox;
  }


  private void createStoredElementsTabs(VBox vbox, ScrollPane tab, Map map, boolean needValue) {
    vbox.getChildren().clear();
    for(Object variable: map.keySet()) {
      if(needValue) {
        linkVariable = new Hyperlink(variable.toString() + "=" + map.get(variable));
      }
      else{
        linkVariable = new Hyperlink(variable.toString());
      }
      linkVariable.getStyleClass().add(HYPERLINK_STYLE);
      vbox.getChildren().add(linkVariable);
      linkVariable.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          inputArea.setText(variable.toString());
        }
      });
    }
    tab.setContent(vbox);
  }

  private void setHistoryTab(VBox historyBox, String thistext) {
    Hyperlink link = new Hyperlink();
    link.getStyleClass().add(HYPERLINK_STYLE);
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


  public static void main(String[] args) {
    launch(args);
  }

  public Color getLineColor() {
    return lineColor;
  }

  public BorderPane getRoot() {
    return root;
  }
  public void addNodeToRoot(Node object){
    root.getChildren().add(object);
  }

  private void createErrorDialog(Exception e){
    ErrorBoxes ep = new ErrorBoxes(e);
  }


  public void displayHelpScreen() {
    Stage stage2 = new Stage();
    stage2.setTitle("Table View Sample");
    ScrollPane pane = new ScrollPane();
    stage2.setWidth(1100);
    stage2.setHeight(500);

    Image command= new Image(getClass().getClassLoader().getResourceAsStream(COMMAND_ONE));
    ImageView commandOneIm = new ImageView(command);
    commandOneIm.setPreserveRatio(true);
    commandOneIm.setFitWidth(800);
    Image commandTwo= new Image(getClass().getClassLoader().getResourceAsStream(COMMAND_TWO));
    ImageView commandTwoIm = new ImageView(commandTwo);
    commandTwoIm.setPreserveRatio(true);
    commandTwoIm.setFitWidth(1000);

    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(commandOneIm,commandTwoIm);
    pane.setContent(vbox);
    pane.setFitToWidth(true);
    Scene scene = new Scene(pane);
    stage2.setScene(scene);
    stage2.show();
  }
  
  public void resetDisplay() {
    root.getChildren().removeIf(object -> object instanceof Line);
    setMoverPosition(myMover.getImage());
    myMover.moverVisible(true);
  }

  public void selectFileScreen() {
    FileChooser fileChooser = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data/examples";
    fileChooser.setInitialDirectory(new File(dataPath));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if(selectedFile!= null){
      try {
        scanFile(selectedFile);
      } catch (FileNotFoundException ex) {
        ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidFile"));
      }
    }
  }

  public void setLanguage(String language) {
    control.setLanguage(language);
  }

  public void setImage(String image){
    double moverXPos = myMover.getImage().getX();
    double moverYPos = myMover.getImage().getY();
    double moverAngle = myMover.getMoverAngle();
    root.getChildren().remove(myMover.getImage());
    String path = myComboBoxOptionsResources.getString(image);
    myMover.changeMoverDisplay(path);
    //)= (ImageView) myMover.changeMoverDisplay(path);
    myMover.getImage().setX(moverXPos);
    myMover.getImage().setY(moverYPos);
    myMover.getImage().setRotate(moverAngle);
    root.getChildren().add(myMover.getImage());
  }

  public void setBackgroundColor(Color color){
    rectangle.setFill(color);
  }
  public void setPenColor(Color color){
    myLine.setStroke(color);
    lineColor = color;
  }

  public void setOnRun(){
    myText = inputArea.getText();
    String thistext = myText;
    control.setCommand(myText);
    control.passTurtle(myMover);
    control.parseCommand();
    inputArea.setText("");
    if(myMover.objectMoved()) {
      System.out.println("variables" + control.getVariables().keySet());
      setHistoryTab(historyBox, thistext);
      myMover.setObjectMoved(false);
    }
    createStoredElementsTabs(variablesBox, variables, control.getVariables(), true);
    createStoredElementsTabs(userCommandsBox, userCommands, control.getUserCommands(), false);
  }

  public void addTurtle(){
    numOfMovers++;
    Mover mover = new Mover(this);
    //moverImage = mover.displayMover(TURTLE);
    setMoverPosition(mover.getImage());
    turtleMap.put(numOfMovers, mover);
    root.getChildren().add(mover.getImage());
  }

  public void moveBackward(){
    myMover.move(0, MOVE_SIZE, 0);
  }

  public void moveForward(){
    myMover.move(0, -MOVE_SIZE, 0);
  }

  public void moveLeft(){
    myMover.move(-MOVE_SIZE,0,  0);
  }

  public void moveRight(){
    myMover.move( MOVE_SIZE, 0, 0);
  }

  public void setOnClear(){
    inputArea.setText("");
  }
}
