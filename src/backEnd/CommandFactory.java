package backEnd;

import controller.Control;
import backEnd.commands.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ResourceBundle;

public class CommandFactory {
    private Command commandGiven;
    private Control myControl;
    private static final String CommandSubPackageResource = "resources.languages.CommandSubPackages";

    public CommandFactory(Control control)
    {
        myControl = control;
    }

    private String subPackageCommand(String commandName){
        System.out.println("INCOMING" + commandName);
        ResourceBundle CommandSubPackage = ResourceBundle.getBundle(CommandSubPackageResource);
        return CommandSubPackage.getString(commandName);
    }

    public int getNumArgs(String commandName) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String completeCommandName = subPackageCommand(commandName);
        Class cls = Class.forName(completeCommandName);
        Constructor constructor = cls.getConstructor();
        Object objectCommand = constructor.newInstance();
        commandGiven = (Command) objectCommand;
        return commandGiven.getNumberOfArgs();
    }

    public Command generateCommand(String commandName, List<String> args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String completeCommandName = subPackageCommand(commandName);
        Class cls = Class.forName(completeCommandName);
        Constructor constructor = cls.getConstructor(List.class, Control.class);
        Object objectCommand = constructor.newInstance((Object) args, (Object) myControl);
        commandGiven = (Command) objectCommand;
        return commandGiven;
    }

}
