package controller;

import backEnd.CommandFactory;
import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CommandExecute {

  private static final String REP_COUNT = ":repCount";
  private static final String MAKE = "backEnd.commands.MakeVariable";
  private static final String LIST_START = "[";
  private static final String LIST_END = "]";
  private static final double VALUE = Double.MIN_VALUE;

  private CommandFactory commandFactory;
  private Control control;
  private StoreFunctions storeFunction;
  private CommandSetUp commandSetUp;
  private List<ListObjects> groupsList;

  private LinkedList<String> argToBePassed;
  private Stack<String> argumentList;
  private Stack<String> commandList;

  private String commandInput;
  private String commandPath;
  private String newCommandInput;
  private String userCommandAttempt;
  private String returnValue;
  private int currentRepeatNumber;
  private int currentListObject;
  private int numberOfFunctions = 0;

  public CommandExecute(){
    commandSetUp = new CommandSetUp();
  }

  public void getUserAttempt(String attempt){ userCommandAttempt = attempt;}
  public void getStoreFunction(StoreFunctions store){storeFunction=store;}
  public void getCommandFactory(CommandFactory factory){commandFactory=factory;}
  public void getCommandInput(String command){commandInput=command;}
  public void getCommandPath(String command){commandPath =command;}
  public void getArgs(LinkedList args){argToBePassed = args;}
  public void getMyArgumentList(Stack argument){argumentList = argument;}
  public Stack setArgumentList(){return argumentList;}
  public void getMyCommandList(Stack argument){commandList = argument;}
  public Stack setCommandList(){return commandList;}
  public String setNewCommandPath(){return newCommandInput;}
  public String setCommandReturn(){ return returnValue;}

  /*
 Passes arguments to the command class and grabs a user function if it exists.
  */
  public void runCommand() {
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
      returnValue = comm.commandValueReturn();
      if (!commandList.isEmpty()) {
        commandSetUp.coordinateCommands();
      }
    }
  }

  private void storeUserCommand(Command comm) {
    if (comm.storeCommands()) {
      findLists();
      numberOfFunctions++;
      storeFunction.storeFunction(userCommandAttempt, groupsList.get(numberOfFunctions).getMyList());
     // hasBeenStored = true;
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
       //   hasBeenStored = true;
        }
      }
      commandSetUp.parseText();
     // hasBeenStored = true;
    }
  }

  private void saveVariables(Command comm) {
    if (comm.getVariablesCreated() != null) {
      //variablesUsed.putAll(comm.getVariablesCreated());
      if (!commandList.isEmpty()) {
        commandSetUp.coordinateCommands();
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
          currentListObject = j;
        }
      }
      recurseLoop(loop);
    } else if (!commandList.isEmpty()) {
      commandSetUp.coordinateCommands();
    }
  }

  private void recurseLoop(double loop) {
    if (loop == 1) {
      groupsList.get(currentListObject).cannotBeRun(true);
      for (int x = 0; x < groupsList.size(); x++) {
        if (groupsList.get(x).canBeRun()) {
          newCommandInput = groupsList.get(x).getMyList();
          break;
        }
      }
    }
    if (loop > 1) {
      commandSetUp.parseText();
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

  private LinkedList matchingLists(LinkedList<Integer> starts, LinkedList<Integer> ends, int numStarts) {
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
      two.add(first + 1);
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
