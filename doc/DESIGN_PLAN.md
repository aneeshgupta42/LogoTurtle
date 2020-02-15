# SloGo Plan


## Introduction

This section describes:
- [ ] the problem your team is trying to solve by writing this program, the primary design goals of the project (i.e., where is it most flexible)
    - We are creating an application that implements an Integrated Development Environment for a simplified version of the Logo programming language, SLogo. We hope to provide an environment that is user friendly and powerful, in line with the 'low floor, high ceiling' principle that Logo was designed on. With our design, we want to provide an easy path for extensibility, specifically for adding more commands, customizing the IDE, or allowing users to create more complex programs in SLogo. 
- [ ] The primary architecture of the design (i.e., what is closed and what is open)
    - The project will be open for extension and closed to modification. Our architecture will be designed so that the way that each class is implemented will be hidden from other classes. This will make it easy to add new features, without creating modifications. 
- [ ]  Discuss the program at a high-level (i.e., without referencing specific classes, data structures, or code).
    - The overall program will take in a command and output an updated display based upon the user input. There will be a back-end and a front-end for the project. The back-end code will function to parse the commands and present that data to the turtle and the pen. Whereas the front end code will be a command window and a turtle display. The front end code will also involve creating and storing user data. 

## Overview

The basic layout of the program is designing interactions between frontend and backend components. In an event, such as a command being entered, the following pipeline is followed:

1. Send the command string to the backend (frontend external API)
2. Parse, and generate the actual command action in the backend (backend internal API)
3. Use the generated command to alter the state of elements like the Turtle or the pen (backend external)
4. Change visual elements, update the display, and so on (frontend internal API)


- Command Interface (Backend, Internal):
    - The Command Interface contains all the methods for updating the current states of the turtle and pen based on the inputted command. This interface is essentially the logic of the turtle and pen relocation and orientation. The classes this interface will be called in will be a parser abstract class that is extended by a turtle and pen parser classes as well as a general command parser class. 
- CommandView Interface (Backend, External):
    - The CommandView Interface will be responsible for communicating the back-end calculation of the updated position of the pen and turtle objects with the front end. This interface will 
- Display Interface (Frontend, Internal):
     - This interface will be used to handle styling and features that don't interact with the backend. This can be stuff like displaying recently entered commands, sizing, display colors, etc.
- User Interface (Frontend, External):
    - This interface is primarily responsible for communicating necessary information provided to the front end with the backend. This class will provide methods that send the backend necessary configuration properties (e.g. a user selected language option) and sending user inputed commands as strings to the backend to be interpreted and executed. 
        
- Alternate Implementations 
    - Command Passing: 
        - In passing our command data from the view to the parser (front end to back end) we will either be using a list or a stack of some sort that can all be classifyed as a collection object to maintain encapsulation. 
    - Storing commands:
        - Store commands in a map or in a list of lists
    - Turtle and Pen
        - turtle and pen objects
    - Language and Command File
        - properties files or XML
    -   Turtle object completely hidden from front end, same with Pen
        -   a front end versus a backend turtle and pen



 -   Front-End:
        -   public View
            -   Scene setUpScene(int height, int width)
            -   displayWindow()
            -   commandWindow()
            -   Collection getCommand()
            -   getLanguage()
            -   int getPosition()
            -   boolean getPenStatus()
        -   public Main
            -   step()
            -   start(Stage stage)

- Back-End 
    - public turtleUpdate
        - locationXUpdate()
        - locationYUpdate()
        - orientationUpdate()
        - math()
    -   public penUpdate
        -    locationXUpdate()
         - locationYUpdate()
         -   orientationUpdate()
          -   math()
   - public abstract Parser
     -   syntaxCheck()
     -   breakUpCommand()
     -   categorizeCommand()
    -   public TurtleParser extends Parser
    -   public PenParser extends Parser
     -  public errorHandler()
           -   getError()
        -   alertError()




![Overview](https://i.imgur.com/CYwR9fJ.jpg)
![Correlation](https://i.imgur.com/zIXjxCg.jpg)

## User Interface
This section describes:
- User will have the ability to enter in text commands into the command line. The user will have the ability to enter in multiple lines of code at once as well.The overall user interface will include a command line and a display scene for the user to see the turtle move on the screen as a result of the commands inputted. Erroneous situations that will be reported to the user are bad commands as well as empty commands to the command line. The user interface will also have the ability to choose to run and/or clear the command line, as well as choose a language to type in. 
![MockUICopy](https://i.imgur.com/9VC3By0.png)


## Design Details 
This section describes:
- [ ] each API introduced in the Overview in detail (as well as any other sub-components that may be needed but are not significant to include in a high-level description of the program)
- [ ] Describe how each API supports specific features given in the assignment specification
    - [ ] what resources it might use 
- [ ] how it is intended to be used
- [ ]  how it could be extended to include additional requirements (from the assignment specification or discussed by your team)
- [ ] justify the decision to create each class introduced with respect to the design's key goals, principles, and abstractions
- [ ] this section should go into as much detail as necessary to cover all your team wants to say.


1. CommandViewInterface (Backend, External)
    - parsing
    - received from view to back end
    - get command from front end
2. CommandView (backend external)
    - passes the current positions / location to the front end
3. DisplayInterface(front end internal)
    - eee
4. UserInterface(front end external)
    - passing information from user interface
## API as Code
- [ ] Your APIs should be written as Java interfaces, types that cannot contain instance variables or private methods, in appropriate packages
- [ ] These should be Java code files that compile and contain extensive comments to explain the purpose of each interface and each method within the interface (note this code can be generated directly from a UML diagram). 
- [ ] Include any Exceptions you plan to throw because of errors that might occur within your methods. Note, this does not require that all of these types will remain as interfaces in the final implementation, just that the goal is for you to focus on each type's behavior and purpose. 
- [ ] Also include the steps needed to complete the Use Cases below to help make your ideas more concrete.
## Design Considerations 
This section describes:
- [ ] any issues which need to be addressed or resolved before attempting to devise a complete design solution
- [ ]  Include any design decisions that the group discussed at length and describe at least one alternative in detail (including pros and cons from all sides of the discussion)
- [ ] Describe any assumptions or dependencies regarding the program that impact the overall design
- [ ] This section should go into as much detail as necessary to cover all your team wants to say.

## Team Responsibilities
This section describes the program components each team member plans to take primary and secondary responsibility for and a high-level plan of how the team will complete the program.

- Libba Lawrence
    - Primary responsiblity: UI and front end code
- Cayla Schuval
    - Primary responsibility: updating turtle and pen locations in back-end code
- Aneesh Gupta
    - Primary responsibility: Parser classes and error throwing
- Turner Jordan
    - Primary responsiblity: updating turtle and pen locations in back-end code


## Use Cases
Clearly show the flow of calls to public methods described in your design needed to complete each example below, indicating in some way which class contains each method called:

- [ ] The user types 'fd 50' in the command window, and sees the turtle move in the display window leaving a trail, and the command is added to the environment's history.Note, it is not necessary to understand exactly how parsing works in order to complete this example, just what the result of parsing the command will be.
- [ ] The user sets the pen's color using the UI so subsequent lines drawn when the turtle moves use that color.
- [ ] Each member of the team should create two use cases of their own (and the appropriate example code) for the part of the project for which they intend to take responsibility. 


