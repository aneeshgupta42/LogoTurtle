package frontEnd;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Creates a popup saying what error occurred and keeps it up until it is closed
 */
public class ErrorBoxes {
  /**
   * Creates a popup that simply has the message of the thrown Exception printed
   * @param e is the exception that caused the error
   */
  public ErrorBoxes(Exception e){
    Stage errorStage = new Stage();
    errorStage.setTitle("Error");
    Label errorLabel = new Label(e.getMessage());
    errorLabel.setAlignment(Pos.CENTER);
    Scene errorScene = new Scene(errorLabel);
    errorStage.setScene(errorScene);
    errorStage.showAndWait();
  }
}
