package frontEnd;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class TabWindow extends TabPane{
  private static final int TAB_PANE_WIDTH = 300;
  private static final String HISTORY_TAB_TITLE = "History";
  private static final String VARIABLE_TAB_TITLE = "Variables";
  private static final String COMMAND_TAB_TITLE = "User Commands";

  public TabWindow(){
    makeTabWindow();
  }
  private void makeTabWindow() {
    setMinWidth(TAB_PANE_WIDTH);
    setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    ScrollPane history = new ScrollPane(); ////here or at the top?
    ScrollPane variables = new ScrollPane();
    ScrollPane userCommands = new ScrollPane();
    Tab tab1 = new Tab(HISTORY_TAB_TITLE, history);
    Tab tab2 = new Tab(VARIABLE_TAB_TITLE, variables);
    Tab tab3 = new Tab(COMMAND_TAB_TITLE, userCommands);
    getTabs().add(tab1);
    getTabs().add(tab2);
    getTabs().add(tab3);
  }

}
