package frontEnd;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
  private TextArea myCommander;
  private static final String TURTLE = "turtle.png";
  private String myText;

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
    //animation.play();
  }

  /**
   * Returns scene for the browser so it can be added to stage.
   */

  private Scene makeScene (int width, int height) {
    BorderPane root = new BorderPane();
    HBox hbox = addHBox();
    root.setTop(hbox);
    root.setCenter(makeDisplayWindow());
    root.setRight(makeSideWindow());
    //root.setCenter(makeDisplayWindow());
    //myCommander = makeCommandWindow();
    root.setBottom(makeCommandWindow());
    //root.getChildren().add(myTurtle);
    myActor = makeActor();
    //root.getChildren().add(myActor);
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
    //gridPane.setMaxSize(1200, 400);
    gridPane.setStyle("-fx-padding: 10;" +
        "-fx-border-style: solid inside;" +
        "-fx-border-width: 2;" +
        "-fx-border-insets: 5;" +
        "-fx-border-radius: 5;" +
        "-fx-border-color: grey;");
    Image turtle= new Image(getClass().getClassLoader().getResourceAsStream(TURTLE));
    ImageView myTurtle = new ImageView(turtle);
    myTurtle.setY(gridPane.getBoundsInLocal().getHeight()/2);
    myTurtle.setX(gridPane.getBoundsInLocal().getWidth()/2);
    gridPane.getChildren().add(myTurtle);
    return gridPane;
  }


  private Node makeSideWindow() {
    GridPane gridPane = new GridPane();
    gridPane.setMinHeight(800);
    gridPane.setMinWidth(400);
    gridPane.setStyle("-fx-padding: 10;" +
        "-fx-border-style: solid inside;" +
        "-fx-border-width: 2;" +
        "-fx-border-insets: 5;" +
        "-fx-border-radius: 5;" +
        "-fx-border-color: grey;");
    return gridPane;
  }

  private Node makeCommandWindow(){
    HBox hbox = new HBox();
    TextArea inputArea = new TextArea();
    inputArea.setPromptText("Enter Command");
    inputArea.setPrefColumnCount(10);
    inputArea.getText();
    GridPane.setConstraints(inputArea, 0, 0);
    //inputArea.getText();
    //inputArea.clear();
    inputArea.setMinHeight(200);
    inputArea.setMinWidth(1500);
    inputArea.setStyle("-fx-padding: 10;" +
        "-fx-border-style: solid inside;" +
        "-fx-border-width: 2;" +
        "-fx-border-insets: 5;" +
        "-fx-border-radius: 5;" +
        "-fx-border-color: #336699;");
    VBox vbox = new VBox();
    Button runButton = new Button("Run");
    runButton.setPrefSize(100, 20);

    runButton.setOnAction(action -> {
      myText = inputArea.getText();
      System.out.println(myText);
      inputArea.setText("Running!");
    });
    Button clearButton = new Button("Clear");
    clearButton.setPrefSize(100, 20);

    clearButton.setOnAction(action -> {
      inputArea.setText("");
    });
    vbox.getChildren().addAll(runButton, clearButton);
    hbox.getChildren().addAll(inputArea, vbox);

    return hbox;
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

  public HBox addHBox() {
    HBox hbox = new HBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
    hbox.setStyle("-fx-background-color: #336699");

    Button buttonCurrent = new Button("Current");
    buttonCurrent.setPrefSize(100, 20);

    Button buttonProjected = new Button("Projected");
    buttonProjected.setPrefSize(100, 20);

    hbox.getChildren().addAll(buttonCurrent, buttonProjected);

    return hbox;
  }

  public String getText(){
    return myText;
  }

  public static void main(String[] args) {
    launch(args);
  }
}
