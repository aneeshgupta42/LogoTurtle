package Controller;

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

public class CommandExecution {

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
  private static final String SYNTAX = "syntax";
  private static final int output = -500;

  private Parser parser;
  private Control control;
  private List<ListGroups> groupsList;
  private String language;
  private CommandFactory commandFactory;
  private Deque<String> command;
  private Deque<String> argument;
  private String commandPath;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed;
  private LinkedList<String> args;
  private StoreLists lists;
  private boolean hasBeenStored = false;
  private String section;
  private LinkedList<Integer> starts;
  private LinkedList<Integer> ends;
  private LinkedList<ArrayList<Integer>> sets;
  private int numstarts;
  private int first;
  private int end;
  private int total;
  private int index;
  private int whichUserFunc;
  private String commandReturn;
  private String userCommandAttempt;

  /*
  Initializes a commandExecutor that calls the parser on the user Input commands
   */
  public CommandExecution(Control myControl)
  {
    lists = new StoreLists();
    control = myControl;
    variablesUsed = new TreeMap();
    commandFactory = new CommandFactory(control);
  }

  /*
  Returns the user-defined variables to the Controller
  @return variablesUsed
   */
  public Map getVariables(){return variablesUsed;}

  /*
  Returns the user-defined functions to the Controller
  @return lists.getFunction()
   */
  public Map getFunctions(){return lists.getFunction();}

  /*
  Sets the controller
   */
  public void setControl(Control c){control = c;}

  /*
  Sets the command to be parsed
   */
  public void setCommand(String command) { input = command; }

  /*
  Sets the language the command is in
   */
  public void setLanguage(String lang) { language = lang; }

  private void setCommandReturn(String commandValueReturn) {
    commandReturn = commandValueReturn;
  }

  /*
  Returns the command output value
  @return commandReturn
   */
  public String getCommandReturn(){return commandReturn;}

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
    whichUserFunc =0;
    parser = new Parser();
    args = new LinkedList<>();
    groupsList = new ArrayList<>();
  }

  /*
  Splits up the command input
   */
  private void parseText() {
    for (String line : input.split(NEWLINE)) {
      if(!line.contains(COMMENT) && !line.isEmpty()){
        organizeInLists(line);
      }
      coordinateCommands();
    }
  }

  /*
  Splits lines into words and categorizes them into two lists
   */
  private void organizeInLists(String line) {
    command = new LinkedList<>();
    argument = new LinkedList<>();
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
        if (!parser.getSymbol(word).equals(ARGUMENT) && !parser.getSymbol(word).equals(VARIABLE)) {
          checkingTypeOfCommand(word);
        }
        else{
          argument.push(word);
        }
      }
    }
  }

  private void checkingTypeOfCommand(String word) {
    Map<String, String> map = lists.getFunction();
    if (map.keySet().contains(word) && input!=map.get(word)) {
      setCommand(map.get(word));
      parseText();
    }
    else
      command.push(word);
  }

  /*
  Getting the number of arguments for each command
   */
  private void coordinateCommands() {
    int argNum = 0;
    if(!command.isEmpty()) {
      for (int i=0;i<command.size();i++) {
        userCom = command.pop();
        makeClassPathToCommand(userCom);
        try {
          argNum = commandFactory.getNumArgs(commandPath);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
          userCommandAttempt = userCom;
          coordinateCommands();
        }
        args.clear();
        coordinateArgumentsForEachCommand(argNum);
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
    if (argNum == 0) { checkCommand(); }
    else {
      if (argument.size() >= argNum) {
        String arg = argument.pop();
        if(parser.getSymbol(arg).equals(VARIABLE)) {
          if (variablesUsed.containsKey(arg)) {
            args.add(variablesUsed.get(arg));
          }
          else args.push(arg);
        }
        else args.push(arg);
      }
      argNum--;
      coordinateArgumentsForEachCommand(argNum);
    }
  }

  private void checkCommand() {
      if (!hasBeenStored) runCommand();
    }


  /*
  Passes arguments to the command class and grabs a user function if it exists.
   */
  private void runCommand() {
    try {
      Command commandGiven = commandFactory.generateCommand(commandPath, args);
      createCommand(commandGiven);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidCommand"));
    }
  }


  private void createCommand(Command comm) {
    if(comm.commandValueReturn()!=null){
      argument.add(comm.commandValueReturn());
      setCommandReturn(comm.commandValueReturn());
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }

    if(comm.storeCommands()) {
      findLists();
      whichUserFunc ++;
      lists.storeFunction(userCommandAttempt,groupsList.get(whichUserFunc).getMyList());
    }
    saveVariables(comm);
    booleanLogic(comm);
    repeatTimes(comm);
  }

  private void booleanLogic(Command comm) {
    if(comm.runnable()!=output) {
      findLists();
      if ((comm.runnable()!=0)) {
        input= groupsList.get(0).getMyList();
      }
      else{
        if(groupsList.size()>1) input = groupsList.get(1).getMyList();
        else hasBeenStored=true;
      }
      parseText();
      hasBeenStored=true;
    }
  }

  private void saveVariables(Command comm) {
    if(comm.getVariablesCreated()!=null) {
      variablesUsed.putAll(comm.getVariablesCreated());
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }
  }

  private void repeatTimes(Command comm) {
    if(comm.repeatCom()!=0) {
      double loop = comm.repeatCom();
      total++;
      if(total==1)repCount(loop, REP_COUNT);
      findLists();
      for(int j=0;j<groupsList.size();j++){
        if(groupsList.get(j).canBeRun()){
          section = groupsList.get(j).getMyList();
          setCommand(section);
          index =j;
        }
      }
      recurseLoop(loop);
    }
    else if(!command.isEmpty()){
      coordinateCommands();
    }
  }

  private void recurseLoop(double loop) {
    if(loop==1){
      groupsList.get(index).cannotBeRun(true);
      for(int x=0;x<groupsList.size();x++) {
        if(groupsList.get(x).canBeRun()) {
          section = groupsList.get(x).getMyList();
          setCommand(section);
          break;
        }
      }
    }
    if(loop>1){
      parseText();
      loop--;
      recurseLoop(loop);
    }
  }

  /*
  Makes variables for the repetitions of loops
   */
  private void repCount(double loop, String s) {
    args.clear();
    commandPath = MAKE;
    args.push(Double.toString(loop));
    args.push(s);
    runCommand();
  }

  private void findLists() {
    starts = new LinkedList<>();
    ends = new LinkedList<>();
    int index = input.indexOf(LIST_START);
    while (index >= 0) {
      starts.push(index);
      numstarts++;
      index = input.indexOf(LIST_START, index + 1);
    }
    int indextwo = input.indexOf(LIST_END);
    while (indextwo >= 0) {
      ends.push(indextwo);
      indextwo = input.indexOf(LIST_END, indextwo + 1);
    }
    matchingLists();
    organizeListPairs();
  }

  private void organizeListPairs() {
    ArrayList<Integer> set;
    while (sets.size() != 0) {
       set = sets.pop();
       first = set.get(0);
       end = set.get(1);
       groupsList.add(new ListGroups(input.substring(first, end)));
    }
  }

  private void matchingLists() {
    sets = new LinkedList<>();
    ArrayList<Integer> two = new ArrayList<>();
    while(numstarts>0) {
      int first = starts.pollLast();
      int last = ends.pollLast();
      for (int whichEnd : starts) {
        if (whichEnd < last) {
          int store = last;
          last = ends.pollLast();
          ends.push(store);
        }
      }
      numstarts--;
      two.add(first+1);
      two.add(last-1);
      sets.add(two);
      two = new ArrayList<>();
    }
    if(total ==1){
      if(sets.size()!=1){
        two.add(0);
        two.add(input.length());
        sets.add(two);
      }
    }
  }
}
