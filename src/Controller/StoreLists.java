package Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StoreLists {
  private ArrayList<String> lines;
  private Map<String,String> function;
  private Deque<String> words;
  private String functionName;
  private String input;

  public StoreLists(){
    lines = new ArrayList<String>();
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
    String functionName = words.get(words.indexOf("to")+1);
    for (String word: words){
      if (!word.equals("to") && !word.equals(functionName)) {
        func = func + " " + word;
      }
    }
    function.put(functionName,func);
  }

  public Map getFunction(){
    return function;}
  public String getFunctionName(){return functionName;}
}
