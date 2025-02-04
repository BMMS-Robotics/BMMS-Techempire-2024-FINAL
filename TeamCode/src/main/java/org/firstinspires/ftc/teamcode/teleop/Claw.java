package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {

    // Class for claw logic

    //Variables

    //Hardware
    Servo clawL;
    Servo clawR;

    public Claw(HardwareMap hardwareMap) { //Claw init
        clawL = hardwareMap.get(Servo.class, "clawL");
        clawR = hardwareMap.get(Servo.class, "clawR");
    }

    public void close() { //Close the claw
        clawL.setPosition(0.415);
        clawR.setPosition(0.615);
    }

    public void open() { //Open the claw
        clawL.setPosition(0.7); 
        clawR.setPosition(0.3);
    }

    public void updateTelemetry(Telemetry telemetry) {
        telemetry.addData("CLAW_L_Position", clawL.getPosition());
        telemetry.addData("CLAW_R_Position", clawR.getPosition());
    }
}
