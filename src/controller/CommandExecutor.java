package controller;

import backEnd.CommandFactory;
import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommandExecutor {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String REP_COUNT = ":repCount";
  private static final String MAKE = "backEnd.commands.MakeVariable";
  private static final String LIST_START = "[";
  private static final String LIST_END = "]";
  private static final String COMMENT = "#";
  private static final String MAKE_SYNTAX = ":";
  private static final String SYNTAX = "syntax";
  private static final double VALUE = Double.MIN_VALUE;

  private Parser parser;
  private Control control;
  private CommandFactory commandFactory;
  private StoreFunctions storeFunction;

  private Deque<String> commandList;
  private Deque<String> argumentList;
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

  /*
  Initializes a commandExecutor that calls the parser on the user Input commands
   */
  public CommandExecutor(Control myControl) {
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
  Sets the controller
   */
  public void setControl(Control c) {
    control = c;
  }

  /*
  Sets the command to be parsed
   */
  public void setCommandList(String commandList) {
    commandInput = commandList;
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
      setCommandList(map.get(word));
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
        try {
          numberOfArguments = commandFactory.getNumArgs(commandPath);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
          userCommandAttempt = currentCommand;
          coordinateCommands();
          if (argumentList.size() > 0 && commandList.size()==1) {
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
      argumentList.add(comm.commandValueReturn());
      setCommandReturn(comm.commandValueReturn());
      if (!commandList.isEmpty()) {
        coordinateCommands();
      }
    }
  }

  private void storeUserCommand(Command comm) {
    if (comm.storeCommands()) {
      findLists();
      numberOfFunctions++;
      storeFunction.storeFunction(userCommandAttempt, groupsList.get(numberOfFunctions).getMyList());
      hasBeenStored = true;
    }
  }

  private void booleanLogic(Command comm) {
    if (comm.runnable() != VALUE) {
      findLists();
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
      parseText();
      hasBeenStored = true;
    }
  }

  private void saveVariables(Command comm) {
    if (comm.getVariablesCreated() != null) {
      variablesUsed.putAll(comm.getVariablesCreated());
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
      findLists();
      for (int j = 0; j < groupsList.size(); j++) {
        if (groupsList.get(j).canBeRun()) {
          newCommandInput = groupsList.get(j).getMyList();
          setCommandList(newCommandInput);
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
          setCommandList(newCommandInput);
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

  private void findLists() {
    LinkedList<Integer> starts = new LinkedList<>();
    LinkedList<Integer> ends = new LinkedList<>();
    int startIndex = commandInput.indexOf(LIST_START);
    int numStarts = 0;
    while (startIndex >= 0) {
      starts.push(startIndex);
      numStarts++;
      startIndex = commandInput.indexOf(LIST_START, startIndex + 1);
    }
    int endIndex = commandInput.indexOf(LIST_END);
    while (endIndex >= 0) {
      ends.push(endIndex);
      endIndex = commandInput.indexOf(LIST_END, endIndex + 1);
    }
    LinkedList<ArrayList<Integer>> sets = matchingLists(starts, ends, numStarts);
    createListObjects(sets);
  }

  private LinkedList matchingLists(LinkedList<Integer> starts, LinkedList<Integer> ends,
      int numStarts) {
    LinkedList<ArrayList<Integer>> sets = new LinkedList<>();
    ArrayList<Integer> two = new ArrayList<>();
    two = linkListPairs(starts, ends, numStarts, sets, two);
    resetInput(sets, two);
    return sets;
  }

  private ArrayList<Integer> linkListPairs(LinkedList<Integer> starts, LinkedList<Integer> ends,
      int numStarts, LinkedList<ArrayList<Integer>> sets, ArrayList<Integer> two) {
      while (numStarts > 0) {
      int first = starts.pollLast();
      int last = ends.pollLast();
      for (int whichEnd : starts) {
        if (whichEnd < last) {
          int store = last;
          last = ends.pollLast();
          ends.push(store);
        }
      }
      numStarts--;
      two.add(first+ 1);
      two.add(last - 1);
      sets.add(two);
      two = new ArrayList<>();
    }
    return two;
  }

  private void resetInput(LinkedList<ArrayList<Integer>> sets, ArrayList<Integer> two) {
    if (currentRepeatNumber == 1) {
      if (sets.size() != 1) {
        two.add(0);
        two.add(commandInput.length());
        sets.add(two);
      }
    }
  }

  private void createListObjects(LinkedList<ArrayList<Integer>> sets) {
    ArrayList<Integer> set;
    while (sets.size() != 0) {
      set = sets.pop();
      int first = set.get(0);
      int end = set.get(1);
      groupsList.add(new ListObjects(commandInput.substring(first, end)));
    }
  }

}
