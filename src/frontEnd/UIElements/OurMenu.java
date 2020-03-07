package frontEnd.UIElements;

import frontEnd.ButtonAction;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class OurMenu extends Menu {

  public OurMenu(String promptText, String methodName, ButtonAction target, ObservableList<String> items){
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    for(String item: items){
      MenuItem menuItem = new MenuItem(item);
      getItems().add(menuItem);
      menuItem.setOnAction(whathappened);
    }
    setText(promptText);
  }

  private EventHandler<ActionEvent> Result(String methodName, ButtonAction target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Method m = target.getClass().getDeclaredMethod(methodName,String.class);
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
