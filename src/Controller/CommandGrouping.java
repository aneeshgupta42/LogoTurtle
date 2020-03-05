package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CommandGrouping {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String repCount = ":repCount";
  private static final String make = "make";
  private static final String listStart = "[";
  private static final String listEnd = "]";

  private Parser parser;
  private Control control;
  private List<ListGroups> groupsList;
  private String language;
  private Deque<String> command;
  private Deque<String> argument;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed;
  private LinkedList<String> args;
  private boolean logicStatement;
  private StoreLists lists;
  private boolean hasBeenStored = false;
  private String section ="";
  private LinkedList<Integer> starts;
  private LinkedList<Integer> ends;
  private LinkedList<ArrayList<Integer>> sets;
  private int numstarts;
  private int first;
  private int end;
  private int total;
  private int index;
  private String variable;

  public CommandGrouping(){
    lists = new StoreLists();
  }

  public void setControl(Control c){control = c;}
  public void setCommand(String command) {
    input = command;
  }
  public void setLanguage(String lang) {
    language = lang;
  }

  /*
 Calls the parser to start parsing the user input
  */
  public void parseCommand() {
    parser = new Parser();
    variablesUsed = new TreeMap();
    command = new LinkedList<>();
    argument = new LinkedList<>();
    args = new LinkedList<>();
    hasBeenStored = false;
    groupsList = new ArrayList<ListGroups>();
    setCommand(input);
    setLanguage(language);
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parseText();
  }
  /*
    Splits text into lines
   */
  private void parseText() {
    logicStatement = true;
    for (String line : input.split(NEWLINE)) {
      if(!line.contains("#") && !line.isEmpty()){
        organizeInStacks(line);
      }
      System.out.println("Command "+command);
      System.out.println("Argument "+argument);
      coordinateCommands();
    }
  }

  /*
  Splits lines into words and categorizes them into two stacks
   */
  private void organizeInStacks(String line) {
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
  //  System.out.println(word);
    Map<String, String> map = lists.getFunction();
    if (map.keySet().contains(word) && input!=map.get(word)) {
      input = map.get(word);
      parseText();
    }
    else
      command.push(word);
  }


  /*
  Getting the number of arguments for each command
   */
  public void coordinateCommands() {
    int argNum = 0;
    if(!command.isEmpty()) {
      for (int i=0;i<command.size();i++) {
        userCom = command.pop();
        makeClassPathToCommand(userCom);
        try {
          Class cls = Class.forName(com);
          Object objectCommand;
          Constructor constructor = cls.getConstructor();
          objectCommand = constructor.newInstance();
          Command commandGiven = (Command) objectCommand;
          argNum = commandGiven.getNumberOfArgs();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
          coordinateCommands();
        }
        args.clear();
        checkIfCommandCanRun(argNum);
      }
    }
  }

  /*
  Creates the complete path for the command class
   */
  private void makeClassPathToCommand(String comm) {
    com = CLASS_PATH + parser.getSymbol(comm);
  }


  /*
  Coordinating the command to the number of arguments it needs and pushing it to be run
   */
  private void checkIfCommandCanRun(int argNum) {
    if (argNum == 0) {
  //    System.out.println(userCom);
      runCommand();
    } else {
      if (argument.size() >= argNum) {
        String arg = argument.pop();
        if(parser.getSymbol(arg).equals(VARIABLE)) {
          variable = arg;
          if (variablesUsed.containsKey(arg)) {
            args.add(variablesUsed.get(arg));
          }
          else args.push(arg);
        }
        else args.push(arg);
      }
      argNum--;
    //  System.out.println("Num "+args +" "+ userCom);
      checkIfCommandCanRun(argNum);
    }
  }

  /*
  Checks if you are not in the parsing of a list, and runs the command
   */
  public void runCommand() {
    System.out.println("GotHere " + userCom +"  " + args);
      if (hasBeenStored == false) {
        if(!logicStatement) {
          findLists();
          input = input.substring(end);
          parseText();
        }
        obtainCommand();
   //     System.out.println("Variable storing " + hasBeenStored);
    }
  }

  /*
  Passes arguments to the command class and grabs a user function if it exists.
   */
  private void obtainCommand() {
  //  System.out.println(com);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
      objectCommand = constructor.newInstance((Object) args, (Object) control);
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidCommand"));
    }
  }

  /*
  Calls the methods of a command to continue parsing logic
   */
  public void createCommand(Command comm) {
    if(comm.commandValueReturn()!=null){
      argument.add(comm.commandValueReturn());
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }
    if(comm.storeCommands()) {
      lists.storeFunction(input);
      hasBeenStored = true;
      parseText();
      }
    saveVariables(comm);
    booleanLogic(comm);
    repeatTimes(comm);
  }

  private void booleanLogic(Command comm) {
    if(comm.runnable()!=-100) {  //fix this
      logicStatement = comm.runnable()!=0;
//      System.out.println("Can the logic run " + logicStatement);
      if (!command.isEmpty()) {
        coordinateCommands();
      }
    }
  }

  private void saveVariables(Command comm) {
    if(comm.getVariablesCreated()!=null) {
      variablesUsed.putAll(comm.getVariablesCreated());
 //     System.out.println("Variables "+ variablesUsed);
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }
  }

  private void repeatTimes(Command comm) {
    if(comm.repeatCom()!=0) {
      total++;
      findLists();
      int loop = comm.repeatCom();
      if (variable != null) {
        repCount(loop, variable);
      } else repCount(loop, repCount);
      for(int j=0;j<groupsList.size();j++){
        if(groupsList.get(j).canBeRun()){
          section = groupsList.get(j).getMyList(); //get the value inside brackets
          setCommand(section);
          index =j;
        }
      }
      recurseLoop(loop);
    }
    else{
      coordinateCommands();
    }
  }


  private void recurseLoop(int loop) {
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
  private void repCount(int loop, String s) {
    args.clear();
    userCom = make;
    args.push(s);
    args.push(loop+"");
    makeClassPathToCommand(userCom);
    obtainCommand();
  }


  private void findLists() {
    starts = new LinkedList<>();
    ends = new LinkedList<>();
    int index = input.indexOf(listStart);
    while (index >= 0) {
      starts.push(index);
      numstarts++;
      index = input.indexOf(listStart, index + 1);
    }
    int indextwo = input.indexOf(listEnd);
    while (indextwo >= 0) {
      ends.push(indextwo);
      indextwo = input.indexOf(listEnd, indextwo + 1);
    }
    matchingLists();
    organizeListPairs();
  }

  private void organizeListPairs() {
    ArrayList<Integer> set = new ArrayList<Integer>();
    while (sets.size() != 0) {
      set = sets.pop();
        first = set.get(0);
        end = set.get(1);
       groupsList.add(new ListGroups(input.substring(first, end)));
    }}


  private void matchingLists() {
    sets = new LinkedList<ArrayList<Integer>>();
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
