# Team 17 - Slogo Questions

## 2/13/20
## Cayla Schuval (cas169), Libba Lawrence (ll313), Aneesh Gupta (ag468), Turner Jordan (tgj5)


1. Parsing takes place when the user inputs a command in the form of text. To parse properly, we need an input channel, a mechanism to enter the command.
2. The result of parsing can be an object that describes what the 'result' of the command is, eg. `forward` or `angle change` or so on. We also need to log the entered commands.
3.  Command -> Parser (Catch errors). We can catch errors while parsing.
4. Commands can be viewed as objects (that may be inherited from a superclass). They will 'know' stuff like the paramter (eg. FORWARD _'50'_)
5. GUI update: have the Turtle object be removed from the screen, the action performed (ie, this objects paramters, like position or angle changed), and then put back on to the scene.

### CRC Cards:-

- Classes: 
    - Parser
        - Responsibilities
            - `parseCommand(String)`
            - `logCommand()`
            - `sendCommand()`
            - `validateCommand(...)`
        - Collaborators:
            - get info from View
            - send info to Model
    - View (Main Class)
        - Responsibilities:
            - `setupScene()`
            - `updateScene()`
            - `initializeScene()`
            - `errorDisplay()`
            - `receiveCommand()`
        - Collaborators:
            - Turtle (mover's display)
            - Parser (Parser parses commands entered in view)
    - Model
        - Responsibilities:
            - `serrorCatch()`
            - Command information
                - `forward()`
                - `backward()` etc.
            - `updatePosition()`
            - `interpretCommand()`
        - Collaborators:
            - Parser
            - Turtle

    - Turtle
        - Responsibilities:
            - location()
            - color()
            - angle()
            - lastCommand() 
                - keep a list of previous commands
            - currentCommand()
        - Collaborators:
            - View
            - Model