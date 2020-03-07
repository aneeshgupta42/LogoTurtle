package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.UIElements.OurMenu;
import frontEnd.Tabs.CommandTab;
import frontEnd.Tabs.HistoryTab;
import frontEnd.Tabs.VariableTab;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class TabWindow extends VBox {
  private static final int TAB_PANE_WIDTH = 420;
  private TabPane tabPane;
  private CommandWindow myCommandWindow;
  private HistoryTab historyTab;
  private VariableTab variableTab;
  private CommandTab commandTab;
  private OurMenu tabOptions;
  private ObservableList<String> tabResources = FXCollections.observableArrayList(List.of());
  private Map<String, Tab> tabMap = new HashMap<>();
  private SingleSelectionModel<Tab> selectionModel;



  public TabWindow(CommandWindow commandWindow, ButtonAction myButtonAction){
    tabPane = new TabPane();
    selectionModel = tabPane.getSelectionModel();
    myCommandWindow = commandWindow;
    makeTabWindow();
    tabOptions = new OurMenu("Open Tab", "openTab", myButtonAction,
        tabResources);
    MenuBar menu = new MenuBar();
    menu.getMenus().add(tabOptions);
    getChildren().addAll(menu, tabPane);
  }
  private void makeTabWindow() {
    tabPane.setMinWidth(TAB_PANE_WIDTH);
    createTabs();
  }

  public void openTabFromPane(String tabName){
    if(!tabPane.getTabs().contains(tabMap.get(tabName))) {
      tabPane.getTabs().add(tabMap.get(tabName));
    }
    selectionModel.select(tabMap.get(tabName));
  }

  public void createTabs(){
    tabPane.getTabs().addAll(createHistoryTab(),
    createVariableTab(),
    createCommandTab());
    addToMapAndList(tabPane.getTabs());
  }

  public HistoryTab createHistoryTab(){
    historyTab = new HistoryTab();
    return historyTab;
  }


  public VariableTab createVariableTab(){
    variableTab = new VariableTab(myCommandWindow);
    return variableTab;
  }

  public CommandTab createCommandTab(){
    commandTab = new CommandTab(myCommandWindow);
    return commandTab;
  }

  private void addToMapAndList(List<Tab> tabs) {
    for(Tab tab : tabs){
      tabMap.put(tab.getText(), tab);
      tabResources.add(tab.getText());
    }
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
