package frontEnd;

import controller.Control;
import backEnd.ErrorHandler;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPicker;
import frontEnd.Windows.CommandWindow;
import frontEnd.Windows.DisplayWindow;
import frontEnd.Windows.MoverPropertiesWindow;
import frontEnd.Windows.TabWindow;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.ResourceBundle;
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
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private DisplayWindow displayWindow;
  private Map<Double, Mover> turtleMap;
  private Control control;
  private ResourceBundle myComboBoxOptionsResources;
  private List<String> imageOptions;
  private double defaultMoveAmount = 50;
  private double defaultTurnAmount = 90;
  private MoverPropertiesWindow propertiesWindow;

  public ButtonAction(UserInterface view){
    myView=view;
    myMover = myView.getMover();
    turtleMap = myView.getTurtleMap();
    control = myView.getControl();
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    String optionsString = myComboBoxOptionsResources.getString("setImageOptions");
    imageOptions = Arrays.asList(optionsString.split(","));
    displayWindow = myView.getDisplayWindow();
    propertiesWindow = myView.getPropertyWindow();
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
    getMover().setImageIndex(imageOptions.indexOf(image)+1);
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
    String finalReturnvalue = control.getFinalReturnValue();
//    System.out.println("This goes in front end----->" + finalReturnvalue);
    myView.displayReturnValue("Output = " + finalReturnvalue);
    if (finalReturnvalue!=null) {
      getTabWindow().getHistoryTab().setHistoryTab(getCommandWindow(), myText);
      getMover().setObjectMoved(false);
    }
    getTabWindow().getCommandTab().setContent(getTabWindow().getCommandTab().resetTabContents(control.getUserCommands(), false));
    System.out.println("commands "+  control.getUserCommands());
    getTabWindow().getVariableTab().setContent(getTabWindow().getVariableTab().resetTabContents(control.getVariables(), true));
    getMover().updateLabels();
  }

  void sendInfoToControl(String myText) {

    for(Mover mover: turtleMap.values()) {
      if (mover.getActive()) {
        control.setCommand(myText);
        control.passTurtle(mover);
        control.parseCommand();
      }
    }
  }

  public void addTurtle() {
    Double numOfMovers = (double) myView.getTurtleMap().size() + 1;
    Mover mover = new Mover(myView, numOfMovers);
    mover.setInitialMoverPosition();
    myView.addToMapAndList(numOfMovers, mover);
    myView.addNodeToRoot(mover.getImage());
    //myView.addToList(numOfMovers);
    //System.out.println(turtleList);
    //System.out.println("LIST" + myView.getPropertyWindow().getTurtleSelection());
    //myView.getPropertyWindow().getTurtleSelection().updateItems(FXCollections.observableArrayList(myView.getTurtleList()));
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
    for(OurLabeledColorPicker label: myView.getPropertyWindow().getPenResources()){
      label.setInitialColor(myMover.getLineColor());
    }
    getMover().updateLabels();
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

  public void setDefaultMoveAmount(String num){
    Double number = Double.parseDouble(num);
    defaultMoveAmount = number;
  }

  public void setDefaultTurnAmount(String num){
    Double number = Double.parseDouble(num);
    defaultTurnAmount = number;
  }

  public void setPenThickness(String num){
    Double number = Double.parseDouble(num);
    getMover().setThickness(number);
  }

  public void openTab(String tabName){
    getTabWindow().openTabFromPane(tabName);
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
  private TabWindow getTabWindow(){
    return myView.getTabWindow();
  }
}


