package controller;

import backEnd.CommandFactory;
import backEnd.ErrorHandler;
import backEnd.commands.Command;
import backEnd.commands.VariableControlUser.MakeVariable;
import frontEnd.ErrorBoxes;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommandSetAndExecute {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String COMMAND = "Command";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String REP_COUNT = ":repCount";
  private static final String MAKE = "backEnd.commands.MakeVariable";
  private static final String COMMENT = "#";
  private static final String SYNTAX = "syntax";
  private static final double VALUE = Double.MIN_VALUE;

  private Parser parser;
  private Control control;
  private CommandFactory commandFactory;
  private StoreFunctions storeFunction;
  private CreatingListObjects creatingListObjects;

  private LinkedList<String> commandList;
  private LinkedList<String> argumentList;
  private Map<String, String> variablesUsed;
  private LinkedList<String> argToBePassed;
  private List<ListObjects> groupsList;

  private boolean hasBeenStored = false;

  private String language;
  private String commandInput;
  private String newCommandInput;
  private String currentCommand;
  private String commandPath;

  private int currentRepeatNumber;
  private int currentListObject;
  private int numberOfFunctions;
  private String commandReturn;
  private String userCommandAttempt;
  private String finalReturnValue;

  /*
  Initializes a commandExecutor that calls the parser on the user Input commands
   */
  public CommandSetAndExecute(Control myControl) {
    storeFunction = new StoreFunctions();
    control = myControl;
    variablesUsed = new TreeMap();
    commandFactory = new CommandFactory(control);
  }

  /*
  Returns the user-defined variables to the Controller
  @return variablesUsed
   */
  public Map getVariables() {
    return variablesUsed;
  }

  /*
  Returns the user-defined functions to the Controller
  @return lists.getFunction()
   */
  public Map getFunctions() {
    return storeFunction.getFunction();
  }
  /*
  Sets the command to be parsed
   */
  public void setCommandInput(String commandList) {
    commandInput = commandList;
    finalReturnValue = null;
  }

  /*
  Sets the language the command is in
   */
  public void setLanguage(String lang) {
    language = lang;
  }

  private void setCommandReturn(String commandValueReturn) {
    commandReturn = commandValueReturn;
  }

  /*
  Returns the command output value
  @return commandReturn
   */
  public String getCommandReturn() {
    return commandReturn;
  }

  public String getFinalReturnValue(){
    return finalReturnValue;
  }

  public void setFinalReturnValue(String x){
    finalReturnValue = x;
  }

  /*
  Calls the parser to start parsing the user input and initializes variables
  */
  public void parseCommand() {
    initializeNeededVariables();
    creatingListObjects = new CreatingListObjects();
    parser.addPatterns(language);
    parser.addPatterns(SYNTAX);
    parseText();
  }

  /*
  Initializes instance variables
   */
  private void initializeNeededVariables() {
    hasBeenStored = false;
    numberOfFunctions = 0;
    parser = new Parser();
    argToBePassed = new LinkedList<>();
    groupsList = new ArrayList<>();
  }

  /*
  Splits up the command input
   */
  private void parseText() {
    for (String line : commandInput.split(NEWLINE)) {
      if (!line.contains(COMMENT) && !line.isEmpty()) {
        organizeInLists(line);
      }
      if (!commandList.isEmpty()) {
        System.out.println(commandList);
        System.out.println(argumentList);
        coordinateCommands();
      }
    }
  }

  /*
  Splits lines into words and categorizes them into two lists
   */
  private void organizeInLists(String line) {
    commandList = new LinkedList<>();
    argumentList = new LinkedList<>();
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
        if (!parser.getSymbol(word).equals(ARGUMENT) && !parser.getSymbol(word).equals(VARIABLE)) {
          checkingTypeOfCommand(word);
        } else {
          argumentList.push(word);
        }
      }
    }
  }

  private void checkingTypeOfCommand(String word) {
    Map<String, String> map = storeFunction.getFunction();
    if (map.keySet().contains(word)) {
      setCommandInput(map.get(word));
      hasBeenStored = false;
      parseText();
    } else {
      commandList.push(word);
    }
  }

  /*
  Getting the number of arguments for each command
   */
  private void coordinateCommands() {
    int numberOfArguments = 0;
    if (!commandList.isEmpty()) {
      for (int i = 0; i < commandList.size(); i++) {
        currentCommand = commandList.pop();
        makeClassPathToCommand(currentCommand);
        System.out.println("this is thte command "+commandPath);
        try {
          numberOfArguments = commandFactory.getNumArgs(commandPath);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
          userCommandAttempt = currentCommand;
          if(commandPath.equals(CLASS_PATH+COMMAND)) coordinateCommands();
          if (argumentList.size() > 0) {
            argToBePassed.addAll(argumentList);
            System.out.println(argToBePassed);
            runCommand();
          }
        }
        argToBePassed.clear();
        coordinateArgumentsForEachCommand(numberOfArguments);
      }
    }
  }

  /*
  Creates the complete path for the command class
   */
  private void makeClassPathToCommand(String comm) {
    commandPath = CLASS_PATH + parser.getSymbol(comm);
  }

  /*
  Coordinating the command to the number of arguments it needs and pushing it to be run
   */
  private void coordinateArgumentsForEachCommand(int argNum) {
    if(argNum == -1){
      argToBePassed.addAll(argumentList);
      runCommand();
    }
    else if (argNum == 0) {
      checkCommand();
    } else {
      if (argumentList.size() >= argNum) {
        String arg = argumentList.pop();
        if (parser.getSymbol(arg).equals(VARIABLE)) {
          if (variablesUsed.containsKey(arg) && !commandPath.equals(MAKE)) {
            argToBePassed.add(variablesUsed.get(arg));
          }
          else {
            argToBePassed.add(arg);
          }
        } else {
          argToBePassed.add(arg);
        }
      }
      argNum--;
      coordinateArgumentsForEachCommand(argNum);
    }
  }

  private void checkCommand() {

    if (!hasBeenStored) {
      runCommand();
    }
  }

  /*
  Passes arguments to the command class and grabs a user function if it exists.
   */
  private void runCommand() {
    try {
      Command commandGiven = commandFactory.generateCommand(commandPath, argToBePassed);
      createCommand(commandGiven);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidCommand"));
    }
  }

  private void createCommand(Command comm) {
    commandReturnValue(comm);
    saveVariables(comm);
    storeUserCommand(comm);
    booleanLogic(comm);
    repeatTimes(comm);
  }

  private void commandReturnValue(Command comm) {
    if (comm.commandValueReturn() != null) {
      argumentList.push(comm.commandValueReturn());
      finalReturnValue = comm.commandValueReturn();
      setCommandReturn(comm.commandValueReturn());
      if (!commandList.isEmpty()) {
        coordinateCommands();
      }
    }
  }

  private void storeUserCommand(Command comm) {
    if (comm.storeCommands()) {
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList= creatingListObjects.getLists();
      numberOfFunctions++;
      storeFunction.storeFunction(userCommandAttempt, groupsList.get(numberOfFunctions).getMyList());
      System.out.println("Store function "+storeFunction);
      hasBeenStored = true;
    }
  }

  private void booleanLogic(Command comm) {
    if (comm.runnable() != VALUE) {
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList= creatingListObjects.getLists();
      if ((comm.runnable() != 0)) {
        commandInput = groupsList.get(0).getMyList();
      } else {
        if (groupsList.size() > 1) {
          commandInput = groupsList.get(1).getMyList();
        }
        else {
          hasBeenStored = true;
        }
      }
      System.out.println(commandInput);
      parseText();
      hasBeenStored = true;
    }
  }

  private void saveVariables(Command comm) {
    if (comm.getVariablesCreated() != null) {
      variablesUsed.putAll(comm.getVariablesCreated());
      System.out.println(variablesUsed);
      if (!commandList.isEmpty()) {
        coordinateCommands();
      }
    }
  }

  private void repeatTimes(Command comm) {
    if (comm.repeatCom() != 0) {
      double loop = comm.repeatCom();
      currentRepeatNumber++;
      if (currentRepeatNumber == 1) {
        repCount(loop, REP_COUNT);
      }
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList = creatingListObjects.getLists();
      for (int j = 0; j < groupsList.size(); j++) {
        if (groupsList.get(j).canBeRun()) {
          newCommandInput = groupsList.get(j).getMyList();
          setCommandInput(newCommandInput);
          currentListObject = j;
        }
      }
      recurseLoop(loop);
    } else if (!commandList.isEmpty()) {
      coordinateCommands();
    }
  }

  private void recurseLoop(double loop) {
    if (loop == 1) {
      groupsList.get(currentListObject).cannotBeRun(true);
      for (int x = 0; x < groupsList.size(); x++) {
        if (groupsList.get(x).canBeRun()) {
          newCommandInput = groupsList.get(x).getMyList();
          setCommandInput(newCommandInput);
          break;
        }
      }
    }
    if (loop > 1) {
      parseText();
      loop--;
      recurseLoop(loop);
    }
  }

  /*
  Makes variables for the repetitions of loops
   */
  private void repCount(double loop, String s) {
    argToBePassed.clear();
    commandPath = MAKE;
    argToBePassed.push(Double.toString(loop));
    argToBePassed.push(s);
    runCommand();
  }
}
