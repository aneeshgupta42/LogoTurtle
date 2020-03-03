package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import frontEnd.Mover;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
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
  private static final String STOREFUNCTION = "MakeUserInstruction";

  private Parser parser;
  private String language;
  private Deque<String> command;
  private Deque<String> argument;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed;

  private Mover myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;

  private LinkedList<String> args;
  private boolean logicStatement;
  private boolean storeFunction;
  private StoreLists lists;
  private boolean hasBeenStored = false;
  private boolean trueFalseStatement;
  private boolean canRun;
  private int logicInt;
  private String section ="";
  private LinkedList<Integer> starts;
  private LinkedList<Integer> ends;
  private LinkedList<ArrayList<Integer>> sets;
  private int numstarts;
  private int numends;
  private int first;
  private int end;
  private String variable;
  private boolean outsideLoop;
  private boolean once;
  private String saved;

  private static final String IF = "If";
  private static final String IFELSE = "IfElse";
  private static final String DOTIMES = "DoTimes";
  private static final String MAKE = "MakeVariable";
  private static final String FOR = "For";


  /*
  Initializing a control (for reference storeLists is where all the data in lists is being passed)
   */
  public Control() {
    lists = new StoreLists();
  }

  public Parser getParser(){return parser;}
  /*
  Gets the variables that have been used by the user and returns them as a Map (variables are the key)
  @return variablesUsed
   */
  public Map getVariables() {
    return variablesUsed;
  }
  public Map getUserCommands() {return lists.getFunction();}
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
  public String getCommand() {
    return input;
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
    parser = new Parser();
    variablesUsed = new TreeMap();
    command = new LinkedList<>();
    argument = new LinkedList<>();
    args = new LinkedList<>();
    numstarts =0;
    numends =0;
    once = true;
    canRun = true;
    saved = input;
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
    hasBeenStored = false;
    logicInt =0;
    for (String line : input.split(NEWLINE)) {
      if(!line.contains("#") && !line.isEmpty()){
        organizeInStacks(line);
      }
      coordinateCommands();
      System.out.println("These are commands" + command);
      System.out.println("These are arguments" + argument);
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
    System.out.println(word);
    Map<String, String> map = lists.getFunction();
    if(parser.getSymbol(word).equals(STOREFUNCTION)) storeFunction=true;
    else if (map.keySet().contains(word) && input!=map.get(word) && hasBeenStored==false) {
      input = map.get(word);
      parseText();
    }
    else if(storeFunction){
      lists.storeFunction(input);
      storeFunction = false;
      hasBeenStored = true;
    }
    else
       command.add(word);
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
          ErrorBoxes box = new ErrorBoxes(new ErrorHandler("InvalidCommand"));
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
      runCommand();
    } else {
      if (argument.size() >= argNum) {
        String arg = argument.pop();
        if(parser.getSymbol(arg).equals(VARIABLE)) {
          variable = arg;
          if (variablesUsed.containsKey(arg)) {
            args.push(variablesUsed.get(arg));
          }
          else args.push(arg);
        }
        else args.push(arg);
      }
      argNum--;
      System.out.println("Num "+args +" "+ userCom);
      checkIfCommandCanRun(argNum);
    }
  }

  /*
  Checks if you are not in the parsing of a list, and runs the command
   */
  public void runCommand() {
    System.out.println("GotHere " + userCom +"  " + args);
    if(trueFalseStatement){
      checkIfList();
      System.out.println("The list can run " + canRun);
      if(canRun) obtainCommand();
    }
    else {
      if (hasBeenStored == false) {
        System.out.println("Variable storing " + hasBeenStored);
        obtainCommand();
      }
    }
  }

  /*
This checks if you have entered into a list [ ]
 */
  private void checkIfList() {
    if (parser.getSymbol(userCom).equals(LIST_START)) {
      logicInt++;
      System.out.println(logicInt);
      if((logicStatement && logicInt==1)){
        canRun = true;
      }
      else if((!logicStatement && logicInt%2==0)){
        canRun =true;
      }
      else canRun = false;
    }
    if (parser.getSymbol(userCom).equals(LIST_END)) {
      if((logicStatement && logicInt==2)) canRun = false;
      else canRun = true;
    }
  }



  /*
  Passes arguments to the command class and grabs a user function if it exists.
   */
  private void obtainCommand() {
    System.out.println(com);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
      objectCommand = constructor.newInstance((Object) args, (Object) this);
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      //error is thrown above
      System.out.println("err");
    }
  }

  /*
  Calls the methods of a command to continue parsing logic
   */
  public void createCommand(Command comm) {

    if (parser.getSymbol(userCom).equals(MAKE) || parser.getSymbol(userCom).equals(DOTIMES)) {
      if(comm.getVariablesCreated()!=null) variablesUsed.putAll(comm.getVariablesCreated());
      System.out.println("Variables "+ variablesUsed);
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }

    if(comm.commandValueReturn()!=null){
      argument.add(comm.commandValueReturn());
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }

    if(parser.getSymbol(userCom).equals(IF)||parser.getSymbol(userCom).equals(IFELSE)){
      trueFalseStatement = true;
      logicStatement = comm.runnable();
      System.out.println("Can the logic run " + logicStatement);
      if(!command.isEmpty()) {
        coordinateCommands();
      }
    }

    if(comm.repeatCom()!=0) {
      int loop = comm.repeatCom();
      int i=1;
      if(parser.getSymbol(userCom).equals(DOTIMES)){
        System.out.println(variable);
        repCount(loop, variable);
      }
      else repCount(i, ":repCount");
      findLists(input);
      if(outsideLoop==false ){  //if there are lists
        section = input.substring(first, end); //get the value inside brackets
        setCommand(section); //set command to that set of values
      }
      recurseLoop(loop, i);
    }

    else if(!command.isEmpty()) coordinateCommands();
  }

  private void recurseLoop(int loop, int i) {
    if(loop==1){
        input = saved;
        outsideLoop=false;
        findLists(input);
        section = input.substring(first, end);
        setCommand(section);
    }
    if (loop >1) {
      i++;
      parseText(); //parse the text for those commands
      loop--; // subtract from that loop
      recurseLoop(loop,i); //repeat
    }
  }

  /*
  Makes variables for the repetitions of loops
   */
  private void repCount(int loop, String s) {
    args.clear();
    userCom = "make";
    args.push(s);
    args.push(loop+"");
    makeClassPathToCommand(userCom);
    obtainCommand();
  }



  private void findLists(String input) {
    ArrayList<Integer> two = new ArrayList<>();
    starts = new LinkedList<>();
    ends = new LinkedList<>();
    sets = new LinkedList<ArrayList<Integer>>();
    int index = input.indexOf("[");
    while (index >= 0) {
      starts.push(index);
      numstarts++;
      index = input.indexOf("[", index + 1);
    }
    int indextwo = input.indexOf("]");
    while (indextwo >= 0) {
      ends.push(indextwo);
      numends++;
      indextwo = input.indexOf("]", indextwo + 1);
    }
    matchingLists();
    ArrayList<Integer> set = new ArrayList<Integer>();
    if (sets.size() != 0) {
      set = sets.pollLast();
      if (parser.getSymbol(userCom).equals(DOTIMES) || parser.getSymbol(userCom).equals(FOR)) {
        set = sets.pollLast();
      }
      if (set.size() != 0) {
        first = set.get(0) + 1;
        end = set.get(1);
      }
    }
    else outsideLoop=true;
    System.out.println(sets);
  }


  private void matchingLists() {
    ArrayList<Integer> two = new ArrayList<>();
    while(numstarts>0) {
      int last=0;
      int first = starts.pop();
      if(parser.getSymbol(userCom).equals(DOTIMES)) last = ends.pop();
      else  last = ends.pollLast();
      two.add(first);
      two.add(last);
      numends--;
      numstarts--;
      sets.add(two);
      two = new ArrayList<>();
    }
  }


























  public void passTurtle(Mover mover) {
    myMover = mover;
    turtleRow = myMover.getMoverRow();
    turtleCol = myMover.getMoverCol();
    turtleAngle = myMover.getMoverAngle();
  }

  public double getTurtleCol() {
    return turtleCol;
  }

  public double getTurtleRow() {
    return turtleRow;
  }

  public void setPenDown(boolean mode){
    myMover.setPen(mode);
  }
  public boolean isPenDown(){
    return myMover.isPenDown();
  }

  public double getTurtleAngle() {
    return turtleAngle;
  }

  public void setTurtleVisible(boolean mode) {
    myMover.moverVisible(mode);
  }

  public boolean findTurtleVisibility() {
    return myMover.isMoverVisible();
  }

  public void updateTurtle(double col, double row, double angle, int distance) {
    turtleRow = myMover.getMoverRow() + row;
    turtleCol = myMover.getMoverCol() + col;
    turtleAngle = myMover.getMoverAngle() + angle;
    myMover.updateDistanceSoFar(distance);
    myMover.move(col, row, angle);
 //   System.out.println("update" + col + " " + row + " " + angle);
  }

  public int getTurtleDistance() {
    return myMover.getDistanceSoFar();
  }

  public void turtleHome(boolean clearScreen) {
    if (clearScreen) {
      myMover.clearScreen();
    } else {
      myMover.resetTurtle();
    }
  }

  public Mover getTurtle() {
    return myMover;
  }

  public double getTurtleRelativeXPos() {
    return turtleCol - myMover.getMoverCenterXPos();
  }

  public double getTurtleRelativeYPos() {
    return myMover.getMoverCenterYPos() - turtleRow;
  }
}

