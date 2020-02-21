package Controller;

import backEnd.ErrorHandler;
import backEnd.PenUpdate;
import backEnd.TurtleUpdate;
import frontEnd.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String CLASS_PATH = "Controller.";
  private View view;
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

  public Control(String command) {
    input = command;
   // view = new View();
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }


  public void parseCommand() {
    Control m = new Control(input);
    parser.addPatterns("English");
    parser.addPatterns("Syntax");
    System.out.println(input);
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
              organizeText(word,parser.getSymbol(word));
             //System.out.println(String.format("%s : %s",word,parser.getSymbol(word)));
          }
        }
      }
    }
  }

  private void organizeText(String word, String symbol){
    if(symbol!=null){
    if(!symbol.equals(ARGUMENT)){
      command.add(symbol);
    }
    else{
      argument.add(word);
    }}
    coordinateCommands();
   // System.out.println(command);
   // System.out.println(argument);
  }



  private void coordinateCommands(){

    if(!argument.isEmpty()){
      arg = argument.pop();
      if(!command.isEmpty()){
        com = CLASS_PATH + command.pop();
        passCommand();

      }
     // System.out.println(command);
     // System.out.println(argument);
    }

  }



  public void passCommand(){
    Class cls = null;
    try {
      cls = Class.forName(com);
    } catch (ClassNotFoundException e) {
      error.handle("error");
    }

    Object objectCommand;
        try {
          Constructor constructor = cls.getConstructor(String[].class);
          objectCommand = constructor.newInstance((Object) new String[] {arg});
          Command commandGiven = (Command) objectCommand;
          createCommand(commandGiven);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
          error.handle("error");
        }
  }

  public void createCommand(Command command){
    System.out.println(command);
  }
}

