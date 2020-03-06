package frontEnd;

import frontEnd.ButtonsBoxesandLabels.PropertyLabel;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public class Mover implements Moveable {

  private static double moverID;
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
  private double lineThickness = 2;
  private Color lineColor = Color.BLACK;
  Line myLine;
  UserInterface myView;
  private static final String TURTLE = "turtle.png";
  private static String defaultImage = TURTLE;
  private boolean moverActive = true;
  private static final String LabelResources = "resources.UIActions.LabelActions";
  private ResourceBundle myLabelPropertyResources;
  private double degreesInCircle = 360;
  private ResourceBundle myComboBoxOptionsResources;
  private static final String ComboBoxOptionsResources = "resources.UIActions.ComboBoxOptions";
  private int currentImageIndex;

  public Mover(UserInterface view, double ID) {
    System.out.print(this);
    myView = view;
    // do we want this to start as true?
    penDown = true;
    moverVisible = true;
    distanceSoFar = 0;
    moverID = ID;
    moverImage = changeMoverDisplay(defaultImage);
    currentImageIndex = 1;
    /*moverImage.setOnMouseClicked(e -> {
      handleKeyInput();
    });*/
    myLabelPropertyResources = ResourceBundle.getBundle(LabelResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    //myView.addNodeToRoot(moverImage);
    initialX = myView.getXCenter();
    initialY = myView.getYCenter();
  }

  private void handleKeyInput(){
    //System.out.println("yo");
    //moverVisible=!moverVisible;
    //moverImage.setVisible(moverVisible);
    //System.out.println(moverVisible);
    if(moverActive) {
      ColorAdjust colorAdjustGrayscale = new ColorAdjust();
      colorAdjustGrayscale.setSaturation(-1);
      moverImage.setEffect(colorAdjustGrayscale);
    }
    else{
      ColorAdjust colorAdjustGrayscale = new ColorAdjust();
      colorAdjustGrayscale.setSaturation(0);
      moverImage.setEffect(colorAdjustGrayscale);
    }
    moverActive = !moverActive;
  }

  public ImageView changeMoverDisplay(String imagePath) {
    Image turtle = new Image(getClass().getClassLoader().getResourceAsStream(imagePath));
    moverImage = new ImageView(turtle);
    moverImage.setOnMouseClicked(e -> {
      handleKeyInput();
    });
    return moverImage;
  }

  public void setImageIndex(int currIndex){
    currentImageIndex = currIndex;
  }
  public void setDefaultImage(String image){
    defaultImage = myComboBoxOptionsResources.getString(image);
    currentImageIndex = 1;
  }

  public ImageView getImage(){
    return moverImage;
  }

  public Double getMoverID(){
    return moverID;
  }

  public void initializeLinePosition(double x, double y, double angle) {
    lineStartXPosition = x;
    lineStartYPosition = y;
    moverAngle = angle;
  }

  public void move(double x, double y, double angle) {
    if (moverActive) {
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
      updateLabels();
    }
  }

  public void updateLabels() {
      for (String key : Collections.list(myLabelPropertyResources.getKeys())) {
        PropertyLabel plabel = new PropertyLabel(myLabelPropertyResources.getString(key), key,
            myView.getButtonAction());
        PropertyLabel propertyLabel = (PropertyLabel) myView.getPropertyLabelMap().get(key);
        propertyLabel.setAmount(key, myView.getButtonAction());
      }
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
    rt.setToAngle(moverAngle + angle);
    rt.setNode(agent);
    return new SequentialTransition(agent, rt);
  }

  private void drawPen(double x, double y) {
    Line line = new Line();
    myLine=line;
    myLine.setStroke(lineColor);
    ///myLine.setStrokeWidth(myView.getLineWidth());
    myLine.setStrokeWidth(lineThickness);
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

  public void changeThickness(double thickness){
    myLine.setStrokeWidth(thickness);
    lineThickness = thickness;
  }

  public void setLineColor(Color color){
    lineColor = color;
    updateLabels();
  }

  public double getXPosition(){
    return moverImage.getX()-initialX;
  }
  public double getYPosition() {
    return moverImage.getY() - initialY;
  }

  public Color getLineColor(){
    return lineColor;
  }

  public double getThickness(){
    return lineThickness;
  }

  public int getCurrentImageIndex() {
    return currentImageIndex;
  }

  public void setThickness(double thickness){
    lineThickness = thickness;
  }

  //get turtle position
  public double getMoverCol(){
    return moverImage.getX();
  }

  public double getMoverRow(){
    return moverImage.getY();
  }

  public double getMoverAngle() {
    if (moverAngle >= degreesInCircle) {
      moverAngle = moverAngle - degreesInCircle;
    } else if (moverAngle <= -degreesInCircle) {
      moverAngle = moverAngle + degreesInCircle;
    }
    return moverAngle;
  }

  public void setPen(boolean bool){
    penDown=bool;
    updateLabels();
  }

  public boolean getPen(){
    return penDown;
  }
  public boolean getActive(){
    return moverActive;
  }

  public String getPenPosition(){
    String ret = "up";
    if(penDown){
      ret = "down";
    }
    return ret;
  }

  public void updateDistanceSoFar(int d){
      distanceSoFar += d;
  }
  public int getDistanceSoFar(){
    return distanceSoFar;
  }


  public void eraseLines(){
    BorderPane root = myView.getRoot();
    root.getChildren().removeIf(object -> object instanceof Line);
  }

  public void clearScreen(){
    setInitialMoverPosition();
    eraseLines();
  }

  public void moverVisible(boolean visible){
    this.moverVisible = visible;
    moverImage.setVisible(this.moverVisible);
  }

  public void changeVisible(){
    moverVisible = !moverVisible;
  }

  public boolean isPenDown() {
    return penDown;
  }

  public boolean isMoverVisible() {
    return moverVisible;
  }

  public void setInitialMoverPosition() {
    moverImage.setX(initialX);
    moverImage.setY(initialY);
    moverImage.setRotate(0);
    initializeLinePosition(moverImage.getX(), moverImage.getY(), moverImage.getRotate());
    setMoverInitialCords(moverImage.getX(), moverImage.getY());
    updateLabels();
  }

  public void resetMover(){
    setInitialMoverPosition();
    moverVisible(true);
    updateLabels();
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
