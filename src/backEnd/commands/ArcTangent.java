package backEnd.commands;

import controller.Control;
import backEnd.commands.Command;
import java.util.List;

public class ArcTangent extends Command {
    private static final int NUMARGS = 1;
    private double arcTanResult;

    public ArcTangent() {
        super();
        super.numberOfArgs = NUMARGS;
    }

    public ArcTangent(List<String> varargs, Control control) {
        super(varargs, control);
        super.numberOfArgs = NUMARGS;

        double argOne = Double.parseDouble(varargs.get(0));
        arcTanResult = Math.atan(Math.toRadians(argOne));

    }

    @Override
    public String commandValueReturn() {
        return Double.toString(arcTanResult);
    }
    @Override
    public double repeatCom() {
        return super.repeatCom();
    }
}
