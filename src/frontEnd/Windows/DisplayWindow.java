package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.Mover;
import frontEnd.UserInterface;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class DisplayWindow extends VBox {
  private static final double DISPLAY_WIDTH = 900;
  private static final double DISPLAY_HEIGHT = 500;
  private static final String RECTANGLE_STYLE = "rectangle";
  private Rectangle rectangle;
  private static CommandWindow commandWindow;
  private static ButtonAction myButtonAction;
  private static CustomWindow myCustomWindow;

  public DisplayWindow(ButtonAction buttonAction, CustomWindow customWindow){
    myButtonAction = buttonAction;
    myCustomWindow = customWindow;
    makeDisplayWindow();
  }


  private void makeDisplayWindow(){
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rectangle.getStyleClass().add(RECTANGLE_STYLE);
    commandWindow = new CommandWindow(DISPLAY_WIDTH, myCustomWindow);
    getChildren().addAll(rectangle, commandWindow);
  }

  public void setBackgroundColor(Color color) {
    rectangle.setFill(color);
  }

  public CommandWindow getCommandWindow(){
    return commandWindow;
  }

  public void addLine(Line line){
    getChildren().add(line);
  }

  public void resetDisplay(Map<Double, Mover> turtleMap) {
    UserInterface view = myButtonAction.getView();
    int length = view.viewChildren().length;
    Node[] children = view.viewChildren();
    for(int i=0; i<length; i++){
      Node child = children[i];
      if(child instanceof Line){
        view.removeNodeFromRoot(child);
      }
    }
    for(Mover mover : turtleMap.values()) {
      mover.resetMover();
    }
  }

}
