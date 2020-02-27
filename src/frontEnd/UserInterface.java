package frontEnd;

import Controller.Control;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
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
import javafx.scene.control.Button;
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
import java.awt.Dimension;
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

  private static final int FRAMES_PER_SECOND = 60;
  private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final String TITLE = "JavaFX Animation Example";
  public static final Dimension DEFAULT_SIZE = new Dimension(1200, 1200);
  private static final int DISPLAY_WIDTH = 1000;
  private static final int DISPLAY_HEIGHT = 500;
  private HBox commandWindow;
  private TabPane tabPane;
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
  private BorderPane root;
  private Color lineColor = Color.BLACK;
  private ImageView moverImage;
  private HBox hbox;
  private ScrollPane history;
  private ScrollPane variables;
  private ScrollPane userCommands;
  private TextArea inputArea;
  private static final String ButtonResources = "resources.UIActions.ButtonActions";
  private static final String ComboBoxResources = "resources.UIActions.ComboBoxActions";
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String InitialColorResources = "resources.UIActions.InitialColors";
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private ResourceBundle myButtonResources;
  private ResourceBundle myComboBoxResources;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myInitialColorResources;
  private ResourceBundle myComboBoxOptionsResources;
  private double lineStartx;
  private double lineStarty;


  public UserInterface() {
    myStage = new Stage();
    myMover = new Mover(this);
    control = new Control();
    myLine = new Line();
    display = new Group();
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources= ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources= ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
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
    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      step(SECOND_DELAY);
    });
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
    myStage.setOnCloseRequest(t->stopEverything());
  }

  private void step(double secondDelay) {
    if(moverImage.getBoundsInParent().intersects(hbox.getBoundsInParent()) ){
      hideMoverAndLine(myMover.getLine().getStartX(), hbox.getBoundsInParent().getMaxY());
    }
    if(moverImage.getBoundsInParent().intersects(commandWindow.getBoundsInParent())){
      hideMoverAndLine(myMover.getLine().getStartX(), commandWindow.getBoundsInParent().getMinY());
    }
    if(moverImage.getBoundsInParent().intersects(tabPane.getBoundsInParent())){
      hideMoverAndLine(tabPane.getBoundsInParent().getMinX(), myMover.getLine().getStartY());
    }
  }

  private void hideMoverAndLine(double endX, double endY) {
    lineStartx = myMover.getLine().getStartX();
    lineStarty = myMover.getLine().getStartY();
    myMover.moverVisible(false);
    root.getChildren().remove(myMover.getLine());
    Line line = new Line(lineStartx, lineStarty, endX, endY);
    root.getChildren().add(line);
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
    moverImage = (ImageView) myMover.displayMover(TURTLE);
    setMoverPosition(moverImage);
    root.getChildren().addAll(moverImage);
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
    commandWindow = new HBox(makeCommandWindow());
    vbox.getChildren().addAll(rectangle, commandWindow);
    display.getChildren().addAll(vbox);
    return display;
  }

  public void setMoverPosition(ImageView image) {
    image.setX(DISPLAY_WIDTH/2-image.getBoundsInLocal().getWidth()/2);
    image.setY(70 + DISPLAY_HEIGHT/2-image.getBoundsInLocal().getHeight()/2);
    image.setRotate(0);
    System.out.println(display.getLayoutY());
    myMover.initializeLinePosition(image.getX(), image.getY(), image.getRotate());
    myMover.setMoverInitialCords(image.getX(), image.getY());
  }

  private Node makeSideWindow() {
    tabPane = new TabPane();
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
      control.passTurtle(myMover);
      control.parseCommand();
      inputArea.setText("");
      if(myMover.objectMoved()) {
        System.out.println("variables" + control.getVariables().keySet());
        setHistoryTab(historyBox, thistext);
        setVariablesTab(variablesBox);
        setUserCommandsTab(userCommandsBox);
        myMover.setObjectMoved(false);
      }});
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
          .observableArrayList(myComboBoxOptionsResources.getString(key+"Options").split(","))));
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
    setMoverPosition(moverImage);
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
        ///****fix this******
        ex.printStackTrace();
      }
    }
  }

  public void setLanguage(String language) {
    control.setLanguage(language);
  }

  public void setImage(String image){
    double moverXPos = moverImage.getX();
    double moverYPos = moverImage.getY();
    double moverAngle = myMover.getMoverAngle();
    root.getChildren().remove(moverImage);
    String path = myComboBoxOptionsResources.getString(image);
    moverImage = (ImageView) myMover.displayMover(path);
    moverImage.setX(moverXPos);
    moverImage.setY(moverYPos);
    moverImage.setRotate(moverAngle);
    root.getChildren().add(moverImage);
  }

  public void setBackgroundColor(Color color){
    rectangle.setFill(color);
  }
  public void setPenColor(Color color){
    myLine.setStroke(color);
    lineColor = color;
  }
}
