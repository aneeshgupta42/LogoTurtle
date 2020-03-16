package controller;

import backEnd.commands.Command;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
@author Libba Lawrence

CommandExecute executes the command after the CommandSet has been run. It takes in a command and checks the return values for its methods.
CommandExecute can be called multiple times (for each command) in the command input.
 */
public class CommandExecute {

  private static final String REP_COUNT = ":repCount";
  private static final double VALUE = Double.MIN_VALUE;
  private CommandSet commandSet;
  private StoreFunctions storeFunction;
  private CreatingListObjects creatingListObjects;
  private List<ListObjects> groupsList;
  private int currentRepeatNumber;
  private LinkedList<String> argumentList;
  private LinkedList<String> commandList;
  private String commandInput;
  private String commandValueReturn;
  private int currentListObject;
  private int numberOfFunctions;
  private String userCommandAttempt;
  private boolean hasBeenStored;
  private Map<String,String> variablesUsed;


  /*
  CommandExecute method initializes the need variables.
  @param CommandSet
   */
  public CommandExecute(CommandSet commandSet){
    commandList = new LinkedList<>();
    argumentList = new LinkedList<>();
    creatingListObjects = new CreatingListObjects();
    groupsList = new ArrayList<>();
    numberOfFunctions =0;
    hasBeenStored = false;
    storeFunction = new StoreFunctions();
    this.commandSet = commandSet;
  }

  /*
  Sets the arguments for each command
  @param LinkedList
   */
  public void setArgumentList(LinkedList argumentList){this.argumentList=argumentList;}
  /*
  Sets the commands for each command input string
  @param LinkedList
   */
  public void setCommandList(LinkedList commandList){this.commandList=commandList;}
  /*
  Sets the command input the user passes in
  @param String
   */
  public void setCommandInput(String commandInput){this.commandInput = commandInput;}
  /*
  Sets the last command the user tried to input, that wasn't recognized
  @param String
   */
  public void setUserCommandAttempt(String userCommandAttempt){this.userCommandAttempt=userCommandAttempt;}
  /*
  Sets the boolean to check if the commands have been stored
  @param Boolean
   */
  public void setHasBeenStored(Boolean hasBeenStored){this.hasBeenStored=hasBeenStored;}
  /*
  Sets the map of variables used from previous commands in the input
  @param Map
   */
  public void setVariablesUsed(Map variablesUsed){this.variablesUsed=variablesUsed;}
  /*
  Sets the list objects (strings between the [ ] )
  @param creatingListObjects
   */
  public void setListObj(CreatingListObjects creatingListObjects) { this.creatingListObjects = creatingListObjects; }
/*
Returns the boolean that tells if the command has been stored
@return hasBeenStored
 */
  public Boolean getHasBeenStored(){return hasBeenStored;}
  /*
  Returns the map of variables created
  @return variablesUsed
   */
  public Map getVariablesUsed(){return variablesUsed;}
  /*
  Returns the return value of the command
  @return commandValueReturn
   */
  public String getCommandValueReturn(){return commandValueReturn;}
  /*
  Returns the storeFunction ~ the functions that might have been stored by this command
  @return storeFunction
   */
  public StoreFunctions getStoreFunction(){return storeFunction;}
  /*
  Returns the linkedList of arguments, because commands can add to those arguments of previous commands.
  @return argumentList
   */
  public LinkedList getArgumentList(){return argumentList;}

/*
Gets the commands, and runs the methods on that command to determine its output
@param comm
 */
  public void createCommand(Command comm) {
    commandReturnValue(comm);
    saveVariables(comm);
    storeUserCommand(comm);
    booleanLogic(comm);
    repeatTimes(comm);
  }

  private void commandReturnValue(Command comm) {
    if (comm.commandValueReturn() != null) {
      argumentList.push(comm.commandValueReturn());
      commandValueReturn = comm.commandValueReturn();
      if (!commandList.isEmpty()) {
        commandSet.coordinateCommands();
      }
    }
  }

  private void storeUserCommand(Command comm) {
    if (comm.storeCommands()) {
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList = creatingListObjects.getLists();
      numberOfFunctions++;
      storeFunction.storeFunction(userCommandAttempt, groupsList.get(numberOfFunctions).getMyList());
      hasBeenStored = true;
    }
  }

  private void booleanLogic(Command comm) {
    if (comm.runnable() != VALUE) {
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList = creatingListObjects.getLists();
      if ((comm.runnable() != 0)) {
        commandSet.setCommandInput(groupsList.get(0).getMyList());
      } else {
        if (groupsList.size() > 1) {
          commandSet.setCommandInput(groupsList.get(1).getMyList());
        }
        else {
          hasBeenStored = true;
        }
      }
      commandSet.parseText();
      hasBeenStored = true;
    }
  }

  private void saveVariables(Command comm) {
    if (comm.getVariablesCreated() != null) {
      variablesUsed.putAll(comm.getVariablesCreated());
      if (!commandList.isEmpty()) {
        commandSet.coordinateCommands();
      }
    }
  }

  private void repeatTimes(Command comm) {
    if (comm.repeatCom() != 0) {
      double loop = comm.repeatCom();
      currentRepeatNumber++;
      if (currentRepeatNumber == 1) {
        commandSet.repCount(loop, REP_COUNT);
      }
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList = creatingListObjects.getLists();
      for (int j = 0; j < groupsList.size(); j++) {
        if (groupsList.get(j).canBeRun()) {
          commandSet.setCommandInput(groupsList.get(j).getMyList());
          currentListObject = j;
        }
      }
      recurseLoop(loop);
    } else if (!commandList.isEmpty()) {
      commandSet.coordinateCommands();
    }
  }

  private void recurseLoop(double loop) {
    if (loop == 1) {
      groupsList.get(currentListObject).cannotBeRun(true);
      for (int x = 0; x < groupsList.size(); x++) {
        if (groupsList.get(x).canBeRun()) {
          commandSet.setCommandInput(groupsList.get(x).getMyList());
          break;
        }
      }
    }
    if (loop > 1) {
      commandSet.parseText();
      loop--;
      recurseLoop(loop);
    }
  }
}
