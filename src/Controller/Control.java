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
  private String arg2;
  private String com;
  private String userCom;
  private String input;

  public Control() {
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
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
  private void parseText(Parser parser, String lines) {
    command = new Stack<>();
    argument = new Stack<>();
    for (String line : lines.split(NEWLINE)) {
      if (line.contains("#")) {
        comment = line;
      } else {
        for (String word : line.split(WHITESPACE)) {
          if (word.trim().length() > 0) {
            String symbol = parser.getSymbol(word);
            if (symbol != null) {
              if (!symbol.equals(ARGUMENT)) {
                command.push(word);
              } else {
                argument.push(word);
              }
            }
          }
        }
      }
      coordinateCommands(parser);
    }
  }

  private void coordinateCommands(Parser parser) {
    if (!argument.isEmpty()) {
      arg = argument.pop();
      if (!command.isEmpty()) {
        userCom = command.pop();
        com = CLASS_PATH + parser.getSymbol(userCom);
        if (command.isEmpty() && !argument.isEmpty()) {
          arg2 = argument.pop();
        }
        passCommand();
      }
      if (!command.isEmpty()) {
        userCom = command.pop();
        com = CLASS_PATH + parser.getSymbol(userCom);
        passCommand();
      }
    } else {
      if (!command.isEmpty()) {
        userCom = command.pop();
        com = CLASS_PATH + parser.getSymbol(userCom);
        passCommand();
      }
    }

  }

  public void passCommand() {
    Class cls = null;
    try {
      cls = Class.forName(com);
    } catch (ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }

    Object objectCommand;
    try {
      Constructor constructor = cls.getConstructor(String[].class);
      objectCommand = constructor.newInstance((Object) new String[]{arg, arg2, userCom});
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      error.handleCommandClassNotFound();
    }
  }

  public void createCommand(Command command) {
    //can call stuff like this
    command.getPenCurrentX();
    coordinateCommands(parser);
  }
}

