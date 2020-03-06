package frontEnd;

import Controller.Control;
import backEnd.ErrorHandler;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPickers;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
  private DisplayWindow displayWindow;
  private Map<Double, Mover> turtleMap;
  private Control control;
  private Rectangle rectangle;
  private double defaultMoveAmount = 50;
  private double defaultTurnAmount = 90;

  public ButtonAction(UserInterface view){
    myView=view;
    myMover = myView.getMover();
    turtleMap = myView.getTurtleMap();
    control = myView.getControl();
    rectangle = myView.getRectangle();
    displayWindow = myView.getDisplayWindow();
  }

  public UserInterface getView(){
    return myView;
  }

  public void resetDisplay() {
    myView.getDisplayWindow().resetDisplay(getMoverMap());
  }

  public void selectFileScreen() {
    FileChooser fileChooser = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data/examples";
    fileChooser.setInitialDirectory(new File(dataPath));
    File selectedFile = fileChooser.showOpenDialog(myView.getStage());
    if (selectedFile != null) {
      try {
        scanFile(selectedFile);
      } catch (FileNotFoundException ex) {
        ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidFile"));
      }
    }
  }

  private void scanFile(File file) throws FileNotFoundException {
    Scanner scnr = new Scanner(file);
    //Reading each line of file using Scanner class
    int lineNumber = 0;
    while (scnr.hasNextLine()) {
      String line = scnr.nextLine();
      getCommandWindow().setText(getCommandWindow().getText() + line + "\n");
      lineNumber++;
    }
  }

  public void setLanguage(String language) {
    control.setLanguage(language);
  }

  public void setImage(String image) {
    //String path =myView.getResource().getString(image);
    //myMover.changeMoverDisplay(path);
    double moverXPos = getMover().getImage().getX();
    double moverYPos = getMover().getImage().getY();
    double moverAngle =getMover().getMoverAngle();
    myView.removeNodeFromRoot(getMover().getImage());
    String path = myView.getResource().getString(image);
    getMover().changeMoverDisplay(path);
    //)= (ImageView) myMover.changeMoverDisplay(path);
    getMover().getImage().setX(moverXPos);
    getMover().getImage().setY(moverYPos);
    getMover().getImage().setRotate(moverAngle);
    myView.addNodeToRoot(getMover().getImage());
  }

  public void setBackgroundColor(Color color) {
    myView.getDisplayWindow().setBackgroundColor(color);
  }

  public void setPenColor(Color color) {
    getMover().setLineColor(color);
  }

  public void setOnRun() {
    String myText = getCommandWindow().getText();
    sendInfoToControl(myText);
    getCommandWindow().clearText();
    if (getMover().objectMoved()) {
      System.out.println("variables" + control.getVariables().keySet());
      //setHistoryTab(historyBox, thistext);
      getMover().setObjectMoved(false);
    }
    //createStoredElementsTabs(variablesBox, variables, control.getVariables(), true);
    //createStoredElementsTabs(userCommandsBox, userCommands, control.getUserCommands(), false);
    getMover().updateLabels();
  }

  void sendInfoToControl(String myText) {
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
    Double numOfMovers = (double) myView.getTurtleMap().size() + 1;
    Mover mover = new Mover(myView, numOfMovers);
    mover.setInitialMoverPosition();
    myView.addToMap(numOfMovers, mover);
    myView.addNodeToRoot(mover.getImage());
    //turtleList.add(numOfMovers);
    //System.out.println(turtleList);
    //turtleSelection.updateItems(FXCollections.observableArrayList(turtleList));
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
    getCommandWindow().clearText();
  }

  public void selectTurtle(String num) {
    //myView.selectTurtle(num);
    Double number = Double.parseDouble(num);
    myView.setMyMover(getMoverMap().get(number));
    for(OurLabeledColorPickers label: myView.getPropertyWindow().getPenResources()){
      label.setInitialColor(myMover.getLineColor());
    }
    getMover().updateLabels();
  }

  public double getLineWidth() {
    return getMover().getThickness();
  }

  public double getXPosition(){
    return getMover().getXPosition();
  }
  public double getYPosition(){
    return getMover().getYPosition();
  }
  public double getMoverID(){
    return getMover().getMoverID();
  }

  public double getAngle(){
    return getMover().getMoverAngle();
  }

  public double getLineThickness(){
    return getMover().getThickness();
  }

  public String getPenPosition(){
    return getMover().getPenPosition();
  }
  public void setDefaultImage(String image) {
    getMover().setDefaultImage(image);

  }
  public void changePenPosition(){
    getMover().setPen(!getMover().getPen());
  }

  public Color getLineColor() {
    return getMover().getLineColor();
  }

  private Mover getMover(){
    return myView.getMover();
  }
  private Map getMoverMap(){
    return myView.getTurtleMap();
  }
  private CommandWindow getCommandWindow(){
    return myView.getDisplayWindow().getCommandWindow();
  }
}


