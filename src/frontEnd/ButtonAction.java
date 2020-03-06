package frontEnd;

import Controller.Control;
import backEnd.ErrorHandler;
import frontEnd.ErrorBoxes;
import frontEnd.Mover;
import frontEnd.UserInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ButtonAction {
  private UserInterface myView;
  private static final String COMMAND_ONE = "viewboc.png";
  private static final String COMMAND_TWO = "viewbox.png";
  private Mover myMover;
  private Map<Double, Mover> turtleMap;
  private BorderPane myRoot;
  private TextArea myCommander;
  private Control control;
  private Rectangle rectangle;
  private double defaultMoveAmount = 50;
  private double defaultTurnAmount = 90;

  public ButtonAction(UserInterface view){
    myView=view;
    myMover = myView.getMover();
    turtleMap = myView.getTurtleMap();
    myRoot = myView.getRoot();
    myCommander = myView.getCommander();
    control = myView.getControl();
    rectangle = myView.getRectangle();
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
    myView.resetDisplay();
  }

  public void selectFileScreen() {
    FileChooser fileChooser = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data/examples";
    fileChooser.setInitialDirectory(new File(dataPath));
    File selectedFile = fileChooser.showOpenDialog(myView.getStage());
    if (selectedFile != null) {
      try {
        myView.scanFile(selectedFile);
      } catch (FileNotFoundException ex) {
        ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidFile"));
      }
    }
  }

  public void setLanguage(String language) {
    control.setLanguage(language);
  }

  public void setImage(String image) {
    //String path =myView.getResource().getString(image);
    //myMover.changeMoverDisplay(path);
    double moverXPos = myView.getMover().getImage().getX();
    double moverYPos = myView.getMover().getImage().getY();
    double moverAngle =myView.getMover().getMoverAngle();
    myView.removeNodeFromRoot(myView.getMover().getImage());
    String path = myView.getResource().getString(image);
    myView.getMover().changeMoverDisplay(path);
    //)= (ImageView) myMover.changeMoverDisplay(path);
    myView.getMover().getImage().setX(moverXPos);
    myView.getMover().getImage().setY(moverYPos);
    myView.getMover().getImage().setRotate(moverAngle);
    myView.addNodeToRoot(myView.getMover().getImage());
  }

  public void setBackgroundColor(Color color) {
    myView.setBackgroundColor(color);
    //rectangle.setFill(color);
  }

  public void setPenColor(Color color) {
    myMover.setLineColor(color);
  }

  public void setOnRun() {
    myView.setOnRun();
    /*myText = inputArea.getText();
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
    myMover.updateLabels();*/
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

  public void addTurtle() {
    myView.addTurtle();
    /*System.out.println("reached");
    numOfMovers++;
    Mover mover = new Mover(this);
    //moverImage = mover.displayMover(TURTLE);
    setMoverPosition(mover.getImage());
    turtleMap.put(numOfMovers, mover);
    myRoot.getChildren().add(mover.getImage());
    turtleList.add(numOfMovers);
    System.out.println(turtleList);
    //turtleSelection.updateItems(FXCollections.observableArrayList(turtleList));*/
  }

  public void moveBackward() {
    sendInfoToControl("fd " + (-defaultMoveAmount));
  }

  public void moveForward() {
    sendInfoToControl("fd " + defaultMoveAmount);
  }

  public void moveLeft() {
    sendInfoToControl("lt " + defaultTurnAmount);
  }

  public void moveRight() {
    sendInfoToControl("rt " + defaultTurnAmount);
  }

  public void setOnClear() {
    myView.setOnClear();
    //inputArea.setText("");
  }

  public void selectTurtle(String num) {
    myView.selectTurtle(num);
    /*Double number = Double.parseDouble(num);
    myView.setMyMover(turtleMap.get(number));
    moverID = number;
    for(OurLabeledColorPickers label: penResources){
      label.setInitialColor(myMover.getLineColor());
    }
    myMover.updateLabels();*/
  }

  public double getLineWidth() {
    return myView.getLineWidth();
  }
  public double getXPosition(){
    //return myMover.getMoverCol()-xcenter;
    return myView.getXPosition();
  }
  public double getYPosition(){
    //return myMover.getMoverRow()-ycenter;
    return myView.getYPosition();
  }
  public double getMoverID(){
    //System.out.println("ID" + moverID);
    return myView.getmoverID();
  }
  public double getAngle(){
    return myMover.getMoverAngle();
  }

  public double getLineThickness(){
    return myMover.getThickness();
  }

  public String getPenPosition(){
    return myMover.getPenPosition();
  }
  public void setDefaultImage(String image) {
    myMover.setDefaultImage(image);

  }
  public void changePenPosition(){
    myMover.setPen(!myMover.getPen());
  }
  public Color getLineColor() {
    return myMover.getLineColor();
  }


}
