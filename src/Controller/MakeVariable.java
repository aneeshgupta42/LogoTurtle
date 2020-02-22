package Controller;

public class MakeVariable extends Command {

  public MakeVariable(String[] varargs) {
    super(varargs);
    System.out.println(varargs[0]);
    System.out.println(varargs[1]);
  }
}
