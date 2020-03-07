package frontEnd.UIElements;

import backEnd.ErrorHandler;
import frontEnd.ButtonAction;

import frontEnd.ErrorBoxes;
import java.lang.reflect.InvocationTargetException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import java.lang.reflect.Method;

/**
 * Basic class that can be used interchangeably with other JavaFX GUI components.
 *
 * @author Robert C. Duvall
 */
public class OurButtons extends Button {
  /**
   * Create input with given label and method to call on the given Controller.
   */
  public OurButtons(String promptText, String methodName, ButtonAction target) {
    setText(promptText);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    setOnAction(whathappened);
  }

  private EventHandler<ActionEvent> Result(String methodName, ButtonAction target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Method m = target.getClass().getDeclaredMethod(methodName);
          try {
            m.invoke(target);
          } catch (IllegalAccessException e) {
            ErrorBoxes box = new ErrorBoxes(new ErrorHandler("IllegalAccess"));
          } catch (InvocationTargetException e) {
            ErrorBoxes box = new ErrorBoxes(new ErrorHandler("IllegalAccess"));
          }
        }catch (NoSuchMethodException e) {
          ErrorBoxes box = new ErrorBoxes(new ErrorHandler("NoMethod"));
        }
      }
    };
    return event;
  }
}