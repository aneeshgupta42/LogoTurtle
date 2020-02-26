package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.Turtle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Control {
//check
  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String FUNCCOMMAND = "Command";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String LIST_END = "ListEnd";
  private static final String LIST_START = "ListStart";
  private final ErrorHandler error;
  private final Parser parser;
  private String language;
  private Deque<String> command;
  private Deque<String> argument;
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
  private boolean inList = false;
  private LinkedList<String> args;
  private boolean dotimes = false;
  private int count = 0;
  private String var;
  private boolean runnable;
  private int i=0;
  private boolean makeFunction;

  public Control() {
    lists = new StoreLists();
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
    parseText();
  }

  private void parseText() {
    System.out.println("here");
    command = new LinkedList<>();
    argument = new LinkedList<>();
    args = new LinkedList<>();
    for (String line : input.split(NEWLINE)) {
      if (line.contains("#")) {
        String comment = line;
      } else {
        organizeInStacks(line);
      }
    }
  }

  private void organizeInStacks(String line) {
    int j=0;
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
        if (!parser.getSymbol(word).equals(ARGUMENT) && !parser.getSymbol(word).equals(VARIABLE)) {
          Map<String, String> map = lists.getFunction();
          if (map.keySet().contains(word) && input!=map.get(word)) {
            input = map.get(word);
            parseText();
          }
          else if(makeFunction){
              lists.storeFunction(word, input);
              makeFunction = false;
              coordinateCommands();
          }
          else  command.push(word);
        }
        else if (parser.getSymbol(word).equals(VARIABLE)) {
          if (commandArguments) {
            dotimes = true;
            var = word;
          }
          else if (variablesUsed.containsKey(word)) {
              argument.push(variablesUsed.get(word));
            }
            else {
              argument.push(word);
            }
        }
        else if(parser.getSymbol(word).equals(ARGUMENT)){
          argument.push(word);
        }
      }
      coordinateCommands();
    }
  }

  public void coordinateCommands() {
    int argNum = 0;
    if(!command.isEmpty()) {
      userCom = command.pop();
      if (parser.getSymbol(userCom).equals("MakeUserInstruction")) {
        makeFunction = true;
      }
      makeClassPathToCommand(parser);
      try {
        Class cls = Class.forName(com);
        Object objectCommand;
        Constructor constructor = cls.getConstructor();
        objectCommand = constructor.newInstance();
        Command commandGiven = (Command) objectCommand;
        System.out.println(commandGiven);
        argNum = commandGiven.getNumberOfArgs();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        error.handleCommandClassNotFound();
      }
      checkIfCommandCanRun(argNum);
    }
  }

  private void checkIfCommandCanRun(int argNum) {
    if (!argument.isEmpty() && argument.size() >= argNum && count <= 2) {
      for (int i = 0; i < argNum; i++) {
        args.push(argument.pop());
      }
      checkIfList();
      runCommand();
      if (!command.isEmpty() && argument.isEmpty()) {
        checkIfList();
        makeClassPathToCommand(parser);
        runCommand();
      }
    } else if (argument.isEmpty() && argNum != 0) {
      command.push(userCom);
      checkIfList();
    } else if (!argument.isEmpty() && argument.size() <= argNum && argNum != 0) {
      String arg = argument.pollLast();
      checkIfList();
      command.push(userCom);
      argument.add(arg);
    } else if (argNum == 0) {
      args = new LinkedList<>();
      checkIfList();
      runCommand();
    }
    count = argNum;
  }

  private void checkIfList() {
    if (parser.getSymbol(userCom).equals(LIST_START)) {
      i++;
      if(runnable && i==1){
        commandArguments=false;
      }
      else commandArguments = true;
    }
    if (parser.getSymbol(userCom).equals(LIST_END)) {
      if(runnable && i==2){
        commandArguments=true;
      }
      else commandArguments = false;
    }
    if ((commandArguments && !parser.getSymbol(userCom).equals(LIST_START) && !parser.getSymbol(userCom).equals(LIST_END)) || inList) {
      lists.store(userCom);
      lists.storeArg(args);
    }
  }

  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void runCommand() {
    if (commandArguments == false || dotimes) {
      obtainCommand();
    }
  }

  private void obtainCommand() {
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
      objectCommand = constructor.newInstance((Object) args, (Object) this);
      Command commandGiven = (Command) objectCommand;
      if (userfunction == null && !parser.getSymbol(userCom).equals(LIST_END) && once == false && !parser.getSymbol(userCom).equals(LIST_START)) {
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
      if(!command.isEmpty()) {
        argument.push(comm.commandValueReturn());
      }
    }
    if (parser1.getSymbol(userCom).equals("MakeVariable")) {
      variablesUsed.putAll(comm.getVariablesCreated());
    }
    if(parser1.getSymbol(userCom).equals("If")){
      runnable = comm.runnable();
    }
    int loop = userfunction.repeatCom();
    if (loop !=0 && commandArguments == false && userfunction != null && !comm.equals(userfunction) && parser.getSymbol(userCom).equals(LIST_END)) {
        int i=0;
        userInputCom(loop,i);
    }
    if (!command.isEmpty() && userfunction.repeatCom() == 0) {
      coordinateCommands();
    }
  }


  public void userInputCom(int loop, int i) {
    if (loop == 0) {
      inList = false;
    } else {
      command = lists.getCommands();
      argument = lists.getArguments();
      repCount(loop,var);
      repCount(i,":repCount");
      loop -=1;
      i += 1;
      inList = true;
      args = new LinkedList<>();
      coordinateCommands();
      userInputCom(loop, i);
    }
  }

  private void repCount(int loop, String s) {
    userCom = "make";
    args.push(s);
    args.push("" + loop);
    makeClassPathToCommand(parser);
    obtainCommand();
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

  public void setPenDown(boolean mode){
    myTurtle.setPen(mode);
  }
  public boolean isPenDown(){
    return myTurtle.isPenDown();
  }

  public double getTurtleAngle() {
    return turtleAngle;
  }

  public void setTurtleVisible(boolean mode) {
    myTurtle.turtleVisible(mode);
  }

  public boolean findTurtleVisibility() {
    return myTurtle.isTurtleVisible();
  }

  public void updateTurtle(double col, double row, double angle, int distance) {
    turtleRow = myTurtle.getTurtleRow() + row;
    turtleCol = myTurtle.getTurtleCol() + col;
    turtleAngle = myTurtle.getTurtleAngle() + angle;
    myTurtle.updateDistanceSoFar(distance);
    myTurtle.move(col, row, angle);
    System.out.println("update" + col + " " + row + " " + angle);
  }

  public int getTurtleDistance() {
    return myTurtle.getDistanceSoFar();
  }

  public void turtleHome(boolean clearScreen) {
    if (clearScreen) {
      myTurtle.clearScreen();
    } else {
      myTurtle.resetTurtle();
    }
  }

  public Turtle getTurtle() {
    return myTurtle;
  }

  public double getTurtleRelativeXPos() {
    return turtleCol - myTurtle.getTurtleCenterXPos();
  }

  public double getTurtleRelativeYPos() {
    return myTurtle.getTurtleCenterYPos() - turtleRow;
  }
}

