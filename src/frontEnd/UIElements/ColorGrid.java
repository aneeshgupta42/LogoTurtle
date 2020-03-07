package frontEnd.UIElements;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ColorGrid extends VBox {
  private ResourceBundle myColorGridResources;
  private static final String ColorGridResources = "resources.UIActions.ColorIndices";
  private static final int NUM_OF_COLUMNS = 3;
  private static final int NUM_OF_ROWS = 4;

  public ColorGrid() {
    setMinSize(50, 50);
    myColorGridResources = ResourceBundle.getBundle(ColorGridResources);
    createLabel();
    createColorGrid();
  }

  private void createLabel(){

  }

  private void createColorGrid(){
    GridPane gridPane = new GridPane();
    int rectangleNumber = 1;
    for(int i=0; i<NUM_OF_COLUMNS; i++){
      for (int j =0; j<NUM_OF_ROWS; j++){
        Rectangle rect = new Rectangle(100, 30);
        gridPane.add(rect, i, j);
        TextField text = new TextField("" + rectangleNumber);
        text.setDisable(true);
        text.setAlignment(Pos.CENTER);
        text.setPrefHeight(30);
        //gridPane.setAlignment(Pos.CENTER);
        gridPane.add(text, i , j);
        System.out.println(Color.valueOf(myColorGridResources.getString("" + rectangleNumber)));
        rect.setFill(Color.valueOf(myColorGridResources.getString("" + rectangleNumber)));
        rectangleNumber++;
      }
    }
    getChildren().add(gridPane);
  }
}
