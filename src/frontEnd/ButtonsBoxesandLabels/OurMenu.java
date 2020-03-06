package frontEnd.ButtonsBoxesandLabels;

import frontEnd.ButtonAction;
import frontEnd.UserInterface;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class OurMenu extends Menu {

  public OurMenu(String promptText, String methodName, ButtonAction target, ObservableList<String> items){
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    for(String item: items){
      MenuItem menuItem = new MenuItem(item);
      getItems().add(menuItem);
      menuItem.setOnAction(whathappened);
    }
    setText(promptText);
    //setPromptText(promptText);
    /*setItems(items);
    setPromptText(promptText);
    getChildren().addAll(makePrompt(promptText), box);*/
  }

  // make input prompt, very basic for now but could be much more involved in general


  private Node makePrompt (String text) {
    System.out.println(text);
    return new Text(text + ": ");
  }

  private EventHandler<ActionEvent> Result(String methodName, ButtonAction target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //System.out.println(target + " " + box.getValue().toString());
        try {
          Method m = target.getClass().getDeclaredMethod(methodName,String.class);
          //System.out.println("method " + m);
          try {
            m.invoke(target, ((MenuItem) event.getSource()).getText());
          } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
          }
          System.out.println(methodName);
        }catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
      }
    };
    return event;
  }
}
