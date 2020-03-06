package frontEnd.ButtonsBoxesandLabels;

import frontEnd.ButtonAction;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OurLabeledColorPicker extends HBox {
  private ColorPicker colors;
  public OurLabeledColorPicker(String promptText, String methodName, ButtonAction target, String initialColor) {
    colors = new ColorPicker();
    colors.setValue(Color.valueOf(initialColor));
    getChildren().addAll(makePrompt(promptText), colors);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    colors.setOnAction(whathappened);
    //ActionEvent event = new ActionEvent(target.getClass().getDeclaredMethod(methodName));
    //setOnAction(target.getClass().getDeclaredMethod(methodName));
    //getChildren().addAll(
    //makeInputAction(target, methodName);
  }

  // make input prompt, very basic for now but could be much more involved in general
  private Node makePrompt (String text) {
   // System.out.println(text);
    return new Text(text + ": ");
  }

  public void setInitialColor(Color color){
    colors.setValue(color);
  }


  private EventHandler<ActionEvent> Result(String methodName,ButtonAction target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Method m = target.getClass().getDeclaredMethod(methodName, Color.class);
          try {
            m.invoke(target, colors.getValue());
          } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
          }
       //   System.out.println(methodName);
        }catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
      }
    };
    return event;
  }
}