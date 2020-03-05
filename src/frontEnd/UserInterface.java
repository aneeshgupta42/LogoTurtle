package frontEnd;

import Controller.Control;
import backEnd.ErrorHandler;
import frontEnd.ButtonsBoxesandLabels.OurButtons;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPickers;
import frontEnd.ButtonsBoxesandLabels.PropertyLabel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class UserInterface extends Application implements Viewable {
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
  List<String> imageOptions;
  private int currImageIndex;



  public UserInterface() {
    myStage = new Stage();
    myMover = new Mover(this);
    control = new Control(this);
    myLine = new Line();
    display = new Group();
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

  private void step() {
    checkIfTurtleMovesOutOfBounds();
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

  @Override
  public void setMyMover(Object mover) {
    myMover = (Mover) mover;
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene() {
    root = new BorderPane();
    hbox = addHBox();
    root.setTop(hbox);
    display_window = makeDisplayWindow();
    root.setLeft(makeTurtlePropertiesWindow());
    root.setCenter(display_window);
    root.setRight(makeSideWindow());
    for (double i = 1; i <= numOfMovers; i++) {
      myMover = new Mover(this);
      moverID = i;
      //moverImage = myMover.displayMover(TURTLE);
      setMoverPosition(myMover.getImage());
      turtleMap.put(i, myMover);
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

  private Node makeTurtlePropertiesWindow() {
    turtlebox = new VBox();
    turtlebox.setSpacing(10);
    turtlebox.setPrefWidth(SIDEPANE_WIDTH);
    VBox buttons = new VBox();
    for (String key : Collections.list(myMoverPropertiesDropDownResources.getKeys())) {
      OurComboBox comboBox = new OurComboBox(myMoverPropertiesDropDownResources.getString(key), key, myButtonAction, FXCollections
          .observableArrayList(
              myComboBoxOptionsResources.getString(key + COMBO_OPTIONS).split(",")));
      buttons.getChildren().add(comboBox);
      imageResources.add(comboBox);
    }
    for (String key : Collections.list(myColorPickerResources.getKeys())) {
      if (key.startsWith("setPen")) {
        OurLabeledColorPickers colorPicker = new OurLabeledColorPickers(myColorPickerResources.getString(key), key, myButtonAction,
            myInitialColorResources.getString(key + COLOR_INITIAL));
        buttons.getChildren().add(colorPicker);
        penResources.add(colorPicker);
      }
    }
    for (String key : Collections.list(myTurtlePropertyResources.getKeys())) {
      buttons.getChildren()
          .add(new OurButtons(myTurtlePropertyResources.getString(key), key, myButtonAction));
    }
    VBox propertiesBox = new VBox();
    OurComboBox turtleSelection = new OurComboBox("Select Mover", "selectTurtle", myButtonAction,
        FXCollections.observableList(turtleList));
    turtleSelection.getBox().itemsProperty().bind(new SimpleObjectProperty<>(turtleList));
    Text moverProperties = new Text("Properties of Mover: ");
    propertiesBox.getChildren().addAll(turtleSelection,moverProperties);
    for (String key : Collections.list(myLabelPropertyResources.getKeys())) {
      PropertyLabel plabel = new PropertyLabel(myLabelPropertyResources.getString(key), key, myButtonAction);
      propertiesBox.getChildren().add(plabel);
      propertyLabelMap.put(key, plabel);
    }
    turtlebox.getChildren().addAll(buttons, propertiesBox);
    return turtlebox;
  }

  @Override
  public ButtonAction getButtonAction(){
    return myButtonAction;
  }


  //fix numbers
  @Override
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
      hbox.getChildren().add(new OurButtons(myButtonResources.getString(key), key, myButtonAction));
    }
    for (String key : Collections.list(myComboBoxResources.getKeys())) {
      hbox.getChildren()
          .add(new OurComboBox(myComboBoxResources.getString(key), key, myButtonAction, FXCollections
              .observableArrayList(
                  myComboBoxOptionsResources.getString(key + COMBO_OPTIONS).split(","))));
    }
    for (String key : Collections.list(myColorPickerResources.getKeys())) {
      if (key.startsWith("setBackground")) {
        hbox.getChildren().add(
            new OurLabeledColorPickers(myColorPickerResources.getString(key), key, myButtonAction,
                myInitialColorResources.getString(key + COLOR_INITIAL)));
      }
    }
  }

  private Node makeDisplayWindow() {
    VBox vbox = new VBox();
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rectangle.getStyleClass().add(RECTANGLE_STYLE);
    commandWindow = new HBox(makeCommandWindow());
    vbox.getChildren().addAll(rectangle, commandWindow);
    display.getChildren().addAll(vbox);
    return display;
  }

  @Override
  public void setMoverPosition(ImageView image) {
    xcenter = DISPLAY_WIDTH / 2 - image.getBoundsInLocal().getWidth() / 2 + SIDEPANE_WIDTH;
    image.setX(xcenter);
    ycenter = BUTTON_PANE_HEIGHT + DISPLAY_HEIGHT / 2 - image.getBoundsInLocal().getHeight() / 2;
    image.setY(ycenter);
    image.setRotate(0);
    myMover.initializeLinePosition(image.getX(), image.getY(), image.getRotate());
    myMover.setMoverInitialCords(image.getX(), image.getY());
  }

  @Override
  public Mover getMover(){
    return myMover;
  }

  @Override
  public Map getTurtleMap(){
    return turtleMap;
  }

  private Node makeSideWindow() {
    tabPane = new TabPane();
    tabPane.setMinWidth(SIDEPANE_WIDTH);
    tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    history = new ScrollPane(); ////here or at the top?
    variables = new ScrollPane();
    userCommands = new ScrollPane();
    Tab tab1 = new Tab(HISTORY_TAB_TITLE, history);
    Tab tab2 = new Tab(VARIABLE_TAB_TITLE, variables);
    Tab tab3 = new Tab(COMMAND_TAB_TITLE, userCommands);
    tabPane.getTabs().add(tab1);
    tabPane.getTabs().add(tab2);
    tabPane.getTabs().add(tab3);
    //VBox vBox = new VBox(tabPane);
    return tabPane;
  }

  private Node makeCommandWindow() {
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
      vbox.getChildren().add(new OurButtons(myTextButtonResources.getString(key), key, myButtonAction));
    }
    vbox.setMinWidth(COMMAND_CONTROLS_WIDTH);
    hbox.getChildren().addAll(inputArea, vbox);
    return hbox;
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

  @Override
  public String getText() {
    return myText;
  }

  @Override
  public TextArea getCommander(){
    return myCommander;
  }

  @Override
  public Control getControl(){
    return control;
  }

  @Override
  public Rectangle getRectangle(){
    return rectangle;
  }


  @Override
  public Color getLineColor() {
    return myMover.getLineColor();
  }

  @Override
  public BorderPane getRoot() {
    return root;
  }

  public void addNodeToRoot(Node object) {
    root.getChildren().add(object);
  }

  public void removeNodeFromRoot(Node object){
    root.getChildren().remove(object);
  }

  @Override
  public void setMoverX(double x) {
    moverX = x;
    System.out.println("x " + x);
  }

  @Override
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

  @Override
  public void resetDisplay() {
    root.getChildren().removeIf(object -> object instanceof Line);
    for(Mover mover : turtleMap.values()) {
      setMoverPosition(mover.getImage());
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

  @Override
  public void setLanguage(String language) {
    control.setLanguage(language);
  }

  @Override
  public void setImage(String image) {
    double moverXPos = myMover.getImage().getX();
    double moverYPos = myMover.getImage().getY();
    double moverAngle = myMover.getMoverAngle();
    root.getChildren().remove(myMover.getImage());
    String path = myComboBoxOptionsResources.getString(image);
    currImageIndex = imageOptions.indexOf(image)+1;
    myMover.setImageIndex(currImageIndex);
    myMover.changeMoverDisplay(path);
    //)= (ImageView) myMover.changeMoverDisplay(path);
    myMover.getImage().setX(moverXPos);
    myMover.getImage().setY(moverYPos);
    myMover.getImage().setRotate(moverAngle);
    root.getChildren().add(myMover.getImage());
  }

  @Override
  public void setImageIndex(int index) {
    String image = imageOptions.get(index-1);
    currImageIndex = index;
    myMover.setImageIndex(currImageIndex);
    setImage(image);
  }

  public int getCurrentImageIndex(){
    return currImageIndex;
  }

  @Override
  public double getmoverID(){
    return moverID;
  }

  @Override
  public void setBackgroundColor(Color color) {
    rectangle.setFill(color);
  }

  @Override
  public void setPenColor(Color color) {
    //myLine.setStroke(color);
    //lineColor = color;
    myMover.setLineColor(color);
    //myMover.updateLabels();
  }

  @Override
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

  private void sendInfoToControl(String myText) {
    for(Mover mover: turtleMap.values()) {
      if (mover.getActive()) {
        myMover= mover;
        control.setCommand(myText);
        control.passTurtle(myMover);
        control.parseCommand();
      }
    }
  }

  @Override
  public void addTurtle() {
    System.out.println("reached");
    numOfMovers++;
    Mover mover = new Mover(this);
    //moverImage = mover.displayMover(TURTLE);
    setMoverPosition(mover.getImage());
    turtleMap.put(numOfMovers, mover);
    root.getChildren().add(mover.getImage());
    turtleList.add(numOfMovers);
    System.out.println(turtleList);
    //turtleSelection.updateItems(FXCollections.observableArrayList(turtleList));
  }

  @Override
  public void setOnClear() {
    inputArea.setText("");
  }

  @Override
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

  @Override
  public double getLineWidth() {
    return lineWidth;
  }
  @Override
  public double getXPosition(){
    return myMover.getMoverCol()-xcenter;
  }
  @Override
  public double getYPosition(){
    return myMover.getMoverRow()-ycenter;
  }
  @Override
  public double getMoverID(){
    System.out.println("ID" + moverID);
    return moverID;
  }
  @Override
  public double getAngle(){
    return myMover.getMoverAngle();
  }

  @Override
  public double getLineThickness(){
    return myMover.getThickness();
  }

  @Override
  public String getPenPosition(){
    return myMover.getPenPosition();
  }
  @Override
  public void setDefaultImage(String image) {
    myMover.setDefaultImage(image);
  }

  @Override
  public void changePenPosition(){
    myMover.setPen(!myMover.getPen());
  }
}
