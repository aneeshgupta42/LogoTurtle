package frontEnd.UIElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private List<Color> colorList= new ArrayList<>();
  private GridPane gridPane;

  public ColorGrid() {
    setMinSize(50, 50);
    myColorGridResources = ResourceBundle.getBundle(ColorGridResources);
    createLabel();
    createColorGrid();
  }

  private void createLabel(){

  }

  private void createColorGrid(){
    gridPane = new GridPane();
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
        Color color = Color.valueOf(myColorGridResources.getString("" + rectangleNumber));
        rect.setFill(color);
        colorList.add(color);
        rectangleNumber++;
      }
    }
    getChildren().add(gridPane);
  }

  public Color getColorFromIndex(int index){
    return colorList.get(index);
  }

  public List getColorList(){
    return colorList;
  }

  public void setColorFromIndexAndRGB(int index, int red, int green, int blue){
    colorList.get(index).rgb(red, green, blue);
    gridPane.getChildren().remove(index%NUM_OF_COLUMNS, index%NUM_OF_ROWS);
    Rectangle rect = new Rectangle(100, 30);
    gridPane.add(rect, index%NUM_OF_COLUMNS, index%NUM_OF_ROWS);
    rect.setFill(colorList.get(index));
  }
}
