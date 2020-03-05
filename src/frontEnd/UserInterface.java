package frontEnd;

import Controller.Control;
import backEnd.ErrorHandler;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPickers;
import frontEnd.ButtonsBoxesandLabels.PropertyLabel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
  private ResourceBundle myLabelPropertyResources;
  private ResourceBundle myMoverPropertiesDropDownResources;
  private Hyperlink linkVariable;
  private Map<Double, Mover> turtleMap = new HashMap<>();
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
  public static final String STYLE_PROPERTIES_FILENAME =
      DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
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
  private static final String LabelResources = "resources.UIActions.LabelActions";
  private static final String MoverPropertiesDropDownResources = "resources.UIActions.MoverPropertiesDropDown";
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
  private static final int NUM_TEXT_COLUMNS = 10;
  private static final int MOVE_SIZE = 50;
  private static double numOfMovers = 1;
  private ObservableList<Double> turtleList = FXCollections.observableArrayList(List.of());
  private static double moverX = 0;
  private double lineWidth = 2;
  private VBox turtlebox;
  private static double xcenter;
  private static double ycenter;
  private double moverID = 1;
  private Map<String, PropertyLabel> propertyLabelMap= new HashMap<>();
  private List<OurLabeledColorPickers> penResources = new ArrayList<>();
  private List<OurComboBox> imageResources = new ArrayList<>();
  private ButtonAction myButtonAction;
  private static final double initialMoverID = 1;
  private static DisplayWindow displayWindow;

  public UserInterface() {
    myStage = new Stage();
    myMover = new Mover(this, initialMoverID);
    control = new Control();
    myLine = new Line();
    display = new Group();
    xcenter = DISPLAY_WIDTH / 2 - myMover.getImage().getBoundsInLocal().getWidth() / 2 + SIDEPANE_WIDTH;
    ycenter = BUTTON_PANE_HEIGHT + DISPLAY_HEIGHT / 2 - myMover.getImage().getBoundsInLocal().getHeight() / 2;
    myButtonAction = new ButtonAction(this);
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources = ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources = ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    myTextButtonResources = ResourceBundle.getBundle(TextBoxButtonResources);
    myTurtlePropertyResources = ResourceBundle.getBundle(TurtlePropertyResources);
    myLabelPropertyResources = ResourceBundle.getBundle(LabelResources);
    myMoverPropertiesDropDownResources = ResourceBundle.getBundle(MoverPropertiesDropDownResources);
    control.setLanguage(DEFAULT_LANGUAGE);
  }

  @Override
  public void start(Stage primaryStage) {
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
    myStage.setOnCloseRequest(t -> stopEverything());

  }

  private void step() {
    //checkIfTurtleMovesOutOfBounds();
  }

  private void checkIfTurtleMovesOutOfBounds() {
    if (myMover.getImage().getBoundsInParent().intersects(hbox.getBoundsInParent())) {
      hideMoverAndLine(myMover.getLine().getStartX(), hbox.getBoundsInParent().getMaxY());
    }
    if (myMover.getImage().getBoundsInParent().intersects(commandWindow.getBoundsInParent())) {
      hideMoverAndLine(myMover.getLine().getStartX(), commandWindow.getBoundsInParent().getMinY());
    }
    if (myMover.getImage().getBoundsInParent().intersects(tabPane.getBoundsInParent())) {
      hideMoverAndLine(tabPane.getBoundsInParent().getMinX(), myMover.getLine().getStartY());
    }
  }

  private void hideMoverAndLine(double endX, double endY) {
    myMover.changeVisible();
    root.getChildren().remove(myMover.getLine());
    Line line = new Line(myMover.getLine().getStartX(), myMover.getLine().getStartY(), endX, endY);
    root.getChildren().add(line);
  }

  public void setMyMover(Object mover) {
    myMover = (Mover) mover;
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene() {
    root = new BorderPane();
    //hbox = addHBox();
    root.setTop(new ToolBar(myButtonAction));
    //display_window = makeDisplayWindow();
    root.setLeft(new MoverPropertiesWindow(myButtonAction, turtleList,propertyLabelMap));
    //root.setLeft(makeTurtlePropertiesWindow());
    displayWindow = new DisplayWindow(myButtonAction);
    root.setCenter(displayWindow);
    //root.setCenter(display_window);
    root.setRight(new TabWindow());
    //root.setRight(makeSideWindow());
    for (double i = 1; i <= numOfMovers; i++) {
      myMover = new Mover(this, i);
      moverID = i;
      //moverImage = myMover.displayMover(TURTLE);
      //setMoverPosition(myMover.getImage());
      myMover.setInitialMoverPosition(xcenter, ycenter);
      turtleMap.put(i, myMover);
      turtleList.add(i);
      turtleList.add(i);
    }
    //root.getChildren().addAll(moverImage);
    for (Object mover : turtleMap.values()) {
      Mover moverObject = (Mover) mover;
      root.getChildren().add(moverObject.getImage());
    }
    //root.getChildren().addAll(turtleMap.values());
    return new Scene(root);
  }

  public ObservableList<Double> getTurtleList(){
    return turtleList;
  }

  public ButtonAction getButtonAction(){
    return myButtonAction;
  }

  public Mover getMover(){
    return myMover;
  }

  public Map getTurtleMap(){
    return turtleMap;
  }


  private void createStoredElementsTabs(VBox vbox, ScrollPane tab, Map map, boolean needValue) {
    vbox.getChildren().clear();
    for (Object variable : map.keySet()) {
      if (needValue) {
        linkVariable = new Hyperlink(variable.toString() + "=" + map.get(variable));
      } else {
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

  private void stopEverything() {
    System.exit(1);
  }

  public Stage getStage(){
    return myStage;
  }


  void scanFile(File file) throws FileNotFoundException {
    Scanner scnr = new Scanner(file);
    //Reading each line of file using Scanner class
    int lineNumber = 0;
    while (scnr.hasNextLine()) {
      String line = scnr.nextLine();
      myCommander.setText(myCommander.getText() + line + "\n");
      lineNumber++;
    }
  }

  public String getText() {
    return myText;
  }

  public TextArea getCommander(){
    return myCommander;
  }

  public Control getControl(){
    return control;
  }

  public Rectangle getRectangle(){
    return rectangle;
  }


  public static void main(String[] args) {
    launch(args);
  }

  public Color getLineColor() {
    return myMover.getLineColor();
  }

  public BorderPane getRoot() {
    return root;
  }

  public void addNodeToRoot(Node object) {
    root.getChildren().add(object);
  }

  public void removeNodeFromRoot(Node object){
    root.getChildren().remove(object);
  }

  public void setMoverX(double x) {
    moverX = x;
    System.out.println("x " + x);
  }

  public Map getPropertyLabelMap(){
    return propertyLabelMap;
  }

  public ResourceBundle getResource(){
    return myComboBoxOptionsResources;
  }

  private void createErrorDialog(Exception e) {
    ErrorBoxes ep = new ErrorBoxes(e);
  }


  public void displayHelpScreen() {
    Stage stage2 = new Stage();
    stage2.setTitle("Table View Sample");
    ScrollPane pane = new ScrollPane();
    stage2.setWidth(1100);
    stage2.setHeight(500);

    Image command = new Image(getClass().getClassLoader().getResourceAsStream(COMMAND_ONE));
    ImageView commandOneIm = new ImageView(command);
    commandOneIm.setPreserveRatio(true);
    commandOneIm.setFitWidth(800);
    Image commandTwo = new Image(getClass().getClassLoader().getResourceAsStream(COMMAND_TWO));
    ImageView commandTwoIm = new ImageView(commandTwo);
    commandTwoIm.setPreserveRatio(true);
    commandTwoIm.setFitWidth(1000);

    final VBox vbox = new VBox();
    vbox.setSpacing(5);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(commandOneIm, commandTwoIm);
    pane.setContent(vbox);
    pane.setFitToWidth(true);
    Scene scene = new Scene(pane);
    stage2.setScene(scene);
    stage2.show();
  }

  public void resetDisplay() {
    root.getChildren().removeIf(object -> object instanceof Line);
    for(Mover mover : turtleMap.values()) {
      mover.setInitialMoverPosition(xcenter, ycenter);
      mover.moverVisible(true);
      mover.updateLabels();
    }
  }


  public void selectFileScreen() {
    FileChooser fileChooser = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data/examples";
    fileChooser.setInitialDirectory(new File(dataPath));
    File selectedFile = fileChooser.showOpenDialog(myStage);
    if (selectedFile != null) {
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

  public void setImage(String image) {
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

  public double getmoverID(){
    return moverID;
  }

  public void setBackgroundColor(Color color) {
    rectangle.setFill(color);
  }

  public void setPenColor(Color color) {
    //myLine.setStroke(color);
    //lineColor = color;
    myMover.setLineColor(color);
    //myMover.updateLabels();
  }

  public void setPen(String position){

  }

  public void setOnRun() {
    myText = inputArea.getText();
    String thistext = myText;
    sendInfoToControl(myText);
    inputArea.setText("");
    if (myMover.objectMoved()) {
      System.out.println("variables" + control.getVariables().keySet());
      setHistoryTab(historyBox, thistext);
      myMover.setObjectMoved(false);
    }
    createStoredElementsTabs(variablesBox, variables, control.getVariables(), true);
    createStoredElementsTabs(userCommandsBox, userCommands, control.getUserCommands(), false);
    myMover.updateLabels();
  }

  public void sendInfoToControl(String myText) {
    for(Mover mover: turtleMap.values()) {
      if (mover.getActive()) {
        myMover= mover;
        control.setCommand(myText);
        control.passTurtle(myMover);
        control.parseCommand();
      }
    }
  }

  public void addTurtle() {
    System.out.println("reached");
    numOfMovers++;
    Mover mover = new Mover(this, numOfMovers);
    //moverImage = mover.displayMover(TURTLE);
    mover.setInitialMoverPosition(xcenter, ycenter);
    //setMoverPosition(mover.getImage());
    turtleMap.put(numOfMovers, mover);
    root.getChildren().add(mover.getImage());
    turtleList.add(numOfMovers);
    System.out.println(turtleList);
    //turtleSelection.updateItems(FXCollections.observableArrayList(turtleList));
  }

  public DisplayWindow getDisplayWindow(){
    return displayWindow;
  }

  public void setOnClear() {
    inputArea.setText("");
  }

  public void selectTurtle(String num) {
    Double number = Double.parseDouble(num);
    setMyMover(turtleMap.get(number));
    moverID = number;
    for(OurLabeledColorPickers label: penResources){
      label.setInitialColor(myMover.getLineColor());
    }
    for(OurComboBox boximage : imageResources){
      //boximage.setValue(myMover.getImage());

    }
    myMover.updateLabels();
  }

  public double getAngle(){
    return myMover.getMoverAngle();
  }
}
