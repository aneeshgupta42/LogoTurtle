package backEnd;

import java.util.ResourceBundle;


public class ErrorHandler extends RuntimeException{
  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String ERROR_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "ErrorTagNames";
  private static final ResourceBundle errorResources= ResourceBundle.getBundle(ERROR_PROPERTIES_FILENAME);


    public ErrorHandler(String property){
      super("COULD NOT CONFIGURE ANIMATION WITH GIVEN COMMAND: " + errorResources.getString(property));
    }
}