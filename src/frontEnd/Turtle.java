package frontEnd;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;


public class Turtle implements Update{
  private static final String TURTLE = "turtle_25_upwards.png";
  ImageView myTurtle;
  private double turtleAngle;
  private boolean penDown = true;
  private double lineStartXPosition;
  private int distanceSoFar;
  private double lineStartYPosition;
  private double turtleStartingXPos;
  private double turtleStartingYPos;
  Line myLine;
  View myView;

  public Turtle(View view){
    myView = view;
    distanceSoFar = 0;
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
    if(penDown){
      drawPen(x, y);
    }
  }

  private void drawPen(double x, double y) {
    Line line = new Line();
    myLine=line;
    myLine.setStroke(myView.getLineColor());
    line.setStartX(turtleStartingXPos+ myTurtle.getBoundsInLocal().getWidth()/2);

    line.setStartY(turtleStartingYPos + myTurtle.getBoundsInLocal().getHeight());

    line.setEndX(turtleStartingXPos + x+ myTurtle.getBoundsInLocal().getWidth()/2);
    line.setEndY(turtleStartingYPos + y+ myTurtle.getBoundsInLocal().getHeight());
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

  public void setPenDown(){
    penDown=true;
  }

  public void setPen(boolean pendown){
    penDown = pendown;
  }

  public void setPenUp(){
    penDown= false;
  }

  public void updateDistanceSoFar(int d){
      distanceSoFar += d;
  }
  public int getDistanceSoFar(){
    return distanceSoFar;
  }

  public void resetTurtle(){
    myTurtle.setRotate(0);
    myView.setTurtlePosition(myTurtle);
  }
  public void eraseLines(){
    // Need Cayla's help
  }
  public void clearScreen(){
    resetTurtle();
  }

  public void turteVisible(boolean visible){
    myTurtle.setVisible(visible);
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
