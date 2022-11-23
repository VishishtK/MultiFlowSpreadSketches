
Explanation of Files

BsktHLL.java
Uses an array of HLL to compute spread

VirtualBitMap.java
Uses bitset class to compute spread. Then executes the python script to plot the graph.
Requires python 3.9 matplotlib and pandas to execute the python script

Flow.java
Class structure to store a single flow and contains the flowId and the packtes of the flow

HashFunctions.java
This is the class that implements the hashing functionality with the FNV hashing algorightm.
Each Algorithm has an object of HashFunctions class which it uses to Hash the flowID.

App.java
This file is the main driver file which initiates the running of the programmed and tests
the algorithms with the dummy values mentioned in the assignment.