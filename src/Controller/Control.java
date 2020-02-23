package Controller;

import backEnd.ErrorHandler;
import backEnd.PenUpdate;
import backEnd.TurtleUpdate;
import backEnd.commands.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Deque;
import java.util.LinkedList;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String LIST_END ="ListEnd";
  private static final String LIST_START ="ListStart";
  private String COMMANDSWITHTWO[];
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Deque<String> command;
  private Deque<String> argument;
  private Deque<String> variable;
  private String arg;
  private String arg2;
  private String com;
  private String var;
  private String userCom;
  private String input;

  public Control() {
    COMMANDSWITHTWO = new String[]{"SetTowards", "SetPosition", "MakeVariable","Repeat", "DoTimes",
        "Sum",
        "Difference", "Product", "Quotient"};

    // pen = new PenUpdate();
    //  turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }

  public void passCommand(String command) {
    input = command;
  }

  public void passLanguage(String lang) {
    language = lang;
  }

  public void parseCommand() {
    Control m = new Control();
    m.passCommand(input);
    parser.addPatterns(language);
    parser.addPatterns("Syntax");
    parser.addPatterns("Functions");
    m.parseText(parser, input);
  }

  // Given commands, calls organization of commands

  //check the difference between constant variable command list comment
  private void parseText(Parser parser1, String lines) {
    command = new LinkedList<>();
    argument = new LinkedList<>();
    variable = new LinkedList<>();
    for (String line : lines.split(NEWLINE)) {
     /* if (line.contains("#")) {
        comment = line;
      } else */{
        for (String word : line.split(WHITESPACE)) {
          if (word.trim().length() > 0) {
            String symbol = parser1.getSymbol(word);
          //  System.out.print(symbol);
          //  System.out.println (" "+ word);
            if (!symbol.equals(null) && !symbol.equals(LIST_END) && !symbol.equals(LIST_START)) {
              if (parser1.getSymbol(word).equals(VARIABLE)) {
                variable.push(word);
              } else if (parser1.getSymbol(word).equals(ARGUMENT)) {
                argument.push(word);
              } else {
                command.push(word);
              }
            }
          }
        }
        coordinateCommands(parser1);
      }
    }

  }

  private void coordinateCommands(Parser parser1) {
    //check for an arg and when their is an arg,pop off a command for that arg
    if(!argument.isEmpty() && !command.isEmpty()){
      arg = argument.pop();
      userCom = command.pop();
      for (String key : COMMANDSWITHTWO) {
        if (key.equals(parser1.getSymbol(userCom))) {
          if(key.equals("MakeVariable") && !variable.isEmpty()){
            var = variable.pop();
          }
          if(!argument.isEmpty()) {
            arg2 = argument.pop();
          }
        }
      }
      makeClassPathToCommand(parser1);
      passCommand(parser1);
    }
    if(!command.isEmpty() && argument.isEmpty()){
      userCom = command.pop();
      makeClassPathToCommand(parser1);
      passCommand(parser1);
    }
  }

  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void passCommand(Parser parser1) {
    System.out.println(com);
    System.out.println(arg);
    System.out.println(arg2);
    System.out.println(var);
    Class cls = null;
    try {
      cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(String[].class);
      objectCommand = constructor.newInstance((Object) new String[]{arg, arg2,var, userCom});
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven, parser1);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }
  }

  public void createCommand(Command command, Parser parser1) {
    if(command.commandValueReturn()!=null){
      argument.push(command.commandValueReturn());
    }
    coordinateCommands(parser1);
  }
}

