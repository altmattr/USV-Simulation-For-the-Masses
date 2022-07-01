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

~~~~~
// import the required libraries.
//  * our own api for javascript access

const { init, startSim } = require("../../apis/api.js");

// connect to the simulation
//  * tell the simulation what to run first (setup)
//  * tell the simulation what to run over and over (act)
const craft = init("ws://0.0.0.0:9090", setup, act);

// Start the station_keeping simulation
//  * need to first check there is not a simulation already running
if(craft.getTaskInfo().name == "None"){
  startSim("station_keeping", "http://0.0.0.0:8090");
}

// the function to run when the simulation starts
function setup(){
    console.log("setup is running")
}

// the function to run over and over during the simulation.
function act(){
    console.log("act is running")
    craft.imm.setLeftThrusterPower(1);

    // check each time to see if the simulation stopped, if it did, we can stop this application
    if (craft.getTaskInfo().state === "finished"){
        process.exit();
    }
}
~~~~~
