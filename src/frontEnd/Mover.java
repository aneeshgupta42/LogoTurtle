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


public class Mover implements Update {

  ImageView myMover;
  private double moverAngle;
  private boolean penDown;
  private boolean moverVisible = true;
  private boolean objectMoved = false;
  private double lineStartXPosition;
  private int distanceSoFar;
  private double lineStartYPosition;
  private double moverStartingXPos;
  private double moverStartingYPos;
  private double moverCenterXPos;
  private double moverCenterYPos;
  Line myLine;
  UserInterface myView;

  public Mover(UserInterface view) {
    myView = view;
    // do we want this to start as true?
    penDown = true;
    moverVisible = true;
    distanceSoFar = 0;
  }

  public Node displayMover(String imagePath) {
    Image turtle = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
    myMover = new ImageView(turtle);
    return myMover;
  }

  public void initializeLinePosition(double x, double y, double angle) {
    lineStartXPosition = x;
    lineStartYPosition = y;
    moverAngle = angle;
  }

  public void move(double x, double y, double angle) {
    moverStartingXPos = myMover.getX();
    moverStartingYPos = myMover.getY();
    Animation animation = makeAnimation(myMover, x, y);
    Animation rotate = makeRotate(myMover, angle);
    //myView.addAnimation(animation);
    if (x != 0 | y != 0) {
      animation.play();
    }
    if (angle != 0) {
      rotate.play();
    }
    //animation.play();
    myMover.setX(myMover.getX() + x);
    myMover.setY(myMover.getY() + y);
    //  System.out.println("hey" + turtleStartingYPos + " " + myTurtle.getY());
    //myTurtle.setRotate(turtleAngle + angle);
    moverAngle = moverAngle + angle;
    if (penDown) {
      drawPen(x, y);
    }
    objectMoved = true;
  }

  public boolean objectMoved() {
    return objectMoved;
  }

  public void setObjectMoved(boolean bool) {
    objectMoved = bool;
  }

  private Animation makeAnimation (Node agent, double x, double y) {
    // create something to follow
    Path path = new Path();
    path.getElements().addAll(new MoveTo(moverStartingXPos + myMover.getBoundsInLocal().getWidth()/2, moverStartingYPos+ myMover.getBoundsInLocal().getHeight()/2), new LineTo(moverStartingXPos + myMover.getBoundsInLocal().getWidth()/2+ x,moverStartingYPos + myMover.getBoundsInLocal().getHeight()/2+ y));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(2), path, agent);
    System.out.println(pt);
    return new SequentialTransition(agent, pt);
  }

  private Animation makeRotate (Node agent, double angle) {
    RotateTransition rt = new RotateTransition(Duration.seconds(2), agent);
    rt.setFromAngle(moverAngle);
    rt.setToAngle(moverAngle+ angle);
    rt.setNode(agent);
    return new SequentialTransition(agent, rt);
  }

  private void drawPen(double x, double y) {
    Line line = new Line();
    myLine=line;
    myLine.setStroke(myView.getLineColor());
    //myView.setLine(line);
    line.setStartX(moverStartingXPos+ myMover.getBoundsInLocal().getWidth()/2);
    line.setStartY(moverStartingYPos + myMover.getBoundsInLocal().getHeight());
 //   System.out.println("yo" + turtleStartingYPos + " " + myTurtle.getY());
    line.setEndX(moverStartingXPos + x+ myMover.getBoundsInLocal().getWidth()/2);
    line.setEndY(moverStartingYPos + y+ myMover.getBoundsInLocal().getHeight());
    myView.addNodeToRoot(myLine);
  }

  //get turtle position
  public double getMoverCol(){
    return myMover.getX();
  }

  public double getMoverRow(){
    return myMover.getY();
  }

  public double getMoverAngle(){
    return moverAngle;
  }

  public void setPen(boolean bool){
    penDown=bool;
  }

  public void updateDistanceSoFar(int d){
      distanceSoFar += d;
  }
  public int getDistanceSoFar(){
    return distanceSoFar;
  }

  public void resetTurtle(){
    myMover.setRotate(0);
    myView.setMoverPosition(myMover);
  }

  public void eraseLines(){
    BorderPane root = myView.getRoot();
    root.getChildren().removeIf(object -> object instanceof Line);
  }

  public void clearScreen(){
    resetTurtle();
    eraseLines();
  }

  public void moverVisible(boolean visible){
    this.moverVisible = visible;
    myMover.setVisible(this.moverVisible);
  }

  public boolean isPenDown() {
    return penDown;
  }

  public boolean isMoverVisible() {
    return moverVisible;
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

  public void setMoverInitialCords(double initialX, double initialY) {
    this.moverCenterXPos = initialX;
    this.moverCenterYPos = initialY;
  }

  public double getMoverCenterXPos() {
    return this.moverCenterXPos;
  }

  public double getMoverCenterYPos() {
    return this.moverCenterYPos;
  }
}
