package frontEnd;

import frontEnd.ButtonsBoxesandLabels.OurButtons;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPickers;
import java.awt.Button;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class ToolBar extends HBox {
  private static final String HBOX_STYLE = "hbox";
  private static final int TOOL_BAR_HEIGHT = 70;
  private ResourceBundle myButtonResources;
  private ResourceBundle myComboBoxResources;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myInitialColorResources;
  private ResourceBundle myComboBoxOptionsResources;
  private static final String ButtonResources = "resources.UIActions.ButtonActions";
  private static final String ComboBoxResources = "resources.UIActions.ComboBoxActions";
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String InitialColorResources = "resources.UIActions.InitialColors";
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private static final String COMBO_OPTIONS = "Options";
  private static final String COLOR_INITIAL = "Initial";


  public ToolBar(ButtonAction myButtonAction){
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources = ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources = ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    makeToolBar(myButtonAction);
  }

  public void makeToolBar(ButtonAction myButtonAction) {
    getStyleClass().add(HBOX_STYLE);
    setPrefHeight(TOOL_BAR_HEIGHT);
    setPadding(new Insets(15, 12, 15, 12));
    setSpacing(10);
    addButtons(myButtonAction);
  }

  private void addButtons(ButtonAction myButtonAction) {
    for (String key : Collections.list(myButtonResources.getKeys())) {
      getChildren().add(new OurButtons(myButtonResources.getString(key), key, myButtonAction));
    }
    for (String key : Collections.list(myComboBoxResources.getKeys())) {
      getChildren()
          .add(new OurComboBox(myComboBoxResources.getString(key), key, myButtonAction, FXCollections
              .observableArrayList(
                  myComboBoxOptionsResources.getString(key + COMBO_OPTIONS).split(","))));
    }
    for (String key : Collections.list(myColorPickerResources.getKeys())) {
      if (key.startsWith("setBackground")) {
        getChildren().add(
            new OurLabeledColorPickers(myColorPickerResources.getString(key), key, myButtonAction,
                myInitialColorResources.getString(key + COLOR_INITIAL)));
      }
    }
  }

}
