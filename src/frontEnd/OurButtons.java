package frontEnd;

import java.lang.reflect.InvocationTargetException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.lang.reflect.Method;

/**
 * Basic class that can be used interchangeably with other JavaFX GUI components.
 *
 * @author Robert C. Duvall
 */
public class OurButtons extends Button implements Display {
  /**
   * Create input with given label and method to call on the given Controller.
   */
  public OurButtons(String promptText, String methodName, UserInterface target) {
    setText(promptText);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    setOnAction(whathappened);
    //ActionEvent event = new ActionEvent(target.getClass().getDeclaredMethod(methodName));
    //setOnAction(target.getClass().getDeclaredMethod(methodName));
    //getChildren().addAll(
        //makeInputAction(target, methodName);
  }

  private EventHandler<ActionEvent> Result(String methodName, UserInterface target) {
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