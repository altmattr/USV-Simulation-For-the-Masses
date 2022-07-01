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