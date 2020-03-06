package frontEnd.Windows;


import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class ToolBarWindow extends HBox {

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
  private CustomWindow myCustomWindow;
  private static final String BACKGROUND_RESOURCE = "setBackground";


  public ToolBarWindow(CustomWindow customWindow) {
    myCustomWindow = customWindow;
    myButtonResources = ResourceBundle.getBundle(ButtonResources);
    myComboBoxResources = ResourceBundle.getBundle(ComboBoxResources);
    myColorPickerResources = ResourceBundle.getBundle(ColorPickerResources);
    myInitialColorResources = ResourceBundle.getBundle(InitialColorResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    makeToolBar();
  }

  public void makeToolBar() {
    getStyleClass().add(HBOX_STYLE);
    setPrefHeight(TOOL_BAR_HEIGHT);
    setPadding(new Insets(15, 12, 15, 12));
    setSpacing(10);
    addButtons();
  }

  private void addButtons() {
    myCustomWindow.createButtons(this, myButtonResources);
    myCustomWindow.createComboBoxes(this,
        myComboBoxResources);
    myCustomWindow.createColorPicker(this,
        myColorPickerResources, BACKGROUND_RESOURCE);
  }
}
