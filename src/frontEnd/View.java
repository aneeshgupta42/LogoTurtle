package frontEnd;

import java.awt.TextArea;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import frontEnd.View;
import java.awt.Dimension;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


public class View extends Application {
  private Scene myScene;
  private GridPane grid;
  private Stage myStage;
  private double xPos;
  private double yPos;
  private int col;
  private int row;
  private double width;
  private double height;
  private ResourceBundle styleResources;
  private Map<Integer, Color> colorMap;
  private double caWidth;
  private double caHeight;

  private Turtle myTurtle;
  private Pen myPen;

  public static final String TITLE = "JavaFX Animation Example";
  public static final Dimension DEFAULT_SIZE = new Dimension(1600, 1600);
  private Node myActor;

  public View() {
   // myTurtle =
   // myPen = new Pen("red");
    //styleResources = ResourceBundle.getBundle(STYLE_PROPERTIES_FILENAME);
    //caHeight = Integer.parseInt(styleResources.getString("ScreenHeight"));
    //caWidth = Integer.parseInt(styleResources.getString("ScreenWidth"));
    makeDisplayWindow();
    makeScene(400, 400);
    makeCommandWindow();
    //initHashmap(rulesClass, totalNumStates);
    xPos = 0;
    yPos = 0;
    row = 0;
    col = 0;
    width = 0;
    height = 0;
    myStage = new Stage();
  }

  @Override
  public void start(Stage primaryStage){
    myStage.setTitle(TITLE);
    View view = new View();
    myStage.setX(0);
   // myScene.setFill(Color.BLACK);
    //myStage.setScene(myScene);
    myStage.setScene(makeScene(DEFAULT_SIZE.width, DEFAULT_SIZE.height));
    myStage.show();
    myStage.setOnCloseRequest(t->stopEverything());
    Animation animation = makeAnimation(myActor);
    animation.play();
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */
  /*public Scene makeScene(int width, int height) {

    Scene scene = new Scene(root, width, height);
    return scene;
  }*/

  // create a simple scene
  private Scene makeScene (int width, int height) {
    //Group root = new Group();
    BorderPane root = new BorderPane();
    root.setTop(makeDisplayWindow());
    //root.setLeft(makeDisplayWindow());
    //root.setRight(makeDisplayWindow());
    //root.setCenter(makeDisplayWindow());
    root.setBottom(makeCommandWindow());
    myActor = makeActor();
    root.getChildren().add(myActor);

    //root.getChildren().add(makeDisplayWindow());
    //root.getChildren().add(makeCommandWindow());
    //TextField inputArea = new TextField();
    //root.getChildren().add(inputArea);
    return new Scene(root, width,  height);
  }

  // create something to animate
  private Node makeActor () {
    Shape result = new Rectangle(50, 50, 50, 50);
    result.setFill(Color.PLUM);
    return result;
  }

  public void closeWindow(){
    myStage.close();
  }

  private Node makeDisplayWindow(){
    GridPane gridPane = new GridPane();
    gridPane.setMinSize(1600, 400);
    gridPane.setStyle("-fx-padding: 10;" +
        "-fx-border-style: solid inside;" +
        "-fx-border-width: 2;" +
        "-fx-border-insets: 5;" +
        "-fx-border-radius: 5;" +
        "-fx-border-color: blue;");
    return gridPane;
  }
  private Node makeCommandWindow(){
    TextField inputArea = new TextField();
    inputArea.setMinSize(1600, 200);
    inputArea.
    return inputArea;
  }

  private void stopEverything(){ 
    System.exit(1);
  }

  private Animation makeAnimation (Node agent) {
    // create something to follow
    Path path = new Path();
    path.getElements().addAll(new MoveTo(50, 50), new HLineTo(350));
    // create an animation where the shape follows a path
    PathTransition pt = new PathTransition(Duration.seconds(4), path, agent);
    // create an animation that rotates the shape
    RotateTransition rt = new RotateTransition(Duration.seconds(3));
    rt.setByAngle(90);
    // put them together in order
    return new SequentialTransition(agent, pt, rt);
  }

  public static void main(String[] args) {
    launch(args);
  }

}
