package backEnd.commands;

import Controller.Control;

public class Forward extends Command {
  private int initX, initY;
  private int distance;
  private final int number = 1;
  private double angle;
  public Forward(String... varargs){
    super(varargs);
    super.numberOfArgs=number;
    distance = Integer.parseInt(varargs[0]);
    initX =
  }

}
