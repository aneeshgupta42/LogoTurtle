package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.UIElements.ButtonWithTextBox;
import frontEnd.UIElements.OurButtons;
import frontEnd.UIElements.OurComboBox;
import frontEnd.UIElements.OurLabeledColorPicker;
import frontEnd.UIElements.PropertyLabel;
import java.util.Collections;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;

public class CustomWindow {

  private ResourceBundle myButtonResources;
  private ResourceBundle myComboBoxResources;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myInitialColorResources;
  private ResourceBundle myComboBoxOptionsResources;
  private ResourceBundle myTextButtonResources;
  private static final String TextBoxButtonResources = "resources.UIActions.TextButtonActions";
  private static final String ButtonResources = "resources.UIActions.ButtonActions";
  private static final String ComboBoxResources = "resources.UIActions.ComboBoxActions";
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String InitialColorResources = "resources.UIActions.InitialColors";
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private static final String COMBO_OPTIONS = "Options";
  private static final String COLOR_INITIAL = "Initial";
  private ButtonAction myButtonAction;

  public CustomWindow(ButtonAction buttonAction) {
    myButtonAction = buttonAction;
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources = ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources = ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    myTextButtonResources = ResourceBundle.getBundle(TextBoxButtonResources);
  }

  public void createComboBoxes(Pane box, ResourceBundle resourceBundle) {
    for (String key : Collections.list(resourceBundle.getKeys())) {
      OurComboBox comboBox = new OurComboBox(resourceBundle.getString(key), key,
          myButtonAction, FXCollections
          .observableArrayList(
              myComboBoxOptionsResources.getString(key + COMBO_OPTIONS).split(",")));
      box.getChildren().add(comboBox);
    }
  }

  public void createColorPicker(Pane box, ResourceBundle resourceBundle,
      String string) {
    for (String key : Collections.list(resourceBundle.getKeys())) {
      if (key.startsWith(string)) {
        OurLabeledColorPicker colorPicker = new OurLabeledColorPicker(
            myColorPickerResources.getString(key), key, myButtonAction,
            myInitialColorResources.getString(key + COLOR_INITIAL));
        box.getChildren().add(colorPicker);
      }
    }
  }

  public void createButtons(Pane box, ResourceBundle resourceBundle){
    for (String key : Collections.list(resourceBundle.getKeys())) {
      box.getChildren().add(new OurButtons(resourceBundle.getString(key), key, myButtonAction));
    }
  }

  public void createTextBoxButtons(Pane box, ResourceBundle resourceBundle) {
    for (String key : Collections.list(resourceBundle.getKeys())) {
      box.getChildren()
          .add(new ButtonWithTextBox(resourceBundle.getString(key), key, myButtonAction));
    }
  }
  public void createLabel(Pane box, ResourceBundle resourceBundle,
      Map labelMap){
    for (String key : Collections.list(resourceBundle.getKeys())) {
      PropertyLabel plabel = new PropertyLabel(resourceBundle.getString(key), key,
          myButtonAction);
      box.getChildren().add(plabel);
      labelMap.put(key, plabel);
    }
  }

  public ButtonAction getButtonAction(){
    return myButtonAction;
  }
}
