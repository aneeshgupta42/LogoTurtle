package Controller;

public class Forward extends Command {

  public Forward(String... varargs){
    super(varargs);
    System.out.println(varargs[0]);
  }

}
