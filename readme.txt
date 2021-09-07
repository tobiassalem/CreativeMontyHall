
The Monty Hall Application
============================

Version: 0.1
Author: Tobias Salem
E.mail: tobias.salem@gmail.com


Background
------------
The Monty Hall Problem gets its name from the TV game show, “Let’s Make A Deal,” hosted by Monty Hall. 
The scenario is such: you are given the opportunity to select one closed door of three, behind one of which there is a prize. 
The other two doors hide “goats” (or some other such “non–prize”), or nothing at all. Once you have made your selection, 
Monty Hall will open one of the remaining doors, revealing that it does not contain the prize2. He then asks you if you 
would like to switch your selection to the other unopened door, or stay with your original choice. 
Here is the problem: Does it matter if you switch?

@See http://montyhallproblem.com/


Purpose
---------
The purpose of the application is to verify and report to the user that in the “Monty Hall problem”, 
it is statistically beneficial for you to switch doors.   


Method
--------
The method with which to achieve this is multi-faceted.

1) Let the user play many games of Monty Hall and have the statistical result reported.
2) Let the computer play many games of Monty Hall where the door is ALWAYS KEPT, and have the statistical result reported.
3) Let the computer play many games of Monty Hall where the door is ALWAYS SWITCHED, and have the statistical result reported.
4) Logical reasoning and mathematical statistics described in natural language in order to help reach the insight.


Requirements
---------------
*Java 8
@See http://www.java.com/

*Maven 3 
@See http://maven.apache.org/

To run the Monty Hall application use the Maven commands:
> mvn clean install
[wait for compile and tests to finish] 
>mvn exec:java

NOTE: for the best experience make sure you console window is at least 120 characters wide.


Possible improvements
-----------------------
*Add more test cases. Concretely add test cases for the simulation runs and verify the results are above/below certain thresholds.
*Add the game option of having more than three doors (to even more clearly illustrate the insight).
*Add network feature where the game session statistics is sent to a server for long term statistical analysis.
*Code improvements, e.g. extract and re-use common logic for the scenarios when the user plays the game and where games are simulated.
*Optimize performance
*Extract strings to property files or equivalent.
*Implement localization.
*Add graphics
*Add sound




