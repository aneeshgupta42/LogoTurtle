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

  public StoredElementsTab(CommandWindow commandWindow) {
    myCommandWindow = commandWindow;
  }


  public ScrollPane resetTabContents(Map map, boolean needValue) {
    System.out.println("mapcontents" + map);
    ScrollPane scrollPane = new ScrollPane();
    VBox vbox = new VBox();
    for (Object variable : map.keySet()) {
      if (needValue) {
        linkVariable = new Hyperlink(variable.toString() + "=" + map.get(variable));
      } else {
        linkVariable = new Hyperlink(variable.toString());
      }
      linkVariable.getStyleClass().add(HYPERLINK_STYLE);
      vbox.getChildren().add(linkVariable);
      linkVariable.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
          myCommandWindow.setText(variable.toString());
        }
      });
    }
    scrollPane.setContent(vbox);
    return scrollPane;
  }
}
