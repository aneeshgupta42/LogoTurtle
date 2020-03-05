package frontEnd.ButtonsBoxesandLabels;

import frontEnd.ButtonAction;
import frontEnd.Display;
import java.lang.reflect.InvocationTargetException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.lang.reflect.Method;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


/**
 * Basic class that can be used interchangeably with other JavaFX GUI components.
 *
 * @author Robert C. Duvall
 */
public class PropertyLabel extends HBox implements Display {
  //private DoubleProperty amountDue = new SimpleDoubleProperty();
  private StringProperty amountDue = new SimpleStringProperty();
  private Label label;



  // Define a getter for the property's value
  /**
   * Create input with given label and method to call on the given Controller.
   */
  public PropertyLabel(String promptText, String methodName, ButtonAction target) {
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

  public void setAmount(String methodName, ButtonAction target) {
    try {
      Method m = target.getClass().getDeclaredMethod(methodName);
      try {
        Object value = m.invoke(target);
        amountDue.set(""+ value);
        System.out.println("value" + value);
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
      System.out.println(methodName);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  private void bindLabel(StringProperty dp){
    //Label label = new Label();
    label.textProperty().bind(dp);

  }
  public final String getAmountDue(){return amountDue.get();}

  // Define a setter for the property's value
  public final void setAmountDue(String value){amountDue.set((value));}

  // Define a getter for the property itself
  public StringProperty amountDueProperty() {return amountDue;}

  private EventHandler<ActionEvent> Result(String methodName, ButtonAction target) {
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