package slogo;

import frontEnd.UserInterface;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


/**
 * Main class which is run to start the program by launching the user interface
 */
public class Main {
    public static void main (String[] args) {
        //Application.launch(UserInterface.class, args);
        Platform.startup(() -> {
            UserInterface myInterface = new UserInterface();
            myInterface.start(new Stage());
        });
    }
}

