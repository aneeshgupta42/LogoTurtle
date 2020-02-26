package frontEnd;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


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
    Animation animation = makeAnimation(myTurtle, x, y);
    Animation rotate = makeRotate(myTurtle, angle);
    //myView.addAnimation(animation);
    if(x!=0 | y !=0) {
      animation.play();
    }
    if (angle!=0) {
      rotate.play();
    }
    //animation.play();
    myTurtle.setX(myTurtle.getX()+x);
    myTurtle.setY(myTurtle.getY()+y);
  //  System.out.println("hey" + turtleStartingYPos + " " + myTurtle.getY());
    //myTurtle.setRotate(turtleAngle + angle);
    turtleAngle = turtleAngle + angle;
    if(penDown){
      drawPen(x, y);
    }
  }

  private Animation makeAnimation (Node agent, double x, double y) {
    // create something to follow
    Path path = new Path();
    path.getElements().addAll(new MoveTo(turtleStartingXPos + myTurtle.getBoundsInLocal().getWidth()/2, turtleStartingYPos+ myTurtle.getBoundsInLocal().getHeight()/2), new LineTo(turtleStartingXPos + myTurtle.getBoundsInLocal().getWidth()/2+ x,turtleStartingYPos + myTurtle.getBoundsInLocal().getHeight()/2+ y));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(2), path, agent);
    System.out.println(pt);
    return new SequentialTransition(agent, pt);
  }

  private Animation makeRotate (Node agent, double angle) {
    RotateTransition rt = new RotateTransition(Duration.seconds(2), agent);
    rt.setFromAngle(turtleAngle);
    rt.setToAngle(turtleAngle+ angle);
    rt.setNode(agent);
    return new SequentialTransition(agent, rt);
  }

  private void drawPen(double x, double y) {
    Line line = new Line();
    myLine=line;
    myLine.setStroke(myView.getLineColor());
    myView.setLine(line);
    line.setStartX(turtleStartingXPos+ myTurtle.getBoundsInLocal().getWidth()/2);

    line.setStartY(turtleStartingYPos + myTurtle.getBoundsInLocal().getHeight());
 //   System.out.println("yo" + turtleStartingYPos + " " + myTurtle.getY());

    line.setEndX(turtleStartingXPos + x+ myTurtle.getBoundsInLocal().getWidth()/2);
    line.setEndY(turtleStartingYPos + y+ myTurtle.getBoundsInLocal().getHeight());
    myView.addNodeToRoot(myLine);
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
    BorderPane root = myView.getRoot();
    root.getChildren().removeIf(object -> object instanceof Line);
  }

  public void clearScreen()
  {
    resetTurtle();
    eraseLines();
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
