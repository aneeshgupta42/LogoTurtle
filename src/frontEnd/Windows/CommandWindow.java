package frontEnd.Windows;

import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CommandWindow extends HBox {
  private TextArea inputArea;
  private static final String TEXT_INPUT_PROMPT = "Enter Command";
  private static final int NUM_TEXT_COLUMNS = 10;
  private static final double TEXTBOX_HEIGHT = 200;
  private static final double COMMAND_CONTROLS_WIDTH = 75;
  private ResourceBundle myTextButtonResources;
  private static final String TextBoxButtonResources = "resources.UIActions.TextButtonActions";
  private static CustomWindow myCustomWindow;

  public CommandWindow(double width, CustomWindow customWindow) {
    myCustomWindow = customWindow;
    myTextButtonResources = ResourceBundle.getBundle(TextBoxButtonResources);
    makeCommandWindow(width);
  }

  private void makeCommandWindow(double display_width){
    setPrefWidth(display_width);
    inputArea = new TextArea();
    inputArea.setPromptText(TEXT_INPUT_PROMPT);
    inputArea.setPrefColumnCount(NUM_TEXT_COLUMNS);
    inputArea.getText();
    GridPane.setConstraints(inputArea, 0, 0);
    inputArea.setPrefWidth(display_width);
    inputArea.setMaxHeight(TEXTBOX_HEIGHT);
    VBox vbox = new VBox();
    myCustomWindow.createButtons(vbox, myTextButtonResources);
    vbox.setMinWidth(COMMAND_CONTROLS_WIDTH);
    getChildren().addAll(inputArea, vbox);
  }

  public void clearText() {
    inputArea.setText("");
  }

  public String getText(){
    return inputArea.getText();
  }

  public void setText(String text){
    inputArea.setText(text);
  }

}
