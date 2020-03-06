package frontEnd;

import controller.Control;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public interface Viewable{
    static void main(String[] args) {
        Application.launch(args);
    }

    void setMyMover(Object mover);

    ButtonAction getButtonAction();

    //fix numbers
    HBox addHBox();

    void setMoverPosition(ImageView image);

    Mover getMover();

    Map getTurtleMap();

    String getText();

    TextArea getCommander();

    Control getControl();

    Rectangle getRectangle();

    Color getLineColor();

    BorderPane getRoot();

    void setMoverX(double x);

    Map getPropertyLabelMap();

    void resetDisplay();

    void setLanguage(String language);

    void setImage(String image);

    void setImageIndex(int index);

    int getCurrentImageIndex();

    double getmoverID();

    void setBackgroundColor(Color color);

    void setPenColor(Color color);

    void setPen(String position);

    void addTurtle();

    void setOnClear();

    void selectTurtle(String num);

    double getLineWidth();

    double getXPosition();

    double getYPosition();

    double getMoverID();

    double getAngle();

    double getLineThickness();

    String getPenPosition();

    void setDefaultImage(String image);

    void changePenPosition();
}
