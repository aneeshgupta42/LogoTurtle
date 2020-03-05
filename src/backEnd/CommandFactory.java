package backEnd;

import Controller.Control;
import backEnd.commands.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public class CommandFactory {
    public CommandFactory()
    private Command commandGiven;

    public Command initializeCommand(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName(commandName);
        Object objectCommand;
        Constructor constructor = cls.getConstructor();
        objectCommand = constructor.newInstance();
        commandGiven = (Command) objectCommand;
        return commandGiven;
    }

    public Command generateCommand(String commandName, LinkedList<String> args, Control control) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class cls = Class.forName(commandName);
        Object objectCommand;
        Constructor constructor = cls.getConstructor(LinkedList.class, Control.class);
        objectCommand = constructor.newInstance((Object) args, (Object) control);
        commandGiven = (Command) objectCommand;
        return commandGiven;
    }


    private void NameLogic(){

    }
    argNum = commandGiven.getNumberOfArgs();
}
