Okay so ....



* Front end
* Create an environment for users to experiment with SLogo commands. At the very least, it should allow the user to:

    * enter commands to the turtle interactively by entering text commands
        * display class
    * see the results of the turtle executing commands displayed visually
        * NOTE, the turtle starts in the center of the display which is considered (0, 0)
    * see errors that may result from entered commands in a user friendly way (i.e., not just printed to the console)
    * set a background color for the turtle's display area
    * set an image to use for the turtle
    * set a color to use for the pen
    * see commands previously run in the environment
    * see variables currently available in the environment
    * see user-defined commands currently available in the environment
    * choose the language in which SLogo commands are understood (here are a few translations)
    * access help about available commands
    * it could just be a "command reference page", like the assignment web page, or you could integrate it into the GUI like IntelliJ does, using these reference text files


* Back End
* Interpret an initial set of SLogo commands:

    * recognize these basic commands (in multiple languages)
    * throw errors that result from incorrectly entered commands