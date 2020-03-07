package frontEnd;

import frontEnd.UIElements.PropertyLabel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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


public class Mover implements Moveable{

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
  private static double initialX;
  private static double initialY;
  private int currentImageIndex;
  private int currentPenColorIndex;
  private List<String> imageOptions;
  private Animation animation;
  private Animation rotate;
  private static final String PEN_UP_DISPLAY_TEXT = "up";
  private static final String PEN_DOWN_DISPLAY_TEXT = "down";
  private static final String MOVER_IS_ACTIVE = "active";
  private static final String MOVER_IS_NOT_ACTIVE = "inactive";

  public Mover(UserInterface view, double ID) {
    myView = view;
    penDown = true;
    distanceSoFar = 0;
    moverID = ID;
    moverImage = changeMoverDisplay(defaultImage);
    currentImageIndex = 1;
    currentPenColorIndex = -1;
    myLabelPropertyResources = ResourceBundle.getBundle(LabelResources);
    myComboBoxOptionsResources = ResourceBundle.getBundle(ComboBoxOptionsResources);
    String optionsString = myComboBoxOptionsResources.getString("setImageOptions");
    imageOptions = Arrays.asList(optionsString.split(","));
    initialX = myView.getXCenter();
    initialY = myView.getYCenter();
  }

  private void handleKeyInput(){
    ColorAdjust colorAdjustGrayscale = new ColorAdjust();
    if(moverActive) {
      colorAdjustGrayscale.setSaturation(-1);;
    }
    else{
      colorAdjustGrayscale.setSaturation(0);
    }
    moverImage.setEffect(colorAdjustGrayscale);
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

  public void setDefaultImage(String image){
    defaultImage = myComboBoxOptionsResources.getString(image);
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
    //&& animation.getStatus()== Status.STOPPED && rotate.getStatus()== Status.STOPPED
    if (moverActive) {
      setInitialPosition();
      createAndRunMoveAnimation(x, y);
      createAndRunRotateAnimation(angle);
      updateMoverPositionAndAngle(x, y, angle);
      if (penDown) {
        drawPen(x, y);
      }
      objectMoved = true;
      updateLabels();
    }
  }

  private void setInitialPosition() {
    moverStartingXPos = moverImage.getX();
    moverStartingYPos = moverImage.getY();
  }

  private void createAndRunMoveAnimation(double x, double y) {
    animation = makeAnimation(moverImage, x, y);
    if (x != 0 | y != 0) {
      animation.play();
    }
  }

  private void createAndRunRotateAnimation(double angle) {
    rotate = makeRotate(moverImage, angle);
    if (angle != 0) {
      rotate.play();
    }
  }

  private void updateMoverPositionAndAngle(double x, double y, double angle) {
    moverImage.setX(moverImage.getX() + x);
    moverImage.setY(moverImage.getY() + y);
    moverAngle = moverAngle + angle;
  }

  public void updateLabels() {
    for (String key : Collections.list(myLabelPropertyResources.getKeys())) {
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
  //  System.out.println(pt);
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
    initializeNewLine();
    setLineCoordinates(x, y);
    myView.addNodeToRoot(myLine);
  }

  private void initializeNewLine() {
    myLine =new Line();
    myLine.setStroke(lineColor);
    myLine.setStrokeWidth(lineThickness);
  }

  private void setLineCoordinates(double x, double y) {
    myLine.setStartX(moverStartingXPos+ moverImage.getBoundsInLocal().getWidth()/2);
    myLine.setStartY(moverStartingYPos + moverImage.getBoundsInLocal().getHeight());
    myLine.setEndX(moverStartingXPos + x+ moverImage.getBoundsInLocal().getWidth()/2);
    myLine.setEndY(moverStartingYPos + y+ moverImage.getBoundsInLocal().getHeight());
  }

  public Line getLine(){
    return myLine;
  }

  public void setLineColor(Color color){
    lineColor = color;
    updateLabels();
    List colorList = myView.getPropertyWindow().getColorGrid().getColorList();
    if(colorList.contains(color)){
      currentPenColorIndex = colorList.indexOf(color);
    }
    else{
      currentPenColorIndex = -1;
    }
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
    updateLabels();
  }

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

  public void setImageIndex(int index) {
    currentImageIndex = index;
  }

  public void setImage(String image) {
    double moverXPos = moverImage.getX();
    double moverYPos = moverImage.getY();
    double moverAngle = getMoverAngle();
    myView.removeNodeFromRoot(moverImage);
    String path = myView.getResource().getString(image);
    changeMoverDisplay(path);
    setImageIndex(imageOptions.indexOf(image)+1);
    moverImage.setX(moverXPos);
    moverImage.setY(moverYPos);
    moverImage.setRotate(moverAngle);
    myView.addNodeToRoot(moverImage);
  }

  public void setImageUsingIndex(int indexChoice){
    String image = imageOptions.get(indexChoice-1);
    currentImageIndex = indexChoice;
    setImageIndex(currentImageIndex);
    setImage(image);
  }

  public boolean getPen(){
    return penDown;
  }
  public boolean getActive(){
    return moverActive;
  }

  public String getPenPosition(){
    String ret = PEN_UP_DISPLAY_TEXT;
    if(penDown){
      ret = PEN_DOWN_DISPLAY_TEXT;
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
  }

  public void resetMover(){
    setInitialMoverPosition();
    moverVisible(true);
    updateLabels();
  }

  public String getMoverState() {
    if(moverActive){
      return MOVER_IS_ACTIVE;
    }
    return MOVER_IS_NOT_ACTIVE;
  }

  public double getMoverCenterXPos() {
    return this.moverCenterXPos;
  }

  public double getMoverCenterYPos() {
    return this.moverCenterYPos;
  }

  public int getCurrentPenColorIndex() {
    return this.currentPenColorIndex;
  }

  public void setCurrentPenColorByIndex(int index) {
    Color color =myView.getPropertyWindow().getColorGrid().getColorFromIndex(index);
    setLineColor(color);
  }
 }
