parser - team 17
====
This project implements a development environment that helps users write SLogo programs.

Names: Libba Lou, Cayla Schuval, Aneesh Gupta, Turner Jordan

### Timeline
Start Date: February 20th, 2020

Finish Date: March 6th, 2020

Hours Spent: 180

### Primary Roles
Libba Lawrence: Implementing the controller module to interface between the front end and back end components. This includes implementing the Parser class to parse command strings taken from the front end.

Cayla Schuval: Responsible for implementing the entire front end package

Turner Jordan: Back-end package

Aneesh Gupta: Back-end package

### Resources Used
The CS308 Piazza board and lectures, Java and JavaFX documentation, and stack overflow for small fixes. 

### Running the Program
Main class: UserInterface
           Logo/Main

Data files needed: English.properties (and all other language property files), mover.png
turtle images and XML file to save settings into

Features implemented: 
*  multiple turtles
*  runs commands
*  multiple windows
*  can call turtles with commands
*  XML file to save setting
 *  muliple pen and background colors
 *  activating/deactivating turtles
 *  indexes for turtles
 *  default images and languages and colors
 *  preset buttons that move turtles foward and backward
 *  command input bar 
    
    

### Notes/Assumptions
Assumptions or Simplifications:  

* We assumed that the commands would each have a definite number of inputs.
* We assumed a a definite style for the properties files.
* We assumed that the pen follows the data points and the turtle also does, but they have separate coded paths. 

Interesting data files: 

* Logo 
* TurtleSavedRecent.XML


Known Bugs: 

* Lists within lists do not function properly with respect to parsing data
* Sometimes the turtle decides to stop drawing and you have to pendown to get it to work

Extra credit: NA

### Impressions
This project is much more extensive than the previous two, and was presented as much more open ended. Initially, our group was hesitant about implementing the design patterns discussed in class, specifically the Command Factory and the four APIs. However, throughout the project we were exposed to the benefits of using inheritance and a hierarchical structure, especially for Parsing. We also gained an understanding of the importance of properly defined APIs. We were able to effectively separate the tasks of the project into three main componenets following the MVC design pattern, and with the help of the APIs we were able to work on these parts seperately without conflict.  
