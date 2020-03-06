package controller;

import backEnd.CommandFactory;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class CommandSetUp {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String COMMENT = "#";
  private static final String SYNTAX = "syntax";


  private Parser parser;
  private Control control;
  private CommandExecute executor;
  private CommandFactory commandFactory;
  private StoreFunctions storeFunction;

  private Stack<String> commandList;
  private Stack<String> argumentList;
  private Map<String, String> variablesUsed;
  private LinkedList<String> argToBePassed;
  private List<ListObjects> groupsList;

  private boolean hasBeenStored = false;

  private String language;
  private String commandInput;
  private String currentCommand;
  private String commandPath;


  private String commandReturn;
  private String userCommandAttempt;

  /*
  Initializes a commandExecutor that calls the parser on the user Input commands
   */
  public CommandSetUp() {
    storeFunction = new StoreFunctions();
    variablesUsed = new TreeMap();
    commandFactory = new CommandFactory(control);
    executor = new CommandExecute();
    executor.getCommandFactory(commandFactory);
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
  Sets the controller
   */
  public void setControl(Control c) {
    control = c;
  }

  /*
  Sets the command to be parsed
   */
  public void setCommandInput(String commandInput) {
    this.commandInput = commandInput;
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

  /*
  Calls the parser to start parsing the user input and initializes variables
  */
  public void parseCommand() {
    initializeNeededVariables();
    parser.addPatterns(language);
    parser.addPatterns(SYNTAX);
    parseText();
  }

  /*
  Initializes instance variables
   */
  private void initializeNeededVariables() {
    hasBeenStored = false;
    parser = new Parser();
    argToBePassed = new LinkedList<>();
    groupsList = new ArrayList<>();
  }

  /*
  Splits up the command input
   */
  void parseText() {
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
    commandList = new Stack<String>();
    argumentList = new Stack<String>();
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
  void coordinateCommands() {
    int numberOfArguments = 0;
    if (!commandList.isEmpty()) {
      for (int i = 0; i < commandList.size(); i++) {
        currentCommand = commandList.pop();
        makeClassPathToCommand(currentCommand);
        try {
          numberOfArguments = commandFactory.getNumArgs(commandPath);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
          userCommandAttempt = currentCommand;
          coordinateCommands();
          if (argumentList.size() > 0 && commandList.size()==1) {
            argToBePassed.addAll(argumentList);
            executor.runCommand();
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
    if (argNum == 0) {
      checkCommand();
    } else {
      if (argumentList.size() >= argNum) {
        String arg = argumentList.pop();
        if (parser.getSymbol(arg).equals(VARIABLE)) {
          if (variablesUsed.containsKey(arg)) {
            argToBePassed.add(variablesUsed.get(arg));
          } else {
            argToBePassed.push(arg);
          }
        } else {
          argToBePassed.push(arg);
        }
      }
      argNum--;
      coordinateArgumentsForEachCommand(argNum);
    }
  }

  private void checkCommand() {
    executor.getArgs(argToBePassed);

    executor.getCommandInput(commandInput);
    executor.getCommandPath(commandPath);
    executor.getMyArgumentList(argumentList);
    executor.getMyCommandList(commandList);
    executor.getUserAttempt(userCommandAttempt);
    executor.getStoreFunction(storeFunction);
    executor.getStoring(hasBeenStored);

    if (!hasBeenStored) {
      executor.runCommand();
    }

    setCommandInput(executor.setNewCommandPath());
    commandList = executor.setCommandList();
    argumentList = executor.setArgumentList();
    variablesUsed.putAll(executor.setVariables());
    hasBeenStored = executor.setStoring();
    setCommandReturn(executor.setCommandReturn());

  }


}
