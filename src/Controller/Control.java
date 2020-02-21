package Controller;

import backEnd.ErrorHandler;
import backEnd.PenUpdate;
import backEnd.TurtleUpdate;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String CLASS_PATH = "Controller.";
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Stack<String> command;
  private Stack<String> argument;
  private String arg;
  private String com;
  private String input;

  public Control() {
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }

  public void takeCommand(String command){
    input=command;
  }
  public void passLanguage(String lang){
    language=lang;
  }

  public void parseCommand() {
    Control m = new Control();
    m.takeCommand(input);
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parser.addPatterns("Functions");
    m.parseText(parser, input);
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
            String symbol = parser.getSymbol(word);
            if(symbol!=null){
              if(!symbol.equals(ARGUMENT)){
                command.add(symbol);
              }
              else{
                argument.add(word);
              }}
             // System.out.println(String.format("%s : %s",word,parser.getSymbol(word)));
          }
        }
      }
    }
    //System.out.println(command);
    //System.out.println(argument);
     coordinateCommands();
  }

  private void coordinateCommands(){
    System.out.println(command);
    System.out.println(argument);
    if(!argument.isEmpty()){
      arg = argument.pop();
      if(!command.isEmpty()){
        com = CLASS_PATH + command.pop();
        passCommand();
      }
      if(!command.isEmpty()){
        com = CLASS_PATH + command.pop();
        passCommand();
      }
    }
    else{
      if(!command.isEmpty()){
        com = CLASS_PATH + command.pop();
        passCommand();
      }
    }

  }



  public void passCommand(){
    Class cls = null;
    try {
      cls = Class.forName(com);
    } catch (ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }

    Object objectCommand;
        try {
          Constructor constructor = cls.getConstructor(String[].class);
          objectCommand = constructor.newInstance((Object) new String[] {arg});
          Command commandGiven = (Command) objectCommand;
          createCommand(commandGiven);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
          error.handleCommandClassNotFound();
        }
  }

  public void createCommand(Command command){
    System.out.println(command);
    coordinateCommands();
  }
}

