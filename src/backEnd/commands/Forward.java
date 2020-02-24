package backEnd.commands;

public class Forward extends Command {

  private static final int number = 1;
  public Forward(String... varargs){
    super(varargs);
    super.numberOfArgs =number;
  }

}
