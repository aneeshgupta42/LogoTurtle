package Controller;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StoreLists {
  private Deque<String> lines;
  private Map<String,String> function;
  private Deque<String> args;
  private Deque<String> words;
  private String functionName;

  public StoreLists(){
    function = new HashMap<>();
    args = new LinkedList<>();
    lines = new LinkedList<>();
    words = new LinkedList<>();
    functionName ="";
  }

  public void storeForArg(String line){
    for (String word:line.split(" ")) {
      if(!line.equals("[") && !line.equals("]") && !line.equals("For"))
        words.add(word);
    }
  }
  public void store(String line){
    if(!line.equals("[") && !line.equals("]") )
      lines.add(line);
  }
  public void storeArg(LinkedList<String> arg) {
    for (String s:arg) {
      args.push(s);
    }
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
      if (!word.equals("to") && !word.equals(functionName) && !word.equals("[") && !word.equals("]")) {
        func = func + " " + word;
      }
    }
    function.put(functionName,func);
  }

  public Deque getForArgs() {
    return words;
  }
  public Deque getCommands(){
    return lines;
  }
  public Deque getArguments(){
    return args;
  }
  public Map getFunction(){
    return function;}
    public int getLength(){
      if(lines.size()>0) return lines.size();
      else return 1;
    }
    public String getFunctionName(){return functionName;}

}
