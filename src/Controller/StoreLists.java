package Controller;

import backEnd.commands.Command;
import java.util.Deque;
import java.util.LinkedList;

public class StoreLists {
  private Deque<String> lines;
  private Deque<Command> coms;
  private Deque<String> args;

  public StoreLists(){
    coms = new LinkedList<>();
    args = new LinkedList<>();
  }

  public void store(String line) {
      lines.push(line);
  }

  public void storeCom(Command command){
    coms.push(command);
  }

  public Command runCom(){
    for (Command c: coms) {
      coms.pop();
      System.out.println(c.getNumberOfArgs());
      System.out.println(c);
      return c;
    }
    return null;
  }

}
