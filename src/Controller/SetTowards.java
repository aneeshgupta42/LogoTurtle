package Controller;

public class SetTowards extends Command {

  public SetTowards(String[] varargs) {
    super(varargs);
    System.out.println(varargs[0]);
    System.out.print(varargs[1]);

    // x and y are in reverse order
  }
}
