package frontEnd;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class TabWindow extends TabPane{
  private static final int TAB_PANE_WIDTH = 300;
  private CommandWindow myCommandWindow;

  public TabWindow(CommandWindow commandWindow){
    myCommandWindow = commandWindow;
    makeTabWindow();
  }
  private void makeTabWindow() {
    setMinWidth(TAB_PANE_WIDTH);
    setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    /*ScrollPane history = new ScrollPane();
    ScrollPane variables = new ScrollPane();
    ScrollPane userCommands = new ScrollPane();
    Tab historyTab = new Tab(HISTORY_TAB_TITLE, history);
    Tab variableTab = new Tab(COMMAND_TAB_TITLE, userCommands);
    Tab commandTab = new Tab(VARIABLE_TAB_TITLE, variables);*/
    HistoryTab historyTab = new HistoryTab();
    VariableTab variableTab = new VariableTab(myCommandWindow);
    CommandTab commandTab = new CommandTab(myCommandWindow);
    getTabs().add(historyTab);
    getTabs().add(variableTab);
    getTabs().add(commandTab);
  }

}
