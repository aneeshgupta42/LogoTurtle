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
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "Controller.";
  private static final String LIST_END ="ListEnd";
  private static final String LIST_START ="ListStart";
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Stack<String> command;
  private Stack<String> argument;
  private Stack<String> variable;
  private String arg;
  private String arg2;
  private String com;
  private String var;
  private String userCom;
  private String input;

  public Control() {
    // pen = new PenUpdate();
    //  turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }

  public void takeCommand(String command) {
    input = command;
  }

  public void passLanguage(String lang) {
    language = lang;
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

  //check the difference between constant variable command list comment
  private void parseText(Parser parser1, String lines) {
    command = new Stack<>();
    argument = new Stack<>();
    variable = new Stack<>();
    for (String line : lines.split(NEWLINE)) {
     /* if (line.contains("#")) {
        comment = line;
      } else */{
        for (String word : line.split(WHITESPACE)) {
          if (word.trim().length() > 0) {
            String symbol = parser1.getSymbol(word);
          //  System.out.print(symbol);
          //  System.out.println (" "+ word);
            if (!symbol.equals(null) && !symbol.equals(LIST_END) && !symbol.equals(LIST_START)) {
              if (parser1.getSymbol(word).equals(VARIABLE)) {
                variable.push(word);
              } else if (parser1.getSymbol(word).equals(ARGUMENT)) {
                argument.push(word);
              } else {
                command.push(word);
              }
            }
          }
          System.out.println(command);
          System.out.println(argument);
          System.out.println(variable);
          checkCommandForArgumentsNeeded();
          coordinateCommands(parser1);
        }



      }
    }

  }

  private void checkCommandForArgumentsNeeded() {
    if (argument.isEmpty()) {
      makeClassPathToCommand(parser);

    }
  }

  private void coordinateCommands(Parser parser1) {
    if (!argument.isEmpty()) {
      arg = argument.pop();
      if (!command.isEmpty()) {
        makeClassPathToCommand(parser1);
        if(!variable.isEmpty()){
          var = variable.pop();
        }
        if (command.isEmpty() && !argument.isEmpty()) {
          arg2 = argument.pop();
        }
        passCommand();
      }
    } else {
      if (!command.isEmpty()) {
        makeClassPathToCommand(parser1);
        passCommand();
      }
    }

  }

  private void makeClassPathToCommand(Parser parser1) {
    userCom = command.pop();
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void passCommand() {
    System.out.println(com);
    System.out.println(arg);
    System.out.println(arg2);
    System.out.println(var);
    System.out.println(userCom);
    Class cls = null;
    try {
      cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(String[].class);
     // objectCommand = constructor.newInstance((Object) new String[]{arg, arg2,var, userCom});
    //  Command commandGiven = (Command) objectCommand;
     // createCommand(commandGiven);
    } catch (NoSuchMethodException | ClassNotFoundException e){  //InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }
  }

  public void createCommand(Command command) {
  }
}

