package frontEnd;

import Controller.Control;
import java.lang.reflect.InvocationTargetException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.lang.reflect.Method;
import javax.swing.Action;

/**
 * Basic class that can be used interchangeably with other JavaFX GUI components.
 *
 * @author Robert C. Duvall
 */
public class OurButtons extends Button {
  /**
   * Create input with given label and method to call on the given Controller.
   */
  public OurButtons(String promptText, String methodName, View target) {
    setText(promptText);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    setOnAction(whathappened);
    //ActionEvent event = new ActionEvent(target.getClass().getDeclaredMethod(methodName));
    //setOnAction(target.getClass().getDeclaredMethod(methodName));
    //getChildren().addAll(
        //makeInputAction(target, methodName);
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
          Method m = target.getClass().getDeclaredMethod(methodName);
          try {
            m.invoke(target);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          } catch (InvocationTargetException e) {
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