package frontEnd;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayWindow extends VBox {
  private static final double DISPLAY_WIDTH = 900;
  private static final double DISPLAY_HEIGHT = 500;
  private static final String RECTANGLE_STYLE = "rectangle";
  private Rectangle rectangle;

  public DisplayWindow(ButtonAction buttonAction){
    makeDisplayWindow(buttonAction);
  }


  private void makeDisplayWindow(ButtonAction buttonAction){
    rectangle = new Rectangle(DISPLAY_WIDTH, DISPLAY_HEIGHT);
    rectangle.getStyleClass().add(RECTANGLE_STYLE);
    //CommandWindow commandWindow = new HBox(makeCommandWindow());
    CommandWindow commandWindow = new CommandWindow(DISPLAY_WIDTH, buttonAction);
    getChildren().addAll(rectangle, commandWindow);
    //display.getChildren().addAll(vbox);
    //return display;
  }

  public void setBackgroundColor(Color color) {
    rectangle.setFill(color);
  }

}
