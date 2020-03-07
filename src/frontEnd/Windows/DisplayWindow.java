package frontEnd.Windows;

import frontEnd.ButtonAction;
import frontEnd.Mover;
import frontEnd.UIElements.ColorGrid;
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
  private static ColorGrid myColorGrid;
  private String myColorString;

  public DisplayWindow(ButtonAction buttonAction, CustomWindow customWindow, ColorGrid colorGrid) {
    myButtonAction = buttonAction;
    myCustomWindow = customWindow;
    myColorGrid = colorGrid;
    makeDisplayWindow();
  }
  public DisplayWindow(ButtonAction buttonAction, CustomWindow customWindow, ColorGrid colorGrid, String backgroundColor) {
    myButtonAction = buttonAction;
    myCustomWindow = customWindow;
    myColorGrid = colorGrid;
    makeDisplayWindow();
    rectangle.setFill(Color.valueOf(backgroundColor));
  }

  private void makeDisplayWindow(){
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rectangle.getStyleClass().add(RECTANGLE_STYLE);
    commandWindow = new CommandWindow(DISPLAY_WIDTH, myCustomWindow);
    getChildren().addAll(rectangle, commandWindow);
  }

  public void setBackgroundColor(Color color) {
    myColorString = color.toString();
    rectangle.setFill(color);
  }

  public String getBackgroundColor() {
    return rectangle.getFill().toString();
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

  public void setCurrentBackgroundColorByIndex(int index) {
    Color color = myColorGrid.getColorFromIndex(index);
    setBackgroundColor(color);
  }

}
