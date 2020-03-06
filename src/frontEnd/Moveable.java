package frontEnd;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public interface Moveable {

    ImageView changeMoverDisplay(String imagePath);

    void setDefaultImage(String image);

    ImageView getImage();

    void move(double x, double y, double angle);

    void initializeLinePosition(double x, double y, double angle);

    void updateLabels();

    boolean objectMoved();

    void setObjectMoved(boolean bool);

    Line getLine();

    void setLineColor(Color color);

    Color getLineColor();

    double getThickness();

    int getCurrentImageIndex();

    double getMoverCol();

    double getMoverRow();

    double getMoverAngle();

    void setPen(boolean bool);

    void setImageIndex(int index);

    void setImageUsingIndex(int indexChoice);

    boolean getPen();

    boolean getActive();

    String getPenPosition();

    void updateDistanceSoFar(int d);
    int getDistanceSoFar();

    void resetTurtle();

    void eraseLines();

    void clearScreen();

    void moverVisible(boolean visible);

    void changeVisible();

    boolean isPenDown();

    boolean isMoverVisible();

    void setMoverInitialCords(double initialX, double initialY);

    double getMoverCenterXPos();

    double getMoverCenterYPos();

    void resetMover();
}
