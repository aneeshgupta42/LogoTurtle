package slogo;

import frontEnd.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */

    private final int FRAMES_PER_SECOND = 5;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 10.0 / FRAMES_PER_SECOND;

    public void start(Stage stage) throws Exception {

        View view = new View();
        stage.setScene(view.makeScene(80,80));
        stage.sizeToScene();
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    public void step(double elapsedTime) {
    }


    public static void main (String[] args) {
        launch(args);
    }

}
