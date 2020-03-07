package controller;

import backEnd.commands.Command;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CommandExecute {

  private static final String REP_COUNT = ":repCount";
  private static final double VALUE = Double.MIN_VALUE;
  private CommandSet commandSet;
  private StoreFunctions storeFunction = new StoreFunctions();
  private CreatingListObjects creatingListObjects = new CreatingListObjects();
  private List<ListObjects> groupsList = new ArrayList<>();
  private int currentRepeatNumber;
  private LinkedList<String> argumentList = new LinkedList<>();
  private LinkedList<String> commandList = new LinkedList<>();
  private String commandInput;
  private String commandValueReturn;
  private int currentListObject;
  private int numberOfFunctions;
  private String userCommandAttempt;
  private boolean hasBeenStored;
  private Map<String,String> variablesUsed;

  public CommandExecute(CommandSet commandSet){
    numberOfFunctions =0;
    hasBeenStored = false;
    this.commandSet = commandSet;
  }

  public void setArgumentList(LinkedList argumentList){this.argumentList=argumentList;}
  public void setCommandList(LinkedList commandList){this.commandList=commandList;}
  public void setCommandInput(String commandInput){this.commandInput = commandInput;}
  public void setUserCommandAttempt(String userCommandAttempt){this.userCommandAttempt=userCommandAttempt;}
  public void setHasBeenStored(Boolean hasBeenStored){this.hasBeenStored=hasBeenStored;}
  public void setVariablesUsed(Map variablesUsed){this.variablesUsed=variablesUsed;}

  public Boolean getHasBeenStored(){return hasBeenStored;}
  public Map getVariablesUsed(){return variablesUsed;}
  public String getCommandValueReturn(){return commandValueReturn;}
  public StoreFunctions getStoreFunction(){return storeFunction;}


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
      groupsList= creatingListObjects.getLists();
      numberOfFunctions++;
      storeFunction.storeFunction(userCommandAttempt, groupsList.get(numberOfFunctions).getMyList());
      hasBeenStored = true;
    }
  }

  private void booleanLogic(Command comm) {
    if (comm.runnable() != VALUE) {
      creatingListObjects.findLists(commandInput,currentRepeatNumber);
      groupsList= creatingListObjects.getLists();
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
