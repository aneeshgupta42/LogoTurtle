package frontEnd;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class Turtle implements Update{

  private static final String TURTLE = "turtle.png";
  ImageView myTurtle;
  private int turtleCol = 0;
  private int turtleRow = 0;

  public Turtle(){


  }

  public Node displayTurtle() {
    Image turtle= new Image(getClass().getClassLoader().getResourceAsStream(TURTLE));
    myTurtle = new ImageView(turtle);
    return myTurtle;
  }

  public void move(int x, int y, int angle){
    GridPane.setConstraints(myTurtle, x, y);
    turtleCol = x;
    turtleRow = y;

    myTurtle.setRotate(angle);
  }

  //get turtle position
  public int getTurtleCol(){
    return turtleCol;
  }

  public int getTurtleRow(){
    return turtleRow;
  }

  @Override
  public int locationXUpdate(int changeInXPos) {
    return 0;
  }

  @Override
  public int locationYUpdate(int changeInYPos) {
    return 0;
  }

  @Override
  public int orientationUpdate(int changeInAngle) {
    return 0;
  }

}
