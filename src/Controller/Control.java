package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import backEnd.commands.MakeVariable;
import frontEnd.Turtle;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String LIST_END = "ListEnd";
  private static final String LIST_START = "ListStart";
  private final String RESOURCES = "resources";
  public final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String FILE = "commands";
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Deque<String> command;
  private Deque<String> argument;
  private String arg;
  private String arg2;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed = new HashMap<>();
  private List<String> words;
  private ResourceBundle myResources;
  private Turtle turtle;
  private int turtleCol;
  private int turtleRow;
  private int turtleAngle;

  public Control() {
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + FILE);
    error = new ErrorHandler();
    parser = new Parser();
    turtle = new Turtle();
  }

  public Map getVariables() {
    return variablesUsed;
  }

  public void setVariables(Map saved) {
    variablesUsed = saved;
  }

  public void passCommand(String command) {
    input = command;
  }

  public void passLanguage(String lang) {
    language = lang;
  }

  public void parseCommand() {
    passCommand(input);
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parseText(input);
  }

  private void parseText(String lines) {
    command = new LinkedList<>();
    argument = new LinkedList<>();
    words = new LinkedList<>();
    for (String line : lines.split(NEWLINE)) {
      /*if (line.contains("#")) {
        comment = line;
      } else */{
        organizeInStacks(line);
      }
    }
  }

  private void organizeInStacks(String line) {
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
        String symbol = parser.getSymbol(word);
        if (!symbol.equals(null) && !symbol.equals(LIST_END) && !symbol.equals(LIST_START)) {
          if (!parser.getSymbol(word).equals(ARGUMENT) && !parser.getSymbol(word).equals(VARIABLE)) {
            command.push(word);
          } else {
            if (parser.getSymbol(word).equals(VARIABLE)) {
              if (variablesUsed.containsKey(word)) {
                argument.push(variablesUsed.get(word));
              } else {
                argument.push(word);
              }
            } else {
              argument.push(word);
            }
          }
        }
      }
    }
    coordinateCommands();
  }

  public void coordinateCommands() {
    int args = 0;
    if (!argument.isEmpty() && !command.isEmpty()) {
      userCom = command.pop();
      arg = argument.pollLast();
      makeClassPathToCommand(parser);
      try {
        Class cls = Class.forName(com);
        Object objectCommand;
        Constructor constructor = cls.getConstructor(String[].class);
        objectCommand = constructor.newInstance((Object) new String[]{"1", "1"});
        Command commandGiven = (Command) objectCommand;
        args = commandGiven.getNumberOfArgs();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        error.handleCommandClassNotFound();
      }
      if (args == 2) {
        arg2 = argument.pollLast();
      }
      passCommand(parser);
      if (!command.isEmpty() && argument.isEmpty()) {
        userCom = command.pop();
        makeClassPathToCommand(parser);
        passCommand(parser);
      }
    }
  }

  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void passCommand(Parser parser1) {
    System.out.println(com);
    System.out.println(arg);
    System.out.println(arg2);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(String[].class);
      objectCommand = constructor.newInstance((Object) new String[]{arg, arg2});
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven, parser1);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }
  }

  public void createCommand(Command command, Parser parser1) {
    if (command.commandValueReturn() != null) {
      argument.push(command.commandValueReturn());
      System.out.println(command.commandValueReturn());
    }
    if (parser1.getSymbol(userCom).equals("MakeVariable")) {
      variablesUsed = command.getVariablesCreated();
      System.out.print(variablesUsed);
    }
    command.setControl(this);
    coordinateCommands();
  }


  public void PassTurtle(Turtle turtle){
    turtleRow = turtle.getTurtleRow();
    turtleCol = turtle.getTurtleCol();
    turtleAngle = turtle.getTurtleAngle();
  }

  public int getTurtleCol(){
    return turtleCol;
  }

  public int getTurtleRow(){
    return turtleRow;
  }

  public int getTurtleAngle(){
    return turtleAngle;
  }

  public void updateTurtle(int col, int row, int angle){
    turtleRow = turtle.getTurtleRow();
    turtleCol = turtle.getTurtleCol();
    turtleAngle = turtle.getTurtleAngle();
    turtle.move(col, row, angle);
  }


}

