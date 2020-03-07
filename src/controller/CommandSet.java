package controller;

import backEnd.CommandFactory;
import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class CommandSet {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String COMMAND = "Command";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String MAKE = "backEnd.commands.MakeVariable";
  private static final String COMMENT = "#";
  private static final String SYNTAX = "syntax";

  private Parser parser;
  private Control control;
  private CommandExecute commandExecute;
  private CommandFactory commandFactory;
  private StoreFunctions storeFunction;

  private LinkedList<String> commandList;
  private LinkedList<String> argumentList;
  private Map<String, String> variablesUsed;
  private LinkedList<String> argToBePassed;

  private boolean hasBeenStored = false;

  private String language;
  private String commandInput;
  private String currentCommand;
  private String commandPath;


  private String commandReturn;
  private String userCommandAttempt;
  private String finalReturnValue;

  /*
  Initializes a commandExecutor that calls the parser on the user Input commands
   */
  public CommandSet(Control myControl) {
    control = myControl;
    variablesUsed = new TreeMap();
    commandFactory = new CommandFactory(control);
    storeFunction = new StoreFunctions();
    commandExecute = new CommandExecute(this);
    storeFunction = commandExecute.getStoreFunction();
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

  public String getFinalReturnValue() {
    return finalReturnValue;
  }

  public void setFinalReturnValue(String x) {
    finalReturnValue = x;
  }

  /*
  Calls the parser to start parsing the user input and initializes variables
  */
  public void parseCommand() {
    commandExecute.setListObj(new CreatingListObjects());
    initializeNeededVariables();
    setCommandInput(commandInput);
    parser.addPatterns(language);
    parser.addPatterns(SYNTAX);
    parseText();
  }

  /*
  Initializes instance variables
   */
  private void initializeNeededVariables() {
    parser = new Parser();
    hasBeenStored = false;
    argToBePassed = new LinkedList<>();
  }

  /*
  Splits up the command input
   */
  public void parseText() {
    commandList = new LinkedList<>();
    argumentList = new LinkedList<>();
    for (String line : commandInput.split(NEWLINE)) {
      if (!line.contains(COMMENT) && !line.isEmpty()) {
        organizeInLists(line);
      }
      if (!commandList.isEmpty()) {
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
    Map<String, String> map = new TreeMap<>();
   if(!storeFunction.getFunction().equals(null)) map = storeFunction.getFunction();
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
  public void coordinateCommands() {
    int numberOfArguments = 0;
    if (!commandList.isEmpty()) {
      for (int i = 0; i < commandList.size(); i++) {
        currentCommand = commandList.pop();
        makeClassPathToCommand(currentCommand);
        System.out.println(currentCommand);
        try {
          numberOfArguments = commandFactory.getNumArgs(commandPath);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
          userCommandAttempt = currentCommand;
          if (commandPath.equals(CLASS_PATH + COMMAND))
            coordinateCommands();
          if (argumentList.size() > 0) {
            argToBePassed.addAll(argumentList);
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
      System.out.println(argToBePassed);
      checkCommand();
    } else {
      if (argumentList.size() >= argNum) {
        String arg = argumentList.pop();
        if (parser.getSymbol(arg).equals(VARIABLE)) {
          if (variablesUsed.containsKey(arg) && !commandPath.equals(MAKE)) {
            argToBePassed.add(variablesUsed.get(arg));
          } else {
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
    commandExecute.setArgumentList(argumentList);
    commandExecute.setCommandInput(commandInput);
    commandExecute.setCommandList(commandList);
    commandExecute.setHasBeenStored(hasBeenStored);
    commandExecute.setUserCommandAttempt(userCommandAttempt);
    commandExecute.setVariablesUsed(variablesUsed);
    try {
      Command commandGiven = commandFactory.generateCommand(commandPath, argToBePassed);
      commandExecute.createCommand(commandGiven);
      update();
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidCommand"));
    }
  }

  private void update() {
    hasBeenStored = commandExecute.getHasBeenStored();
    variablesUsed = commandExecute.getVariablesUsed();
    argumentList = commandExecute.getArgumentList();
    setFinalReturnValue(commandExecute.getCommandValueReturn());
  }
  /*
Makes variables for the repetitions of loops
 */
  public void repCount(double loop, String s) {
    argToBePassed.clear();
    commandPath = MAKE;
    argToBePassed.push(Double.toString(loop));
    argToBePassed.push(s);
    runCommand();
  }

}
