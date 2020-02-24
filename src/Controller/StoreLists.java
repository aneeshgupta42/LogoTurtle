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
    lines = new LinkedList<>();
  }

  public void store(String line, String arg) {
      lines.push(line);
      args.push(arg);
    System.out.println(lines);
    System.out.println(args);
  }

  public Deque print(){
    return lines;
  }
  public Deque print2(){
    return args;
  }

  public void storeCom(Command command){
    coms.push(command);
  }

  public Command runCom(){
    for (Command c: coms) {
      return c;
    }
    return null;
  }

}
