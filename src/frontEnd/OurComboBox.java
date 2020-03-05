package frontEnd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class OurComboBox extends HBox {
  private ComboBox box;

  public OurComboBox(String promptText, String methodName, UserInterface target, ObservableList items){
    box = new ComboBox();
    box.setItems(items);
    box.setPromptText(promptText);
    getChildren().addAll(makePrompt(promptText), box);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    box.setOnAction(whathappened);
  }

  // make input prompt, very basic for now but could be much more involved in general

  public void updateItems(ObservableList items){
    box.setItems(items);
  }

  public ComboBox getBox(){
    return box;
  }

  private Node makePrompt (String text) {
    System.out.println(text);
    return new Text(text + ": ");

  }

  // make input field that calls Controller method using reflection as its action
  private Node makeInputAction (UserInterface target, String methodName) {
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

  private EventHandler<ActionEvent> Result(String methodName, UserInterface target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //System.out.println(target + " " + box.getValue().toString());
        try {
          Method m = target.getClass().getDeclaredMethod(methodName,String.class);
          //System.out.println("method " + m);
          try {
            m.invoke(target, box.getValue().toString());
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
