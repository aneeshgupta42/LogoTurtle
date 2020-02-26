package frontEnd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OurComboBox extends ComboBox {

  public OurComboBox(String promptText, String methodName, View target, ObservableList items){
    setItems(items);
    setPromptText(promptText);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    setOnAction(whathappened);
  }

  // make input prompt, very basic for now but could be much more involved in general
  private Node makePrompt (String text) {
    return new Label(text + "  ");
  }

  // make input field that calls Controller method using reflection as its action
  private Node makeInputAction (View target, String methodName) {
    TextField result = new TextField();
    result.setOnAction(handler -> {
      try {
        // find method with given name that takes String as its only parameter
        Method m = target.getClass().getDeclaredMethod(methodName, String.class);
        m.invoke(target, result.getText());
        result.clear();
      }
      catch (Exception e) {
        // FIXME: typically make your own custom exception to throw
        throw new RuntimeException("Improper configuration", e);
      }
    });
    return result;
  }

  private EventHandler<ActionEvent> Result(String methodName, View target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Method m = target.getClass().getDeclaredMethod(methodName,String.class);
          try {
            m.invoke(target, getValue().toString());
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
