package Controller;

public abstract class Command {

  private int turtleXVal;
  private int turtleYVal;
  private int penXVal;
  private int penYVal;
  private int turtleAngle;
  private int penAngle;
  private int penColor;
  private int turtleColor;
  private String language;
  private String myCommand;


  public Command(String command) {
    myCommand = command;
  }

  public Command(String[] varargs) {

  }

  public int getTurtleCurrentX() {
    return turtleXVal;
  }

  public int getTurtleCurrentY() {
    return turtleYVal;
  }

  public int getPenCurrentX() {
    return penXVal;
  }

  public int getPenCurrentY() {
    return penYVal;
  }

  public int getTurtleAngle() {
    return turtleAngle;
  }

  public int getPenAngle() {
    return penAngle;
  }

  public int getPenColor() {
    return penColor;
  }

  public int getTurtleColor() {
    return turtleColor;
  }

  public String getLanguage() {
    return language;
  }

}
