package backEnd.commands;

import controller.Control;
import java.util.List;
import java.util.Map;

/**
 * @author Aneesh Gupta, Turner Jordan
 *
 * Command is an abstract class that is a super class for every command. It serves to ensure that each specific command
 * class contains a standard set of public methods that other classes can access. The class depends on the Control
 * package, because each command is initialized by passing in a control object.
 */
public abstract class Command {

  protected String commandReturn;
  protected Map<String, String> map;
  protected int numberOfArgs;
  protected Boolean store = false;
  protected double repeat = Double.MIN_VALUE ;
  protected double repeatNumber=0;

  /**
   * This Command method is an initializer with no parameters.
   */
  public Command(){

  }

  /**
   * This initializer is the standard to be used when initializing command objects. Each object takes in the varargs
   * variable to determine how many arguments are needed to run that command (for example, SUM takes in two arguments,
   * whereas forward takes in one). The control variable is a Control object, the object that serves as the interface
   * between the front end package and back end package. This is passed to allow the command objects a way to
   * communicate (indirectly) with the front end.
   *
   * @param varargs: the number of arguments that the command takes in
   * @param control: the control object that is currently being used
   */
  public Command(List<String> varargs, Control control) {}

  /**
   * The commandValueReturn method returns the result of the command as a string. It is used by the Control to report
   * the returned value to the front-end. In the abstract class, it is not actually implemented to return a usable
   * value. However, each command subclass implements the value to return a usable String value (usually computed
   * on command initialization).
   *
   * @return commandReturn: the String value representing the value returned by each command.
   */
  public String commandValueReturn(){
    return commandReturn;
  }

  /**
   * The getVariablesCreated method returns a map of any variables that have been created while the command is run, so
   * that the control and front end can take note of the variables.
   *
   * @return map: a Map containing the variables created by the command
   */
  public Map<String, String> getVariablesCreated(){return map;}

  /**
   * The getNumberOfArgs method returns the numberOfArgs that the command uses (which is stored on initialization).
   * This is used so that instance variables are not accessed directly.
   *
   * @return numberOfArgs, the number of arguments that the command used to execute.
   */
  public int getNumberOfArgs(){return numberOfArgs;}

  /**
   * The repeatCom method is used by commands that repeat (such as loops) to determine how many times the command must
   * be repeated. It returns 0 by default (since most commands don't use it).
   *
   * @return repeatNumber: number of times the command should be repeated
   */
  public double repeatCom(){return repeatNumber;}

  /**
   *
   * @return repeat, which determines whether the command is runnable or not in control. Not used by most commands
   */
  public double runnable(){return repeat;}

  /**
   * Checks if the command is a store command. The store variable defaults to false for most commands.
   *
   * @return store, a boolean that is used to check whether the command needs to be stored in history.
   */
  public boolean storeCommands(){ return store; }

}
