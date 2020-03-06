package Controller;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class StoreLists {
  private static final String TO = "to";
  private static final String NEWLINE = "\n";
  private static final String SPACE = " ";
  private static final String INTIALIZER = "";
  private Map<String,String> function;
  private String input;

  public StoreLists(){
    function = new TreeMap<>();
  }

  public void storeFunction(String command, String line){
    String func = INTIALIZER;
    LinkedList<String> words = new LinkedList();
    for(String lines :line.split(NEWLINE)){
      for (String word : lines.split(SPACE)) {
        words.add(word);
      }
    }

    String functionName = command;
    for (String word: words){
      if (!word.equals(TO) && !word.equals(functionName)) {
        func = func + SPACE + word;
      }
    }
    function.put(functionName,func);
  }

  public Map getFunction(){return function;}

}
