package backEnd;

import Controller.Command;

public class Forward extends Command {

  public Forward(String... varargs){
    super(varargs);
    System.out.println(varargs[0]);
    System.out.println(varargs[2]);
  }

}
