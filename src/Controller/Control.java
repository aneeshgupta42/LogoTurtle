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
  private boolean once = false;
  private boolean inList =false;
  private LinkedList<String> args;

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
    args = new LinkedList<>();
    lists = new StoreLists();
    for (String line : lines.split(NEWLINE)){
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
          if (parser.getSymbol(word).equals(LIST_END) || parser.getSymbol(word)
              .equals(LIST_START)) {
            command.add(word);
          } else {
            command.push(word);
          }
        } else if (parser.getSymbol(word).equals(VARIABLE)) {
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
    coordinateCommands();
  }

  public void coordinateCommands() {
    int argNum = 0;
    if (!argument.isEmpty()) {
      userCom = command.pop();
      makeClassPathToCommand(parser);
      try {
        Class cls = Class.forName(com);
        Object objectCommand;
        Constructor constructor = cls.getConstructor();
        objectCommand = constructor.newInstance();
        Command commandGiven = (Command) objectCommand;
        argNum = commandGiven.getNumberOfArgs();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        error.handleCommandClassNotFound();
      }
      for (int i =0;i<argNum;i++){
        args.push(argument.pollLast());
      }
      if(argNum==0) args = null;
      checkIfList();
      passCommand();
    }
    if (!command.isEmpty() && argument.isEmpty()) {
      userCom = command.pop();
      makeClassPathToCommand(parser);
      checkIfList();
      passCommand();
    }
  }

  private void checkIfList() {
    if(inList){
      lists.store(userCom);
      lists.storeArg(args);
    }
    if (parser.getSymbol(userCom).equals(LIST_START)){
      commandArguments = true;
    }
    if (parser.getSymbol(userCom).equals(LIST_END)){
      commandArguments = false;
    }
    if ((commandArguments && !parser.getSymbol(userCom).equals(LIST_START) && !parser.getSymbol(userCom).equals(LIST_END))){
      lists.store(userCom);
      lists.storeArg(args);
    }
  }

  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void passCommand() {
    System.out.println(com);
    System.out.println(args);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
      objectCommand = constructor.newInstance((Object) args, (Object) this);
      Command commandGiven = (Command) objectCommand;
      if(commandArguments==false && userfunction==null && !parser.getSymbol(userCom).equals(LIST_END) && once==false) {
        userfunction = commandGiven;
        once = true;
      }
      createCommand(commandGiven, parser);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }

  }

  public void createCommand(Command comm, Parser parser1) {
    if (comm.commandValueReturn() != null) {
      argument.push(comm.commandValueReturn());
    }
    if (parser1.getSymbol(userCom).equals("MakeVariable")){
      variablesUsed = comm.getVariablesCreated();
    }
    if(commandArguments == false && userfunction !=null && !comm.equals(userfunction) && parser.getSymbol(userCom).equals(LIST_END)){
      int b = userfunction.repeatCom();
      userInputCom(b);
    }
    else if(!command.isEmpty() && inList==false){
      coordinateCommands();
    }
  }

  public void userInputCom(int b){
    if(b==1){
      inList = false;
    }
    else {
      command = lists.print();
      argument = lists.print2();
      b-=1;
      inList = true;
      args = new LinkedList<>();
      coordinateCommands();
      userInputCom(b);
    }
  }

  public void passTurtle(Turtle turtle) {
    myTurtle = turtle;
    turtleRow = myTurtle.getTurtleRow();
    turtleCol = myTurtle.getTurtleCol();
    turtleAngle = myTurtle.getTurtleAngle();
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

  public void setTurtleVisible(boolean mode){
    myTurtle.turteVisible(mode);
  }

  public void updateTurtle(double col, double row, double angle, int distance){
    turtleRow = myTurtle.getTurtleRow();
    turtleCol = myTurtle.getTurtleCol();
    turtleAngle = myTurtle.getTurtleAngle();
    myTurtle.updateDistanceSoFar(distance);
    myTurtle.move(col, row, angle);
  }
  public int getTurtleDistance(){
    return myTurtle.getDistanceSoFar();
  }

  public void turtleHome(boolean clearScreen){
    if(clearScreen){
      myTurtle.clearScreen();
    }
    else{
      myTurtle.resetTurtle();
    }
  }

  public Turtle getTurtle() {
    return myTurtle;
  }
}

