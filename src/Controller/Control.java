package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import frontEnd.Turtle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class Control {
  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String LIST_END = "ListEnd";
  private static final String LIST_START = "ListStart";
  //private final ErrorHandler error;
  private final Parser parser;
  private String language;
  private Deque<String> command;
  private Deque<String> argument;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed = new TreeMap<>();
  private Turtle myTurtle;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private StoreLists lists;
  private boolean checkingIfLoopInt = false;
  private Command userfunction;
  private boolean once = false;
  private boolean inList = false;
  private LinkedList<String> args;
  private int trackPreviousNumArgsInt = 0;
  private String var;
  private boolean runnableIf;
  private int logicStatementInt =0;
  private boolean makeFunction;
  private int iterationOfLoopInt =0;
  private int numOfLoops;
  private int numberOfCommands;
  private boolean loopingComplete;
  private boolean doTimes;
  /*
  Initializing a control (for reference storeLists is where all the data in lists is being passed)
   */
  public Control() {
    lists = new StoreLists();
    //error = new ErrorHandler();
    parser = new Parser();
  }

  /*
  Gets the variables that have been used by the user and returns them as a Map (variables are the key)
  @return variablesUsed
   */
  public Map getVariables() {
    return variablesUsed;
  }
  public Map getUserCommands() {
    return lists.getFunction();
  }

  /*
  Sets the Map to be the map retrieved from our StoreLists object
   */
  public void setVariables(Map saved) {
    variablesUsed = saved;
  }

  /*
  Sets user input to be parsed
   */
  public void setCommand(String command) {
    input = command;
  }
  /*
  Sets language to be used
 */
  public void setLanguage(String lang) {
    language = lang;
  }

  /*
  Calls the parser to start parsing the user input
   */
  public void parseCommand() {
    setCommand(input);
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parseText();
  }

  /*
    Splits text into lines
   */
  private void parseText() {
    command = new LinkedList<>();
    argument = new LinkedList<>();
    args = new LinkedList<>();
    logicStatementInt =0;
    for (String line : input.split(NEWLINE)) {
      if (line.contains("#")) {
        String comment = line;
      }
      else {
        organizeInStacks(line);
      }
    }
  }

  /*
  Splits lines into words and categorizes them into two stacks
   */
  private void organizeInStacks(String line) {
    iterationOfLoopInt =0;
    trackPreviousNumArgsInt =0;
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
        if (!parser.getSymbol(word).equals(ARGUMENT) && !parser.getSymbol(word).equals(VARIABLE)) {
        //  System.out.println("Command");
          checkingTypeOfCommand(word);
        }
        else if (parser.getSymbol(word).equals(VARIABLE)) {
         // System.out.println("variable");
          checkingIfVariableExists(word);
        }
        else if(parser.getSymbol(word).equals(ARGUMENT)){
        //  System.out.println("Argument");
          argument.push(word);
        }
      }
      coordinateCommands();
    }
  }

  /*
  Checking if command comes from a TO function, else pushing to stack to be run immediately.
   */
  private void checkingTypeOfCommand(String word) {
    //this is if you input a to function
    Map<String, String> map = lists.getFunction();
    if (map.keySet().contains(word) && input!=map.get(word)) {
      //it checks that it isn't already running before parsing it
      input = map.get(word);
      parseText();
    }
    else if(makeFunction){
      //otherwise if the map does not have that function stored already, it stores it
      lists.storeFunction(word, input);
      makeFunction = false;
      coordinateCommands();
    }
    else command.push(word);
  }

  /*
  Checks if variable is already present. If it is a TO statement, this is saving the variable name separately.
   */
  private void checkingIfVariableExists(String word) {
    if (checkingIfLoopInt) {
      //if this is a loop that contains a variable
      doTimes = true;
      var = word;
    }
    else if (variablesUsed.containsKey(word)) {
        argument.push(variablesUsed.get(word));
      }
      else {
        argument.push(word);
      }
  }

  /*
  Getting the number of arguments for each command
   */
  public void coordinateCommands() {
//    System.out.println(command);
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
        argNum = commandGiven.getNumberOfArgs();
      } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        //error.handleCommandClassNotFound()
        ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidCommand"));
        //throw new ErrorHandler("FileErrorMessage");
        //ErrorBoxes errorScreen = new ErrorHandler(new RuntimeException(styleResources.getString(errorMessageProperty)));
      }
      checkIfCommandCanRun(argNum);
    }
  }

  /*
  Coordinating the command to the number of arguments it needs and pushing it to be run
   */
  private void checkIfCommandCanRun(int argNum) {
    if (!argument.isEmpty() && argument.size() >= argNum && trackPreviousNumArgsInt <= 2) {
      for (int i = 0; i < argNum; i++) {
        args.push(argument.pop());
      }
      checkIfList();
      runCommand();
      if (!command.isEmpty() && argument.isEmpty()) {
        userCom = command.pop();
        makeClassPathToCommand(parser);
        checkIfList();
        runCommand();
      }
    } else if (argument.isEmpty() && argNum != 0) {
      command.push(userCom);
    } else if (!argument.isEmpty() && argument.size() <= argNum && argNum != 0) {
      String arg = argument.pollLast();
      command.push(userCom);
      argument.add(arg);
    } else if (argNum == 0) {
      args = new LinkedList<>();
      checkIfList();
      runCommand();
    }
    trackPreviousNumArgsInt = argNum;
  }

  /*
  This checks if you have entered into a list [ ]
   */
  private void checkIfList() {
    iterationOfLoopInt++;
    loopingComplete = (inList || doTimes) && (iterationOfLoopInt < ((numOfLoops)* numberOfCommands + numberOfCommands%2));
    if(loopingComplete){
      lists.store(userCom);
      lists.storeArg(args);
    }
    if (parser.getSymbol(userCom).equals(LIST_START)) {
      logicStatementInt++;
      if((runnableIf && logicStatementInt==1)){
        checkingIfLoopInt =false;
      }
      else if((!runnableIf && logicStatementInt%2==0)){
        checkingIfLoopInt =false;
      }
      else checkingIfLoopInt = true;
    }
     if (parser.getSymbol(userCom).equals(LIST_END)) {
      checkingIfLoopInt = false;
    }
    else if (checkingIfLoopInt) {
       lists.store(userCom);
       lists.storeArg(args);
    }
  }

  /*
  Creates the complete path for the command class
   */
  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser.getSymbol(userCom);
  }

  /*
  Checks if you are not in the parsing of a list, and runs the command
   */
  public void runCommand() {
    if (checkingIfLoopInt == false || doTimes) {
      obtainCommand();
    }
  }

  /*
  Passes arguments to the command class and grabs a user function if it exists.
   */
  private void obtainCommand() {
   // System.out.println(com);
   // System.out.println(args);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
      objectCommand = constructor.newInstance((Object) args, (Object) this);
      Command commandGiven = (Command) objectCommand;
//      System.out.println(commandGiven);
      if (userfunction == null && !parser.getSymbol(userCom).equals(LIST_END) && once == false && !parser.getSymbol(userCom).equals(LIST_START)) {
        userfunction = commandGiven;
        once = true;
      }
      createCommand(commandGiven, parser);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      //error.handleCommandClassNotFound();
      //System.out.println("wrong");
      //throw new ErrorHandler("FileErrorMessage");
      ErrorBoxes box = new ErrorBoxes(new ErrorHandler("NoClass"));
    }
  }

  /*
  Calls the methods of a command to continue parsing logic
   */
  public void createCommand(Command comm, Parser parser1) {
   if(userfunction!=null) numOfLoops = userfunction.repeatCom();
    if (comm.commandValueReturn() != null) {
      if(!command.isEmpty()) {
        argument.push(comm.commandValueReturn());
        coordinateCommands();
      }
    }
     if (parser1.getSymbol(userCom).equals("MakeVariable")) {
      variablesUsed.putAll(comm.getVariablesCreated());
    }
    if(parser1.getSymbol(userCom).equals("If") ||parser1.getSymbol(userCom).equals("IfElse") ){
      runnableIf = comm.runnable();
    }
    if (!comm.equals(userfunction) && checkingIfLoopInt == false && userfunction != null && parser.getSymbol(userCom).equals(LIST_END)) {
        int i=0;
        userInputCom(numOfLoops,i, numOfLoops);
    }
  }

/*
If user input command, runs the content inside the [ ] the specified numbers of times.
 */
  public void userInputCom(int looping, int i, int start) {
    if (looping == 0) {
      inList = false;
    }   else {
    inList = true;
   // doTimes = false;
 //   System.out.println(userfunction);
  //  if (start == looping) {
      command = lists.getCommands();
   //   System.out.println(command);
   //   System.out.println(argument);
      argument = lists.getArguments();
      numberOfCommands = lists.getLength();
   //   System.out.println(numberOfCommands);
  //  }
    coordinateCommands();
    repCount(looping, var);
    repCount(i, ":repCount");
    if (loopingComplete == false) {
      iterationOfLoopInt = 0;
    }
    looping--;
    i++;

    userInputCom(looping, i, start);
     // }
    }
  }

  /*
  Makes variables for the repetitions of loops
   */
  private void repCount(int loop, String s) {
    userCom = "make";
    args.push(s);
    args.push("" + loop);
    makeClassPathToCommand(parser);
    obtainCommand();
  }



















  // THIS NEEDS TO BE A DIFFERENT CLASS @ ANEESH


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
 //   System.out.println("update" + col + " " + row + " " + angle);
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

