# Team 17 - Slogo APIs

## 2/13/20
## Cayla Schuval (cas169), Libba Lawrence (ll313), Aneesh Gupta (ag468), Turner Jordan (tgj5)


## External Goals:
- Communicating between View-Parser-Model
- Communicating between Model-Turtle
- Communicating between Turtle-View

APIs:
1. Separating graphical interface from interpretor and how they communicate
    - Parser
        - Passing the information from the interface (View) to the interpreter(Model)
    - Turtle 
        - Passing the result from model into view
3. what object will be used for communication
    - Command objects -> to pass and recieve commands
    - Turtle Object 
    - Parser information
    - Pass errors -> exceptions

## Internal Goals:
- Adding more commands in Model
- Adding more error catches in Parser
- Adding more features to the Turtle
- Adding more elements to the View

APIs:
1. Paths For Extension
    - Abstract Command class: stuff like Forward, Backward, Rotate, etc. can be inherited from here.
    - Abstract Animal class: stuff like Turtles, Frogs, multiple moving features
    - 
3. New features
    - APIs should be closed 
    - implementing new features : inheritance and extending classes
# Use Cases:
- The user types 'fd 50' in the command window, sees the turtle move in the display window leaving a trail, and has the command added to the environment's history.
    - Command passed from view to Parser
    - Parser set up the command object and loads in the information
    - Model takes the command object and updates the status of the turtle
    - Turtle object in the view class as well is then updated 
- The user types '50 fd' in the command window and sees an error message that the command was not formatted correctly.
    - Command passed from view to parser
    - Parser registers an error with the command (Could also be the Model or an additional command class)
    - Throws an exception and sends the info to view (bypasses model)(store in model?)
    - View pops up dialog error box
- The user types 'pu fd 50 pd fd 50' in the command window and sees the turtle move twice (once without a trail and once with a trail).
    - Same as above 
    - Parser or command class indictaes there are multiple commands and then the turtle is updated twice accordingly and same with the model and the view
- The user changes the color of the environment's background.
    - Button in view can just change background image of scene. `setSceneColor(Color newColor)`
    