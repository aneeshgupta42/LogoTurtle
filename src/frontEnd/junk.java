package frontEnd;

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
}
