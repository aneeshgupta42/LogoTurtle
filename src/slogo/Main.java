package slogo;

import frontEnd.UserInterface;
import javafx.application.Application;


/**
 * Main class which is run to start the program by launching the user interface
 */
public class Main {
    public static void main (String[] args) {
        Application.launch(UserInterface.class, args);
    }
}

