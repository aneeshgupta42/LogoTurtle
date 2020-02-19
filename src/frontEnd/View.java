package frontEnd;

import java.awt.TextArea;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class View {

  private Turtle myTurtle;
  private Pen myPen;


  public View() {
   View view = new View();
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */
  public Scene makeScene(int width, int height) {
    BorderPane root = new BorderPane();
    root.setTop(makeDisplayWindow());
    root.setBottom(makeCommandWindow());
    Scene scene = new Scene(root, width, height);
    return scene;
  }

  private Node makeDisplayWindow(){
    GridPane gridPane = new GridPane();
   // gridPane.getChildren().add(myPen);
    return gridPane;
  }
  private Node makeCommandWindow(){
    TextField inputArea = new TextField();

    return inputArea;
  }

}
