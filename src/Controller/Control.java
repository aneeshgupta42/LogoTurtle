package Controller;

import backEnd.ErrorHandler;
import backEnd.PenUpdate;
import backEnd.TurtleUpdate;
import frontEnd.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class Control {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private View view;
  private PenUpdate pen;
  private TurtleUpdate turtle;
  private ErrorHandler error;
  private Parser parser;
  private String language;
  private String comment;
  private Stack<String> command;
  private Stack<String> argument;
  private String arg;
  private String com;
  private String input;

  public Control(String command) {
    input = command;
   // view = new View();
    pen = new PenUpdate();
    turtle = new TurtleUpdate();
    error = new ErrorHandler();
    parser = new Parser();
  }


  public void parseCommand() {
    Control m = new Control(input);
    //parser.addPatterns(language);
    parser.addPatterns("Syntax");
    System.out.println(input);
    m.parseText(parser, input);
  }


  // Given commands, calls organization of commands
  private void parseText(Parser parser, String lines) {
    command = new Stack<>();
    argument= new Stack<>();
    for (String line : lines.split(NEWLINE)) {
      if (line.contains("#")) {
        comment = line;
      } else {
        for (String word : line.split(WHITESPACE)) {
          if (word.trim().length() > 0) {
              organizeText(word,parser.getSymbol(word));
              System.out.println(String.format("%s : %s",word,parser.getSymbol(word)));
          }
        }
      }
    }
  }

  private void organizeText(String word, String symbol){
    if(symbol!=null){
    if(!symbol.equals(ARGUMENT)){
      command.add(word);
    }
    else{
      argument.add(word);
    }}
    coordinateCommands();
    System.out.println(command);
    System.out.println(argument);
  }

   //exact path of the command class

  //this is throwing an error -> the class path needs to be input like src/.resources etc.

//    Class<?> cls;
//  {
//    try {
//      cls = Class.forName(com);
//    } catch (ClassNotFoundException e) {
//      error.handle("error");
//    }
//  }

  private void coordinateCommands(){

    if(!argument.isEmpty()){
      arg = argument.pop();
      if(!command.isEmpty()){
        com = command.pop();

//        Object objectCommand;
//        try {
//          Constructor constructor = cls.getConstructor(int.class);
//          objectCommand = constructor.newInstance((Object) new int [Integer.parseInt(arg)]);
//          Command commandGiven = (Command) objectCommand;
//        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
//          error.handle("error");
//        }

      }
      System.out.println(command);
      System.out.println(argument);
    }

  }


}

