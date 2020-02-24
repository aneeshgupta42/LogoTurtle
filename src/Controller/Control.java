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
  private final Turtle turtle;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private StoreLists lists;
  private boolean commandArguments;
  private Command commandObj;

  public Control() {
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
        System.out.println(parser.getSymbol(word));
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
    System.out.println(argument);
    System.out.println(command);
    coordinateCommands();
  }

  public void coordinateCommands() {
    int args = 0;
    boolean repeat = false;
   // if (!argument.isEmpty() && !command.isEmpty()) {
    if(!argument.isEmpty()) {
      userCom = command.pop();
     /* if(parser.getSymbol(userCom).equals(LIST_START)){
        commandArguments = true;
   //     userCom = command.pollLast();
      }
      if(parser.getSymbol(userCom).equals(LIST_END)){
        commandArguments = false;
     //   userCom = command.pollLast();
      }*/
      makeClassPathToCommand(parser);
      try {
        Class cls = Class.forName(com);
        Object objectCommand;
        Constructor constructor1 = cls.getConstructor(String[].class);
        objectCommand = constructor1.newInstance((Object) new String[]{"1", "1"});
        Command commandGiven = (Command) objectCommand;
        args = commandGiven.getNumberOfArgs();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        error.handleCommandClassNotFound();
      }

      arg = argument.pollLast();

      if (args == 2) {
        arg2 = argument.pollLast();
      }
      passCommand();
    }
    if (!command.isEmpty() && argument.isEmpty()) {
          userCom = command.pop();
         /* if(parser.getSymbol(userCom).equals(LIST_START)){
            commandArguments = true;
            System.out.println("hi");
          //  userCom = command.pollLast();
          }
          if(parser.getSymbol(userCom).equals(LIST_END)){
            commandArguments = false;
            System.out.println("hi");
          //  userCom = command.pollLast();
          }*/
          makeClassPathToCommand(parser);
          passCommand();
    //    }
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
      Constructor constructor = cls.getConstructor(String[].class);
      objectCommand = constructor.newInstance((Object) new String[]{arg, arg2});
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven, parser);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }
  }

  public void createCommand(Command comm, Parser parser1) {
   // commandObj = comm;

   /* if(commandArguments){
      System.out.println("okay");
      lists.storeCom(commandObj);
      commandObj.setControl(this);
      commandObj.repeatCom();
    if(command.isEmpty() && argument.isEmpty()){
      System.out.println("rer");

   //   repeat();
    }*/

    if (comm.commandValueReturn() != null) {
      argument.push(comm.commandValueReturn());
      System.out.println(comm.commandValueReturn());
    }
    if (parser1.getSymbol(userCom).equals("MakeVariable")) {
      variablesUsed = comm.getVariablesCreated();
    }
    if(!command.isEmpty()) {
      coordinateCommands();
    }
  }

  /*public void repeat(){
    System.out.println("repeating");
    Command c = lists.runCom();
    System.out.println(c);
    System.out.println(c.getNumberOfArgs());
  }

*/







  public void PassTurtle(Turtle turtle){
    turtleRow = turtle.getTurtleRow();
    turtleCol = turtle.getTurtleCol();
    turtleAngle = turtle.getTurtleAngle();
  }

  public double getTurtleCol(){
    return turtleCol;
  }

  public double getTurtleRow(){
    return turtleRow;
  }

  public double getTurtleAngle(){
    return turtleAngle;
  }

  public void updateTurtle(int col, int row, int angle){
    turtleRow = turtle.getTurtleRow();
    turtleCol = turtle.getTurtleCol();
    turtleAngle = turtle.getTurtleAngle();
    turtle.move(col, row, angle);
  }

}

