package backEnd.commands;

import Controller.Control;
import java.util.LinkedList;

public class Heading extends Command {
  private final int NUMARGS = 0;
  private final double DEGREES = 360.0;
  private double currentHeading;

  public Heading(){
    super();
    super.numberOfArgs = NUMARGS;
  }

  public Heading(LinkedList<String> varargs, Control control) {
    super(varargs, control);
    super.numberOfArgs = NUMARGS;

    currentHeading = ((control.getTurtleAngle() % DEGREES) + DEGREES) % DEGREES;
    System.out.println("Current angle: " + currentHeading);
  }

  @Override
  public String commandValueReturn() {
    return Double.toString(currentHeading);
  }

  @Override
  public int repeatCom() {
    return super.repeatCom();
  }

}
