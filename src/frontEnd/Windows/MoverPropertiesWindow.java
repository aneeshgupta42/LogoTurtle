package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.UIElements.ColorGrid;
import frontEnd.UIElements.OurComboBox;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MoverPropertiesWindow  extends VBox {

private static final int SIDEPANE_WIDTH = 300;
  private ResourceBundle myColorPickerResources;
  private ResourceBundle myTextFieldResources;
  private ResourceBundle myTurtlePropertyResources;
  private ResourceBundle myLabelPropertyResources;
  private ResourceBundle myMoverPropertiesDropDownResources;
  private static final String ColorPickerResources = "resources.UIActions.ColorPickerActions";
  private static final String TextFieldResources = "resources.UIActions.TextFieldActions";
  private static final String TurtlePropertyResources = "resources.UIActions.TurtlePropertyActions";
  private static final String LabelResources = "resources.UIActions.LabelActions";
  private static final String MoverPropertiesDropDownResources = "resources.UIActions.MoverPropertiesDropDown";
  private static final String PEN_RESOUCE = "setPen";
  private OurComboBox turtleSelection;
  private CustomWindow myCustomWindow;
  private static final String  MOVER_SELECTION = "Select Mover";
  private static final String MOVER_SELECTION_METHOD_NAME = "selectTurtle";
  private static final String TITLE_OF_PROPERTIES = "Properties of Mover: ";

  public MoverPropertiesWindow(ButtonAction myButtonAction, CustomWindow customWindow, ObservableList<Double> turtleList, Map propertyLabelMap){
    myCustomWindow = customWindow;
    myColorPickerResources = ResourceBundle.getBundle(ColorPickerResources);
    myTextFieldResources = ResourceBundle.getBundle(TextFieldResources);
    myTurtlePropertyResources = ResourceBundle.getBundle(TurtlePropertyResources);
    myLabelPropertyResources = ResourceBundle.getBundle(LabelResources);
    myMoverPropertiesDropDownResources = ResourceBundle.getBundle(MoverPropertiesDropDownResources);
    makeTurtlePropertiesWindow(myButtonAction, turtleList,propertyLabelMap);

  }

  private void makeTurtlePropertiesWindow(ButtonAction myButtonAction, ObservableList<Double> turtleList, Map propertyLabelMap) {
    setSpacing(10);
    setPrefWidth(SIDEPANE_WIDTH);
    VBox buttons = new VBox();
    myCustomWindow.createComboBoxes(buttons, myMoverPropertiesDropDownResources);
    myCustomWindow.createColorPicker(buttons, myColorPickerResources, PEN_RESOUCE);
    myCustomWindow.createButtons(buttons, myTurtlePropertyResources);
    myCustomWindow.createTextBoxButtons(buttons, myTextFieldResources);
    VBox propertiesBox = new VBox();
    createTurtleSelectionBoxandTitle(myButtonAction, turtleList, propertiesBox);
    myCustomWindow.createLabel(propertiesBox, myLabelPropertyResources, propertyLabelMap);
    ColorGrid colorGrid = new ColorGrid();
    getChildren().addAll(buttons, propertiesBox, colorGrid);
  }

  private void createTurtleSelectionBoxandTitle(ButtonAction myButtonAction,
      ObservableList<Double> turtleList, VBox propertiesBox) {
    turtleSelection = new OurComboBox(MOVER_SELECTION, MOVER_SELECTION_METHOD_NAME, myButtonAction,
        FXCollections.observableList(turtleList));
    turtleSelection.getBox().itemsProperty().bind(new SimpleObjectProperty<>(turtleList));
    Text moverProperties = new Text(TITLE_OF_PROPERTIES);
    propertiesBox.getChildren().addAll(turtleSelection, moverProperties);
  }
}
