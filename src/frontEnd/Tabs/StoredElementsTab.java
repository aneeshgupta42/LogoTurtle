package frontEnd.Tabs;

import frontEnd.Windows.CommandWindow;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

abstract public class StoredElementsTab extends Tab {

  private static Hyperlink linkVariable;
  private static final String HYPERLINK_STYLE = "hyper-link";
  protected CommandWindow myCommandWindow;
  private static final String SET_VALUE = "set ";
  private static final String KEY_EQUALS_VALUE = " = ";
  private ScrollPane scrollPane;
  private VBox vbox;

  public StoredElementsTab(CommandWindow commandWindow) {
    myCommandWindow = commandWindow;
    scrollPane = new ScrollPane();
    scrollPane.setPrefHeight(500);
    vbox = new VBox();
    setContent(scrollPane);
  }


  public void resetTabContents(Map map, boolean needValue) {
    //scrollPane = new ScrollPane();
    //scrollPane.setPrefHeight(500);
    //vbox = new VBox();
    vbox.getChildren().clear();
    for (Object variable : map.keySet()) {
      if (needValue) {
        linkVariable = new Hyperlink(variable.toString() + KEY_EQUALS_VALUE + map.get(variable));
      } else {
        linkVariable = new Hyperlink(variable.toString());
      }
      linkVariable.getStyleClass().add(HYPERLINK_STYLE);
      vbox.getChildren().add(0, linkVariable);
      linkVariable.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          if(needValue) {
            myCommandWindow.setText(SET_VALUE + variable.toString());
          }
          else {
            myCommandWindow.setText(variable.toString());
          }
        }
      });
    }
    scrollPane.setContent(vbox);
    setContent(vbox);
    //return scrollPane;
  }
}
