package frontEnd;

import controller.Control;
import frontEnd.UIElements.PropertyLabel;
import frontEnd.Windows.*;

import frontEnd.Windows.CustomWindow;
import frontEnd.Windows.DisplayWindow;
import frontEnd.Windows.MoverPropertiesWindow;
import frontEnd.Windows.TabWindow;
import frontEnd.Windows.ToolBarWindow;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import frontEnd.UIElements.ColorGrid;
import frontEnd.UIElements.PropertyLabel;


public class UserInterface extends Application{
  private Scene myScene;
  private Stage myStage;
  private Mover myMover;
  private Control control;
  private CommandWindow commandWindow;
  private BorderPane root = new BorderPane();
  private ResourceBundle myComboBoxOptionsResources;
  private Map<Double, Mover> turtleMap = new HashMap<>();
  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final String TITLE = "Slogo Team 17";
  private static final double DISPLAY_WIDTH = 900;
  private static final double DISPLAY_HEIGHT = 500;
  private static final String RESOURCES = "resources.languages.";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String DEFAULT_RESOURCE_FOLDER = "" + RESOURCES + "/";
  public static final String STYLE_PROPERTIES_FILENAME =
      DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
  private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
  private static final String STYLESHEET = "default.css";
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private static final String DEFAULT_LANGUAGE = "English";
  private static final int BUTTON_PANE_HEIGHT = 70;
  private static final int SIDEPANE_WIDTH = 300;
  private static double numOfMovers = 1;
  private ObservableList<Double> turtleList = FXCollections.observableArrayList(List.of());
  private static double xcenter;
  private static double ycenter;
  private Map<String, PropertyLabel> propertyLabelMap= new HashMap<>();
  private ButtonAction myButtonAction;
  private static final double initialMoverID = 1;
  private static DisplayWindow displayWindow;
  private static MoverPropertiesWindow myPropertyWindow;
  private static TabWindow tabWindow;
  List<String> imageOptions;
  private ToolBarWindow myToolBarWindow;
  private CustomWindow myCustomWindow;

  public UserInterface() {
    myStage = new Stage();
    myMover = new Mover(this, initialMoverID);
    control = new Control(this);
    myButtonAction = new ButtonAction(this);
    myCustomWindow = new CustomWindow(myButtonAction);
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
    System.out.println("Children" + root.getChildren());
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
    //root = new BorderPane();
    myToolBarWindow = new ToolBarWindow(myCustomWindow);
    root.setTop(myToolBarWindow);
    myPropertyWindow = new MoverPropertiesWindow(myButtonAction, myCustomWindow, turtleList, propertyLabelMap);
    root.setLeft(myPropertyWindow);
    //root.setLeft(new GridPane());
    displayWindow = new DisplayWindow(myButtonAction, myCustomWindow, myPropertyWindow.getColorGrid());
    root.setCenter(displayWindow);
    tabWindow = new TabWindow(displayWindow.getCommandWindow(), myButtonAction);
    root.setRight(tabWindow);
    xcenter = DISPLAY_WIDTH / 2 - myMover.getImage().getBoundsInLocal().getWidth() / 2 + SIDEPANE_WIDTH;
    ycenter = BUTTON_PANE_HEIGHT + DISPLAY_HEIGHT / 2 - myMover.getImage().getBoundsInLocal().getHeight() / 2;
    /*List<ImageView> images = moverMap.createTurtles(root);
    root.getChildren().addAll(images);
    for(ImageView image: images){
      image.setX(xcenter);
      image.setY(ycenter);
    }*/
    //moverMap.createTurtles();
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
    checkIfTurtleMovesOutOfBounds();
  }

  ///move into a new class
 private void checkIfTurtleMovesOutOfBounds() {
    if (myMover.getImage().getBoundsInParent().intersects(myToolBarWindow.getBoundsInParent())) {
      hideMoverAndLine(myMover.getLine().getStartX(), myToolBarWindow.getBoundsInParent().getMaxY());
    }
    if (myMover.getImage().getBoundsInParent().intersects(displayWindow.getCommandWindow().getBoundsInParent())) {
      hideMoverAndLine(myMover.getLine().getStartX(), displayWindow.getCommandWindow().getBoundsInParent().getMinY());
    }
    if (myMover.getImage().getBoundsInParent().intersects(tabWindow.getBoundsInParent())) {
      hideMoverAndLine(tabWindow.getBoundsInParent().getMinX(), myMover.getLine().getStartY());
    }
  }

  private void hideMoverAndLine(double endX, double endY) {
    myMover.changeVisible();
    root.getChildren().remove(myMover.getLine());
    Line line = new Line(myMover.getLine().getStartX(), myMover.getLine().getStartY(), endX, endY);
    root.getChildren().add(line);
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

  public void setMover(Mover mover){
    myMover = mover;
  }
  public Map getTurtleMap(){
    return turtleMap;
  }

  public int getNumTurtles(){
      int count = 0;
      for(Mover mover: turtleMap.values()) {
            count++;
      }
      return count;
  }

  public void changeColorGrid(int index, int red, int green, int blue){
    myPropertyWindow.getColorGrid().setColorFromIndexAndRGB(index, red, green, blue);
  }

  public MoverPropertiesWindow getPropertyWindow(){
    return myPropertyWindow;
  }

  public TabWindow getTabWindow(){
    return tabWindow;
  }

  private void stopEverything() {
    System.exit(1);
  }

  public Stage getStage(){
    return myStage;
  }

  public Control getControl(){
    return control;
  }

  public BorderPane getRoot() {
    return root;
  }

  public void addNodeToRoot(Node object) {
    System.out.println("ROOTOBJ" + object);
    System.out.println(root);
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

  public void addToMapAndList(Double doub, Mover mover){
    turtleMap.put(doub, mover);
    turtleList.add(doub);
  }


  public void displayReturnValue(String returnValue){
    displayWindow.getCommandWindow().setText(returnValue);
  }

  public void selectTurtle(String num) {
    Double number = Double.parseDouble(num);
    setMyMover(turtleMap.get(number));
  }

  private void setMyMover(Mover mover) {
    myMover = mover;
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
