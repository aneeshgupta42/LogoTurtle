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
    historyBox = new VBox();
  }

  public void setHistoryTab(CommandWindow commandWindow, String commands) {
    Hyperlink link = new Hyperlink();
    link.getStyleClass().add(HYPERLINK_STYLE);
    link.setText(commands);
    link.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        commandWindow.setText(link.getText());
      }
    });
    historyBox.getChildren().add(0, link);
    setContent(historyBox);
  }
}