package backEnd.commands;

public class Forward extends Command {
  int initX, initY;
  double angle;
  
  public Forward(String... varargs){
    super(varargs);

    System.out.println(varargs[0]);
    System.out.println(varargs[2]);
  }

}
