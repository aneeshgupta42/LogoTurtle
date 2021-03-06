package backEnd.commands.TurtleControl;

import backEnd.commands.Command;
import controller.Control;

import java.util.List;

public class Home extends Command {
    private final int NUMARGS = 0;
    private int distance;
    public Home(){
        super();
        super.numberOfArgs = NUMARGS;
    }

    public Home(List<String> varargs, Control control){
        super(varargs, control);
        control.turtleHome(false);
        distance = control.getTurtleDistance();
    }

    @Override
    public String commandValueReturn() {
        String ret = Integer.toString(distance);
        return ret;
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }

}
