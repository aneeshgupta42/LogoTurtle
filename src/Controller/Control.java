package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import frontEnd.ErrorBoxes;
import frontEnd.Mover;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
  private static final String MAKE = "MakeVariable";
  private static final String COMMENT = "Comment";
  private final Parser parser;
  private String language;
  private Deque<String> command;
  private Deque<String> argument;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed = new TreeMap<>();
  private Mover myMover;
  private double turtleCol;
  private double turtleRow;
  private double turtleAngle;
  private LinkedList<String> args;
  private boolean inList;
  private int listStart;
  private int listEnd;
  /*
  Initializing a control (for reference storeLists is where all the data in lists is being passed)
   */
  public Control() {
    parser = new Parser();
  }

  /*
  Gets the variables that have been used by the user and returns them as a Map (variables are the key)
  @return variablesUsed
   */
  public Map getVariables() {
    return variablesUsed;
  }
  public Map getUserCommands() {return variablesUsed;}
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
    setCommand(input);
    setLanguage(language);
    System.out.println(input);
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parseText();
  }

  /*
    Splits text into lines
   */
  private void parseText() {
    command = new LinkedList<>();
    argument = new LinkedList<>();
    for (String line : input.split(NEWLINE)) {
      if(!line.contains("#")){
        organizeInStacks(line);
      }
    }
  }

  /*
  Splits lines into words and categorizes them into two stacks
   */
  private void organizeInStacks(String line) {
    args = new LinkedList<>();
    for (String word : line.split(WHITESPACE)) {
      if (word.trim().length() > 0) {
        if (!parser.getSymbol(word).equals(ARGUMENT) && !parser.getSymbol(word).equals(VARIABLE)) {
          command.push(word);
        }
        else{
            argument.push(word);
        }
      }
    }
    System.out.println(argument);
    System.out.println(command);
    coordinateCommands();
  }


  /*
  Getting the number of arguments for each command
   */
  public void coordinateCommands() {
    int argNum = 0;
    if(!command.isEmpty()) {
      for (int i=0;i<command.size();i++) {
        userCom = command.pop();
        makeClassPathToCommand();
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
  private void makeClassPathToCommand() {
    com = CLASS_PATH + parser.getSymbol(userCom);
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
        if (variablesUsed.containsKey(arg)) {
         args.push(variablesUsed.get(arg));
        }
        else {
          args.push(arg);
        }
      }
      argNum--;
      System.out.println(userCom);
      System.out.println("Numb "+args);
      checkIfCommandCanRun(argNum);
    }
  }


  /*
  Checks if you are not in the parsing of a list, and runs the command
   */
  public void runCommand() {
    System.out.println("GotHere " + userCom +"  " + args);
    obtainCommand();
  }

  /*
  Passes arguments to the command class and grabs a user function if it exists.
   */
  private void obtainCommand() {
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
      objectCommand = constructor.newInstance((Object) args, (Object) this);
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException | ExceptionInInitializerError e) {
      //error is thrown above
    }
  }

  /*
  Calls the methods of a command to continue parsing logic
   */
  public void createCommand(Command comm) {

    if (parser.getSymbol(userCom).equals(MAKE)) {
      variablesUsed.putAll(comm.getVariablesCreated());
      if(!command.isEmpty()) {
        System.out.println("Aqui" + variablesUsed);
        coordinateCommands();
      }

    }


      else if(comm.commandValueReturn()!=null){
        argument.push(comm.commandValueReturn());
        if(!command.isEmpty()) {
          coordinateCommands();
        }
    }


   else if(comm.repeatCom()!=0) {
      int loop = comm.repeatCom();
      setCommand(input.substring(input.indexOf("["), input.indexOf("]")));
      while (loop != 0) {
        parseText();
        loop--;
      }
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

