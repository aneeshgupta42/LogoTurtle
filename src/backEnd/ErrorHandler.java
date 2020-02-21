package backEnd;

public class ErrorHandler {

  public ErrorHandler() {

  }

  public static void handle() {
    System.out.println("Command not found");
  }

  public void handleCommandClassNotFound() {
    System.out.println("This command does not have a class yet");
  }
}