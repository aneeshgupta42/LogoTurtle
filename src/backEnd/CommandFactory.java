package backEnd;

import controller.Control;
import backEnd.commands.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CommandFactory {
    private Command commandGiven;
    private Control myControl;

    public CommandFactory(Control control){
        myControl = control;
    }

    public int getNumArgs(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName(commandName);
        Constructor constructor = cls.getConstructor();
        Object objectCommand = constructor.newInstance();
        commandGiven = (Command) objectCommand;
        return commandGiven.getNumberOfArgs();
    }

    public Command generateCommand(String commandName, List<String> args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName(commandName);
        Constructor constructor = cls.getConstructor(List.class, Control.class);
        Object objectCommand = constructor.newInstance((Object) args, (Object) myControl);
        commandGiven = (Command) objectCommand;
        return commandGiven;
    }

}
