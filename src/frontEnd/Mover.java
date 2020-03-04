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

  ImageView moverImage;
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
  private static final String TURTLE = "turtle.png";

  public Mover(UserInterface view) {
    myView = view;
    // do we want this to start as true?
    penDown = true;
    moverVisible = true;
    distanceSoFar = 0;
    moverImage = changeMoverDisplay(TURTLE);
  }

  public ImageView changeMoverDisplay(String imagePath) {
    Image turtle = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
    moverImage = new ImageView(turtle);
    return moverImage;
  }

  public ImageView getImage(){
    return moverImage;
  }

  public void initializeLinePosition(double x, double y, double angle) {
    lineStartXPosition = x;
    lineStartYPosition = y;
    moverAngle = angle;
  }

  public void move(double x, double y, double angle) {
    moverStartingXPos = moverImage.getX();
    moverStartingYPos = moverImage.getY();
    Animation animation = makeAnimation(moverImage, x, y);
    Animation rotate = makeRotate(moverImage, angle);
    //myView.addAnimation(animation);
    if (x != 0 | y != 0) {
      animation.play();
    }
    if (angle != 0) {
      rotate.play();
    }
    //animation.play();
    moverImage.setX(moverImage.getX() + x);
    moverImage.setY(moverImage.getY() + y);
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
    path.getElements().addAll(new MoveTo(moverStartingXPos + moverImage.getBoundsInLocal().getWidth()/2, moverStartingYPos+ moverImage.getBoundsInLocal().getHeight()/2), new LineTo(moverStartingXPos + moverImage.getBoundsInLocal().getWidth()/2+ x,moverStartingYPos + moverImage.getBoundsInLocal().getHeight()/2+ y));
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
    line.setStartX(moverStartingXPos+ moverImage.getBoundsInLocal().getWidth()/2);
    line.setStartY(moverStartingYPos + moverImage.getBoundsInLocal().getHeight());
 //   System.out.println("yo" + turtleStartingYPos + " " + myTurtle.getY());
    line.setEndX(moverStartingXPos + x+ moverImage.getBoundsInLocal().getWidth()/2);
    line.setEndY(moverStartingYPos + y+ moverImage.getBoundsInLocal().getHeight());
    myView.addNodeToRoot(myLine);
  }

  public Line getLine(){
    return myLine;
  }

  //get turtle position
  public double getMoverCol(){
    return moverImage.getX();
  }

  public double getMoverRow(){
    return moverImage.getY();
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
    moverImage.setRotate(0);
    myView.setMoverPosition(moverImage);
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
    moverImage.setVisible(this.moverVisible);
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
