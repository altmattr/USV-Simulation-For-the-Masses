# USV Simulation For the Masses (USV_FTM)

This software opens the field of USV software creation to any programmer.  No special tooling, no special skills, and no special devices are required to get started.

The underlying simulation is state-of-the-art so your code is being tested against the best possible simulation of the marine environment.

This is your starting point for a journey into software for marine simulations.

# Usage

This is the simplest way to use the software.  [Get a gitpod account (it is free)](https://gitpod.io) and then follow these steps:
  * Make sure you have [the Gitpod Local Companion](https://www.gitpod.io/docs/ides-and-editors/local-companion) installed on your computer.
  * [Follow this link to setup your own gitpod workspace with the tool installed](https://gitpod.io/#https://github.com/altmattr/USV-Simulation-For-the-Masses).
  * That's it!


# Tutorial - Hello World

In this demonstration, we will start a simulation, make the usv move in a circle, then stop the simulation.   We will write our program in Javascript.


This repository contains a docker image to run a [vrx gazebo simulation](https://github.com/osrf/vrx). This simulation uses [ROS](http://wiki.ros.org) for communication. In addition, we use the [Rosbridge suite]((http://wiki.ros.org/rosbridge_suite)) to allow for communication between non-ros clients such as the browser or a [node](https://nodejs.org/en/) applicaiton.
