/*
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
  private View view;
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;

  public Control(String command){
    view = new View();
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }


  public void parseCommand(){
    Control m = new Control(view.display());
    // these are more specific, so add them first to ensure they are checked first
    parser.addPatterns("English");
    // general checks, added last
    parser.addPatterns("Syntax");

    // try against different kinds of inputs
    m.parseText(parser, view.display());
    String userInput = "fd 50 rt 90 BACK :distance Left :angle";
    // note, this simple "algorithm" will not handle SLogo comments
  }


  // given some text, prints results of parsing it using the given language
  private void parseText (Parser parser, String lines) {
    for (String line : lines.split(WHITESPACE)) {
      if (line.trim().length() > 0) {
        System.out.println(String.format("%s : %s", line, parser.getSymbol(line)));
      }
    }
    System.out.println();
  }


}
*/
