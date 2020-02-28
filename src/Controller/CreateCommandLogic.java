package Controller;

import java.util.LinkedList;

public class CreateCommandLogic {

  private static final String WHITESPACE = " ";
  private static final String NEWLINE = "\n";
  private static final String ARGUMENT = "Constant";
  private static final String VARIABLE = "Variable";
  private static final String CLASS_PATH = "backEnd.commands.";
  private static final String LIST_END = "ListEnd";
  private static final String LIST_START = "ListStart";
  private static final String COMMENT = "Comment";

  private Parser parser;
  private LinkedList<String> arguments;
  private LinkedList<String> commands;
  private LinkedList<String> theirArguments;
  private StoreLists storedInfo;


  public CreateCommandLogic(){
    Control control = new Control();
    storedInfo = new StoreLists();
    String input = control.getCommand();
    parser = new Parser();
    parser.addPatterns(control.getLanguage());
    parser.addPatterns("Syntax");
    parseText(input);
  }

  private void parseText(String input) {
    arguments = new LinkedList<>();
    commands = new LinkedList<>();
    theirArguments = new LinkedList<>();

    for(String line :input.split(NEWLINE)){
      for (String word:line.split(WHITESPACE)) {
        organize(word);
      }
    }
  }

  private void organize(String word) {
    String symbol = parser.getSymbol(word);

    if(symbol.equals(ARGUMENT)){
      arguments(word);
    }
    else if(symbol.equals(VARIABLE)){
      variables(word);
    }
    else {
      commands(word);
    }


  }

  private void arguments(String word) {
  }
  
  private void variables(String word) {
  }

  private void commands(String word) {
  }






}
