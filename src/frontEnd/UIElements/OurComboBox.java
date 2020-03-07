package frontEnd.UIElements;

import backEnd.ErrorHandler;
import frontEnd.ButtonAction;
import frontEnd.ErrorBoxes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class OurComboBox extends HBox {
  private ComboBox box;

  public OurComboBox(String promptText, String methodName, ButtonAction target, ObservableList items){
    box = new ComboBox();
    box.setItems(items);
    box.setPromptText(promptText);
    getChildren().addAll(makePrompt(promptText), box);
    EventHandler<ActionEvent> whathappened = Result(methodName, target);
    box.setOnAction(whathappened);
  }

  // make input prompt, very basic for now but could be much more involved in general

  public ComboBox getBox(){
    return box;
  }

  private Node makePrompt (String text) {
    return new Text(text + ": ");
  }

  private EventHandler<ActionEvent> Result(String methodName, ButtonAction target) {
    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        try {
          Method m = target.getClass().getDeclaredMethod(methodName,String.class);
          try {
            m.invoke(target, box.getValue().toString());
          } catch (IllegalAccessException | InvocationTargetException e) {
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
