package frontEnd;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class Turtle implements Update{
  private static final String TURTLE = "turtle_25.png";
  ImageView myTurtle;
  private double turtleAngle = 0;

  public Turtle(){


  }

  public Node displayTurtle() {
    Image turtle= new Image(getClass().getClassLoader().getResourceAsStream(TURTLE));
    myTurtle = new ImageView(turtle);
    return myTurtle;
  }

  public void move(double x, double y, double angle){
    myTurtle.setX(myTurtle.getX()+x);
    myTurtle.setY(myTurtle.getY()+y);
    myTurtle.setRotate(turtleAngle + angle);
    turtleAngle = turtleAngle + angle;
  }

  //get turtle position
  public double getTurtleCol(){
    return myTurtle.getX();
  }

  public double getTurtleRow(){
    return myTurtle.getY();
  }

  public double getTurtleAngle(){
    return turtleAngle;
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
