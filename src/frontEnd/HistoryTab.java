package frontEnd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class HistoryTab extends Tab {
  private VBox historyBox;
  private static final String HYPERLINK_STYLE = "hyper-link";
  private static final String HISTORY_TAB_TITLE = "History";

  public HistoryTab(){
    setText(HISTORY_TAB_TITLE);
  }

  private void setHistoryTab(VBox historyBox, String thistext, CommandWindow commandWindow) {
    Hyperlink link = new Hyperlink();
    link.getStyleClass().add(HYPERLINK_STYLE);
    link.setText(commandWindow.getText());
    link.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        commandWindow.setText(thistext);
      }
    });
    historyBox.getChildren().add(0, link);
    setContent(historyBox);
  }
}
