package Controller;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StoreLists {
  private Deque<String> lines;
  private Map<String,String> function;
  private Deque<String> words;
  private String functionName;

  public StoreLists(){
    function = new HashMap<>();
    words = new LinkedList<>();
    functionName ="";
  }

  public void storeFunction(String line){
    String func ="";
    LinkedList<String> words = new LinkedList();
    for(String lines :line.split("\n")){
    for (String word : lines.split(" ")) {
         words.add(word);
    }
   }
    String functionName = words.get(words.indexOf("to")+2);
    for (String word: words){
      if (!word.equals("to") && !word.equals(functionName) && !word.equals("[") && !word.equals("]")) {
        func = func + " " + word;
      }
    }
    function.put(functionName,func);
  }

  public Map getFunction(){
    return function;}
  public String getFunctionName(){return functionName;}

}
