package Controller;

import java.util.Deque;
import java.util.LinkedList;

public class StoreLists {

  private Deque<String> coms;
  private Deque<String> args;

  public StoreLists(){
    coms = new LinkedList<>();
    args = new LinkedList<>();
  }

  public StoreLists(Deque<String> argument, Deque<String> command) {

  }

  public void store(String com, String arg){
    coms.push(com);
    args.push(arg);
  }

}
