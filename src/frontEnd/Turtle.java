package frontEnd;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;


public class Turtle implements Update{
  private static final String TURTLE = "turtle_25.png";
  ImageView myTurtle;
  private double turtleAngle;
  private boolean penDown = true;
  private double lineStartXPosition;
  private double lineStartYPosition;
  private double turtleStartingXPos;
  private double turtleStartingYPos;
  Line myLine;
  View myView;

  public Turtle(View view){
    myView = view;

  }

  public Node displayTurtle() {
    Image turtle= new Image(getClass().getClassLoader().getResourceAsStream(TURTLE));
    myTurtle = new ImageView(turtle);
    return myTurtle;
  }

  public void initializeLinePosition(double x, double y, double angle){
    lineStartXPosition = x;
    lineStartYPosition = y;
    turtleAngle = angle;
  }

  public void move(double x, double y, double angle){
    turtleStartingXPos = myTurtle.getX();
    turtleStartingYPos= myTurtle.getY();
    myTurtle.setX(myTurtle.getX()+x);
    myTurtle.setY(myTurtle.getY()+y);
    myTurtle.setRotate(turtleAngle + angle);
    turtleAngle = turtleAngle + angle;
    if(penDown==true){
      drawPen();
    }
  }

  private void drawPen() {
    Line line = new Line();
    myLine=line;
    line.setStartX(turtleStartingXPos);
    line.setStartY(turtleStartingYPos);
    line.setEndX(myTurtle.getX());
    line.setEndY(myTurtle.getY());
    myView.addLineToRoot(myLine);
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

  public void putPenDown(){
    penDown=true;
  }

  public void pickPenUp(){
    penDown= false;
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

  public void setLine(Line line) {
    myLine = line;
  }
}
