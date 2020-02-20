package Controller;

import backEnd.ErrorHandler;
import backEnd.PenUpdate;
import backEnd.TurtleUpdate;
import frontEnd.View;
import java.util.Stack;
import java.util.regex.Pattern;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private View view;
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Stack<String> command;
  private Stack<String> argument;

  public Control(String command) {
    view = new View();
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }


  public void parseCommand() {
    Control m = new Control(view.display());
    //parser.addPatterns("Chinese");
    //parser.addPatterns(language);
    parser.addPatterns("Syntax");
    m.parseText(parser, view.display());
  }


  // Given commands, calls organization of commands
  private void parseText(Parser parser, String lines) {
    command = new Stack<>();
    argument= new Stack<>();
    for (String line : lines.split(NEWLINE)) {
      if (line.contains("#")) {
        comment = line;
      } else {
        for (String word : line.split(WHITESPACE)) {
          if (word.trim().length() > 0) {
              organizeText(word,parser.getSymbol(word));
             // System.out.println(String.format("%s : %s",word,parser.getSymbol(word)));
          }
        }
      }
    }
  }

  private void organizeText(String word, String symbol){
    if(symbol!=null){
    if(!symbol.equals("Constant")){
      command.add(word);
    }
    else{
      argument.add(word);
    }}
    System.out.println(command);
    System.out.println(argument);
  }



}
