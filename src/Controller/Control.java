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
  private final ErrorHandler error;
  private final Parser parser;
  private String language;
  private Deque<String> command;
  private Deque<String> argument;
  private String arg;
  private String arg2;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed = new HashMap<>();
  private Turtle myTurtle;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private StoreLists lists;
  private boolean commandArguments = false;
  private Command userfunction;

  public Control() {
    error = new ErrorHandler();
    parser = new Parser();
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
    lists = new StoreLists();
    for (String line : lines.split(NEWLINE)) {
      if (line.contains("#")) {
        String comment = line;
      } else {
        organizeInStacks(line);
      }
    }
  }

  private void organizeInStacks(String line) {
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
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
    coordinateCommands();
  }

  public void coordinateCommands() {
    int args = 0;
    System.out.println(argument);
    System.out.println(command);
    if (!argument.isEmpty()) {
      userCom = command.pollLast();
    //  System.out.println(userCom);
      if (parser.getSymbol(userCom).equals(LIST_START)) {
        commandArguments = true;
      }
      if (parser.getSymbol(userCom).equals(LIST_END)) {
        commandArguments = false;
      }
      makeClassPathToCommand(parser);
      try {
        Class cls = Class.forName(com);
        Object objectCommand;
        Constructor constructor = cls.getConstructor(String[].class, Control.class);
        objectCommand = constructor.newInstance((Object) new String[]{"1", "1"}, (Object) this);
        Command commandGiven = (Command) objectCommand;
        args = commandGiven.getNumberOfArgs();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        error.handleCommandClassNotFound();
      }

      arg = argument.pollLast();

      if (args == 2) {
        arg2 = argument.pollLast();
      }
      if (commandArguments && !parser.getSymbol(userCom).equals(LIST_START) && !parser.getSymbol(userCom).equals(LIST_END)) {
        lists.store(userCom, arg);
      }
      passCommand();
    }
    if (!command.isEmpty() && argument.isEmpty()) {
      userCom = command.pollLast();
      if (parser.getSymbol(userCom).equals(LIST_START)) {
        commandArguments = true;
      }
      if (parser.getSymbol(userCom).equals(LIST_END)) {
        commandArguments = false;
      }
      if (commandArguments && !parser.getSymbol(userCom).equals(LIST_START) && !parser.getSymbol(userCom).equals(LIST_END)) {
        lists.store(userCom, arg);
      }
      makeClassPathToCommand(parser);
      passCommand();
    }
  }

  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void passCommand() {
    System.out.println(com);
    System.out.println(arg);
    System.out.println(arg2);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(String[].class, Control.class);
      objectCommand = constructor.newInstance((Object) new String[]{arg, arg2}, (Object) this);
      Command commandGiven = (Command) objectCommand;
      if(commandArguments==false && !parser.getSymbol(userCom).equals(LIST_END)) {
        userfunction = commandGiven;
      }
      createCommand(commandGiven, parser);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }

  }

  public void createCommand(Command comm, Parser parser1) {
    if (comm.commandValueReturn() != null) {
      argument.push(comm.commandValueReturn());
      System.out.println(comm.commandValueReturn());
    }
    if (parser1.getSymbol(userCom).equals("MakeVariable")) {
      variablesUsed = comm.getVariablesCreated();
    }
    if (!command.isEmpty()) {
      coordinateCommands();
    }

    System.out.println(userfunction);
    System.out.println(commandArguments);
    if(commandArguments ==false && userfunction !=null) userfunction.repeatCom();
    /// it stores the commands that are in the [ ], but now they have to be acted upon
  }

  public void userInputCom(){
    command = lists.print();
    argument = lists.print2();
    System.out.println("1");
    System.out.println("2");
    coordinateCommands();
  }

  public void passTurtle(Turtle turtle) {
    myTurtle = turtle;
    turtleRow = myTurtle.getTurtleRow();
    turtleCol = myTurtle.getTurtleCol();
    turtleAngle = myTurtle.getTurtleAngle();
    System.out.println("Got turtle: Control:" + turtleCol + turtleRow + turtleAngle);
  }

  public double getTurtleCol() {
    return turtleCol;
  }

  public double getTurtleRow() {
    return turtleRow;
  }

  public double getTurtleAngle() {
    return turtleAngle;
  }

  public void updateTurtle(int col, int row, double angle) {
    turtleRow = myTurtle.getTurtleRow();
    turtleCol = myTurtle.getTurtleCol();
    turtleAngle = myTurtle.getTurtleAngle();
    myTurtle.move(col, row, angle);
  }

  public Turtle getTurtle() {
    return myTurtle;
  }
}

