package frontEnd;

import Controller.Control;
import backEnd.ErrorHandler;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPickers;
import frontEnd.ButtonsBoxesandLabels.PropertyLabel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
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
  private ResourceBundle myComboBoxOptionsResources;
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
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String HYPERLINK_STYLE = "hyper-link";
  private static final int BUTTON_PANE_HEIGHT = 70;
  private static final int SIDEPANE_WIDTH = 300;
  private static double numOfMovers = 1;
  private ObservableList<Double> turtleList = FXCollections.observableArrayList(List.of());
  private static double moverX = 0;
  private double lineWidth = 2;
  private VBox turtlebox;
  private static double xcenter;
  private static double ycenter;
  private Map<String, PropertyLabel> propertyLabelMap= new HashMap<>();
  private List<OurLabeledColorPickers> penResources = new ArrayList<>();
  private List<OurComboBox> imageResources = new ArrayList<>();
  private ButtonAction myButtonAction;
  private static final double initialMoverID = 1;
  private static DisplayWindow displayWindow;
  private static MoverPropertiesWindow myPropertyWindow;
  List<String> imageOptions;
  private int currImageIndex;


  public UserInterface() {
    myStage = new Stage();
    myMover = new Mover(this, initialMoverID);
    control = new Control(this);
    myLine = new Line();
    display = new Group();
    myButtonAction = new ButtonAction(this);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    myButtonAction.setLanguage(DEFAULT_LANGUAGE);
    String optionsString = myComboBoxOptionsResources.getString("setImageOptions");
    imageOptions = Arrays.asList(optionsString.split(","));

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

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene() {
    root = new BorderPane();
    root.setTop(new ToolBar(myButtonAction));
    myPropertyWindow = new MoverPropertiesWindow(myButtonAction, turtleList,propertyLabelMap);
    root.setLeft(myPropertyWindow);
    displayWindow = new DisplayWindow(myButtonAction);
    root.setCenter(displayWindow);
    root.setRight(new TabWindow(displayWindow.getCommandWindow()));
    xcenter = DISPLAY_WIDTH / 2 - myMover.getImage().getBoundsInLocal().getWidth() / 2 + SIDEPANE_WIDTH;
    ycenter = BUTTON_PANE_HEIGHT + DISPLAY_HEIGHT / 2 - myMover.getImage().getBoundsInLocal().getHeight() / 2;
    for (double i = 1; i <= numOfMovers; i++) {
      myMover = new Mover(this, i);
      myMover.setInitialMoverPosition();
      turtleMap.put(i, myMover);
      turtleList.add(i);
      root.getChildren().add(myMover.getImage());
    }
    return new Scene(root);
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
    myMover = (frontEnd.Mover) mover;
  }

  public ObservableList<Double> getTurtleList(){
    return turtleList;
  }

  public ButtonAction getButtonAction(){
    return myButtonAction;
  }

  public frontEnd.Mover getMover(){
    return myMover;
  }

  public Map getTurtleMap(){
    return turtleMap;
  }

  public MoverPropertiesWindow getPropertyWindow(){
    return myPropertyWindow;
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

  public TextArea getCommander(){
    return myCommander;
  }

  public Control getControl(){
    return control;
  }

  public Rectangle getRectangle(){
    return rectangle;
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

  public Map getPropertyLabelMap(){
    return propertyLabelMap;
  }

  public ResourceBundle getResource(){
    return myComboBoxOptionsResources;
  }

  private void createErrorDialog(Exception e) {
    ErrorBoxes ep = new ErrorBoxes(e);
  }

  public void addToMap(Double doub, Mover mover){
    turtleMap.put(doub, mover);
  }

  public DisplayWindow getDisplayWindow(){
    return displayWindow;
  }

  public double getXCenter(){
    return xcenter;
  }
  public double getYCenter(){
    return ycenter;
  }

  public double getAngle(){
    return myMover.getMoverAngle();
  }

  public Node[] viewChildren(){
    Node[] children = new Node[root.getChildren().size()];
    int i=0;
    for (Node child: root.getChildren()){
      children[i] = child;
      i++;
    }
    return children;
  }
}
