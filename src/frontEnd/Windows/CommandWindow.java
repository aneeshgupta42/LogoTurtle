package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.ButtonsBoxesandLabels.OurButtons;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommandWindow extends HBox {
  private TextArea inputArea;
  private static final String TEXT_INPUT_PROMPT = "Enter Command";
  private static final int NUM_TEXT_COLUMNS = 10;
  private static final double TEXTBOX_HEIGHT = 200;
  private static final double COMMAND_CONTROLS_WIDTH = 75;
  private ResourceBundle myTextButtonResources;
  private static final String TextBoxButtonResources = "resources.UIActions.TextButtonActions";

  public CommandWindow(double width, ButtonAction myButtonAction) {
    myTextButtonResources = ResourceBundle.getBundle(TextBoxButtonResources);
    makeCommandWindow(width, myButtonAction);
  }

  private void makeCommandWindow(double display_width, ButtonAction myButtonAction){
    setPrefWidth(display_width);
    inputArea = new TextArea();
    //myCommander = inputArea;
    inputArea.setPromptText(TEXT_INPUT_PROMPT);
    inputArea.setPrefColumnCount(NUM_TEXT_COLUMNS);
    inputArea.getText();
    GridPane.setConstraints(inputArea, 0, 0);
    inputArea.setPrefWidth(display_width);
    inputArea.setMaxHeight(TEXTBOX_HEIGHT);
    VBox vbox = new VBox();
    for (String key : Collections.list(myTextButtonResources.getKeys())) {
      vbox.getChildren().add(new OurButtons(myTextButtonResources.getString(key), key, myButtonAction));
    }
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
