package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.ButtonsBoxesandLabels.OurComboBox;
import frontEnd.ButtonsBoxesandLabels.OurLabeledColorPicker;
import frontEnd.ButtonsBoxesandLabels.OurMenu;
import frontEnd.Tabs.CommandTab;
import frontEnd.Tabs.HistoryTab;
import frontEnd.Tabs.VariableTab;
import frontEnd.Windows.CommandWindow;
import frontEnd.Windows.MoverPropertiesWindow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class TabWindow extends VBox {
  private static final int TAB_PANE_WIDTH = 300;
  private TabPane tabPane;
  private CommandWindow myCommandWindow;
  private HistoryTab historyTab;
  private VariableTab variableTab;
  private CommandTab commandTab;
  private Tab propertiesTab;
  private Tab properties2Tab;
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

    //tabOptions = new OurComboBox("Add Tab", "addTab", myButtonAction,tabResources);

    //tabOptions.getBox().itemsProperty().bind(new SimpleObjectProperty<>(tabResources));

    //Button butt = new Button();
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
    //tabOptions.getChildren().remove(tabName);
    //tabResources.remove(tabName);
  }

  public void createTabs(){
    tabPane.getTabs().addAll(createHistoryTab(),
    createVariableTab(),
    createCommandTab());
    addToMapAndSetOnClosed(historyTab.getText(), historyTab);
    addToMapAndSetOnClosed(variableTab.getText(), variableTab);
    addToMapAndSetOnClosed(commandTab.getText(), commandTab);
    //addToMapAndSetOnClosed(propertiesTab.getText(), propertiesTab);

  }

  public HistoryTab createHistoryTab(){
    historyTab = new HistoryTab();
    //addToMapAndSetOnClosed(historyTab.getText(), historyTab);
    return historyTab;
  }


  public VariableTab createVariableTab(){
    variableTab = new VariableTab(myCommandWindow);
    //addToMapAndSetOnClosed(variableTab.getText(), variableTab);
    return variableTab;
  }

  public CommandTab createCommandTab(){
    commandTab = new CommandTab(myCommandWindow);
    //addToMapAndSetOnClosed(commandTab.getText(), commandTab);
    return commandTab;
  }

  /*public Tab createPropertiesTab(){
    propertiesTab = new Tab();
    propertiesTab.setContent(myPropertiesWindow);
    propertiesTab.setText("Mover Information");
    //addToMapAndSetOnClosed(propertiesTab.getText(), propertiesTab);
    return propertiesTab;
  }*/

  private void addToMapAndSetOnClosed(String tabName, Tab tab) {
    tabMap.put(tabName, tab);
    tabResources.add(tabName);
    //tab.setOnSelectionChanged(addToList(tabName));
    //tab.setOnClosed(addToList(tabName));
  }

 /* public EventHandler<Event> addToList(String tabName){
    EventHandler<Event> event = t -> tabOptions.setValue(tabPane.getSelectionModel().getSelectedItem().getText());
    System.out.println(tabResources);
    return event;
  }*/


  public HistoryTab getHistoryTab(){
    return historyTab;
  }
  public CommandTab getCommandTab(){
    return commandTab;
  }
  public VariableTab getVariableTab(){
    return variableTab;
  }
  public Tab getPropertiesTab(){ return propertiesTab;}

}
