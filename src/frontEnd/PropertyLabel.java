package frontEnd;

import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.lang.reflect.Method;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


/**
 * Basic class that can be used interchangeably with other JavaFX GUI components.
 *
 * @author Robert C. Duvall
 */
public class PropertyLabel extends HBox implements Display {
  private DoubleProperty amountDue = new SimpleDoubleProperty();
  private Label label;



  // Define a getter for the property's value
  /**
   * Create input with given label and method to call on the given Controller.
   */
  public PropertyLabel(String promptText, String methodName, UserInterface target) {
    label = new Label();
    setAmount(methodName, target);
    bindLabel(amountDue);
    getChildren().addAll(makePrompt(promptText), label);

    //EventHandler<ActionEvent> whathappened = Result(methodName, target);
    //setOnAction(whathappened);
    //ActionEvent event = new ActionEvent(target.getClass().getDeclaredMethod(methodName));
    //setOnAction(target.getClass().getDeclaredMethod(methodName));
    //getChildren().addAll(
    //makeInputAction(target, methodName);
  }

  private Node makePrompt (String text) {
    System.out.println(text);
    return new Text(text + ": ");

  }

  public void setAmount(String methodName, UserInterface target) {
    try {
      Method m = target.getClass().getDeclaredMethod(methodName);
      try {
        Object value = m.invoke(target);
        amountDue.set((double) value);
        System.out.println("value" + value);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
      System.out.println(methodName);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  private void bindLabel(DoubleProperty dp){
    //Label label = new Label();
    label.textProperty().bind(dp.asString());

  }
  public final double getAmountDue(){return amountDue.get();}

  // Define a setter for the property's value
  public final void setAmountDue(double value){amountDue.set(value);}

  // Define a getter for the property itself
  public DoubleProperty amountDueProperty() {return amountDue;}

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