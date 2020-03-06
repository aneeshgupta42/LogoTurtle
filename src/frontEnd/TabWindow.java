package frontEnd;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

public class TabWindow extends TabPane{
  private static final int TAB_PANE_WIDTH = 300;
  private CommandWindow myCommandWindow;
  private  HistoryTab historyTab;
  private VariableTab variableTab;
  private CommandTab commandTab;
  private Tab propertiesTab;


  public TabWindow(CommandWindow commandWindow, MoverPropertiesWindow propertiesWindow){
    myCommandWindow = commandWindow;
    historyTab = new HistoryTab();
    variableTab = new VariableTab(myCommandWindow);
    commandTab = new CommandTab(myCommandWindow);
    propertiesTab = new Tab();
    propertiesTab.setContent(propertiesWindow);
    propertiesTab.setText("Mover Information");

    makeTabWindow();
  }
  private void makeTabWindow() {
    setMinWidth(TAB_PANE_WIDTH);
    //setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    /*ScrollPane history = new ScrollPane();
    ScrollPane variables = new ScrollPane();
    ScrollPane userCommands = new ScrollPane();
    Tab historyTab = new Tab(HISTORY_TAB_TITLE, history);
    Tab variableTab = new Tab(COMMAND_TAB_TITLE, userCommands);
    Tab commandTab = new Tab(VARIABLE_TAB_TITLE, variables);*/
    getTabs().add(historyTab);
    getTabs().add(variableTab);
    getTabs().add(commandTab);
    getTabs().add(propertiesTab);
  }

  public HistoryTab getHistoryTab(){
    return historyTab;
  }

  public CommandTab getCommandTab(){
    return commandTab;
  }
  public VariableTab getVariableTab(){
    return variableTab;
  }

}
