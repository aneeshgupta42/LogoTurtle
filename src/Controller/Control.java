package Controller;

import backEnd.ErrorHandler;
import backEnd.PenUpdate;
import backEnd.TurtleUpdate;
import frontEnd.View;
import java.util.Arrays;
import java.util.List;
import slogo.Main;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private View view;
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;
  private String language;

  public Control(String command) {
    view = new View();
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }


  public void parseCommand() {
    Control m = new Control(view.display());
    parser.addPatterns("English");
    //parser.addPatterns(language);
    parser.addPatterns("Syntax");

    // try against different kinds of inputs
    m.parseText(parser, view.display());
    String userInput = "fd 50 rt 90 BACK :distance Left :angle";
    // note, this simple "algorithm" will not handle SLogo comments
  }


  // given some text, prints results of parsing it using the given language
  private void parseText(Parser parser, String lines) {
    for (String line : lines.split(NEWLINE)) {
      if (line.contains("#")) {
        System.out.println(String.format("%s : %s", line, "Comment"));
      } else {
        for (String word : line.split(WHITESPACE)) {
          if (word.trim().length() > 0) {
            System.out.println(String.format("%s : %s", word, parser.getSymbol(word)));
          }
        }
      }
      System.out.println();
    }
  }
}

//need to add in capabilities for # comments to be ignored