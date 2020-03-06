/*package frontEnd;

import frontEnd.ButtonsBoxesandLabels.OurButtons;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPickers;
import frontEnd.ButtonsBoxesandLabels.PropertyLabel;
import java.util.Collections;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class junk {
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


  private Node makeDisplayWindow() {
    VBox vbox = new VBox();
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rectangle.getStyleClass().add(RECTANGLE_STYLE);
    commandWindow = new HBox(makeCommandWindow());
    vbox.getChildren().addAll(rectangle, commandWindow);
    display.getChildren().addAll(vbox);
    return display;
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
//move to mover
  public void setMoverPosition(ImageView image) {
    xcenter = DISPLAY_WIDTH / 2 - image.getBoundsInLocal().getWidth() / 2 + SIDEPANE_WIDTH;
    image.setX(xcenter);
    ycenter = BUTTON_PANE_HEIGHT + DISPLAY_HEIGHT / 2 - image.getBoundsInLocal().getHeight() / 2;
    image.setY(ycenter);
    image.setRotate(0);
    myMover.initializeLinePosition(image.getX(), image.getY(), image.getRotate());
    myMover.setMoverInitialCords(image.getX(), image.getY());
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
 public void setOnRun() {
    myText = inputArea.getText();
    String thistext = myText;
    myButtonAction.sendInfoToControl(myText);
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
  public void addTurtle() {
    System.out.println("reached");
    numOfMovers++;
    Mover mover = new Mover(this, numOfMovers);
    mover.setInitialMoverPosition();
    turtleMap.put(numOfMovers, mover);
    root.getChildren().add(mover.getImage());
    turtleList.add(numOfMovers);
  }

  public void setMyMover(Object mover) {
    myMover = (frontEnd.Mover) mover;
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
   public double getAngle(){
    return myMover.getMoverAngle();
  }
}*/
