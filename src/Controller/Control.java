package Controller;

import backEnd.ErrorHandler;
import backEnd.commands.Command;
import backEnd.commands.MakeVariable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String LIST_END = "ListEnd";
  private static final String LIST_START = "ListStart";
  private String[] COMMANDSWITHTWO;
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Deque<String> command;
  private Deque<String> argument;
  private String arg;
  private String arg2;
  private String com;
  private String userCom;
  private String input;
  private Map<String, String> variablesUsed = new HashMap<>();
  ;

  public Control() {
    COMMANDSWITHTWO = new String[]{"SetTowards", "SetPosition", "MakeVariable", "Repeat", "DoTimes",
        "Sum", "Difference", "Product", "Quotient"};
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
    for (String line : lines.split(NEWLINE)) {
     /* if (line.contains("#")) {
        comment = line;
      } else */
      System.out.println(variablesUsed);
      for (String word : line.split(WHITESPACE)) {
        if (word.trim().length() > 0) {
          String symbol = parser1.getSymbol(word);
          if (!symbol.equals(null) && !symbol.equals(LIST_END) && !symbol.equals(LIST_START)) {

            if (!parser1.getSymbol(word).equals(ARGUMENT) && !parser1.getSymbol(word).equals(VARIABLE)) {
              command.push(word);

            } else {

              if (parser1.getSymbol(word).equals(VARIABLE)) {
               if(variablesUsed.containsKey(word)){
                 System.out.println(variablesUsed.get(word));
                    argument.push(variablesUsed.get(word));
                }
               else{
                 argument.push(word);
               }
              }
              else{
                argument.push(word);
              }
            }

          }
        }
        coordinateCommands(parser1);
      }
    }
  }

  private void coordinateCommands(Parser parser1) {
    System.out.println(argument);
    System.out.println(command);
    if (!argument.isEmpty() && !command.isEmpty()) {
      arg = argument.pollLast();
      userCom = command.pop();
      for (String key : COMMANDSWITHTWO) {
        if (key.equals(parser1.getSymbol(userCom))) {
          if (!argument.isEmpty()) {
            arg2 = argument.pop();
          }
        }
      }
      makeClassPathToCommand(parser1);
      passCommand(parser1);
      if (!command.isEmpty() && argument.isEmpty() ) {
        userCom = command.pop();
        makeClassPathToCommand(parser1);
        passCommand(parser1);
      }
    }
  }

  private void makeClassPathToCommand(Parser parser1) {
    com = CLASS_PATH + parser1.getSymbol(userCom);
  }

  public void passCommand(Parser parser1) {
    System.out.println(com);
    System.out.println(arg);
    System.out.println(arg2);
    try {
      Class cls = Class.forName(com);
      Object objectCommand;
      Constructor constructor = cls.getConstructor(String[].class);
      objectCommand = constructor.newInstance((Object) new String[]{arg, arg2, userCom});
      Command commandGiven = (Command) objectCommand;
      createCommand(commandGiven, parser1);
    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
      error.handleCommandClassNotFound();
    }
  }

  public void createCommand(Command command, Parser parser1) {
    if (command.commandValueReturn() != null) {
      argument.push(command.commandValueReturn());
      System.out.println(command.commandValueReturn());
    }
    if (parser1.getSymbol(userCom).equals("MakeVariable")) {
      variablesUsed = command.getVariablesCreated();
      System.out.print(variablesUsed);
    }
    coordinateCommands(parser1);
  }
}

