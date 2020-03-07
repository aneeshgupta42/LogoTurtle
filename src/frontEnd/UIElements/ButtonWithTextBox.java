package frontEnd.UIElements;

import frontEnd.ButtonAction;
import java.lang.reflect.InvocationTargetException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.lang.reflect.Method;
import javax.swing.text.View;

/**
 * Basic class that can be used interchangeably with other JavaFX GUI components.
 *
 * @author Robert C. Duvall
 */
public class ButtonWithTextBox extends HBox {
  private Button myButton;
  private TextField myTextField;

  /**
   * Create input with given label and method to call on the given Controller.
   */
  public ButtonWithTextBox(String promptText, String methodName, ButtonAction buttonAction) {
    myButton = new Button();
    myTextField = new TextField();
    myButton.setText(promptText);
    getChildren().addAll(myButton, myTextField);
    EventHandler<ActionEvent> whathappened = Result(methodName, buttonAction);
    myButton.setOnAction(whathappened);
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
  private EventHandler<ActionEvent> Result(String methodName, ButtonAction target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        //System.out.println(target + " " + box.getValue().toString());
        try {
          Method m = target.getClass().getDeclaredMethod(methodName,String.class);
          //System.out.println("method " + m);
          try {
            m.invoke(target, myTextField.getCharacters().toString());
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

