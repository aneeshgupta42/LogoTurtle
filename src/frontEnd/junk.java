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

}*/
