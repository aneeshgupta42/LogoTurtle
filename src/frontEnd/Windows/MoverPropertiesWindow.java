package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.ButtonsBoxesandLabels.ButtonWithTextBox;
import frontEnd.ButtonsBoxesandLabels.OurButtons;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPicker;
import frontEnd.ButtonsBoxesandLabels.PropertyLabel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MoverPropertiesWindow  extends VBox {
private VBox turtlebox;

private static final int SIDEPANE_WIDTH = 300;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myInitialColorResources;
  private ResourceBundle myComboBoxOptionsResources;
  private ResourceBundle myTextFieldResources;
  private ResourceBundle myTurtlePropertyResources;
  private ResourceBundle myLabelPropertyResources;
  private ResourceBundle myMoverPropertiesDropDownResources;
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String InitialColorResources = "resources.UIActions.InitialColors";
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private static final String TextFieldResources = "resources.UIActions.TextFieldActions";
  private static final String TurtlePropertyResources = "resources.UIActions.TurtlePropertyActions";
  private static final String LabelResources = "resources.UIActions.LabelActions";
  private static final String MoverPropertiesDropDownResources = "resources.UIActions.MoverPropertiesDropDown";
  private static final String COMBO_OPTIONS = "Options";
  private static final String COLOR_INITIAL = "Initial";
  private List<OurComboBox> imageResources = new ArrayList<>();
  private List<OurLabeledColorPicker> penResources = new ArrayList<>();
  private OurComboBox turtleSelection;

  public MoverPropertiesWindow(ButtonAction myButtonAction, ObservableList<Double> turtleList, Map propertyLabelMap){
    myColorPickerResources = ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources = ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    myTextFieldResources = ResourceBundle.getBundle(TextFieldResources);
    myTurtlePropertyResources = ResourceBundle.getBundle(TurtlePropertyResources);
    myLabelPropertyResources = ResourceBundle.getBundle(LabelResources);
    myMoverPropertiesDropDownResources = ResourceBundle.getBundle(MoverPropertiesDropDownResources);
    makeTurtlePropertiesWindow(myButtonAction, turtleList,propertyLabelMap);

  }

  private void makeTurtlePropertiesWindow(ButtonAction myButtonAction, ObservableList<Double> turtleList, Map propertyLabelMap) {
    //turtlebox = new VBox();
    setSpacing(10);
    setPrefWidth(SIDEPANE_WIDTH);
    VBox buttons = new VBox();
    for (String key : Collections.list(myMoverPropertiesDropDownResources.getKeys())) {
      OurComboBox comboBox = new OurComboBox(myMoverPropertiesDropDownResources.getString(key), key,
          myButtonAction, FXCollections
          .observableArrayList(
              myComboBoxOptionsResources.getString(key + COMBO_OPTIONS).split(",")));
      buttons.getChildren().add(comboBox);
      imageResources.add(comboBox);
    }
    for (String key : Collections.list(myColorPickerResources.getKeys())) {
      if (key.startsWith("setPen")) {
        OurLabeledColorPicker colorPicker = new OurLabeledColorPicker(
            myColorPickerResources.getString(key), key, myButtonAction,
            myInitialColorResources.getString(key + COLOR_INITIAL));
        buttons.getChildren().add(colorPicker);
        penResources.add(colorPicker);
      }
    }
    for (String key : Collections.list(myTurtlePropertyResources.getKeys())) {
      buttons.getChildren()
          .add(new OurButtons(myTurtlePropertyResources.getString(key), key, myButtonAction));
    }
    for (String key : Collections.list(myTextFieldResources.getKeys())) {
      buttons.getChildren()
          .add(new ButtonWithTextBox(myTextFieldResources.getString(key), key, myButtonAction));
    }
    VBox propertiesBox = new VBox();
    turtleSelection = new OurComboBox("Select Mover", "selectTurtle", myButtonAction,
        FXCollections.observableList(turtleList));
    turtleSelection.getBox().itemsProperty().bind(new SimpleObjectProperty<>(turtleList));
    Text moverProperties = new Text("Properties of Mover: ");
    propertiesBox.getChildren().addAll(turtleSelection, moverProperties);
    for (String key : Collections.list(myLabelPropertyResources.getKeys())) {
      PropertyLabel plabel = new PropertyLabel(myLabelPropertyResources.getString(key), key,
          myButtonAction);
      propertiesBox.getChildren().add(plabel);
      propertyLabelMap.put(key, plabel);
    }
    getChildren().addAll(buttons, propertiesBox);
  }

  public OurComboBox getTurtleSelection(){
    return turtleSelection;
  }

  public List<OurLabeledColorPicker> getPenResources(){
    return penResources;
  }
}
