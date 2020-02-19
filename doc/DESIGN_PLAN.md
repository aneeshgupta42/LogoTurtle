# SloGo Plan
## Introduction
We are creating an application that implements an Integrated Development Environment for a simplified version of the Logo programming language, SLogo. We hope to provide an environment that is user friendly and powerful, in line with the 'low floor, high ceiling' principle that Logo was designed on. With our design, we want to provide an easy path for extensibility, specifically for adding more commands, customizing the IDE, or allowing users to create more complex programs in SLogo. The project will be open for extension and closed to modification. Our architecture will be designed so that the way that each class is implemented will be hidden from other classes. This will make it easy to add new features, without creating modifications. The overall program will take in a command and output an updated display based upon the user input. There will be a back-end and a front-end for the project. The back-end code will function to parse the commands and present that data to the turtle and the pen. Whereas the front end code will be a command window and a turtle display. The front end code will also involve creating and storing user data. 

## Overview
The basic layout of the program is designing interactions between frontend and backend components. In an event, such as a command being entered, the following pipeline is followed:

1. Send the command string to the backend (frontend external API)
2. Parse, and generate the actual command action in the backend (backend internal API)
3. Use the generated command to alter the state of elements like the Turtle or the pen (backend external)
4. Change visual elements, update the display, and so on (frontend internal API)

- Command Interface (Backend, Internal):
    - The Command Interface contains all the methods for updating the current states of the turtle and pen based on the inputted command. This interface is essentially the logic of the turtle and pen relocation and orientation. The classes this interface will be called in will be a parser abstract class that is extended by a turtle and pen parser classes as well as a general command parser class. 
- Update Interface (Backend, External):
    - The Update Interface will be responsible for communicating the back-end calculation of the updated data of the pen and turtle objects with the front end. 
- Display Interface (Frontend, Internal):
     - This interface will be used to handle styling and features that don't interact with the backend and are used by multiple classes. This can be stuff like displaying recently entered commands, sizing, display colors, etc.
- User Interface (Frontend, External):
    - This interface is primarily responsible for communicating necessary information provided to the front end with the backend. This class will provide methods that send the backend necessary configuration properties (e.g. a user selected language option) and sending user inputed commands as strings to the backend to be interpreted and executed. 
        
- Alternate Implementations 
    - Command Passing: 
        - In passing our command data from the view to the parser (front end to back end) we will either be using a list or a stack of some sort that can all be classifyed as a collection object to maintain encapsulation. 
    - Storing commands:
        - Store commands in a map or in a list of lists
    - Turtle and Pen
        - one turtle and one pen object
        - a front end versus a backend turtle and pen
    - Language and Command File
        - properties files or XML file
       
 -   Front-End:
        -   public View
            -   Scene setUpScene(int height, int width)
            -   private displayWindow()
            -   commandWindow()
            -   Collection getCommand()
            -   storeCommands()
            -   getLanguage()
            -   int getPosition()
            -   boolean getPenStatus()
        -   public Main
            -   step()
            -   start(Stage stage)

- Back-End 
    - public moveObject
        - locationXUpdate(int changeinXPos)
        - locationYUpdate(int changeinYPos)
        - orientationUpdate(int changeinAngle)
    - public turtleUpdate extends moveObject
    - public penUpdate extends moveObject
  -  public Parser (uses Interface)
     -   syntaxCheck(List commands)
     -   breakUpCommand(List commands)
     -   categorizeCommand(List commands)
  -  public errorHandler()
        - getError()
        - alertError()

![Correlation](https://i.imgur.com/zIXjxCg.jpg)

## User Interface
- User will have the ability to enter in text commands into the command line. The user will have the ability to enter in multiple lines of code at once as well.The overall user interface will include a command line and a display scene for the user to see the turtle move on the screen as a result of the commands inputted. Erroneous situations that will be reported to the user are bad commands as well as empty commands to the command line. The user interface will also have the ability to choose to run and/or clear the command line, as well as choose a language to type in. 
![MockUICopy](https://i.imgur.com/9VC3By0.png)

## Design Details 
* Command Interface (Backend, Internal):
    * The Command Interface will be responsible for associated the actions of the user with an event in back-end. For example, if the user types in "fwd 50", the goal is this interface is to tell the turtle object to move forward 50 units. This interface will implemented by a``Parser`` class. Furthermore, the ``Parser`` interface will have methods ``syntaxCheck()``, ``breakUpCommand()``, ``categorizeCommand()``. This API ccould easily be extended to include more objects and additional commands. This API adheres to this assignments key goals as it has the purpose of connecting the commands from the user with actual events that are hidden from te front-end. This is significant as it means that changing something in the front end will have no impact on the back-end implementation. This API will work directly with the User-Interface as the UI will be provided the command object containing to be decoded into an action.
* Update Interface (Backend, External):
    * The Update Interface will be responsible for communicating the back-end logic of the action to be executed with the front end, so that the display can be updated accordingly. For example, if a command tells the pen to rotate by 50 degrees, this calculation will be completed and then this API will be pass this information to the front `moveObject` class that will be extended by the `turtleUpdate` and `penUpdate` classes. These classes will hold information regarding the object's position and orientation. We will not directly store the object's position, as this would use front-end concepts in the back-end of our code. In a similar manner, the pen color and where the tip of the pen is located will be located in our `penUpdate` class. This API can easily be extended to create other objects that can move throughout our display. The interface will have the following public methods. This API adheres to this assignments key goals as it has the purpose of connecting the commands from the user with actual events that are hidden from te front-end. This API will work directly with the Display as actions will require the display to update. 
* Display Interface (Frontend, Internal):
    * The Display Interface will be responsible for updating the UI when the changes do not depend on the results of an executed command in the backend. This includes things like displaying recent command history, sizing, color, display language, and other visual preferences. For example, when the user sets a new image for the turtle, this class we update the display sprite without needing to communicate the change to the backend. We will have a ``View`` class that will host the UI view elements. It will initialize the UI to the default settings with the method Scene ``setUpScene()``. It will also have the methods ``displayWindow()`` and `commandWindow()` for initializing the JavaFX objects in the scene. It will also have methods to update the objects based on user preference input. This can be exstensible by allowing for multiple view objects to be created, so multiple different types of UI views can be utilized at the same time. 
* User Interface (Frontend, External):
    * The User Interface interface is primarily responsible for communicating information inputed to the front end to the back end. For example, when a command is entered by the user into the command line, the method ``Collection getCommand()`` method will be called to send the command to the backend parser to begin the execution process. This API is also responsible for sending information about user preferences to the backend when they are needed in the backend. The ``getLanguage()`` method and the ``getPosition`` method are responsible for communicating the command input language and the current position of the turtle from the front end whenever the backend needs these values. This interface is also responsible for updating the display when information is received from the backend regarding command execution or error reporting. 

## API as Code
**The Command Interface will have the following public methods:**
* syntaxCheck(List commands)
    * this method will take in a string or an object that contains the command provided by the user
    * this method will check if the command typed in by the user is valid
    * this method will throw an exception if the command is not valid that will communicate to the user that they typed in an invalid command
    * this will check that a valid number of arguments is given for a command   
* breakUpCommand(List commands)
    * this method will break down everything that the user entered into single individual commands that can be translated directly
* categorizeCommand(List commands)
    * this method will connect the string provided from the user with an actual event
* getCommand()
    * this method will be called by the `penUpdate` and `turtleUpdate` classes before they handle the calculations associated with the command line 

**The Update Interface will have the following public methods:**
* locationXUpdate(int changeInXPos)
    * this method will take the current xposition of the object and move it by the given integer    
* locationYUpdate(int changeInYPos)
    * this method will take the current yposition of the object and move it by the given integer
* orientationUpdate(int changeInAngle)
    * this method will rotate the object by the given angle

**The Display Interface will have the following public methods:**
* Scene setUpScene(int height, int width)
    * this method provides the initial configuration of the scene. It calls displayWindow() and commandWindow() to initialize the UI of the application
* storeCommand()
    * this method stores the current command in a data structure that will be used to keep the function and argument parts of the commands separate.
* displayWindow()
    * this method initializes the main turtle display window
* commandWindow()
    * this method initializes the main command line window
* step()
    * this method is called by the timeline 60 times per second to update the application display when needed
* start(Stage stage)
    * the javaFX required start method to begin the application
* updatePenColor(Color color)
    * this method is called when the pen color needs to be updated due to user input entering a new pen color.
* updateTurtleSprite(Image sprite)
    * this method is called when the user inputs a new turtle sprite and updates the sprite with the new image.

**The User Interface will have the following public methods:**
* int getPosition()
    * this method will be called in the backend when the position of the turtle on the display screen needs to be used by the backend.
* boolean getPenStatus()
    * this method will be called in the backend when the current status of the pen object in the display is needed.
* getLanguage()
    * this method will be called in the backend when the language of the commands being entered needs to be communicated.
* Collection getCommand()
    * this method will be called to send the text of a new command line entry to the backend parser object.

## Design Considerations 
This section describes:
- Moving on to implementation:
    - Actual implementation might differ from current API layout (and changes will be documented with rationale).
    - As a team, we still need to visualize and plan how the API structure, with four APIs, is reflected through formal, functional code. We have an accurate idea, on paper, about flows and interactions, but reflecting this through formal code will be a step further.
- Design discussions and thoughts:
    - One of the main things that the team spend time discussing was the overarching, high-level API layout.
    - Even though we have laid out the 4 APIs, (backend and frontend, external and internal for each), we still had to spend time on figuring out what methods would go in where, and what methods would not go in any APIs, for example.
    - We also spent some time discussing how parsing would take place. We came up with a structure, that comprises of one initial parsing classes, that then branches off to two different parsers, one for the turtle, one for the pen.
    - One view that was helpful in visualizing APIs was viewing them as _services_; external APIs are available as services to external packages and modules, while internal APIs service your own package and module, in terms of extending and deepening it.
- Assumptions and Dependencies:
    - We adopted a four-API model, with External and Internal APIs for Backend and Frontend.
    - We assumed that the program relies on user inputs, from the front end to enter commands and control the turtle, and consequently, sending the command to the backend is a service provided by the Frontend External API.
    - The frontend accepts commands in the form of strings, and the backend must be able to recognize these commands (which maybe in differing languages).


## Team Responsibilities
This section describes the program components each team member plans to take primary and secondary responsibility for and a high-level plan of how the team will complete the program.
- Libba Lawrence
    - Primary responsibility: external interface classes (both back and front end, passing/storing commands, receiving return commands from back-end)
- Cayla Schuval
    - Primary responsiblity: UI and front end code
    - (front end internal, display)
- Aneesh Gupta
     - Primary responsiblity: updating turtle and pen locations in back-end code (back end internal)
- Turner Jordan
    -  Primary responsibility: Parser and error throwing 
  
## Use Cases
Clearly show the flow of calls to public methods described in your design needed to complete each example below, indicating in some way which class contains each method called:
* The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the command is added to the environment's history. Note, it is not necessary to understand exactly how parsing works in order to complete this example, just what the result of parsing the command will be.
    1. `getCommand()`: Sends command (Input object) to backend
    2. go into `Parser` class:`syntaxCheck()`, `breakUpCommand()`, `categorizeCommand()`
    4. go into `turtleUpdate` class: `locationXUpdate()`, `locationYUpdate()`
    5. `View` class: `step()` -> `update()`
* The user sets the pen's color using the UI so subsequent lines drawn when the turtle moves use that color.
    1. `View` class: UI Button to update color
    2. `updatePenColor(colorChoice)`
    3. `Pen class`: `setPenColor()` (which will use `getPenColor()` from frontend external API)
<!-- * Each member of the team should create two use cases of their own (and the appropriate example code) for the part of the project for which they intend to take responsibility. -->
* `RIGHT 90` is entered by the user:
    1. `getCommand()`: Sends command (`Input` object) to backend
    2. go into `Parser` class:`syntaxCheck()`, `breakUpCommand()`, `categorizeCommand()`
    4. go into `turtleUpdate` class: `orientationUpdate()`
    5. `View` class: `step()` -> `update()`
* Language is changed to German:
    1. in `View` class: Using a dropdown, select language. 
    2. `getCommand()`: will send the command (`Input` object) to the backend for parsing and continued step. In this `Input` object, a language instance variable will be set to German
    3. In Parsing, we can load in the German properties file and translate the commands. `loadLanguage(String language)`, `translateCommand(String command)`.
* User enters in a function (set of commands)
    1. `storeCommand` will store the function into a data structure (most probably a map) with the function name as a key.
    2. The map will be consulted against every command inputted to see if that function is called.
    3. If that function is called, those commands will be passed to `getCommand()` to send the command input to the back end code to be parsed. 
* User enters just a number with no command
    1. the user will enter a command into the command window that just contains a number
    2. `storeCommand` will store the function into a data structure where the number will be saved to the argument stack and nothing is saved to the command stack
    3. When the argument is paired to the command, an error will be thrown stated that there is no command
* User enters one argument to something that requires two arguments
    1. the user will enter a command with one argument into the command window that just requires two arguments
    2. `storeCommand` will store the function into a data structure where one number will be saved to the argument stack and the commands will be saved for the command stack
    3. When the argument is paired to the command, an error will be thrown stated that there is an invalid numbe of arguments
* `fd fd 50` is entered by the user:
    1. the user will enter a command with one argument into the command window that just requires two arguments
    2. `storeCommand` will store the function into a data structure where `50` will be saved to the argument stack and `fd` and `fd` will be saved for the command stack
    3. When the argument will be paired to the number. This will then be passed as an argument to the next `fd` and then sent to the `turtleUpdate` class
    4. `turtleUpdate` will call `orientationUpdate()` 
    5. this updated orientation will then be sent to the view to update visualization
* User enters a command that is not supported by the Logo interpreter:
    1. the user will enter a command into the comman window with a command that is not supported by the logo interpreter
    2. getCommand() is called and sends command (Input object) to backend
    4. the parser calls syntaxCheck(), breakUpCommand(), and caregorizeCommand(), which then throws an error due to the invalid command
    5. an error is sent to the front end to be displayed with displayError()
* ``HOME`` command is entered by the user:
    1. the user enters the HOME command into the command interface.
    2. getCommand() is called and sends command (Input object) to backend
    3. the parser class calls the methods syntaxCheck(), breakUpCommand(), and categorizeCommand() to initialize the correct command subclass (HomeCommand)
    4. the turtleClass is used with the methods orientationUpdate() to return the turtle to the straightforward position and locationXUpdate() and locationYUpdate() to set the turtle to position 0,0
    5. View class: step() -> update()


